package com.pd.controller;

import com.pd.client.UserServiceClient;
import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户功能层
 *
 * @author GTY
 */
@RestController
public class UserController {

    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/registerUserSetPhoneAndPwd")
    public MessageResult<?> registerUser(String phone, String note, String pwd) throws Exception {
        User user = new User();
        user.setTelephoneNumber(phone);
        user.setPassword(pwd);
        MessageResult<?> regin = userServiceClient.regin(user, note);
        return regin;
    }

    @PostMapping("/sendNote")
    public MessageResult<?> sendNote(String phone) throws Exception {
        MessageResult<?> messageResult = userServiceClient.sendNote(phone);
        return messageResult;
    }

    @PostMapping("/capiatilOrPhoneAndPasswordLogin")
    public MessageResult<?> phoneAndPasswordLogin(String phone, String password) throws Exception {
        String regex = "[0-9]{11}";
        int phoneLen = 11;
        //是手机号就通过手机号和密码登录，否则通过资金账户和密码登录
        if (phone.matches(regex) && phone.length() == phoneLen) {
            User user = new User();
            user.setTelephoneNumber(phone);
            user.setPassword(password);
            MessageResult<?> messageResult = userServiceClient.phoneLogin(user);
            return messageResult;
        } else {
            Account account = new Account();
            account.setCapitalAccountNumber(phone);
            account.setCapitalPassword(password);
            MessageResult<?> messageResult = userServiceClient.capitalLogin(account);
            return messageResult;
        }
    }

}
