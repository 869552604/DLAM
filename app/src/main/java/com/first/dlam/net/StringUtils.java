package com.first.dlam.net;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class StringUtils {

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_FULLTIME = "HH:mm";
    public static final String FORMAT_YEAR = "yyyy";
    public static final String FORMAT_MONTH = "MM";
    public static final String FORMAT_DAY = "dd";
    public static final String FORMAT_HOUR = "HH";
    public static final String FORMAT_MINUTE = "mm";
    public static final String FORMAT_SECOND = "ss";

    /**
     * 按格式格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 获取当前日期（含时间）
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime() {
        return getDateTime(new Date());
    }

    public static String getDateTime(Date date) {
        return formatDate(new Date(), FORMAT_DATETIME);
    }

    /**
     * 获取当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getDate() {
        return getDate(new Date());
    }

    public static String getDate(Date date) {
        return formatDate(new Date(), FORMAT_DATE);
    }

    /**
     * 获取当前时间
     *
     * @return HH:mm
     */
    public static String getTime() {
        return getTime(new Date());
    }

    public static String getTime(Date date) {
        return formatDate(date, FORMAT_TIME);
    }

    /**
     * 获取当前完整时间
     *
     * @return HH:mm
     */
    public static String getFullTime() {
        return getFullTime(new Date());
    }

    public static String getFullTime(Date date) {
        return formatDate(date, FORMAT_FULLTIME);
    }

    /**
     * @return 返回当前年String格式：yyyy
     */
    public static String getYear() {
        return getYear(new Date());
    }

    public static String getYear(Date date) {
        return formatDate(date, FORMAT_YEAR);
    }

    /**
     * 获得当前年份所在的月份
     *
     * @return MM
     */
    public static String getMonth() {
        return getMonth(new Date());
    }

    public static String getMonth(Date date) {
        return formatDate(date, FORMAT_MONTH);
    }

    /**
     * 获得当前小时
     *
     * @return
     */
    public static String getDay() {
        return getDay(new Date());
    }

    public static String getDay(Date date) {
        return formatDate(date, FORMAT_DAY);
    }

    /**
     * 获得当前分钟
     *
     * @return
     */
    public static String getMinute() {
        return getMinute(new Date());
    }

    public static String getMinute(Date date) {
        return formatDate(date, FORMAT_MINUTE);
    }

    /**
     * 获得当前秒
     *
     * @return
     */
    public static String getSecond() {
        return getSecond(new Date());
    }

    public static String getSecond(Date date) {
        return formatDate(date, FORMAT_SECOND);
    }

    /**
     * 判断是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if ("".equals(str) || str == null) {
            return false;
        }
        for (int i = str.length(); --i >= 0;) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    /**
     * 监督系统密码加密
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        String sRslt = str;
        // ref to: hiibase.HTP_encrypt.f_encrypt
        long lSum = 0;
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        int iLen = str.length();
        for (int iTmp = 1; iTmp <= iLen; iTmp++) {
            // char直接
            lSum = lSum + str.charAt(iTmp - 1) * 20 * iTmp;
        }
        sRslt = String.valueOf(lSum);

        return sRslt;
    }

    /**
     * 获取随机的uuid
     *
     * @return
     */
    public static String newUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 字符串空判断，来自于org.apache.commons.lang.StringUtils
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * 字符串非空判断，来自于org.apache.commons.lang.StringUtils
     */
    public static boolean isNotEmpty(String str) {
        return (str != null) && (str.length() > 0);
    }

    /**
     * 字符串空判断，来自于org.apache.commons.lang.StringUtils
     */
    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串非空判断，来自于org.apache.commons.lang.StringUtils
     */
    public static boolean isNotBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0))
            return false;
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i)) == false) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串布尔值判定
     * 支持1,Y,true，不区分大小写
     *
     * @param value
     * @return
     */
    public static boolean isTrue(String value) {
        if (value == null) {
            return false;
        }
        if (value.equalsIgnoreCase("1") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsText(Context context,String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    public static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            while (true) {
                String readLine = bufferedReader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
