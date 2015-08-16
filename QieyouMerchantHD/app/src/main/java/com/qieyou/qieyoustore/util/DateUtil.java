package com.qieyou.qieyoustore.util;

import android.text.format.DateFormat;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by zhoufeng'an on 2015/8/14.
 */
public class DateUtil {
    public static String formatDateTime(String mseconds){
        Date date = new Date();
        date.setTime(Long.valueOf(mseconds));
        return formatDateTime(date);
    }
    public static String formatDateTime(Date date) {
        String text;
        long dateTime = date.getTime();
        if (isSameDay(dateTime)) {
            Calendar calendar = GregorianCalendar.getInstance();
            if (inOneMinute(dateTime, calendar.getTimeInMillis())) {
                return "刚刚";
            } else if (inOneHour(dateTime, calendar.getTimeInMillis())) {
                return String.format("%d分钟之前", Math.abs(dateTime - calendar.getTimeInMillis())
                        / 60000);
            } else {
                calendar.setTime(date);
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                if (hourOfDay > 17) {
                    text = "晚上 hh:mm";
                } else if (hourOfDay >= 0 && hourOfDay <= 6) {
                    text = "凌晨 hh:mm";
                } else if (hourOfDay > 11 && hourOfDay <= 17) {
                    text = "下午 hh:mm";
                } else {
                    text = "上午 hh:mm";
                }
            }
        } else if (isYesterday(dateTime)) {
            text = "昨天 HH:mm";
        } else if (isSameYear(dateTime)) {
            text = "M月d日 HH:mm";
        } else {
            text = "yyyy-M-d HH:mm";
        }

        // 注意，如果使用android.text.format.DateFormat这个工具类，在API 17之前它只支持adEhkMmszy
        return new SimpleDateFormat(text, Locale.CHINA).format(date);
    }

    private static boolean inOneMinute(long time1, long time2) {
        return Math.abs(time1 - time2) < 60000;
    }

    private static boolean inOneHour(long time1, long time2) {
        return Math.abs(time1 - time2) < 3600000;
    }

    private static boolean isSameDay(long time) {
        long startTime = floorDay(Calendar.getInstance()).getTimeInMillis();
        long endTime = ceilDay(Calendar.getInstance()).getTimeInMillis();
        return time > startTime && time < endTime;
    }

    private static boolean isYesterday(long time) {
        Calendar startCal;
        startCal = floorDay(Calendar.getInstance());
        startCal.add(Calendar.DAY_OF_MONTH, -1);
        long startTime = startCal.getTimeInMillis();

        Calendar endCal;
        endCal = ceilDay(Calendar.getInstance());
        endCal.add(Calendar.DAY_OF_MONTH, -1);
        long endTime = endCal.getTimeInMillis();
        return time > startTime && time < endTime;
    }

    private static boolean isSameYear(long time) {
        Calendar startCal;
        startCal = floorDay(Calendar.getInstance());
        startCal.set(Calendar.MONTH, Calendar.JANUARY);
        startCal.set(Calendar.DAY_OF_MONTH, 1);
        return time >= startCal.getTimeInMillis();
    }

    private static Calendar floorDay(Calendar startCal) {
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        return startCal;
    }

    private static Calendar ceilDay(Calendar endCal) {
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        return endCal;
    }

    public static final String EMPTY_STRING = "";

    public static Date getDate(String dataString) {
        return getDate(dataString, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDate(String dataStr, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = f.parse(dataStr);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }

    public static String getDateStr(String format){
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date();
        return sf.format(date);
    }

    public static String formatDate(long date, String format) {
        return DateFormat.format(format, date).toString();
    }

    public static String formatDate(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return sf.format(date);
    }


    public static long getSystemTime() {
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month;//
        int date = time.monthDay;
        int hour = time.hour; // 0-23
        int minute = time.minute;
        int second = time.second;
        String sMonth, sDate, sHour, sMinute, sSecond;
        if (month < 10) {
            sMonth = "0" + month;
        } else {
            sMonth = "" + month;
        }
        if (date < 10) {
            sDate = "0" + date;
        } else {
            sDate = "" + date;
        }
        if (hour < 10) {
            sHour = "0" + hour;
        } else {
            sHour = "" + hour;
        }
        if (minute < 10) {
            sMinute = "0" + minute;
        } else {
            sMinute = "" + minute;
        }
        if (second < 10) {
            sSecond = "0" + second;
        } else {
            sSecond = "" + second;
        }
        long currentTime = Long.parseLong(year + sMonth + sDate + sHour
                + sMinute + sSecond);
        return currentTime;
    }

    public static String getSystemTime(String pattern) {
        if (pattern == null || pattern.trim().length() < 5) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date curDate = new Date(System.currentTimeMillis());//
        return formatter.format(curDate);
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(day > 365){
            day = 365;
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }

    public static int getAge(Date birthDay){
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;// 注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH)+1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

		/*if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				} else {
					// do nothing
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		} else {
			// monthNow<monthBirth
			// donothing
		}*/

        if (monthNow == monthBirth) {

            if (dayOfMonthNow < dayOfMonthBirth) {
                age--;
            }

        } else if (monthNow < monthBirth) {
            age--;
        }

        return age;
    }
}
