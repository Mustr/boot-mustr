package com.mustr.document.controller;

import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.mustr.common.annotation.Log;
import com.mustr.common.utils.Res;
import com.mustr.document.entity.ProjectBean;
import com.mustr.document.service.ProjectService;

/**
 * 
 * @Description: 项目管理
 * @author mustr
 * @date 2021-6-5
 *
 */

@Controller
@RequestMapping("/document/project")
public class ProjectController {

    private final String prefix = "document/project";
    
    @Autowired
    private ProjectService projectService;
    
    @Log("项目列表")
    @GetMapping("/projectList")
    public ModelAndView deptList() {
        ModelAndView model = new ModelAndView(prefix + "/projectList");
        Map<String, Object> params = new HashMap<>();
        model.addObject("tree", projectService.getProjectTree(params));
        return model;
    }
    
    @GetMapping("/addProject/{pid}")
    public String addDept(@PathVariable("pid") Long pid, Model model) {
        model.addAttribute("pid", pid);
        if (pid == 0) {
            model.addAttribute("pName", "根项目");
        } else {
            ProjectBean project = projectService.getProjectById(pid);
            model.addAttribute("pName", project != null ? project.getName() : "");
        }
        
        return prefix + "/add";
    }
    
    @Log("添加项目")
    @PostMapping("/project")
    @ResponseBody
    public Res saveDept(ProjectBean project) {
        long saveProject = projectService.saveProject(project);
        return Res.succ().put("id", saveProject);
    }
    
    @Log("删除项目")
    @DeleteMapping("/project/{id}")
    @ResponseBody
    public Res removeDept(@PathVariable("id") Long id) {
        return Res.res(projectService.deleteProject(id));
    }
    
    @GetMapping("/updateProject/{id}")
    public String updateDept(@PathVariable("id") Long id, Model model) {
        model.addAttribute("project", projectService.getProjectById(id));
        return prefix + "/update";
    }
    
    @Log("编辑项目")
    @PutMapping("/project")
    @ResponseBody
    public Res update(ProjectBean project) {
        if (project.getId() == null) {
            return Res.error("id is blank!");
        }
        return Res.res(projectService.upateProject(project));
    }
}
