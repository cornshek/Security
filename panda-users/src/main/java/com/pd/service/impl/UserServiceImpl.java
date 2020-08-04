package com.pd.service.impl;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pd.jsonconversion.DataMap;
import com.pd.mapper.IAccountMapper;
import com.pd.mapper.IPersonMapper;
import com.pd.mapper.IUserMapper;
import com.pd.pojo.Account;
import com.pd.pojo.Person;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import com.pd.service.IUserService;
import com.pd.util.*;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author GTY
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IAccountMapper accountMapper;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private IPersonMapper personMapper;

    @Override
    public MessageResult<?> insertUser(User user, String note) throws Exception {
        //验证输入的验证码是否正确
        Object redisNote = redisTemplate.opsForValue().get(user.getTelephoneNumber());
        MessageResult<?> objectMessageResult = new MessageResult<>();
        if (redisNote != null && note.equals(redisNote)) {
            //将密码进行加密
            user.setPassword(EncryptUtils.md5s(user.getPassword() + EncryptUtils.SALT));
            int insert = userMapper.insert(user);
            if (insert == 1) {
                objectMessageResult.setCode("20000");
                objectMessageResult.setMsg("注册成功！");
                objectMessageResult.setData(user);
                //注册成功，将验证码删除
                redisTemplate.expire(user.getTelephoneNumber(), 0, TimeUnit.SECONDS);
            } else {
                objectMessageResult.setCode("20001");
                objectMessageResult.setMsg("后台处理异常！");
            }
        } else if (redisNote != null && !note.equals(redisNote)) {
            //验证码输入错误，添加失败
            objectMessageResult.setCode("20001");
            objectMessageResult.setMsg("验证码错误！");
        } else {
            //验证码输入错误，添加失败
            objectMessageResult.setCode("20001");
            objectMessageResult.setMsg("验证码失效！");
        }
        return objectMessageResult;
    }

    @Override
    public MessageResult<?> getNote(String phone) throws Exception {
        MessageResult<?> message = new MessageResult<>();
        //创建短信类
        SendNote sendNote = new SendNote();
        SendNote.NoteData yihu = sendNote.yihu(phone);
        Element root = (Element) yihu.getResultData();
        //返回值为2时，表示提交成功   0为失败
        String code = root.elementText("code");
        //提交结果的描述
        String msg = root.elementText("msg");
        //当提交成功后，此字段的流水号
        String smsid = root.elementText("smsid");
        //2状态表示发送成功
        String flag = "2";
        if (flag.equals(code)) {
            //存放在redis缓存中
            redisTemplate.opsForValue().set(phone, yihu.getNote(), 30, TimeUnit.MINUTES);
            message.setCode("20000");
            message.setMsg("验证码已发送至您手机！");
        } else {
            message.setCode("20001");
            message.setMsg("没钱了！");
        }
        return message;
    }

    @Override
    public MessageResult<?> noteMobileLogin(String phone, String note) throws Exception {
        MessageResult<Object> objectMessageResult = new MessageResult<>();
        Object rs = redisTemplate.opsForValue().get(phone);
        if (rs == null) {
            objectMessageResult.setCode("20001");
            objectMessageResult.setMsg("验证码失效！");
        } else {
            String sessionNote = rs.toString();
            if (note.equals(sessionNote) && sessionNote != null) {
                objectMessageResult.setCode("20000");
                objectMessageResult.setMsg("登录成功");
                //删除redis缓存，让验证码失效
                redisTemplate.expire(phone, 0, TimeUnit.SECONDS);
            } else {
                objectMessageResult.setCode("20001");
                objectMessageResult.setMsg("验证码错误！");
            }
        }
        return objectMessageResult;
    }

    @Override
    public MessageResult<?> findCapitalAndPasswordLogin(Account account) throws Exception {
        QueryWrapper<Account> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(Account::getCapitalAccountNumber, account.getCapitalAccountNumber())
                .eq(Account::getCapitalPassword, account.getCapitalPassword());
        Account rs = accountMapper.selectOne(userQueryWrapper);
        return messageResult(rs);
    }

    @Override
    public MessageResult<?> findPhoneAndPasswordLogin(User user) throws Exception {
        //将密码进解密，将密码变成了存储到数据的加密密码
        user.setPassword(EncryptUtils.md5s(user.getPassword() + EncryptUtils.SALT));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getTelephoneNumber, user.getTelephoneNumber())
                .eq(User::getPassword, user.getPassword());
        User rs = userMapper.selectOne(userQueryWrapper);
        //创建session,存储用户
        httpSession.setAttribute("user", rs);
        redisTemplate.opsForValue().set("user",rs);
        return messageResult(rs);
    }

    private MessageResult<?> messageResult(Object object) {
        MessageResult<?> messageResult = new MessageResult<>();
        if (object == null) {
            messageResult.setCode("20001");
            messageResult.setMsg("账号或者密码错误！");
        } else {
            messageResult.setCode("20000");
            messageResult.setMsg("登录成功！");
            messageResult.setData(object);
        }
        return messageResult;
    }

    @Override
    public PageInfo<User> checkUserList(Integer page, Integer pageSize) throws Exception {
        PageHelper.startPage(page, pageSize, true);
        List<User> checkUserList = userMapper.checkUserList();
        PageInfo<User> pageInfo1 = new PageInfo<>(checkUserList);
        return pageInfo1;
    }

    @Override
    public User checkUser(Integer userId) throws Exception {
        User user = userMapper.selectByIdUser(userId);
        return user;
    }

    @Override
    public int modifyStatus(Account account) throws Exception {
        int updateById = accountMapper.updateById(account);
        return updateById;
    }

    @Override
    public int checkIdCard(String idcard, String name, String bankcard, String mobile) throws Exception {
        String url = "https://bankcard4c.shumaidata.com/bankcard4c";
        String appCode = "c05c7f6e47264f63953ce00c71ccdc3c";
        Map<String, String> params = new HashMap<>();
        params.put("idcard", idcard);
        params.put("name", name);
        params.put("bankcard", bankcard);
        params.put("mobile", mobile);
        int result = BankCard.get(appCode, url, params);
        return result;
    }

    @Override
    public String checkIdCardImage(String cardImagePath, String humanFaceImagePath) throws Exception {
        //对图片进行转码base64
        String cardImage = ImageBase64.ImageToBase64("H:\\身份证证件\\qq.jpg");
        String humanFaceImage = ImageBase64.ImageToBase64("H:\\身份证证件\\ww.jpg");
        String host = "http://idcardfacerecognition.sinosecu.com.cn";
        String path = "/api/faceliu.do";
        String method = "POST";
        String appcode = "c05c7f6e47264f63953ce00c71ccdc3c";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("img1", cardImage);
        bodys.put("img2", humanFaceImage);

        /**
         * 重要提示如下:
         * HttpUtils请从
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
         * 下载
         *
         * 相应的依赖请参照
         * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
         */
        HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
        //获取response的body
        String body = EntityUtils.toString(response.getEntity(),"UTF-8");
        return body;
    }

    @Override
    public User verify(User User) throws Exception {
        return null;
    }

    @Override
    public MessageResult<?> insertPerson(Person person) throws Exception {
        MessageResult<?> messageResult = new MessageResult<>();
        if (person.getIdCardA() != null && person.getIdCardB() != null) {
            //当姓名没有输入时，进来，姓名填了就不进来
            if (person.getRealName() == null) {
                //如果身份证件上传后，调用接口进行识别
                String json = checkIdCardImage(person.getIdCardA(), person.getIdCardB());
                //将json数据进行解析
                Map<String, String> jsonData = DataMap.getJsonData(json);
                Set<String> strings = jsonData.keySet();
                for (String key : strings) {
                    String value = jsonData.get(key);
                    if (("姓名").equals(key)) {
                        person.setRealName(value);
                    } else if (("公民身份号码").equals(key)) {
                        person.setIdCardNo(value);
                    } else if (("住址").equals(key)) {
                        person.setAddress(value);
                    }
                }
            }
        }
        Object ect = redisTemplate.opsForValue().get("per");
        if (ect == null) {
            redisTemplate.opsForValue().set("per", person, 30, TimeUnit.MINUTES);
        } else {
            Person per = (Person) ect;
            Person p = (Person) CombineBeans.CopyBeanToBean(person, per);
            redisTemplate.opsForValue().set("per", p, 30, TimeUnit.MINUTES);
            Object acc = redisTemplate.opsForValue().get("acc");
            //判断是否写入完整
            if (acc != null) {
                //添加前获取登录凭证，用户的id
//                User user = (User) httpSession.getAttribute("user");
                User user = (User)redisTemplate.opsForValue().get("user");
                Account resAcc = (Account) acc;
                per.setUserId(user.getUserId());
                resAcc.setUserId(user.getUserId());
                if (resAcc.getTradingAccount() != null) {
                    int insert = personMapper.insert(per);
                    int insert1 = accountMapper.insert(resAcc);
                    if (insert == 1 && insert1 == 1) {
                        messageResult.setMsg("新增成功");
                        messageResult.setCode("20000");
                        //添加成功，删除注册缓存数据
                        redisTemplate.expire("per", 0, TimeUnit.SECONDS);
                        redisTemplate.expire("acc", 0, TimeUnit.SECONDS);
                    }
                    messageResult.setMsg("用户中断开户");
                    messageResult.setCode("20001");
                }
            } else {
                messageResult.setMsg("用户正在添加");
                messageResult.setCode("20001");
                messageResult.setData(p);
            }
        }
        return messageResult;
    }

    @Override
    public Account insertAccount(Account account) throws Exception {
        String randomNickname = PlaceNumber.getRandomNickname(10);
        if (account.getCapitalAccountNumber() == null) {
            account.setCapitalAccountNumber(randomNickname);
        }
        Account newValue = (Account) ReflectValue.getObjectValue(account, Account.class);
        redisTemplate.opsForValue().set("acc", newValue, 30, TimeUnit.MINUTES);
        return newValue;
    }
}
