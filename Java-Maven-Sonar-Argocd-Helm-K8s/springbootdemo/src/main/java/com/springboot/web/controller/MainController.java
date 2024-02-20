package com.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/home")
    public String home(){
        System.out.println("Loading home page");
        return "home";
    }

    @RequestMapping("/about")
    public String about(){
        System.out.println("Loading about page");
        return "about";
    }
}
