package com.pd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pd.mapper.IAccountMapper;
import com.pd.mapper.IUserMapper;
import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import com.pd.service.IUserService;
import com.pd.util.EncryptUtils;
import com.pd.util.SendNote;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
        /*SendNote sendNote = new SendNote();
        SendNote.NoteData ali = sendNote.ali(phone);
        System.out.println("短信验证码："+ali.getNote());*/
        //使用互亿短信
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
        }else{
            message.setCode("20001");
            message.setMsg("没钱了！");
        }
        System.out.println(root+"\n"+msg+"\n"+smsid);
        return message;
    }

    @Override
    public MessageResult<?> noteMobileLogin(String phone, String note) throws Exception {
        MessageResult<Object> objectMessageResult = new MessageResult<>();
        Object rs = redisTemplate.opsForValue().get(phone);
        if (rs == null){
            objectMessageResult.setCode("20001");
            objectMessageResult.setMsg("验证码失效！");
        }else {
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
}
