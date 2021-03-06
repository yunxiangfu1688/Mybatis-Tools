package com.yxf.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateUtil {

    public static final String DATE_PATTERN_S               = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN_YYYYMMDDHHMMSS  = "yyyyMMddHHmmssSSS";

    public static final String DATE_PATTERN_YYYYMMDDHHMMSS2 = "yyyy/MM/dd HH:mm:ss";

    public static final String DATE_PATTERN_HHMMSS          = "HH:mm:ss";

    public static final String DATE_PATTERN_D               = "yyyy-MM-dd";
    public static final String DATE_PATTERN_MDY             = "MM/dd/yyyy";
    public static final String DATE_PATTERN_YMD             = "yyyy/MM/dd";

    public static final String DATE_PATTERN_M               = "yyyyMM";
    public static final String DATE_PATTERN_YYYYMMDD        = "yyyyMMdd";

    public static final String DATE_PATTERN_D_CN            = "yyyy年MM月dd日";
    public static final String DATE_PATTERN_D_EN            = "dd/MM/yyyy";
    public static final String DATE_PATTERN_YYYYMMDDHHMM    = "yyyyMMddHHmm";

    public static final int    HistoryDay                   = -1;                   // 三天以后就查询Hadoop

    public static Date format(String dateStr) {
        Date date = null;
        if (dateStr == null || "".equals(dateStr.trim())) {
            return date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_D);

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 把字符串转化为日期类型
     * 
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date convert2Date(String dateStr, String pattern) {
        Date date = null;
        if (dateStr == null || "".equals(dateStr.trim())) {
            return date;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public static void main(String[] args) {
        System.out.println(getAddParamHours(12, DateUtil.DATE_PATTERN_YYYYMMDD));
        System.out.println(date2String("20190604000231"));
    }

    public static String format(Date date) {
        String dateStr = null;
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_D);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static String format2(Date date) {
        String dateStr = null;
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_S);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        dateStr = sdf.format(date);
        return dateStr;
    }

    /*
     * 取得当前日期（格式：yyyyMMdd）
     */
    public static String format3(Date date) {
        String dateStr = null;
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static String format4(Date date) {
        String dateStr = null;
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS2);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date parse3(String dateStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据日期模式，返回需要的日期对象
     * 
     * @param date
     * @param pattern
     * @return String
     */
    public static String convert2Str(Date date, String pattern) {
        String dateStr = null;
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 根据日期格式, 获取当前日期字符串
     * 
     * @param pattern
     * @return
     */
    public static String getCurrentDate(String pattern) {
        return convert2Str(new Date(), pattern);
    }

    /**
     * 日期加一天的方法
     * 
     * @param date
     * @return
     */
    public static Date getAddDate(Date date, int step) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        // 设置日期
        c.setTime(date);
        // 日期加1
        c.add(Calendar.DATE, step);
        // 结果
        return c.getTime();
    }

    /**
     * @param date
     * @return
     */
    public static String addDate(Date date, int type, int step) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        // 设置日期
        c.setTime(date);
        // 日期加1
        c.add(type, step);
        // 结果
        return convert2Str(c.getTime(), DATE_PATTERN_YYYYMMDDHHMMSS);
    }

    /**
     * 日期加多少天的方法2
     * 
     * @param specifiedDay 日期 格式yyyy-MM-dd
     * @param step 天
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int step) {
        String dayAfter = "";
        try {
            Calendar c = Calendar.getInstance();
            Date date = null;
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);

            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day + 1);
            dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayAfter;
    }

    /**
     * 当前系统下一个月份
     * 
     * @return
     */
    public static Long getNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(GregorianCalendar.MONTH, 1);
        SimpleDateFormat theDate = new SimpleDateFormat(DATE_PATTERN_M);
        String dateString = theDate.format(cal.getTime());
        return Long.valueOf(dateString);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;

        return weekDays[w];
    }

    public static String getWeekOfDateEn(Date dt) {
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) w = 0;

        return weekDays[w];
    }

    /**
     * 设置区域时间
     * 
     * @param sdf SimpleDateFormat 日期格式
     */
    public static void setTimeZone(SimpleDateFormat sdf) {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    /**
     * 设置区域时间
     * 
     * @param sdf SimpleDateFormat 日期格式
     * @param area 区域
     */
    public static void setTimeZone(SimpleDateFormat sdf, String area) {
        sdf.setTimeZone(TimeZone.getTimeZone(area));
    }

    /**
     * 昨天的日期
     * 
     * @param now 当前日期
     */
    public static Date calcYesterday(String now) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(GregorianCalendar.DATE, -1);

        return calendar.getTime();

    }

    public static String date2String(String datestr) {
        String str = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newDate = formatter.parse(datestr);
            return formatter1.format(newDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 本周开始的日期
     * 
     * @param now 当前日期
     * @return
     */
    public static Date calcThisWeek(String now) {
        GregorianCalendar calendar = new GregorianCalendar();
        int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) - 2;
        if (minus < 0) {
            return new Date();
        } else {
            calendar.add(GregorianCalendar.DATE, -minus);
            return calendar.getTime();
        }
    }

    /**
     * 上周开始日期
     * 
     * @param now 当前日期
     * @return
     */
    public static Date calcLastWeekBegin(String now) {
        GregorianCalendar calendar = new GregorianCalendar();
        int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) + 1;

        calendar.add(GregorianCalendar.DATE, -minus);
        calendar.add(GregorianCalendar.DATE, -4);
        return calendar.getTime();
    }

    /**
     * 上周结束日期
     * 
     * @param now
     * @return
     */
    public static Date calcLastWeekEnd(String now) {
        GregorianCalendar calendar = new GregorianCalendar();
        int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK) - 1;
        calendar.add(GregorianCalendar.DATE, -minus);
        return calendar.getTime();
    }

    /**
     * 上个月的结束日期
     * 
     * @param now 当前日期
     * @return
     */
    public static Date calcLastMonth(String now) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(calendar.get(GregorianCalendar.YEAR), calendar.get(GregorianCalendar.MONTH), 1);
        calendar.add(GregorianCalendar.DATE, -1);
        return calendar.getTime();

    }

    public static String getTodayStart() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        String now = sdf.format(date);
        return now.substring(0, 8) + "000000";
    }

    public static String getTodayEnd() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        String now = sdf.format(date);
        return now.substring(0, 8) + "235959";
    }

    /**
     * @Title minCovertDayHourMin @author 尹新红 yin.xh@gener-tech.com @Description 将分钟转化为天小时分 @param startTime
     * 预计停车时间 @param endTime 实际停车时间 @return @return String @throws
     */
    public static String timeDifference(Date startTime, Date endTime) {
        if (startTime == null || endTime == null || startTime.getTime() > endTime.getTime()
            || startTime.equals(endTime)) {
            return "0";
        } else {
            StringBuffer timeDifferenceStr = new StringBuffer();
            long timeDifference = endTime.getTime() - startTime.getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeDifference);
            calendar.add(Calendar.HOUR_OF_DAY, -8);
            timeDifferenceStr.append(calendar.get(Calendar.HOUR_OF_DAY) + "小时" + calendar.get(Calendar.MINUTE) + "分钟");
            return timeDifferenceStr.toString();
        }
    }

    public static String timeDifference(String startTime, String endTime) {
        String result = endTime;
        if (startTime.contains("小时") && startTime.contains("分钟")) {
            String sH = startTime.substring(0, startTime.indexOf("小时"));
            String sS = startTime.substring(startTime.indexOf("小时") + 2);
            String sM = sS.substring(0, sS.indexOf("分钟"));
            String eH = endTime.substring(0, endTime.indexOf("小时"));
            String eS = endTime.substring(endTime.indexOf("小时") + 2);
            String eM = eS.substring(0, eS.indexOf("分钟"));
            Integer minute = Integer.parseInt(eH) * 60 + Integer.parseInt(eM) - Integer.parseInt(sH) * 60
                             - Integer.parseInt(sM);
            Integer h = minute / 60;
            Integer m = minute - (minute / 60 * 60);
            result = h + "小时" + m + "分钟";

        }
        return result;
    }

    public static Date parse(String strDate, String ft) {
        Date rlt = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(ft);
            rlt = sdf.parse(strDate);
        } catch (Exception e) {
            // Log.warn("DateUtil.parse Exception--->"+e);
        }

        return rlt;
    }

    /**
     * 获得2个时间字符串之间的日期集合
     * 
     * @author lxf
     * @param min
     * @param max
     * @return
     */
    public static List<String> getDatesByTime(String min, String max) {
        List<String> result = new ArrayList<String>();
        if (min != null && !"".equals(min.trim()) && max != null && !"".equals(max.trim()) && min.compareTo(max) < 1) {
            Date date1 = parse(min.substring(0, 8), DATE_PATTERN_YYYYMMDD);
            Date date2 = parse(max.substring(0, 8), DATE_PATTERN_YYYYMMDD);
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            while (true) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                if (-1 < date2.compareTo(c.getTime())) {
                    String year = String.valueOf(c.get(Calendar.YEAR));
                    String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                    if (month.length() < 2) {
                        month = "0" + month;
                    }
                    String day = String.valueOf(c.get(Calendar.DATE));
                    if (day.length() < 2) {
                        day = "0" + day;
                    }
                    String date = year + month + day;
                    result.add(date);
                } else {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获得2个时间字符串之间的日期集合
     * 
     * @author tns
     * @param min
     * @param max
     * @return
     */
    public static Map<String, String> getDatesToDay(String min) {
        Date dated = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dated);
        c1.add(Calendar.DAY_OF_MONTH, HistoryDay);
        String max = convert2Str(c1.getTime(), DATE_PATTERN_YYYYMMDD);
        Map<String, String> map = new HashMap<String, String>();
        if (min != null && !"".equals(min.trim()) && max != null && !"".equals(max.trim()) && min.compareTo(max) < 1) {
            Date date1 = parse(min.substring(0, 8), DATE_PATTERN_YYYYMMDD);
            Date date2 = parse(max.substring(0, 8), DATE_PATTERN_YYYYMMDD);
            Calendar c = Calendar.getInstance();
            c.setTime(date1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            while (true) {
                c.add(Calendar.DAY_OF_MONTH, 1);
                if (-1 < date2.compareTo(c.getTime())) {
                    String year = String.valueOf(c.get(Calendar.YEAR));
                    String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                    if (month.length() < 2) {
                        month = "0" + month;
                    }
                    String day = String.valueOf(c.get(Calendar.DATE));
                    if (day.length() < 2) {
                        day = "0" + day;
                    }
                    String date = year + month + day;
                    map.put(date, date);
                } else {
                    break;
                }
            }
        }
        return map;
    }

    public static String getSameDay() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, 0);
        Date date = time.getTime();
        return sf.format(date);
    }

    public static String getSameDayHHmmss() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, 0);
        Date date = time.getTime();
        return sf.format(date);
    }

    public static String getSame() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, -3);
        Date date = time.getTime();
        return sf.format(date);
    }

    public static String getStartTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar time = Calendar.getInstance();
        Date date = time.getTime();
        return sf.format(date);
    }

    public static String getAddParam(Integer day) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, -day);
        Date date = time.getTime();
        return sf.format(date);
    }

    /**
     * @param day 增加的天数,减少传负值
     * @return
     */
    public static Date addParamDay(Date date, Integer day) {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        time.add(Calendar.DATE, day);
        return time.getTime();
    }

    /**
     * @param day 减去的天数
     * @return
     */
    public static String getAddParamDay(Integer day) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, -day);
        Date date = time.getTime();
        return sf.format(date);
    }

    /*
     * 减去多少天 返回：yyyyMMddHHmmss
     */
    public static String getAddSouers(String datetime, Integer day) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar time = Calendar.getInstance();
        try {
            time.setTime(sf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.add(Calendar.DAY_OF_YEAR, -day);
        Date date = time.getTime();
        String min = sf.format(date);
        min = min.replaceAll("-", "");
        min = min.replaceAll(" ", "");
        min = min.replaceAll(":", "");
        return min;
    }

    /*
     * 减去多少天 返回：yyyyMMdd
     */
    public static String getAddSouers2(String datetime, Integer day) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Calendar time = Calendar.getInstance();
        try {
            time.setTime(sf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.add(Calendar.DAY_OF_YEAR, -day);
        Date date = time.getTime();
        String min = sf.format(date);
        return min;
    }

    /*
     * 拿到当前时间 返回：yyyyMMddHHmmss
     */
    public static String getLocalhostDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return sf.format(date);
    }

    /*
     * 拿到当前时间 返回：HHmmss
     */
    public static String getLocalhostHHMMSS() {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_PATTERN_HHMMSS);
        Date date = new Date();
        return sf.format(date);
    }

    /**
     * @Description: 当前日期减去参数中对应的小时数
     * @param hours
     * @return String
     * @throws @author LiLW
     * @date 2016年1月12日 下午3:03:04
     * @version V1.0
     */
    public static String getAddParamHours(Integer hours) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();
        time.add(Calendar.HOUR_OF_DAY, -hours);
        Date date = time.getTime();
        return sf.format(date);
    }

    /**
     * @Description: 当前日期减去参数中对应的小时数(自定义格式)
     * @param hours
     * @param strFromat
     * @return String
     * @throws @author LiLW
     * @date 2016年7月29日 下午2:32:16
     * @version V1.0
     */
    public static String getAddParamHours(Integer hours, String strFromat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFromat);
        Calendar time = Calendar.getInstance();
        time.add(Calendar.HOUR_OF_DAY, -hours);
        Date date = time.getTime();
        return sf.format(date);
    }

    /**
     * @Description: 获取今日及之前的num天的日期
     * @param num
     * @return List<String>
     * @throws @author LiLW
     * @date 2016年3月23日 下午1:31:29
     * @version V1.0
     */
    public static List<String> getDayListByNum(int num, Date nowDate) {
        List<String> dayList = new ArrayList<String>();
        if (nowDate == null) {
            nowDate = new Date();
        }
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN_YYYYMMDD);
        for (int i = 0; i < num; i++) {
            dayList.add(df.format(new Date(nowDate.getTime() - i * 24 * 60 * 60 * 1000)));
        }
        return dayList;
    }

    /**
     * @Description: Stirng转日期类型
     * @param oldString
     * @param strFromat
     * @return Date
     * @throws @author LiLW
     * @date 2016年3月29日 上午9:18:30
     * @version V1.0
     */
    public static Date stringToDate(String oldString, String strFromat) {
        try {
            if (null == strFromat) {
                strFromat = DATE_PATTERN_D;
            }
            SimpleDateFormat bartDateFormat = new SimpleDateFormat(strFromat);
            ParsePosition parseposition = new ParsePosition(0);
            Date newDate = null;
            if ((null != oldString) && (!oldString.equals(""))) {
                newDate = bartDateFormat.parse(oldString, parseposition);
            }
            return newDate;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Description: 日期转String
     * @param oldDate
     * @param strFromat
     * @return String
     * @throws @author LiLW
     * @date 2016年3月29日 上午9:25:37
     * @version V1.0
     */
    public static String dateToString(Date oldDate, String strFromat) {
        try {
            if (null == strFromat) {
                strFromat = DATE_PATTERN_D;
            }
            SimpleDateFormat bartDateFormat = new SimpleDateFormat(strFromat);
            String newStringDate = null;
            if ((null != oldDate) && (!oldDate.equals(""))) {
                newStringDate = bartDateFormat.format(oldDate);
            }
            return newStringDate;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getToDate(String day) {
        String time = null;
        if (day != null && day.length() == 14) {
            time = day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6, 8) + " "
                   + day.substring(8, 10) + ":" + day.substring(10, 12) + ":" + day.substring(12, 14);
        }
        return time;
    }

    /**
     * 获取intervalMinute分钟时间字符串
     * 
     * @param time
     * @param intervalMinute
     * @return
     * @author lxf
     * @date 2016年6月6日 上午11:42:36
     */
    public static String getIntervalTime(String time, int intervalMinute) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, intervalMinute);
            result = sdf.format(calendar.getTime());
        } catch (Throwable e) {
            // Log.error("getIntervalTime异常!!!",e);
        }
        return result;
    }

    /**
     * @Description: 获取第i个周日的日期，如果i=0，则获取本周周日值 i=-1时获取上周周日值，以此类推（周日为每周第一天）
     * @param i
     * @return Date
     * @throws @author LiLW
     * @date 2016年12月10日 上午10:12:12
     * @version V1.0
     */
    /**
     * @Description: 获取第i个周日的日期，如果i=0，则获取本周周日值 i=-1时获取上周周日值和本日在年度周次，以此类推（周日为每周第一天）
     * @param i
     * @return Map
     * @throws @author LiLW
     * @date 2016年12月10日 下午1:23:43
     * @version V1.0
     */
    public static Map getLastWeekSunday(int i) {
        Map map = new HashMap();
        Calendar date = Calendar.getInstance(Locale.CHINA);
        date.setFirstDayOfWeek(Calendar.MONDAY);
        date.add(Calendar.WEEK_OF_MONTH, -1);
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        date.add(Calendar.DATE, i * 7);
        date.getWeeksInWeekYear();
        map.put("num", date.get(Calendar.WEEK_OF_YEAR));
        map.put("dt", dateToString(date.getTime(), null));
        return map;
    }

    public static String getSpecifiedtDate(String specifiedDay, String pattern, int step) {
        String dayStr = "";
        Calendar can = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat(pattern).parse(specifiedDay);
            can.setTime(date);
            int day = can.get(Calendar.DATE);
            can.set(Calendar.DATE, day + step);
            dayStr = new SimpleDateFormat(pattern).format(can.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dayStr;
    }

    public static Long dateDiffSeconds(String startTime, String endTime) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        long nm = 1000;// 一秒钟的毫秒数
        long diff;
        long result = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            result = diff / nm;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getDataAddSecond(String specifiedDay, String pattern, int step) {
        String dayStr = "";
        Calendar can = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat(pattern).parse(specifiedDay);
            can.setTime(date);
            can.add(Calendar.SECOND, step);
            dayStr = new SimpleDateFormat(pattern).format(can.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayStr;
    }

    /**
     * 加5分钟
     * 
     * @param min
     * @return
     * @throws ParseException
     */
    public static String getAddTime(String day) {
        String time = "";
        if (day != null && day.length() == 14) {
            time = day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6, 8) + " "
                   + day.substring(8, 10) + ":" + day.substring(10, 12) + ":" + day.substring(12, 14);
        }
        SimpleDateFormat sd = new SimpleDateFormat(DATE_PATTERN_S);
        SimpleDateFormat hms = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(sd.parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ca.add(Calendar.MINUTE, -5);
        return hms.format(ca.getTime());
    }

    /**
     * 当前时间减去分钟数得到时间
     * 
     * @param minute 分钟
     * @return
     */
    // public static Map<String,String> getAddMinute(){
    // Map<String,String> mapDay = new HashMap<String, String>();
    //
    // SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    // String minute = getPropertiesByKey("recentTime");
    // Calendar time = Calendar.getInstance();
    // Date lastDate = time.getTime();
    // if(minute!=null && !minute.equals("")){
    // time.add(Calendar.MINUTE, -Integer.parseInt(minute));
    // }
    // Date endDate = time.getTime();
    // mapDay.put("lastDate", sf.format(endDate));
    // mapDay.put("endDate", sf.format(lastDate));
    // return mapDay;
    // }

    /**
     * 全图减去30分钟颜色区分
     * 
     * @param minute 分钟
     * @return
     */
    public static String getAddMinuteColor() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        // String minute = getPropertiesByKey("recentTime");
        Calendar time = Calendar.getInstance();
        // Date lastDate = time.getTime();
        // if(minute!=null && !minute.equals("")){
        time.add(Calendar.MINUTE, -30);
        // }
        Date date = time.getTime();
        return sf.format(date);
    }

    /**
     * 对时间格式化为：yyyy-MM-dd HH:mm:ss
     * 
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        if (!"".equals(str) && str != null) {
            String dateStr = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);// 日期
            String timeStr = str.substring(8, 10) + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);// 时间
            str = dateStr + " " + timeStr;
        }
        return str;
    }

    public static Long getTimeByDateStr(String dateStr) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        try {
            return sd.parse(dateStr).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateStrByTime(long time) {
        SimpleDateFormat sd = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        return sd.format(new Date(time));
    }

    /**
     * 减去30分钟
     * 
     * @param minute 分钟
     * @return
     */
    public static String getMinus30(String dateTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf1 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar time = Calendar.getInstance();
        try {
            if (dateTime != null && !"".equals(dateTime)) {
                time.setTime(sf.parse(dateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.add(Calendar.MINUTE, -30);
        Date date = time.getTime();
        return sf1.format(date);
    }

    /**
     * 加30分钟
     * 
     * @param minute 分钟
     * @return
     */
    public static String getAdd30(String dateTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf1 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar time = Calendar.getInstance();
        try {
            if (dateTime != null && !"".equals(dateTime)) {
                time.setTime(sf.parse(dateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.add(Calendar.MINUTE, 30);
        Date date = time.getTime();
        return sf1.format(date);
    }

    /**
     * 减去10分钟
     * 
     * @param minute 分钟
     * @return
     */
    public static String getMinus10(String dateTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf1 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar time = Calendar.getInstance();
        try {
            if (dateTime != null && !"".equals(dateTime)) {
                time.setTime(sf.parse(dateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.add(Calendar.MINUTE, -10);
        Date date = time.getTime();
        return sf1.format(date);
    }

    /**
     * 加10分钟
     * 
     * @param minute 分钟
     * @return
     */
    public static String getAdd10(String dateTime) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf1 = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar time = Calendar.getInstance();
        try {
            if (dateTime != null && !"".equals(dateTime)) {
                time.setTime(sf.parse(dateTime));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time.add(Calendar.MINUTE, 10);
        Date date = time.getTime();
        return sf1.format(date);
    }

    /**
     * 对数据库中取出的故障时间格式化为：yyyy-MM-dd HH:mm:ss
     * 
     * @param str
     * @return
     */
    public static String formatDate2(String str) {
        if (!" ".equals(str) && str != null && str.length() == 14) {
            String dateStr = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);// 日期
            String timeStr = str.substring(8, 10) + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);// 时间
            str = dateStr + " " + timeStr;
        }
        return str;
    }

    /**
     * 取出min~max之间所有的月份
     * 
     * @param min
     * @param max
     * @return
     */
    public static Map<String, String> getMonth(String min, String max) {
        List<String> dates = getDatesByTime(min, max);
        Map<String, String> map = new HashMap<>();
        for (String date : dates) {
            String month = date.substring(0, 6);
            map.put(month, month);
        }
        return map;
    }

    public static Map<String, String[]> getMonthDate(String startDate, String endDate) {
        Map<String, String[]> mapData = new HashMap<String, String[]>();
        mapData.put(startDate.substring(0, 6), new String[] { startDate, endDate });
        Date d1 = convert2Date(startDate, DATE_PATTERN_YYYYMMDDHHMMSS);
        Date d2 = convert2Date(endDate, DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar c = Calendar.getInstance();
        c.setTime(d1);
        // before() 没有等于
        while (c.getTime().before(d2)) {
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            String key = year + "" + (month < 10 ? "0" + month : month);
            String[] times = mapData.get(key);
            if (times == null) {
                times = new String[2];
                mapData.put(key, times);
                times[0] = convert2Str(c.getTime(), DATE_PATTERN_YYYYMMDDHHMMSS);
            }
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            times[1] = convert2Str(c.getTime(), DATE_PATTERN_YYYYMMDDHHMMSS);
            c.add(Calendar.DAY_OF_YEAR, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            if (c.getTime().getTime() > d2.getTime()) {
                times[1] = convert2Str(d2, DATE_PATTERN_YYYYMMDDHHMMSS);
            }
        }
        return mapData;

    }

    /**
     * 取得当前时间并转换成 yyyyMMddHHmmss
     * 
     * @param day 传入增加或者减少的天数
     * @return
     */
    public static String getNumFormatByDate(int day) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN_YYYYMMDDHHMMSS);
        Calendar time = Calendar.getInstance();
        time.add(Calendar.DATE, day);
        Date date = time.getTime();

        return sdf.format(date);
    }

}
