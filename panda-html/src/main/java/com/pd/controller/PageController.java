package com.pd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面加载
 */
@Controller
public class PageController {

    @RequestMapping("/login.html")
    public void login(){}
    @RequestMapping("/account.html")
    public void account(){}
    @RequestMapping("/register.html")
    public void register(){}
    @RequestMapping("/mobileLogin.html")
    public void mobileLogin(){ }

}
