package com.pd.controller;

import com.pd.pojo.Account;
import com.pd.pojo.Person;
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
     * 校验银行卡信息
     * @return
     */
    @RequestMapping(value = "/regIdCard", method = RequestMethod.POST)
    public int regIdCard(@RequestParam("idCardNo") String idCardNo, @RequestParam("realName") String realName, @RequestParam("tradingAccount") String tradingAccount, @RequestParam("phone") String phone) throws Exception{
        int result = userService.checkIdCard(idCardNo, realName, tradingAccount, phone);
        return result;
    }

    @RequestMapping(value = "/regHumanFace",method = RequestMethod.POST)
    public String regHumanFace(String cardImagePath,String humanFaceImagePath) throws Exception {
        String result = userService.checkIdCardImage(cardImagePath,humanFaceImagePath);
        return result;
    }

    @RequestMapping(value = "/regPerson",method = RequestMethod.POST)
    public MessageResult<?> insertPerson(@RequestBody Person person)throws Exception{
        MessageResult<?> messageResult = userService.insertPerson(person);
        return messageResult;
    }

    @RequestMapping(value = "/regAccount",method = RequestMethod.POST)
    public Account insertAccount(@RequestBody Account account)throws Exception{
        Account acc = userService.insertAccount(account);
        return acc;
    }
}
