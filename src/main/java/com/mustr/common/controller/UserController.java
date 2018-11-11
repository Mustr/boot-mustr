package com.mustr.common.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.mustr.common.annotation.Log;
import com.mustr.common.entity.UserBean;
import com.mustr.common.service.UserService;
import com.mustr.common.utils.Pagination;
import com.mustr.common.utils.Res;
import com.mustr.common.utils.SecurityUtils;
import com.mustr.oa.entity.DeptBean;
import com.mustr.oa.service.DeptService;

@Controller
@RequestMapping("/common/user")
public class UserController extends PageController{
    private static String prefix = "common/user";
    
    @Autowired
    UserService userService;
    
    @Autowired
    DeptService deptService;
    
    @Log("用户列表")
    @GetMapping("/users")
    public String users(Model model) {
        return prefix + "/userList";
    }
    
    @ResponseBody
    @GetMapping("/userList")
    public Pagination<UserBean> userList(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        String username = request.getParameter("username");
        if (isNotBlank(username)) {
            params.put("username", username);
        }
        Page<UserBean> users = userService.getUsers(params, getPageNum(), getPageSize());
        
        Pagination<UserBean> page = Pagination.builderPage(users);
        return page;
    }
    
    @GetMapping("/user")
    public String addUser(Model model) {
        List<DeptBean> allDepts = deptService.getAllDepts(new HashMap<String, Object>());
        model.addAttribute("depts", allDepts);
        return prefix + "/addUser";
    }
    
    @Log("创建用户")
    @PostMapping("/user")
    @ResponseBody
    public Res saveUser(UserBean userBean) {
        userBean.setCreator(SecurityUtils.getUserId());
        Long newId = userService.createUser(userBean);
        return newId == null ? Res.error() : Res.succ();
    }
    
    @GetMapping("/user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            model.addAttribute("user", userService.getUserById(id));
            model.addAttribute("depts", deptService.getAllDepts(new HashMap<String, Object>()));
        }
        return prefix + "/editUser";
    }
    
    @Log("更新用户")
    @PutMapping("/user")
    @ResponseBody
    public Res updateUser(UserBean userBean) {
        boolean flage = false;
        if (userBean != null && userBean.getId() != null) {
            flage = userService.updateUser(userBean);
        }
        return flage ? Res.succ() : Res.error();
    }
    
    @Log("删除用户")
    @DeleteMapping("/user/{id}")
    @ResponseBody
    public Res deleteUser(@PathVariable("id") Long id) {
        boolean flage = false;
        if (id != null) {
            flage = userService.deleteUser(id);
        }
        return flage ? Res.succ() : Res.error();
    }
    
    @Log("查看用户信息")
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return prefix + "/view";
    }
    
    @GetMapping("/check")
    @ResponseBody
    public Res checkUsername(String username) {
        boolean flage = true;
        if (isNotBlank(username)) {
            UserBean user = userService.getUserByUsername(username);
            flage = user == null;
        }
        return flage ? Res.succ() : Res.error();
    }
    
}
