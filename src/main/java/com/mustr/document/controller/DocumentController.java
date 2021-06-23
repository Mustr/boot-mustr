package com.mustr.document.controller;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mustr.common.annotation.Log;
import com.mustr.common.entity.FileBean;
import com.mustr.common.service.FileService;
import com.mustr.common.utils.Res;
import com.mustr.common.utils.SecurityUtils;
import com.mustr.document.entity.DocumentBean;
import com.mustr.document.entity.ProjectBean;
import com.mustr.document.service.DocumentService;
import com.mustr.document.service.ProjectService;

import cn.hutool.core.net.URLEncoder;

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
    
    @Autowired
    private DocumentService documentService;
    @Autowired
    private FileService fileServcie;
    @Autowired
    private ProjectService projectService;
    
    @Log("文件列表")
    @GetMapping("/fileList/{projectId}")
    public ModelAndView fileList(@PathVariable("projectId") Long projectId) {
        ModelAndView model = new ModelAndView(prefix + "/fileList");
        model.addObject("list", documentService.getByProjectId(projectId));
        
        ProjectBean project = projectService.getProjectById(projectId);
        model.addObject("project", project);
        if (project != null && project.getParentId() != null) {
            model.addObject("parent", projectService.getProjectById(project.getParentId()));
        }
        return model;
    }
    
    
    @Log("文件历史记录")
    @GetMapping("/fileLogs/{projectId}")
    public ModelAndView fileLogList(@PathVariable("projectId") Long projectId, String name) {
        ModelAndView model = new ModelAndView(prefix + "/fileLogs");
        if (StringUtils.isBlank(name)) {
            return model;
        }
        model.addObject("list", documentService.getByProjectIdAndName(projectId, name));
        return model;
    }
    
    @Log("删除文档")
    @DeleteMapping("/file/{id}")
    @ResponseBody
    public Res remove(@PathVariable("id") Long id) {
        return Res.res(documentService.deleteById(id));
    }
    
    @Log("删除文档日志")
    @DeleteMapping("/file/log/{id}")
    @ResponseBody
    public Res removeLog(@PathVariable("id") Long id) {
        return Res.res(documentService.deleteLog(id));
    }
    
    @GetMapping("/toUpdate/{id}")
    public ModelAndView toUpdate(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView(prefix + "/toUpdate");
        model.addObject("bean", documentService.getById(id));
        return model;
    }
    
    @Log("更新文档信息")
    @PutMapping("/file")
    @ResponseBody
    public Res update(DocumentBean bean) {
        return Res.res(documentService.update(bean));
    }
    
    @Log("发送提示信息")
    @PostMapping("/sendMsg/{id}")
    @ResponseBody
    public Res sendMsg(@PathVariable("id") Long id) {
        int sendMsg = documentService.sendMsg(id);
        //0正常 1文件不存在  2没有配置webhook
        if (sendMsg == 1) {
            return Res.error("文件不存在");
        } else if (sendMsg == 2) {
            return Res.error("没有配置webhook");
        }
        return Res.succ();
    }
    
    
    @Log("上传文件")
    @RequestMapping("/upload")
    @ResponseBody
    public Res upload(MultipartFile file, HttpServletRequest request, Long projectId) {
        Long docId = documentService.createDocument(file, SecurityUtils.getUserId(), projectId);
        if (docId != null) {
            return Res.succ().put("id", docId);
        }
        return Res.error();
    }
    
    @Log("查看文件")
    @GetMapping("/view/{id}")
    public void view(@PathVariable("id") Long id, HttpServletResponse response) {
        DocumentBean result = documentService.getById(id);
        if (result == null || result.getFileId() == null) {
            return;
        }
        
        Long fileId = result.getFileId();
        if (result.getConvertFileId() != null) {
            fileId = result.getConvertFileId();
        }
        
        FileBean file = fileServcie.getById(fileId);
        if (file == null) {
            return;
        }
        
        try {
            String viewUrl = fileServcie.getViewUrl(file.getBucket(), file.getObjectName());
            if (viewUrl != null) {
                response.setHeader("Cache-Control", "max-age="+ (7 * 24 *60 * 60));
                response.sendRedirect(viewUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Log("下载文件")
    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        DocumentBean result = documentService.getById(id);
        if (result == null || result.getFileId() == null) {
            return;
        }

        FileBean file = fileServcie.getById(result.getFileId());
        if (file == null) {
            return;
        }

        try {
            String fileName = URLEncoder.createDefault().encode(file.getName(), Charset.forName("utf-8"));
            String downloadUrl = fileServcie.getDownloadUrl(file.getBucket(), file.getObjectName(), fileName);
            if (downloadUrl != null) {
                response.sendRedirect(downloadUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
