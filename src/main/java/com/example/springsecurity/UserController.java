package com.example.springsecurity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public String hello(){
        return "Hello Security";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(){
        return "Welcome!!!";
    }
}
