package com.atguigu.xingqiji;

import com.atguigu.util.HraUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//输入给定日期 问经过13月6天后是星期几的问题
public class computexingqiji {
    public static int count = 0;

    public static void main(String[] args) {
        noUse(2015, 3, 1, 0, 13, 6);
        System.out.println("==================================");
        noUse2(2015, 3, 1, 0, 13, 6);
    }

    //2015年3月1日
    public static void noUse2(int y, int m, int d, int countY, int countM, int countD) {

        try {
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            //设置时间2015.3.1
            Date date1 = (new SimpleDateFormat("yyyy年MM月dd日").parse("2015年3月1日"));
            System.out.println("设置时间：" + date1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(date1);
            int zeroDays = 6;
            int cx = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
            int ds = 0;
            int cc = 0;
            if (countY == 0) {
                countY = -1;
            }
            if (countM == 0) {
                countM = -1;
            }
            //如果现在是3月1日 我们这里说的3个月后就是6月1日
            int months = (countY + 1) * 12 + countM + 1;
            while (cc <= months) {
                if (m > 12) {
                    m = m % 12;
                    y++;
                }
                switch (m) {
                    case 12:
                        ds += 31;
                    case 11:
                        ds += 30;
                    case 10:
                        ds += 31;
                    case 9:
                        ds += 30;
                    case 8:
                        ds += 31;
                    case 7:
                        ds += 31;
                    case 6:
                        ds += 30;
                    case 5:
                        ds += 31;
                    case 4:
                        ds += 30;
                    case 3:
                        ds += 31;
                    case 2:
                        if (y % 400 == 0 || (y % 4 == 0 && y % 100 != 0))
                            ds += 29;
                        else
                            ds += 28;
                    case 1:
                        ds += 31;
                }
                cc++;
                m++;
            }
            ds += (zeroDays + 1);
            ds -= d;
            //如现在是星期二 过了31天 31%7=3那么现在就该是星期五
            int day = (cx + ds) % 7;
            System.out.println("现在是" + weekDays[day]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void noUse(int y, int m, int d, int countY, int countM, int countD) {

        try {
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            //设置时间2015.3.1
            Date date1 = HraUtility.strToDate(String.format("%s/%s/%s", y, m, d));

            //计算13个月6天后是周几
            Date date2 = HraUtility.addDays(HraUtility.addMonths(HraUtility.addYears(date1, countY), countM), countD);


            int dayofWeek = HraUtility.getDayofWeek(date2);
            String x1 = "现在是星期" + castZhongwen(dayofWeek);
            System.out.println(x1);

            //距离现在（2015.3.1）秒数
            long time = (date2.getTime() - date1.getTime()) / 1000;
            System.out.println("秒数：" + time);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String castZhongwen(int dayofWeek) {
        switch (dayofWeek){
            case 1:return"一";
            case 2:return"二";
            case 3:return"三";
            case 4:return"四";
            case 5:return"五";
            case 6:return"六";
            case 7:return"七";
        }
        return "";
    }

}
