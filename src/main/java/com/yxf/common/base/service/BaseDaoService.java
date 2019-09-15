package com.yxf.common.base.service;

/**
 * yunxiangfu@163.com
 * 20190301
 */


import com.yxf.common.base.bean.Pager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface BaseDaoService {
    /**
     * 实体对象插入到数据库
     *
     * 示例：
     * LjShortAbc ljShortAbc = new LjShortAbc();
     * ljShortAbc.setGroupCode("1111111");
     * ljShortAbc.setId("AAA");
     * baseDaoService.insertObject(ljShortAbc);
     *
     * @param entity
     * @return
     */
    public int insertObject(Object entity);

    /**
     * 批量全字段insert
     * @param list
     * @return
     */
    public int insertObjects(List list);
    public int insertObjects(List list,int bathLimit);

    /**
     * 批量指定字段insert
     * @param columns
     * @param list
     * @return
     */
    public int insertObjects(String[] columns,List list);
    public int insertObjects(String[] columns,List list,int bathLimit);

    /**
     * 采用insert all 方式批量添加
     * @param list
     * @return
     */
    public int insertAllObjects(List list);
    public int insertAllObjects(List list,int bathLimit);
    /**
     * 批量insert并支持分批插入，如全量则bathLimit=0
     * @param tableName
     * @param columns
     * @param list
     * @param bathLimit 分批
     * @return
     */
    public int insertMaps(String tableName,String[] columns,List<Map<String,Object>> list,int bathLimit);
    /**
     * 通过Map插入到数据库
     * @param tableName
     * @param dataMap
     * @return
     */
    public int insertMap(String tableName,LinkedHashMap<String,Object> dataMap);

    /**
     * 根据实体对象保存
     *
     * 示例：
     * LjShortAbc ljShortAbc = new LjShortAbc();
     * ljShortAbc.setId("123");
     * ljShortAbc.setGroupCode("1111111");
     * baseDaoService.updateObject(ljShortAbc);
     * @param entity
     * @param keyValue  主键值
     * @return
     */
    public int updateObject(Object entity);
    public int updateObject(Object entity,Object keyValue);
    public int updateObjects(List list);
    public int updateObjects(List list,int bathLimit);
    public int updateObjects(String[] columns,List list,int bathLimit);
    public int updateMaps(String tableName,String keyColumn,String[] columns,List<Map<String,Object>> list,int bathLimit);
    /**
     * 主键不为空为新增，反之更新
     * @param entity
     * @return
     */
    public int saveObject(Object entity);
    /**
     * 根据主键删除指定实体对象对应的表数据
     *
     * 示例：
     * baseDaoService.deleteObject(LjShortAbc.class,"AAA");
     *
     * @param entity    实体类
     * @param keyValue  主键值
     * @return
     */
    public int deleteObject(Class entity,Object keyValue);
    /**
     * 执行sql
     *
     * 示例：
     * baseDaoService.execute("delete from LJ_SHORTABC where id=#{id}",paramss);
     *
     * @param sql
     * @param params
     * @return
     */
    public int execute(String sql,Map<String,Object> params);
    /**
     * 返回实体类的所有数据
     *
     * 示例：
     * List<LjShortAbc> list = (List<LjShortAbc>) baseDaoService.listAll(LjShortAbc.class);
     *
     * @param entity
     * @return
     */
    public <T> List<T> listAll(Class<T> entity);
    /**
     * 通过sql查询并将结果集赋值到指定的类
     * @param sql
     * @param params
     * @param clazz
     * @return
     */
    public <T> List<T> list(String sql, Map<String, Object> params, Class<T> clazz);
    /**
     * 通过sql查询并将结果集Map
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String,Object>> listMap(String sql,Map<String,Object> params);

    /**
     *  获取第一条第一列数据
     * @param sql
     * @param params
     * @return
     */
    public Object queryObject(String sql,Map<String,Object> params);

    /**
     *  获取汇总
     * @param sql
     * @param params
     * @return
     */
    public int queryCount(String sql,Map<String,Object> params);
    /**
     * 分页处理
     * @param sql
     * @param params
     * @param clazz
     * @param currentPage
     * @param pageSize
     * @return
     */
    public <T> Pager pagedQuery(String sql, Map<String, Object> params, Class<T> clazz, int currentPage, int pageSize);

    /**
     * 返回一个实体对象
     *  例：select * from handover_Fault t where id='668b36cd71c84606ac2b06abfa111190'
     * @param sql
     * @param params
     * @return
     */
    public <T> T queryObject(String sql, Map<String, Object> params, Class<T> entity);
}
