package com.mustr.common.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mustr.common.constant.PermissionsConstant;

public class RoleBean extends Mu {
    private static final long serialVersionUID = 8945589996092274763L;

    private String sign;
    private String remark;
    private Long creator;
    private Date createTime;
    private Date lastModifiedTime;
    private List<String> permissionses;
    private String permissionName;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public List<String> getPermissionses() {
        return permissionses;
    }

    public void setPermissionses(List<String> permissionses) {
        this.permissionses = permissionses;
        if (permissionses != null && !permissionses.isEmpty()) {
            List<String> perName = new ArrayList<>();
            for (String per : permissionses) {
                perName.add(PermissionsConstant.getPermissionsMap().get(per));
            }
            this.permissionName = StringUtils.join(perName.iterator(), ",");
        }
    }
    
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "RoleBean [sign=" + sign + ", remark=" + remark + ", creator=" + creator + ", createTime=" + createTime
                + ", lastModifiedTime=" + lastModifiedTime + ", permissionses=" + permissionses + "]";
    }
    
}
