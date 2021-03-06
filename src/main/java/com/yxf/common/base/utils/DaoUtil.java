package com.yxf.common.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.yxf.common.base.annotation.Association;
import com.yxf.common.base.annotation.Column;
import com.yxf.common.base.annotation.Id;
import com.yxf.common.base.annotation.Table;
import com.yxf.common.base.bean.AssociationsDto;

/**
 * yunxiangfu@163.com 20190301
 */
public class DaoUtil {

    /**
     * 根据class获得表名
     * 
     * @param entity
     * @return
     */
    public static String getTableKey(Class entity) {
        Table table = (Table) entity.getAnnotation(Table.class);
        if (table == null) return null;
        return table.value();
    }

    /**
     * 获得实体类主键字段
     * 
     * @param entity
     * @return
     */
    public static String getIdKey(Class entity) {
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            Id idKey = field.getAnnotation(Id.class);
            if (idKey != null) {
                return idKey.value();
            }
        }
        return null;
    }

    /**
     * 获得外键关联
     * 
     * @param entity
     * @return
     */
    public static List<AssociationsDto> getAssociations(Class entity) {
        Field[] fields = entity.getDeclaredFields();
        List<AssociationsDto> list = new ArrayList();
        for (Field field : fields) {
            Association annotation = field.getAnnotation(Association.class);
            if (annotation != null) {
                AssociationsDto associationsDto = new AssociationsDto();
                associationsDto.setColumnPrefix(annotation.value());
                associationsDto.setField(field);
                list.add(associationsDto);
            }
        }
        return list;
    }

    /**
     * 将实体对象根据注解转换为map
     * 
     * @param entity 实体对象
     * @return
     */
    public static Map<String, Object> transformObj(Object entity) {
        // 获取表名
        String tableName = getTableKey(entity.getClass());
        if (null == tableName) {
            throw new RuntimeException("Error Input Object! Error @Table Annotation.");
        }
        Map<String, Object> re = new HashMap<String, Object>();
        re.put("TABLE_NAME", tableName);

        Field[] fields = entity.getClass().getDeclaredFields();
        if (null == fields || fields.length <= 0) {
            throw new RuntimeException("Error Input Object! No Field.");
        }
        // 存放列名及值
        Map<String, Object> data = new TreeMap();
        for (Field field : fields) {
            // 获取列名和值
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (null != field.getAnnotation(Id.class)) {// 获取注解主键
                    re.put("KEY_ID", field.getAnnotation(Id.class).value().toUpperCase());
                    re.put("KEY_VALUE", value);
                    //
                    if (null != value) {
                        // 主键字段
                        data.put(field.getAnnotation(Id.class).value().toUpperCase(), value);
                    }
                }
                // 获取注解字段
                else if (null != field.getAnnotation(Column.class)) {
                    if (null != value) {
                        data.put(field.getAnnotation(Column.class).value().toUpperCase(), value);
                    }
                    continue;
                }
                // 未注解的不处理
                else {

                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error Input Object! Error Invoke Get Method.", e);
            }
        }
        // 将map按Key排序
        // Map<String,Object> newDataMap = sortMapByKey(data);
        re.put("dataMap", data);
        return re;
    }

    /**
     * 获取对象所有注解字段
     * 
     * @param entity 实体对象
     * @param isValueNotNull 是否只值不为null的
     * @return
     */
    public static List<String> getAllColumn(Object entity, boolean isValueNotNull) {
        List<String> columnList = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 获取列名和值
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (null != field.getAnnotation(Id.class)) {// 获取注解主键
                    if (value != null && isValueNotNull) {
                        columnList.add(field.getAnnotation(Id.class).value().toUpperCase());
                    } else {
                        columnList.add(field.getAnnotation(Id.class).value().toUpperCase());
                    }
                }
                // 获取注解字段
                else if (null != field.getAnnotation(Column.class)) {
                    if (value != null && isValueNotNull) {
                        columnList.add(field.getAnnotation(Column.class).value().toUpperCase());
                    } else {
                        columnList.add(field.getAnnotation(Column.class).value().toUpperCase());
                    }
                    continue;
                }
                // 未注解的不处理
                else {

                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error Input Object! Error Invoke Get Method.", e);
            }
        }
        return columnList;
    }

    /**
     * 属性赋值
     * 
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void fieldToValue(Object obj, String fieldName, Object value) {
        Class<?> ojbClass = obj.getClass();
        Field[] fields = ojbClass.getDeclaredFields();
        for (Field field : fields) {
            if ((field.getAnnotation(Column.class) != null && field.getAnnotation(Column.class).value() != null
                 && field.getAnnotation(Column.class).value().equalsIgnoreCase(fieldName))// 根据表字段注解匹配
                || (field.getAnnotation(Id.class) != null && field.getAnnotation(Id.class).value() != null
                    && field.getAnnotation(Id.class).value().equalsIgnoreCase(fieldName))// 根据表主键注解匹配
                || field.getName().equalsIgnoreCase(fieldName)// 根据属性名称匹配
                || field.getName().equalsIgnoreCase(fieldName.replaceAll("_", ""))) {// 根据属性名称除去'_'匹配
                fieldToValue(obj, field, value);
                break;
            }
        }
    }

    /**
     * 属性赋值
     * 
     * @param obj
     * @param field
     * @param value
     */
    public static void fieldToValue(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            //
            Class<?> fieldType = field.getType();
            // 针对数据类型为BigDecimal的处理
            if (value instanceof BigDecimal) {
                if (fieldType == java.math.BigDecimal.class) {// java.lang.BigDecimal
                    java.math.BigDecimal decimal = (java.math.BigDecimal) value;
                    field.set(obj, decimal);
                } else if (fieldType == Double.class) {// java.lang.Double
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, decimal.doubleValue());
                } else if (fieldType == Float.class) {// java.lang.Float
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, decimal.floatValue());
                } else if (fieldType == Long.class) {// java.lang.Long
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, decimal.longValue());
                } else if (fieldType == Integer.class) {// java.lang.Integer
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, decimal.intValue());
                } else if (fieldType == Short.class) {// java.lang.Short
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, decimal.shortValue());
                } else if (fieldType == Byte.class) {// java.lang.Byte
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, decimal.byteValue());
                } else if (fieldType == String.class) {// java.lang.String
                    BigDecimal decimal = (BigDecimal) value;
                    field.set(obj, String.valueOf(decimal.intValue()));
                }
            } else {// 非java.math.BigDecimal类型处理
                field.set(obj, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("返回数据类型与对象字段类型不匹配", e);
        }
    }

    /**
     * 将List<Map>转换为指定的List对象
     * 
     * @param oldList
     * @param toClazz
     * @return 返回封装后的List
     */
    public static <T> List<T> list(List<Map<String, Object>> oldList, Class<T> toClazz) {
        List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
        // 外键关联字段
        List<AssociationsDto> associations = getAssociations(toClazz);
        /**
         * 一对多去重处理
         */
        for (AssociationsDto association : associations) {
            /**
             * 一对多处理
             */
            if (association.getField().getType() == List.class) {
                Field field = association.getField();
                for (int i = 0; i < oldList.size(); i++) {
                    Map<String, Object> oldMap = oldList.get(i);
                    boolean flag = false;
                    for (int j = 0; j < newList.size(); j++) {
                        Map<String, Object> newMap = newList.get(j);
                        /**
                         * 去重比较
                         */
                        int n1 = 0;// 字段相同数
                        int n2 = 0;// 总需要验证的字段数
                        for (Map.Entry<String, Object> entry : newMap.entrySet()) {
                            String column = entry.getKey();
                            if (!column.toLowerCase().startsWith(association.getColumnPrefix().toLowerCase())
                                && !column.equalsIgnoreCase(field.getName())) {
                                // null验证处理
                                if (entry.getValue() == null && oldMap.get(entry.getKey()) == null
                                    || (entry.getValue() == null || oldMap.get(entry.getKey()) == null)) {
                                    continue;
                                }
                                n2++;
                                if (entry.getValue() instanceof String) {
                                    // 数据比较：String
                                    String oldValue = oldMap.get(entry.getKey()).toString();
                                    String value = entry.getValue().toString();
                                    if ((value != null && oldValue != null && value.equalsIgnoreCase(oldValue))) {
                                        n1++;
                                    }
                                } else if (entry.getValue() instanceof BigDecimal) {
                                    // 数据比较：BigDecimal
                                    BigDecimal oldValue = (BigDecimal) oldMap.get(entry.getKey());
                                    BigDecimal value = (BigDecimal) entry.getValue();
                                    if ((value != null && oldValue != null && value.compareTo(oldValue) == 0)) {
                                        n1++;
                                    }
                                } else if (entry.getValue() instanceof Long) {
                                    // 数据比较：Long
                                    Long oldValue = (Long) oldMap.get(entry.getKey());
                                    Long value = (Long) entry.getValue();
                                    if ((value != null && oldValue != null
                                         && value.longValue() == oldValue.longValue())) {
                                        n1++;
                                    }
                                } else if (entry.getValue() instanceof Integer) {
                                    // 数据比较：Long
                                    Integer oldValue = (Integer) oldMap.get(entry.getKey());
                                    Integer value = (Integer) entry.getValue();
                                    if ((value != null && oldValue != null
                                         && value.intValue() == oldValue.intValue())) {
                                        n1++;
                                    }
                                }
                            }
                        }
                        /**
                         * 验证通过
                         */
                        if (n1 == n2) {
                            // 比对已存在
                            List associationList = (List) newMap.get(field.getName());
                            field.setAccessible(true);
                            // 获取 list 字段的泛型参数
                            ParameterizedType listGenericType = (ParameterizedType) field.getGenericType();
                            Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
                            if (listActualTypeArguments.length == 0) {
                                throw new RuntimeException("没有指定对象类型【" + field.getName() + "】");
                            }
                            // 实例化外键关联对象
                            Class<?> accountPrincipalApproveClazz = (Class<?>) listActualTypeArguments[0];
                            try {
                                Object associationObject = accountPrincipalApproveClazz.newInstance();
                                // 对象赋值
                                toAssociationObject(oldList.get(i), associationObject, association.getColumnPrefix());
                                associationList.add(associationObject);
                            } catch (Exception ex) {
                                throw new RuntimeException("实例化外键关联对象【" + accountPrincipalApproveClazz.getName()
                                                           + "】失败");
                            }
                            flag = true;
                            continue;
                        }
                    }
                    if (!flag) {
                        Map newMap = new HashMap(oldMap);
                        field.setAccessible(true);
                        // 获取 list 字段的泛型参数
                        ParameterizedType listGenericType = (ParameterizedType) field.getGenericType();
                        Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
                        if (listActualTypeArguments.length == 0) {
                            throw new RuntimeException("没有指定对象类型【" + field.getName() + "】");
                        }
                        List associationList = new ArrayList();
                        // 实例化外键关联对象
                        Class<?> accountPrincipalApproveClazz = (Class<?>) listActualTypeArguments[0];
                        try {
                            Object associationObject = accountPrincipalApproveClazz.newInstance();
                            // 对象赋值
                            toAssociationObject(oldList.get(i), associationObject, association.getColumnPrefix());
                            associationList.add(associationObject);
                        } catch (Exception ex) {
                            throw new RuntimeException("实例化外键关联对象【" + accountPrincipalApproveClazz.getName() + "】失败");
                        }
                        newMap.put(field.getName(), associationList);

                        newList.add(newMap);
                    }
                }
            }
        }
        if (newList.size() == 0 && oldList.size() > 0) {
            newList = oldList;
        }
        ///
        List list = new ArrayList();
        for (Map<String, Object> map : newList) {
            Object obj = null;
            try {
                // 对象实例化
                obj = toClazz.newInstance();
                /**
                 * 主对象赋值
                 */
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 数据字段名称
                    String field = entry.getKey();
                    // 字段对象属性赋值
                    fieldToValue(obj, field, entry.getValue());
                }
                /**
                 * 一对一处理
                 */
                for (AssociationsDto association : associations) {
                    if (association.getField().getType() != List.class) {
                        // 实例化外键关联对象
                        Object associationObject = association.getField().getType().newInstance();
                        // 对象赋值
                        toAssociationObject(map, associationObject, association.getColumnPrefix());
                        // 主实体赋值
                        fieldToValue(obj, association.getField().getName(), associationObject);
                    }
                }
                //
                list.add(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static void toAssociationObject(Map<String, Object> map, Object associationObject, String columnPrefix) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            // 数据字段名称
            String fieldName = entry.getKey().toLowerCase();
            if (fieldName.startsWith(columnPrefix.toLowerCase())) {
                fieldName = fieldName.substring(columnPrefix.length());
                // 字段对象属性赋值
                fieldToValue(associationObject, fieldName, entry.getValue());
            }
        }
    }
}
