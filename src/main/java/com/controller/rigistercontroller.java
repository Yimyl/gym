package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class rigistercontroller {
    @Autowired
    UserService us1;

    @RequestMapping("/register")
    public String index() {
        return "register";
    }

    @PostMapping(value = "/register_handle")
    public String register(@RequestParam("username") String username,
                           @RequestParam("pwd") String pwd,
                           Map<String, Object> map, HttpSession hs
    ) {
        if (us1.register(username, pwd).equals("Username has been registered!")) {
            map.put("msg", "账号已被注册");
            return "register";
        } else
        {
            hs.setAttribute("user",us1.login(username,pwd)[1]);
            return "search";
        }

    }


}
