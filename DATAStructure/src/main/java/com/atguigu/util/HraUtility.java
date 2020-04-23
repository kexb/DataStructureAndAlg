package com.atguigu.util;

import com.alibaba.fastjson.JSON;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

public class HraUtility {

    public static String convertToTableOrColumnName(String str) {
        String tableName = "";
        char[] chs = str.toCharArray();
        for (char s : chs) {
            if (s >= 'A' && s <= 'Z') {
                tableName += "_";
            }
            tableName += String.valueOf(s);

        }
        String firstChar = tableName.substring(0, 1);
        if (firstChar.equals("_")) {
            tableName = tableName.substring(1, tableName.length());
        } else {
            tableName = tableName.substring(0, tableName.length());
        }
        return tableName;
    }

    public static String[] RegularPaterns = new String[]{"LK", "GEQ", "LEQ", "GT", "LT"};



    public static Object getListValueByIndexAndKey(List list, int index, String key) {
        Object obj = ((Map) list.get(index)).get(key.toLowerCase());
        if (obj == null) {
            obj = ((Map) list.get(index)).get(key.toUpperCase());
        }
        return obj;
    }

    public static Map<String, Object> Services = new HashMap<String, Object>();

    public static boolean IsNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    public static boolean IsNullOrEmpty(Object str) {
        if (str == null) {
            return true;
        }
        return IsNullOrEmpty(str.toString());
    }

    public static boolean IsNullOrEmpty(Long str) {
        return str == null || str == 0;
    }

    public static boolean IsNullOrEmpty(Integer str) {
        return str == null || str == 0;
    }

    public static boolean IsNullOrEmpty(Double str) {
        return str == null || str == 0;
    }

    public static String AddParam(Map<String, Object> parameters, String sql2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map.Entry<String, Object> item : parameters.entrySet()) {
            String nv = "";
            boolean isDate = false;
            Object value = item.getValue();
            String typeName = (value.getClass().getName().toLowerCase());
            switch (typeName) {
                case "java.lang.string":
                    nv = "'" + value + "'";
                    break;
                case "java.lang.integer":
                case "java.lang.long":
                case "java.lang.double":
                    nv = value.toString();
                    break;
                case "java.lang.boolean":
                    if (value.toString().equals("true")) {
                        nv = "1";
                    } else {
                        nv = "0";
                    }
                    break;
                case "java.util.date":
                    Date date = (Date) value;
                    if (date == null) {
                        sql2 = sql2.replace("#{" + item.getKey() + "}", null);
                        break;
                    }
                    isDate = true;
                    String startTime = sdf.format(date).replace("/", "-");
                    Calendar calendar = new GregorianCalendar();
                    calendar.setTime(date);
                    calendar.add(calendar.DATE, 1);
                    String endTime = sdf.format(calendar.getTime()).replace("/", "-");
                    int idx = sql2.indexOf("#{" + item.getKey() + "}");
                    int nidx = idx - 1;
                    char[] chs = sql2.toCharArray();
                    List<String> before = new ArrayList<String>();
                    while (true) {
                        String ch = String.valueOf(chs[nidx--]);
                        if (!ch.equals(" ")) {
                            before.add(ch);
                        } else {
                            break;
                        }
                    }
                    StringBuilder b = new StringBuilder(before.toString());
                    String reverseStr = b.reverse().toString();
                    String beforeString = reverseStr
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", "")
                            .replace(" ", "");

                    String replaceKey = beforeString + "#{" + item.getKey() + "}";
                    String replaceValue = " " + beforeString.replace("=", "") + ">="
                            + "to_date('" + startTime + "','" + "yyyy-mm-dd')"
                            + " and " + beforeString.replace("=", "")
                            + "<" + "to_date('" + endTime + "','" + "yyyy-mm-dd')";
                    if (isDate) {
                        sql2 = sql2.replace(replaceKey, replaceValue);
                    }
                default:
                    break;
            }
            if (!isDate) {
                sql2 = sql2.replace("#{" + item.getKey() + "}", nv);
            }
        }

