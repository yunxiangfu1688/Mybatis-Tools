package com.yxf.common.base.bean;

import java.util.HashMap;
import java.util.Map;

public class BasePojo {
    /**
     * 用于新增或更新时SQL参数赋值
     */
    private Map<String, String> sqlColumns = new HashMap<String, String>();

    /**
     * 增加SQL
     * @param column    字段名称，例：createDate
     * @param sqlValue  对应值，例：to_date('2019-09-10','yyyy-mm-dd')
     */
    public void addSqlColumns(String column, String sqlValue){
        sqlColumns.put(column,sqlValue);
    }

    public Map<String, String> getSqlColumns() {
        return sqlColumns;
    }

    public void setSqlColumns(Map<String, String> sqlColumns) {
        this.sqlColumns = sqlColumns;
    }
}
