package com.jsq.controller;

import com.jsq.entity.User;
import com.jsq.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        System.out.print(123);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(User User){
        // 此处省略校验逻辑
        if (userService.insert(User))
            return "redirect:register?success";
        return "redirect:register?error";
    }
}
