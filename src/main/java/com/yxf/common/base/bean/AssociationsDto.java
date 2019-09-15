package com.yxf.common.base.bean;

import java.lang.reflect.Field;

public class AssociationsDto {
    /**
     * 字段前缀，用于处理外键关联匹配
     */
    private String columnPrefix;
    /**
     * 外键关联字段
     */
    private Field field;


    public String getColumnPrefix() {
        return columnPrefix;
    }

    public void setColumnPrefix(String columnPrefix) {
        this.columnPrefix = columnPrefix;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
