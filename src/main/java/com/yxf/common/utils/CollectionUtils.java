/**
 * Copyright(c) 2017-2018 NIOS, All Rights Reserved. Project: openTsdb Author: mujiaqi Createdate: 2018年8月8日 下午2:02:28
 * Version: 1.0
 */
package com.yxf.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:集合操作工具类
 * @project openTsdb
 * @class FixGroupUtil.java
 * @author mujiaqi
 * @version 1.0
 * @date 2018年8月8日 下午2:02:28
 */
public class CollectionUtils {

    /**
     * @Description:按照指定条数切分
     * @author mujiaqi
     * @version 1.0
     * @date 2018年9月12日 上午11:40:46
     * @param source
     * @param recordsNumOfList
     * @return
     */
    public static <T> List<List<T>> fixedGrouping(List<T> source, int recordsNumOfList) {
        if (null == source || source.size() == 0 || recordsNumOfList <= 0) return null;
        List<List<T>> result = new ArrayList<List<T>>();

        int sourceSize = source.size();
        int size = (source.size() / recordsNumOfList) + 1;
        for (int i = 0; i < size; i++) {
            List<T> subset = new ArrayList<T>();
            for (int j = i * recordsNumOfList; j < (i + 1) * recordsNumOfList; j++) {
                if (j < sourceSize) {
                    subset.add(source.get(j));
                }
            }
            result.add(subset);
        }
        return result;
    }

}
