package com.yxf.example.pojo;

import com.yxf.common.base.annotation.Column;
import com.yxf.common.base.annotation.Id;
import com.yxf.common.base.annotation.Table;
import com.yxf.common.base.bean.BasePojo;

import java.util.Date;

@Table("user")
public class User extends BasePojo {

    @Id("ID")
    private String id;
    @Column("dept_id")
    private String deptId;
    @Column("user_name")
    private String userName;
    @Column("user_type")
    private String userType;
    @Column("name")
    private String name;
    @Column("sex")
    private String sex;
    @Column("crt_date")
    private Date crtDate;
    @Column("upd_date")
    private Date updDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}
