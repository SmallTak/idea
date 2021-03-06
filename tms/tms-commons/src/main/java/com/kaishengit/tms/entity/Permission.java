package com.kaishengit.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**用户权限实体类
 * permission
 * @author 
 */
public class Permission implements  Serializable {

    public static final String  MENU_TYPE = "菜单";
    public static final String  BUTTON_TYPE = "按钮";


    private Integer id;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限代号
     */
    private String permissionCode;

    /**
     * 权限类型，菜单 按钮
     */
    private String permissionType;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 权限对应的url
     */
    private String url;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", permissionCode='" + permissionCode + '\'' +
                ", permissionType='" + permissionType + '\'' +
                ", parentId=" + parentId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", url='" + url + '\'' +
                '}';
    }
}