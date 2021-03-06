package com.yxf.common.base.service;
/**
 * yunxiangfu@163.com 20190301
 */

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.yxf.common.base.dao.BaseMapper;
import com.yxf.common.base.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxf.common.base.bean.Pager;
import com.yxf.common.base.utils.DaoUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BaseDaoServiceImpl implements BaseDaoService {

    private static final Logger log = LoggerFactory.getLogger(BaseDaoServiceImpl.class);
    @Resource
    private BaseMapper          baseMapper;

    /**
     * 实体对象插入到数据库
     *
     * @param entity
     * @return
     */
    public int insertObject(Object entity) {
        Map<String, Object> params = DaoUtil.transformObj(entity);
        log.info(new StringBuffer("Function Insert.Transformed Params:").append(params).toString());
        return baseMapper.insert(params);
    }

    /**
     * 采用 union all select方式insert，该方式效率较高，会对对象所有注解字段进行操作
     *
     * @param list 实体对象集合
     * @return
     */
    public int insertObjects(List list) {
        return insertObjects(list, 0);
    }

    public int insertObjects(List list, int bathLimit) {
        if (list == null || list.size() == 0) return 0;
        Object entity = list.get(0);
        List<String> columns = DaoUtil.getAllColumn(entity, false);
        String[] s = new String[0];
        return insertObjects(columns.toArray(s), list, bathLimit);
    }

    public int insertObjects(String[] columns, List list) {
        return insertObjects(columns, list, 0);
    }

    /**
     * 采用 union all select方式insert，该方式效率较高，但需要指定字段
     *
     * @param columns 指定插入的字段例（不区分大小写）：id,name,age
     * @param list 实体对象集合
     * @return
     */
    public int insertObjects(String[] columns, List list, int bathLimit) {
        if (columns == null || columns.length == 0 || list == null || list.size() == 0) return 0;
        // 将对象数据转为Map数据
        List<Map<String, Object>> dataList = new ArrayList();
        // 字段转换为大写
        List<String> columnList = new ArrayList();
        for (int i = 0; i < columns.length; i++) {
            columns[i] = columns[i].toUpperCase();
            columnList.add(columns[i]);
        }
        // 表名
        String table = null;
        for (Object entity : list) {
            Map<String, Object> info = DaoUtil.transformObj(entity);
            if (table == null) {
                // 表名
                table = (String) info.get("TABLE_NAME");
            }
            // 数据
            Map<String, Object> dataMap = (Map<String, Object>) info.get("dataMap");
            // 处理字段数量一致
            for (String column : columns) {
                if (!dataMap.containsKey(column)) {
                    dataMap.put(column, null);
                }
            }
            // 处理删除多余的字段
            Object[] columnAttr = dataMap.keySet().toArray();
            for (int i=0;i<columnAttr.length;i++) {
                String column = columnAttr[i].toString();
                if (!column.startsWith("_SQL_") && !columnList.contains(column)) {
                    dataMap.remove(column);
                }
            }
            dataList.add(dataMap);
        }
        // 字段排序
        Arrays.sort(columns);
        return insertMaps(table, columns, dataList, bathLimit);// 采用SELECT形式处理
    }

    /**
     * 批量insert并支持分批插入，如全量则bathLimit=0,采用select模式
     *
     * @param tableName
     * @param columns 采用insert all模式时不需要
     * @param list
     * @param bathLimit 分批
     * @return
     */
    public int insertMaps(String tableName, String[] columns, List<Map<String, Object>> list, int bathLimit) {
        if (list == null || list.size() == 0) return 0;
        // 分批insert
        if (bathLimit > 0) {
            int diffCount = bathLimit;
            int diffCic = 0;
            int n = 0;
            if (list.size() % diffCount == 0) {
                diffCic = list.size() / diffCount;
            } else {
                diffCic = list.size() / diffCount + 1;
            }
            for (int dif = 0; dif < diffCic; dif++) {
                int start = dif * diffCount;
                int end = (dif + 1) * diffCount;
                // 分批
                n += baseMapper.insertBath(tableName, columns,
                        list.subList(start, end > list.size() ? list.size() : end));
            }
            return n;
        }
        int result = baseMapper.insertBath(tableName, columns, list);
        return result;
    }

    /**
     * 批量insert并支持全量插入，该方式支持不同实体分别insert到不同的表中,采用insert all模式
     *
     * @param list
     * @return
     */
    public int insertAllObjects(List list) {
        return insertAllObjects(list, 0);
    }

    /**
     * 采用 union all select方式insert，该方式效率较高，但需要指定字段
     *
     * @param list 实体对象集合
     * @return
     */
    public int insertAllObjects(List list, int bathLimit) {
        if (list == null || list.size() == 0) return 0;
        // 将对象数据转为Map数据
        List<Map<String, Object>> dataList = new ArrayList();
        for (Object entity : list) {
            Map<String, Object> info = DaoUtil.transformObj(entity);
            dataList.add(info);
        }
        return insertAllMaps(dataList, bathLimit);// INSERT ALL形式
    }

    /**
     * 批量insert并支持分批插入，该方式支持不同实体分别insert到不同的表中，如全量则bathLimit=0,采用insert all模式
     *
     * @param list
     * @param bathLimit 分批
     * @return
     */
    private int insertAllMaps(List<Map<String, Object>> list, int bathLimit) {
        if (list == null || list.size() == 0) return 0;
        // 分批insert
        if (bathLimit > 0) {
            int diffCount = bathLimit;
            int diffCic = 0;
            int n = 0;
            if (list.size() % diffCount == 0) {
                diffCic = list.size() / diffCount;
            } else {
                diffCic = list.size() / diffCount + 1;
            }
            for (int dif = 0; dif < diffCic; dif++) {
                int start = dif * diffCount;
                int end = (dif + 1) * diffCount;
                n += baseMapper.insertAllBath(list.subList(start, end > list.size() ? list.size() : end));
            }
            return n;
        }
        int result = baseMapper.insertAllBath(list);
        return result;
    }

    /**
     * 通过Map插入到数据库
     *
     * @param tableName
     * @param dataMap
     * @return
     */
    public int insertMap(String tableName, LinkedHashMap<String, Object> dataMap) {
        Map<String, Object> re = new HashMap<String, Object>();
        re.put("TABLE_NAME", tableName);
        re.put("dataMap", dataMap);
        return baseMapper.insert(re);
    }

    public int updateObjects(List list) {
        return updateObjects(list, 0);
    }

    public int updateObjects(List list, int bathLimit) {
        if (list == null || list.size() == 0) return 0;
        Object entity = list.get(0);
        List<String> columns = DaoUtil.getAllColumn(entity, false);
        String[] s = new String[0];
        return updateObjects(columns.toArray(s), list, bathLimit);
    }

    public int updateObjects(String[] columns, List list, int bathLimit) {
        if (columns == null || columns.length == 0 || list == null || list.size() == 0) return 0;
        // 将对象数据转为Map数据
        List<Map<String, Object>> dataList = new ArrayList();
        // 字段转换为大写
        List<String> columnList = new ArrayList();
        for (int i = 0; i < columns.length; i++) {
            columns[i] = columns[i].toUpperCase();
            columnList.add(columns[i]);
        }
        // 表名
        String table = null;
        String keyColumn = null;
        for (Object entity : list) {
            Map<String, Object> info = DaoUtil.transformObj(entity);
            if (table == null) {
                // 表名
                table = (String) info.get("TABLE_NAME");
                // 主键字段
                keyColumn = (String) info.get("KEY_ID");
            }
            // 数据
            Map<String, Object> dataMap = (Map<String, Object>) info.get("dataMap");
            // 处理字段数量一致
            for (String column : columns) {
                if (!dataMap.containsKey(column)) {
                    dataMap.put(column, null);
                }
            }
            // 处理删除多余的字段
            Object[] columnAttr = dataMap.keySet().toArray();
            for (int i=0;i<columnAttr.length;i++) {
                String column = columnAttr[i].toString();
                if (!column.startsWith("_SQL_") && !columnList.contains(column) && !keyColumn.equalsIgnoreCase(column)) {
                    dataMap.remove(column);
                }
            }
            dataList.add(dataMap);
        }
        // 字段排序
        Arrays.sort(columns);
        return updateMaps(table, keyColumn, columns, dataList, bathLimit);
    }
    /**
     * 批量更新
     * @param tableName 表名
     * @param keyColumn 主键字段
     * @param columns   更新字段
     * @param list      更新内容
     * @param bathLimit 分批处理（每批次更新条数）
     * @return  返回更新数
     */
    public int updateMaps(String tableName, String keyColumn, String[] columns, List<Map<String, Object>> list,
                          int bathLimit) {
        return updateMaps(tableName, keyColumn, columns, list,bathLimit,null);
    }

    /**
     * 批量更新
     * @param tableName 表名
     * @param keyColumn 主键字段
     * @param columns   更新字段
     * @param list      更新内容
     * @param bathLimit 分批处理（每批次更新条数）
     * @param where     增加查询条件
     * @return  返回更新数
     */
    public int updateMaps(String tableName, String keyColumn, String[] columns, List<Map<String, Object>> list,
                          int bathLimit,String where) {
        if (columns == null || columns.length == 0 || list == null || list.size() == 0) return 0;
        if(list.get(0).get(keyColumn) == null)
            throw new SystemException("主键字段无内容");
        // 分批处理update
        if (bathLimit > 0) {
            int diffCount = bathLimit;
            int diffCic = 0;
            int n = 0;
            if (list.size() % diffCount == 0) {
                diffCic = list.size() / diffCount;
            } else {
                diffCic = list.size() / diffCount + 1;
            }
            for (int dif = 0; dif < diffCic; dif++) {
                int start = dif * diffCount;
                int end = (dif + 1) * diffCount;
                // 分批
                n += baseMapper.updateBath(tableName, keyColumn, columns,
                        list.subList(start, end > list.size() ? list.size() : end),where);
            }
            return n;
        }
        return baseMapper.updateBath(tableName, keyColumn, columns, list,where);
    }


    /**
     * 根据实体对象保存
     *
     * @param entity
     * @return
     */
    public int updateObject(Object entity) {
        Map<String, Object> params = DaoUtil.transformObj(entity);
        if (!params.containsKey("KEY_ID")) {
            throw new RuntimeException("实体类没有主键.");
        }
        if (params.get("KEY_VALUE") == null
                || (params.get("KEY_VALUE") instanceof String && ((String) params.get("KEY_VALUE")).trim().equals(""))) {
            throw new RuntimeException("主键值不能为空.");
        }
        // 主键赋值
        // params.put("KEY_VALUE",params.get("KEY_ID"));
        // 删除对象中主键数据
        TreeMap<String, Object> dataMap = (TreeMap<String, Object>) params.get("dataMap");
        dataMap.remove(params.get("KEY_ID"));
        // 保存
        return baseMapper.update(params);
    }

    public int updateObject(Object entity, Object keyValue) {
        Map<String, Object> params = DaoUtil.transformObj(entity);
        if (!params.containsKey("KEY_ID")) {
            throw new RuntimeException("实体类没有主键.");
        }
        // 主键赋值
        params.put("KEY_VALUE", keyValue);
        // 删除对象中主键数据
        Map<String, Object> dataMap = (Map<String, Object>) params.get("dataMap");
        dataMap.remove(params.get("KEY_ID"));
        // 保存
        return baseMapper.update(params);
    }

    /**
     * 主键不为空为新增，反之更新
     *
     * @param entity
     * @return
     */
    public int saveObject(Object entity) {
        Map<String, Object> params = DaoUtil.transformObj(entity);
        if (!params.containsKey("KEY_ID")) {
            throw new RuntimeException("实体类没有主键.");
        }
        // 判断是更新还是新增
        if (params.get("KEY_VALUE") == null
                || (params.get("KEY_VALUE") instanceof String && ((String) params.get("KEY_VALUE")).trim().equals(""))) {
            // 新增
            return insertObject(entity);
        } else {
            // 更新
            return updateObject(entity);
        }
    }

    /**
     * 根据主键删除指定实体对象对应的表数据
     *
     * @param entity 实体类
     * @param keyValue 主键值
     * @return
     */
    public int deleteObject(Class entity, Object keyValue) {
        Map<String, Object> params = new HashMap<String, Object>();
        String tableKey = DaoUtil.getTableKey(entity);
        String idKey = DaoUtil.getIdKey(entity);
        //
        if (tableKey == null || idKey == null) {
            throw new RuntimeException("实体或主键不存在.");
        }
        // 赋值
        params.put("TABLE_NAME", tableKey);// 表
        params.put("KEY_ID", idKey);// 主键
        params.put("KEY_VALUE", keyValue);// 主键值
        return baseMapper.delete(params);
    }

    /**
     * 执行sql
     *
     * @param sql
     * @param params
     * @return
     */
    public int execute(String sql, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        // 处理sql
        sql = sqlParameter(sql, params);
        params.put("SQL", sql);
        return baseMapper.execute(params);
    }

    /**
     * 通过sql查询并将结果集赋值到指定的类
     *
     * @param sql
     * @param params
     * @param clazz
     * @return
     */
    public <T> List<T> list(String sql, Map<String, Object> params, Class<T> clazz) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        // 处理sql
        sql = sqlParameter(sql, params);
        //
        params.put("SQL", sql);
        List<Map<String, Object>> result = baseMapper.list(params);
        List list = DaoUtil.list(result, clazz);
        return list;
    }

    public List<Map<String, Object>> listMap(String sql, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        // 处理sql
        sql = sqlParameter(sql, params);
        params.put("SQL", sql);
        List<Map<String, Object>> result = baseMapper.list(params);
        return result;
    }

    /**
     * 返回一个数据结果
     *
     * @param sql
     * @param params
     * @return
     */
    public Object queryObject(String sql, Map<String, Object> params) {
        List<Map<String, Object>> list = listMap(sql, params);
        if (list.size() > 0) {
            Map<String, Object> data = list.get(0);
            if(data.values().size() == 1){
                //单字段：获取返回值
                return data.values().iterator().next();
            }else{
                //多字段：获取第一条Map集合
                return data;
            }
        }
        return null;
    }

    /**
     * 返回一个实体对象
     *  例：select * from handover_Fault t where id='668b36cd71c84606ac2b06abfa111190'
     * @param sql
     * @param params
     * @param entity
     * @return
     */
    public <T> T queryObject(String sql, Map<String, Object> params, Class<T> entity) {
        List<T> list = list(sql, params,entity);
        return list.size() == 0 ? null : list.get(0);
    }
    /**
     * 获取结果集总数
     *
     * @param sql
     * @param params
     * @return
     */
    public int queryCount(String sql, Map<String, Object> params) {
        Object o = queryObject(sql, params);
        int result = 0;
        if (o != null) {
            if (o instanceof Integer) {
                result = (Integer) o;
            } else if (o instanceof Long) {
                result = ((Long) o).intValue();
            } else if (o instanceof BigInteger) {
                result = ((BigInteger) o).intValue();
            } else if (o instanceof BigDecimal) {
                result = ((BigDecimal) o).intValue();
            } else {
                throw new RuntimeException("数据类型不支持，请在此处维护.");
            }
        }
        return result;
    }

    /**
     * 返回实体类的所有数据
     *
     * @param entity
     * @return
     */
    public <T> List<T> listAll(Class<T> entity) {
        // 获取表名
        String table = DaoUtil.getTableKey(entity);
        if (null == table) {
            throw new RuntimeException("Error Input Object! Error @Table Annotation.");
        }
        StringBuilder sql = new StringBuilder("select * from ").append(table);
        return list(sql.toString(), null, entity);
    }

    /**
     * 分页处理
     *
     * @param sql
     * @param params
     * @param clazz
     * @param currentPage
     * @param pageSize
     * @return
     */
    public <T> Pager<T> pagedQuery(String sql, Map<String, Object> params, Class<T> clazz, int currentPage, int pageSize) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        // 处理sql
        sql = sqlParameter(sql, params);
        params.put("SQL", sql);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Map<String, Object>> result = baseMapper.list(params);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(result);
        // 将Map转换为实体对象
        List<T> list = DaoUtil.list(result, clazz);
        // 封装到分页
        Pager pager = new Pager();
        pager.setData(list);
        pager.setPageSize(pageSize);
        pager.setCurrentPage(currentPage);
        pager.setTotalPage(page.getPages());
        pager.setTotalSize(page.getTotal());

        return pager;
    }

    /**
     * 针对sql中${}参数的处理
     *
     * @param sql
     * @return
     */
    private String sqlParameter(String sql, Map<String, Object> params) {
        // 提取sql中的所有参数
        Matcher matcher = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE).matcher(sql);
        while (matcher.find()) {
            // 替换参数
            if (params.containsKey(matcher.group(1))) {
                sql = sql.replaceAll("\\$\\{" + matcher.group(1) + "\\}", params.get(matcher.group(1)).toString());
            } else {
                throw new RuntimeException("缺少参数【" + matcher.group(1) + "】");
            }
        }
        // 集合参数处理
        for (Map.Entry<String, Object> p : params.entrySet()) {
            StringBuffer arrayStr = new StringBuffer();
            if (p.getValue() instanceof Object[]) {
                if (p.getValue() instanceof String[]) {
                    arrayStr.append("'").append(StringUtils.join((String[]) p.getValue(), "','")).append("'");
                } else {
                    throw new RuntimeException("该参数【" + p.getKey() + "】数据格式请在此扩展");
                }
            }
            if (p.getValue() instanceof int[]) {
                for (int i : (int[]) p.getValue()) {
                    if (arrayStr.length() > 0) arrayStr.append(",");
                    arrayStr.append(i);
                }
            }
            if (p.getValue() instanceof long[]) {
                for (long i : (long[]) p.getValue()) {
                    if (arrayStr.length() > 0) arrayStr.append(",");
                    arrayStr.append(i);
                }
            }
            if (p.getValue() instanceof float[]) {
                for (float i : (float[]) p.getValue()) {
                    if (arrayStr.length() > 0) arrayStr.append(",");
                    arrayStr.append(i);
                }
            }
            if (p.getValue() instanceof double[]) {
                for (double i : (double[]) p.getValue()) {
                    if (arrayStr.length() > 0) arrayStr.append(",");
                    arrayStr.append(i);
                }
            }
            if (p.getValue() instanceof Collection) {
                Collection c = (Collection) p.getValue();
                for (Object o : c) {
                    if (o instanceof String) {
                        if (arrayStr.length() > 0) arrayStr.append(",");
                        arrayStr.append("'").append(o).append("'");
                    } else if (o instanceof Integer) {
                        if (arrayStr.length() > 0) arrayStr.append(",");
                        arrayStr.append(o);
                    } else if (o instanceof Long) {
                        if (arrayStr.length() > 0) arrayStr.append(",");
                        arrayStr.append(o);
                    } else if (o instanceof Float) {
                        if (arrayStr.length() > 0) arrayStr.append(",");
                        arrayStr.append(o);
                    } else if (o instanceof Double) {
                        if (arrayStr.length() > 0) arrayStr.append(",");
                        arrayStr.append(o);
                    } else {
                        throw new RuntimeException("该参数【" + p.getKey() + "】数据格式请在此扩展");
                    }
                }
            }
            if (arrayStr.length() > 0) {
                sql = sql.replaceAll("\\#\\{" + p.getKey() + "\\}", arrayStr.toString());
            }
        }
        return sql;
    }
}

