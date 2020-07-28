package com.pd.controller;

import com.pd.pojo.Account;
import com.pd.pojo.User;
import com.pd.result.MessageResult;
import com.pd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GTY
 * 登录层
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private IUserService userService;

    /**
     * 通过电话和密码行登录
     * @return
     */
    @RequestMapping(value = "/phoneLogin",method = RequestMethod.POST)
    public MessageResult<?> phoneLogin(@RequestBody  User user)throws Exception{
        MessageResult<?> phoneAndPasswordLogin = userService.findPhoneAndPasswordLogin(user);
        return phoneAndPasswordLogin;
    }

    /**
     * 根据资金账户和密码登录
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/capitalLogin",method = RequestMethod.POST)
    public MessageResult<?> capitalLogin(@RequestBody Account account)throws Exception{
        MessageResult<?> capitalAndPasswordLogin = userService.findCapitalAndPasswordLogin(account);
        return capitalAndPasswordLogin;
    }


    /**
     * 通过短信进行验证登录
     * @param phone
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendNote",method = RequestMethod.POST)
    public MessageResult<?>  sendNote(@RequestParam("phone") String phone)throws  Exception{
        MessageResult<?> note = userService.getNote(phone);
        return note;
    }

}
