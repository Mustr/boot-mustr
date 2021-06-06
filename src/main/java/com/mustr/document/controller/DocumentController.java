package com.mustr.document.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mustr.common.annotation.Log;
import com.mustr.common.utils.Res;

/**
 * 
 * @Description: 文档管理
 * @author mustr
 * @date 2021-6-5
 *
 */

@Controller
@RequestMapping("/document/file")
public class DocumentController {

    
private final String prefix = "document/file";
    
    @Log("文件列表")
    @GetMapping("/fileList/{projectId}")
    public ModelAndView deptList(@PathVariable("projectId") Long projectId) {
        System.out.println("projectId :" + projectId);
        ModelAndView model = new ModelAndView(prefix + "/fileList");
        return model;
    }
    
    
    @Log("上传文件")
    @RequestMapping("/upload")
    @ResponseBody
    public Res upload(MultipartFile file, HttpServletRequest request) {
        System.out.println(file.getName());
        System.out.println(file.getSize());
        
        return Res.succ().put("id", "111111");
    }
    
}
