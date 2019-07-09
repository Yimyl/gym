package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class logincontroller {
    @Autowired
    UserService us;
    @RequestMapping(value = "/")
    public String index(){
        return "login";
    }
    @RequestMapping(value = "/login")
    public String index1(){
        return "login";
    }
/*
    @RequestMapping(value="/user/login",method= RequestMethod.POST)
*/
    @PostMapping(value="/login_handle")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model mod, HttpSession hs){


        if(us.login(username,password)[0].equals("login success"))
        {
            hs.setAttribute("user",us.login(username,password)[1]);
            return "search";
        }
        else if(us.login(username,password)[0].equals("username or password error"))
        {
            mod.addAttribute("msg","账号或者密码有误");
            return "login";
        }
        return "login";
    }
}