        return sql2;
    }

    /*
     * 字符串转为日期格式 pattern:yyyy-MM-dd
     * */
    public static Date DateTimeParse(String datestr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = sdf.parse(datestr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 返回java.sql.Date格式的
     *
     * @param strDate
     * @return
     */
    public static java.sql.Date strToDate(String strDate) {
        if(IsNullOrEmpty(strDate)){
            return null;
        }
        strDate = strDate.replaceAll("/", "-");
        String str = strDate;
        String pattern = "yyyy-MM-dd";
        if (!strDate.contains("-")) {
            str = strDate.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(d.getTime());
        return date;
    }

    public static java.sql.Date timestamp2javasqldate(java.sql.Timestamp t) {
        return new java.sql.Date(t.getTime());
    }



    public static String columnName2Property(String col) {
        String[] arr = col.split("_");
        String word = "";
        for (String s : arr) {
            word += s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return word;

    }



    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(java.sql.Date date) {
        Date ud = convert2utildate(date);
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern("yyyy");
        int year = Integer.parseInt(sformat.format(ud));
        return year;
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern("yyyy");
        int year = Integer.parseInt(sformat.format(date));
        return year;
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(java.sql.Date date) {
        Date ud = convert2utildate(date);
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern("MM");
        int month = Integer.parseInt(sformat.format(ud));
        return month;
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern("MM");
        int month = Integer.parseInt(sformat.format(date));
        return month;
    }


    /**
     * 获取几号
     *
     * @param date
     * @return
     */
    public static int getDay(java.sql.Date date) {
        Date ud = convert2utildate(date);
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern("dd");
        int day = Integer.parseInt(sformat.format(ud));
        return day;
    }

    public static int getDayofWeek(java.util.Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            return 7; //周日
        }
        weekday = weekday - 1;
        return weekday;
    }

    /**
     * 获取几号
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern("dd");
        int month = Integer.parseInt(sformat.format(date));
        return month;
    }

    /**
     * 日期转为字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String date2String(java.sql.Date date, String pattern) {
        SimpleDateFormat sformat = new SimpleDateFormat();
        if (HraUtility.IsNullOrEmpty(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        sformat.applyPattern(pattern);
        return sformat.format(date);
    }

    public static String date2String(Date date, String pattern) {
        if (HraUtility.IsNullOrEmpty(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern(pattern);
        String str = sformat.format(date);
        return str;
    }

    /**
     * java.sql.Date转为java.util.Date
     *
     * @param date
     * @return
     */
    public static Date convert2utildate(java.sql.Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        Date ud = calendar.getTime();
        Date nd = new Date(ud.getTime());
        return nd;
    }

    public static java.sql.Date convert2sqldate(Date date, String pattern) {
        if (HraUtility.IsNullOrEmpty(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sformat = new SimpleDateFormat();
        sformat.applyPattern(pattern);
        String str = sformat.format(date);
        java.sql.Date nd = strToDate(str);
        return nd;
    }

    /**
     * 增加年数
     *
     * @param date
     * @return
     */
    public static java.sql.Date addYears(java.util.Date date, int cx) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.YEAR, cx);

        Date ud = calendar.getTime();
        Date nd = new Date(ud.getTime());
        java.sql.Date sd = convert2sqldate(nd, null);
        return sd;
    }

    /**
     * 增加月份
     *
     * @param date
     * @return
     */
    public static java.sql.Date addMonths(java.sql.Date date, int cx) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, cx);

        Date ud = calendar.getTime();
        Date nd = new Date(ud.getTime());
        java.sql.Date sd = convert2sqldate(nd, null);
        return sd;
    }

    /**
     * 增加天数
     *
     * @param date
     * @return
     */
    public static java.sql.Date addDays(java.sql.Date date, int cx) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, cx);

        Date ud = calendar.getTime();
        Date nd = new Date(ud.getTime());
        java.sql.Date sd = convert2sqldate(nd, null);
        return sd;
    }

    public static TimeSpan Substract(java.sql.Date end_date, java.sql.Date begin_date) throws Exception {
        long timespan = 0;
        Date ubegin;
        Date uend;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (begin_date != null) {
            String begin = sdf.format(begin_date);
            ubegin = sdf.parse(begin);
        }
        if (end_date != null) {
            String end = sdf.format(end_date);
            uend = sdf.parse(end);
        }
        timespan = (end_date.getTime() - begin_date.getTime());//(24*60*60*1000);
        TimeSpan span = new TimeSpan();
        span.timespan = timespan;
        return span;
    }

    /**
     * 保留小数
     *
     * @param d
     * @param scale
     * @return
     */
    public static double round(double d, int scale) {
        BigDecimal b = new BigDecimal(d);
        double f = b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    public static double[] round(double[] d, int scale) {
        for (int i = 0; i < d.length; i++) {
            d[i] = round(d[i], scale);
        }
        return d;
    }

    public static Double[] round(Double[] d, int scale) {
        for (int i = 0; i < d.length; i++) {
            d[i] = round(d[i], scale);
        }
        return d;
    }

    /**
     * 默认保留四位小数
     *
     * @param d
     * @return
     */
    public static double round(double d) {
        BigDecimal b = new BigDecimal(d);
        double f = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f;
    }

    /**
     * 深度克隆 采用流序列化方式
     *
     * @param ori
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object clone(Object ori) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(ori);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return ois.readObject();
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone2(T ori) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(ori);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        return (T) ois.readObject();
    }

    /**
     * 反序列化
     *
     * @param obj
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T Deserializer(Object obj, Class<T> type) {
        return JSON.parseObject(JSON.toJSONString(obj), type);
    }

    /**
     * 反序列化
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T Deserializer(String str, Class<T> type) {
        return JSON.parseObject(str, type);
    }


    public static Double[] DoubleArrayInit(int length) {
        Double[] arr = new Double[length];
        for (int i = 0; i < length; i++) {
            arr[i] = 0.0;
        }
        return arr;
    }

    public static Integer[] IntegerArrayInit(int length) {
        Integer[] arr = new Integer[length];
        for (int i = 0; i < length; i++) {
            arr[i] = 0;
        }
        return arr;
    }

    public static Long[] LongArrayInit(int length) {
        Long[] arr = new Long[length];
        for (int i = 0; i < length; i++) {
            arr[i] = 0L;
        }
        return arr;
    }

    /**
     * 反转后键值对都是字符串类型 这里原本只是为转码做的，如不符合要求，请自己实现
     *
     * @param map
     * @return
     */
    public static HashMap<String, String> reverseMap(Map map) {
        HashMap<String, String> reverseMap = new HashMap<>();
        for (Object o : map.entrySet()) {
            reverseMap.put(((Map.Entry) o).getValue().toString(), ((Map.Entry) o).getKey() + "");

        }
        return reverseMap;
    }

    public static java.sql.Date strToDate(Object date) {
        if (date == null) {
            return null;
        }
        return strToDate(date.toString());
    }



    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void put(HashMap map, K key, V value) {
        map.put(key, value);
    }

    public static class TenorInterpolation {
        public String[] timeSpan;
        public Double[] timeData;
    }


    public static class FreqStruc {
        public FreqStruc(Double timeDouble, List<String> splits) {
            this.timeDouble = timeDouble;
            this.splits = splits;
        }

        public Double timeDouble;
        public List<String> splits;

    }

}

