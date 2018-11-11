package com.mustr.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {
    
    @RequestMapping(value = { "/index", "/", "" })
    public String welcome() {
        return "index";
    }
    
    @GetMapping(value= {"/login","/login.html"})
    public String login() {
        return "login";
    }
    
    /*@Log("登录")
    @ResponseBody
    @PostMapping("login")
    public Res loginSub(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        if ("123".equals(username)) {
            return Res.succ();
        }  else {
            logger.info("用户[%s]登录失败!");
        }
        return Res.error("用户名或密码错误，请重新输入!");
    }*/
}
