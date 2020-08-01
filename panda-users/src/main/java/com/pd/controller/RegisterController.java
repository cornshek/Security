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

    /**
     * 校验身份证信息
     * @return
     */
    @RequestMapping(value = "/regIdCard",method = RequestMethod.POST)
    public String regIdCard(String idcard,String name,String bankcard,String mobile) throws Exception {
        String result = userService.checkIdCard(idcard, name, bankcard, mobile);
//        String result = idcard+"\n"+name+"\n"+bankcard+"\n"+mobile;
        return result;
    }

    @RequestMapping(value = "/regHumanFace",method = RequestMethod.POST)
    public String regHumanFace(String cardImagePath,String humanFaceImagePath) throws Exception {
        System.out.println(cardImagePath+":"+humanFaceImagePath);
        String result = userService.checkIdCardImage(cardImagePath,humanFaceImagePath);
//        String result = idcard+"\n"+name+"\n"+bankcard+"\n"+mobile;
        return result;
    }
}
