package com.kx.todaynews.utils;

import java.util.Date;

/**
 * @author Administrator
 */
public class TyDateUtils {
    /**
     * 一小时的秒数
     */
    private static final int HOUR_SECOND = 60 * 60;

    /**
     * 一分钟的秒数
     */
    private static final int MINUTE_SECOND = 60;


    /**
     * 与当前时间比较，得到多少年，多少月，多少天前,多少小时前，多小分钟前
     */
    public static String getFriendlytimeByDate(Date d) {
        long delta = (System.currentTimeMillis() - d.getTime()) / 1000;
        if (delta <= 0) return d.toLocaleString();
        if (delta / (60 * 60 * 24 * 365) > 0) return delta / (60 * 60 * 24 * 365) + "年前";
        if (delta / (60 * 60 * 24 * 30) > 0) return delta / (60 * 60 * 24 * 30) + "个月前";
        if (delta / (60 * 60 * 24 * 7) > 0) return delta / (60 * 60 * 24 * 7) + "周前";
        if (delta / (60 * 60 * 24) > 0) return delta / (60 * 60 * 24) + "天前";
        if (delta / (60 * 60) > 0) return delta / (60 * 60) + "小时前";
        if (delta / (60) > 0) return delta / (60) + "分钟前";
        return "刚刚";
    }

    public static String getFriendlytimeByTime(Long date) {
        long time = (Long.valueOf(String.format("%s",System.currentTimeMillis()).substring(0,10)) - date);
        // 1小时内  
        if (time > 0 && time < 60) {
           // return time + "秒前";
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 48) {
            return "昨天";
        } else if (time >= 3600 * 48 && time < 3600 * 72) {
            return "前天";
        }else {
            return "未知时间";
        }
//                else if (time >= 3600 * 72) {  
//            return dateToString(dateStr, DATE_FORMAT);  
//        }
    }
    /**
     * 根据秒数获取时间串
     * @param secondTime (eg: 100s)
     * @return (eg: 00:01:40)
     */
    public static String getTimeStrBySecond(int secondTime) {
        if (secondTime <= 0) {
            return "00:00";
        }
        StringBuilder sb = new StringBuilder();
        int hours = secondTime / HOUR_SECOND;
        long minute = secondTime / MINUTE_SECOND % MINUTE_SECOND;
        long second = secondTime % MINUTE_SECOND;
        if (hours > 10 ) {
            sb.append(String.format("%s:",hours));
        }else if (hours> 0){
            sb.append(String.format("0%s:",hours));
        }
        if (minute >= 10) {
            sb.append(String.format("%s:",minute));
        }else {
            sb.append(String.format("0%s:",minute));
        }
        if (second >= 10) {
            sb.append(String.format("%s",second));
        }else {
            sb.append(String.format("0%s",second));
        }
        return sb.toString();
    }
}
