package com.yxf.common.base.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * yunxiangfu@163.com
 * 20190301
 */

@Mapper
public interface BaseMapper {

    public int insert(Map params);
    public int insertBath(@Param("TABLE_NAME")String table, @Param("columns")String[] columns, @Param("dataList")List<Map<String,Object>> dataList);
    public int insertAllBath(@Param("dataList")List<Map<String,Object>> dataList);
    public int updateBath(@Param("TABLE_NAME")String table,@Param("KEY_ID")String keyColumn, @Param("columns")String[] columns, @Param("dataList")List<Map<String,Object>> dataList,@Param("where")String where);
    //    public int updateBath();
    public int update(Map params);
    public int delete(Map params);
    public int execute(Map params);
    public List<Map<String,Object>> list(Map<String, Object> params);
}
