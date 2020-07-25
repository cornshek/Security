package com.pd.controller;

import com.pd.client.UserServiceClient;
import com.pd.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GTY
 * 验证码层
 */
@RestController
public class NoteController {

    @Autowired
    private UserServiceClient userServiceClient;

    @PostMapping("/noteMobileLogin")
    public MessageResult<?> noteMobileLogin(@RequestParam("phone") String phone, @RequestParam("note") String note) throws Exception {
        MessageResult<?> messageResult = userServiceClient.noteMobileLogin(phone, note);
        return messageResult;
    }
}
