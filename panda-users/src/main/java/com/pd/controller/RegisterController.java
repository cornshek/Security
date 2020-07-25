package com.pd.controller;

import com.pd.pojo.User;
import com.pd.result.MessageResult;
import com.pd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GTY
 * 注册层
 */
@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public MessageResult<?> regin(@RequestBody User user, @RequestParam("note") String note)throws Exception{
        MessageResult<?> messageResult = userService.insertUser(user, note);
        return messageResult;
    }
}
