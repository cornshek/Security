package com.pd.service.impl;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pd.mapper.IAccountMapper;
import com.pd.mapper.IUserMapper;
import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import com.pd.service.IUserService;
import com.pd.util.EncryptUtils;
import com.pd.util.ImageBase64;
import com.pd.util.SendNote;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        System.out.println(root + "\n" + msg + "\n" + smsid);
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
            System.out.println("数据库中储存的验证码是：" + sessionNote);
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
        System.out.println(httpSession.getId());
        httpSession.setAttribute("user", rs);
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
    public String checkIdCard(String idcard, String name, String bankcard, String mobile) throws Exception {
        String url = "https://bankcard4c.shumaidata.com/bankcard4c";
        String appCode = "c05c7f6e47264f63953ce00c71ccdc3c";
        Map<String, String> params = new HashMap<>();
        params.put("idcard", idcard);
        params.put("name", name);
        params.put("bankcard", bankcard);
        params.put("mobile", mobile);
        String result = get(appCode, url, params);
        return result;
    }

    @Override
    public String checkIdCardImage(String cardImagePath,String humanFaceImagePath) throws Exception {
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
        System.out.println(response.toString());
        //获取response的body
        System.out.println(EntityUtils.toString(response.getEntity()));
        return response.toString();
    }

    public String get(String appCode, String url, Map<String, String> params) throws IOException {
        url = url + buildRequestUrl(params);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).build();
        Response response = client.newCall(request).execute();
        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
        String result = response.body().string();
        return result;
    }

    public String buildRequestUrl(Map<String, String> params) {
        StringBuilder url = new StringBuilder("?");
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            url.append(key).append("=").append(params.get(key)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }

    @Override
    public User verify(User User) throws Exception {
        return null;
    }
}
