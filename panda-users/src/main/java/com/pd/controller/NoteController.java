package com.pd.controller;

import com.pd.result.MessageResult;
import com.pd.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GTY
 * 短信验证层
 */
@RestController
@RequestMapping("/user")
public class NoteController {

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/noteMobileLogin")
    public MessageResult<?> noteMobileLogin(@RequestParam("phone") String phone, @RequestParam("note") String note) throws Exception {
        MessageResult<?> messageResult = userService.noteMobileLogin(phone, note);
        return messageResult;
    }
}
