package com.pd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 页面加载
 */
@Controller
public class PageController {

    @RequestMapping("/login.html")
    public void login() {
    }

    @RequestMapping("/account.html")
    public void account() {
    }

    @RequestMapping("/register.html")
    public void register() {
    }

    @RequestMapping("/mobileLogin.html")
    public void mobileLogin() {
    }

    @RequestMapping("/openAccount.html")
    public void openAccount() {
    }

    @RequestMapping("/se")
    @ResponseBody
    public String sess(HttpServletRequest servletRequest){
        HttpSession session = servletRequest.getSession();
        Object user = session.getAttribute("user");
        System.out.println(user);
        return "";
    }

    @RequestMapping("/main.html")
    public void main(){}
}
