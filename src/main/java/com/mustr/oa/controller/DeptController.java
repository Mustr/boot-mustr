package com.mustr.oa.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.mustr.common.annotation.Log;
import com.mustr.common.utils.Res;
import com.mustr.oa.entity.DeptBean;
import com.mustr.oa.service.DeptService;

@RequestMapping("/oa/dept")
@Controller
public class DeptController {

    private String prefix = "oa/dept";
    
    @Autowired
    private DeptService deptService;
    
    @Log("部门列表")
    @GetMapping("/deptList")
    public ModelAndView deptList() {
        ModelAndView model = new ModelAndView(prefix + "/deptList");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sort", "orderx");
        params.put("order", "asc");
        model.addObject("tree", deptService.getDeptTree(params));
        return model;
    }
    
    @GetMapping("/addDept/{pid}")
    public String addDept(@PathVariable("pid") Long pid, Model model) {
        model.addAttribute("pid", pid);
        if (0 == pid) {
            model.addAttribute("pName", "总部");
        } else {
            model.addAttribute("pName", deptService.getDeptById(pid).getName());
        }
        return prefix + "/addDept";
    }
    
    @Log("添加部门")
    @PostMapping("/dept")
    @ResponseBody
    public Res saveDept(DeptBean dept) {
        if (StringUtils.isNotBlank(dept.getName()) && dept.getParentId() != null) {
            deptService.saveDept(dept);
        }
        return Res.succ();
    }
    
    @Log("删除部门")
    @DeleteMapping("/dept/{id}")
    @ResponseBody
    public Res removeDept(@PathVariable("id") Long id) {
        boolean flage = false;
        if (id != null) {
            flage = deptService.deleteDept(id);
        }
        return flage ? Res.succ() : Res.error();
    }
    
    @GetMapping("/updateDept/{id}")
    public String updateDept(@PathVariable("id") Long id, Model model) {
        DeptBean dept = deptService.getDeptById(id);
        model.addAttribute("dept", dept);
        return prefix + "/update";
    }
    
    @Log("编辑部门")
    @PutMapping("/dept")
    @ResponseBody
    public Res update(DeptBean dept) {
        boolean flage = false;
        if (dept.getId() != null) {
            flage = deptService.upateDept(dept);
        }
        return flage ? Res.succ() : Res.error();
    }
}
