package com.yxf.example.pojo;

import com.yxf.common.base.annotation.Association;
import com.yxf.common.base.annotation.Column;
import com.yxf.common.base.annotation.Id;

import java.util.List;

public class Dept {

    @Id("ID")
    private String id;
    @Column("type")
    private String type;
    @Column("name")
    private String name;
    @Association("user_")
    private List<User> userList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
