package com.mustr.document.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mustr.common.entity.Tree;
import com.mustr.common.utils.TreeBuilder;
import com.mustr.document.dao.ProjectDao;
import com.mustr.document.entity.ProjectBean;
import com.mustr.document.service.ProjectService;

/**
 * 
 * @author mustr
 * @date 2021-6-6
 *
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    
    @Override
    public List<ProjectBean> getAllProjects(Map<String, Object> params) {
        return projectDao.getProjects(params);
    }

    @Override
    public ProjectBean getProjectById(Long id) {
        return projectDao.getById(id).orElse(null);
    }

    @Override
    public Tree<ProjectBean> getProjectTree(Map<String, Object> params) {
        List<ProjectBean> projects = projectDao.getProjects(params);
        List<Tree<ProjectBean>> nodes = new ArrayList<Tree<ProjectBean>>();
        if (!CollectionUtils.isEmpty(projects)) {
            for (ProjectBean dept : projects) {
                Tree<ProjectBean> node = new Tree<ProjectBean>();
                node.setId(dept.getId().toString());
                node.setData(dept);
                node.setTitle(dept.getName());
                node.setParentId(dept.getParentId().toString());
                nodes.add(node);
            }
        }

        Tree<ProjectBean> tree = TreeBuilder.build(nodes);
        return tree;
    }

    @Override
    public long saveProject(ProjectBean project) {
        return projectDao.save(project);
    }

    @Override
    public boolean deleteProject(long id) {
        int res = projectDao.remove(id);
        return res > 0;
    }

    @Override
    public boolean upateProject(ProjectBean project) {
        if (project.getId() != null) {
            int update = projectDao.update(project);
            return update > 0;
        }
        return false;
    }

}
