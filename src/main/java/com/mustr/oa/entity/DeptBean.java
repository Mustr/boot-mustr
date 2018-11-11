package com.mustr.oa.entity;

import com.mustr.common.entity.Mu;

public class DeptBean extends Mu{
    private static final long serialVersionUID = -6413377060964018205L;
    
    private Long parentId;//所属部门
    private Integer orderx;//排序
    private Short status; //状态 0正常，-1删除

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderx() {
        return orderx;
    }

    public void setOrderx(Integer orderx) {
        this.orderx = orderx;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DeptBean [id=" + id + ", name=" + name + ", parentId=" + parentId + ", orderx=" + orderx + ", status="
                + status + "]";
    }
    
}