package com.youdaoxsj.backstage.information.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateUtils<T> {

    /**
     * 字符串转换为日期
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String s) {
        String format = "yyyy-MM-dd";
        try {
            return new SimpleDateFormat(format).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串转换为日期时间
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date stringToDateTime(String s) {
        String format = "yyyy-MM-dd HH:mm:ss";
        try {
            return new SimpleDateFormat(format).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int compateDate2(String date1, String date2) {
        Date d1 = stringToDate(date1);
        Date d2 = stringToDate(date2);
        if (d1.getTime() > d2.getTime()) {
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
            return 2;
        } else {
            return 0;
        }
    }

    public List<T> sort(List<T> list, final String sortField, final Boolean Ascending) {

        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                //首字母转大写
                int retVal = 0;
                String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
                String methodStr = "get" + newStr;
                try {
                    //  获取方法明
                    Method method1 = ((T) o1).getClass().getMethod(methodStr, null);
                    Method method2 = ((T) o2).getClass().getMethod(methodStr, null);
                    //获取返回值类型  Type t = method.getAnnotatedReturnType().getType();
                    //返回值为数字和字符串使用不同的比较方法
                    if (isStr2Num(method2.invoke(((T) o1), null).toString())) {//判断值能否转数字
                        retVal = Integer.parseInt(method2.invoke(((T) o2), null).toString()) - Integer.parseInt(method1.invoke(((T) o1), null).toString()); // 倒序
                    }  else {
                        retVal = method2.invoke(((T) o2), null).toString().compareTo(method1.invoke(((T) o1), null).toString()); // 倒序
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (Ascending) {
                    return 0 - retVal;
                } else {
                    return retVal;
                }
            }
        });
        return list;
    }

    /**
     * 查看一个字符串是否可以转换为数字
     *
     * @param str 字符串
     * @return true 可以; false 不可以
     */
    public boolean isStr2Num(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //判断是否是时间格式
//    public boolean isStr2Date(String str) {
//
//}
}
