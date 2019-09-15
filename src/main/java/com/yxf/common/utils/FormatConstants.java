/**
 * FormatConstants.java
 * create on 2012-9-28
 * Copyright 2007-2012 gener-tech All Rights Reserved.
 */
package com.yxf.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * <pre>
 * 功能说明：日期格式化类
 * </pre>
 * 
 * @author <a href="mailto:liu.w@gener-tech.com">liuwei</a>
 * @version 1.0
 */
public abstract class FormatConstants {

    public static final DateFormat DATE_FORMAT                   = new SimpleDateFormat("yyyy-MM-dd",
                                                                         java.util.Locale.CHINA);

    public static final DateFormat TIME_FORMAT                   = new SimpleDateFormat("HH:mm:ss",
                                                                         java.util.Locale.CHINA);

    public static final DateFormat DATE_TIME_FORMAT              = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                                                                         java.util.Locale.CHINA);

    public static final DateFormat DATE_TIME_FORMAT_STR          = new SimpleDateFormat("yyyyMMddHHmmss",
                                                                         java.util.Locale.CHINA);

    public static final DateFormat YYYYMMDDHHMM_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                                                                         java.util.Locale.CHINA);

}
