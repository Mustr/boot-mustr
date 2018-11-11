package com.mustr.oa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mustr.common.entity.Tree;
import com.mustr.common.service.ServiceBase;
import com.mustr.common.utils.TreeBuilder;
import com.mustr.oa.dao.DeptDao;
import com.mustr.oa.entity.DeptBean;
import com.mustr.oa.service.DeptService;

@Service
public class DeptServiceImpl extends ServiceBase implements DeptService {

    @Autowired
    DeptDao deptDao;

    @Override
    public List<DeptBean> getAllDepts(Map<String, Object> params) {
        return deptDao.getDepts(params);
    }

    @Override
    public DeptBean getDeptById(Long id) {
        return deptDao.getDeptById(id);
    }

    @Override
    public Tree<DeptBean> getDeptTree(Map<String, Object> params) {
        List<DeptBean> depts = deptDao.getDepts(params);
        List<Tree<DeptBean>> nodes = new ArrayList<Tree<DeptBean>>();
        if (!CollectionUtils.isEmpty(depts)) {
            for (DeptBean dept : depts) {
                Tree<DeptBean> node = new Tree<DeptBean>();
                node.setId(dept.getId().toString());
                node.setData(dept);
                node.setTitle(dept.getName());
                node.setParentId(dept.getParentId().toString());
                nodes.add(node);
            }
        }

        Tree<DeptBean> tree = TreeBuilder.build(nodes);
        return tree;
    }

    @Override
    public long saveDept(DeptBean dept) {
        if (dept.getOrderx() == null) {
            dept.setOrderx(deptDao.getMaxOrderx(dept.getParentId()));
        }
        if (dept.getOrderx() < 0) {
            dept.setOrderx(0);
        }
        return deptDao.save(dept);
    }

    @Override
    public boolean deleteDept(long id) {
        int res = deptDao.remove(id);
        return res > 0;
    }

    @Override
    public boolean upateDept(DeptBean dept) {
        if (dept.getId() != null) {
            int res = deptDao.update(dept);
            return res > 0;
        }
        return false;
    }

}
