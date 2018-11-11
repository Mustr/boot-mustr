package com.mustr.common.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mustr.common.annotation.Log;
import com.mustr.common.constant.PermissionsConstant;
import com.mustr.common.entity.RoleBean;
import com.mustr.common.service.RoleService;
import com.mustr.common.utils.Pagination;
import com.mustr.common.utils.Res;
import com.mustr.common.utils.SecurityUtils;

@Controller
@RequestMapping("/common/role")
public class RoleController extends ControllerBase{
    private static String prefix = "/common/role";
    
    @Autowired
    RoleService roleService;
    
    
    @Log("角色管理")
    @GetMapping("/roleList")
    public String roleManage() {
        
        return prefix + "/roleList";
    }
    
    @ResponseBody
    @GetMapping("/roles")
    public Pagination<RoleBean> getRoles() {
        Map<String, Object> cond = new HashMap<>();
        List<RoleBean> roles = roleService.getRoles(cond);
        return new Pagination<RoleBean>(roles, 1, -1);
    }
    
    @GetMapping("/role")
    public String addRole(Model model) {
        model.addAttribute("permissionsMap", PermissionsConstant.getPermissionsMap());
        return prefix + "/addRole";
    }
    
    @Log("创建角色")
    @PostMapping("/role")
    @ResponseBody
    public Res saveRole(RoleBean role) {
        if (role != null) {
            Date currDate = new Date();
            role.setCreateTime(currDate);
            role.setLastModifiedTime(currDate);
            role.setCreator(SecurityUtils.getUserId());
            roleService.createRole(role);
        }
        return Res.succ();
    }
    
    @Log("删除角色")
    @DeleteMapping("/role/{id}")
    @ResponseBody
    public Res deleteRole(@PathVariable("id") Long id) {
        boolean flage = false;
        if (id != null) {
            flage = roleService.deleteRole(id);
        }
        return flage ? Res.succ() : Res.error();
    }
    
    @GetMapping("/role/{id}")
    public String editRole(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            model.addAttribute("role", roleService.getRoleById(id));
            model.addAttribute("permissionsMap", PermissionsConstant.getPermissionsMap());
        }
        return prefix + "/editRole";
    }
    
    @Log("更新角色")
    @PutMapping("/role")
    @ResponseBody
    public Res update(RoleBean roleBean) {
        boolean flage = false;
        if (roleBean != null && roleBean.getId() != null) {
            flage = roleService.updateRole(roleBean);
        }
        return flage ? Res.succ() : Res.error();
    }
}
