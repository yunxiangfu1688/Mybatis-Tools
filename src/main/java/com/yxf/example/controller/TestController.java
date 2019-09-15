package com.yxf.example.controller;

import com.yxf.common.base.service.BaseDaoService;
import com.yxf.example.pojo.Dept;
import com.yxf.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TestController {

    @Autowired
    private BaseDaoService daoService;

    /**
     * 获得列表全部字段
     * @return
     */
    @RequestMapping("/list")
    public Object list(){
        List list = daoService.list("select * from user",null, User.class);
        return null;
    }
    /**
     * 获得列表部分字段
     * @return
     */
    @RequestMapping("/list2")
    public Object list2(){
        Map<String, Object> params = new HashMap<>();
        List li = new ArrayList();
        li.add("D501050014");
        li.add("D303050075");
        params.put("ids",li);
        params.put("type","1");
        List list = daoService.list("select user_name,name,id from user where id in #{ids} and user_type = #{type}",params, User.class);
        return null;
    }
    /**
     * 获得一对多数据
     * @return
     */
    @RequestMapping("/list3")
    public Object list3(){
        Map<String, Object> params = new HashMap<>();
        params.put("id","1");
        StringBuilder sql = new StringBuilder("select ");
        sql.append("t.id,");//部门ID
        sql.append("t.name,");//部门名称
        sql.append("t2.id as user_id,");//用户ID
        sql.append("t2.name as user_name,");//用户名称
        sql.append("t2.sex as user_sex,");//用户性别
        sql.append("t2.user_type as user_user_type");//用户类型
        sql.append(" from dept t");//部门表
        sql.append(" left join user t2 on t2.dept_id = t.id");//用户表
        sql.append(" where t.id=#{deptId}");
        List list = daoService.list(sql.toString(),params, Dept.class);
        return null;
    }
    //insert单条
    @RequestMapping("/insert")
    public Object insert(){
        User user = new User();
        user.setId("1");
        user.setCrtDate(new Date());
        user.setDeptId("1");
        user.setName("张三");
        user.setSex("1");
        user.setUserType("1");
        daoService.insertObject(user);
        return "1";
    }
    //insert批量
    @RequestMapping("/insert2")
    public Object insert2(){
        List<User> insertList = new ArrayList();
        User user = new User();
        user.setId("1");
        user.setCrtDate(new Date());
        user.setDeptId("1");
        user.setName("张三");
        user.setSex("1");
        user.setUserType("1");

        User user2 = new User();
        user2.setId("2");
        user2.setCrtDate(new Date());
        user2.setDeptId("1");
        user2.setName("张三");
        user2.setSex("1");
        user2.setUserType("1");

        User user3 = new User();
        user3.setId("3");
//        user3.setCrtDate(new Date());
        user3.setDeptId("1");
        user3.setName("张三");
        user3.setSex("1");
        user3.setUserType("1");
        //指定字段值为SQL
        user3.addSqlColumns("crt_date","sysdate");

        insertList.add(user);
        insertList.add(user2);
        insertList.add(user3);

        //批量新增，每500条comint一次以防止超出最大数据包限制
        daoService.insertObjects(insertList);
        return "1";
    }
    //update
    @RequestMapping("/update")
    public void update(){
        User user = new User();
        user.setId("1");
        user.setCrtDate(new Date());
        user.setDeptId("1");
        user.setName("张三");
        user.setSex("1");
        user.setUserType("1");

        daoService.updateObject(user);
    }
    //update批量
    @RequestMapping("/update2")
    public Object update2(){
        List<User> updateList = new ArrayList();
        User user = new User();
        user.setId("1");
        user.setUpdDate(new Date());
        user.setDeptId("1");
        user.setName("张三");
        user.setSex("1");
        user.setUserType("1");

        User user2 = new User();
        user2.setId("2");
        user2.setUpdDate(new Date());
        user2.setDeptId("1");
        user2.setName("张三");
        user2.setSex("1");
        user2.setUserType("1");

        User user3 = new User();
        user3.setId("3");
        user3.setDeptId("1");
        user3.setName("张三");
        user3.setSex("1");
        user3.setUserType("1");
        //指定字段值为SQL
        user3.addSqlColumns("upd_date","sysdate");

        updateList.add(user);
        updateList.add(user2);
        updateList.add(user3);

        //批量更新，每500条comint一次以防止超出最大数据包限制
        daoService.updateObjects(updateList,500);
        return "1";
    }
}
