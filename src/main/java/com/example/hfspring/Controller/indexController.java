package com.example.hfspring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {

    @RequestMapping("/login")
    public String login(){
        return "/Org/login";
    }

    @RequestMapping("/register")
    public String index(){
        return "/Org/register";
    }

    @RequestMapping("/poster")
    public String poster(){
        return "/Org/poster";
    }
}
