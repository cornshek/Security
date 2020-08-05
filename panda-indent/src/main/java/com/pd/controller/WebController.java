package com.pd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/navigation")
    public String navigation() {
        return "navigation";
    }

}
