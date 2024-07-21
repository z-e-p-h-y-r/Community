package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller   // 允许该类接收前端请求
public class Hello {
    @GetMapping("/hello")
    public String hello(@RequestParam("name")String name , Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
