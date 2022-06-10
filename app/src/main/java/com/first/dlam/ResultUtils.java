package com.first.dlam;

import com.alibaba.fastjson.JSON;
import com.first.dlam.bean.AllBean15;
import com.first.dlam.bean.AllBean30;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.bean.AllBean60;
import com.first.dlam.bean.AllBeanDay;
import com.first.dlam.bean.AllBeanMonth;
import com.first.dlam.bean.AllBeanWeek;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ResultUtils {
    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static final DecimalFormat decimalFormatNew = new DecimalFormat("0.000");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

    public static String D5(AllBean5 bean5,int type, boolean ifCheck5) {
        if (bean5.getM5() == null || bean5.getM5().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean5.getM5().size() - 1;
        } else {
            max = bean5.getM5().size();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append(bean5.getM5().get(i).get(type)).append("#");
        }
        return (builder.toString().substring(0, builder.toString().length() - 1));

    }

    public static String newD5(AllBean5 bean5,int type) {
        if (bean5.getM5() == null || bean5.getM5().size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int max = bean5.getM5().size();
        for (int i = max-70; i < max; i++) {
            builder.append(bean5.getM5().get(i).get(type)).append("#");
        }

        return (builder.toString().substring(0, builder.toString().length() - 1));
    }


    public static String D15(AllBean15 bean15,int type, boolean ifCheck5) {
        if (bean15.getM15() == null || bean15.getM15().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean15.getM15().size() - 1;
        } else {
            max = bean15.getM15().size();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append(bean15.getM15().get(i).get(type)).append("#");
        }
        return (builder.toString().substring(0, builder.toString().length() - 1));

    }

    public static String newD15(AllBean15 bean15,int type) {
        if (bean15.getM15() == null || bean15.getM15().size() == 0) {
            return "";
        }
        int max = bean15.getM15().size();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append(bean15.getM15().get(i).get(type)).append("#");
        }

        return (builder.toString().substring(0, builder.toString().length() - 1));
    }


    public static String D30(AllBean30 bean15,int type, boolean ifCheck5) {
        if (bean15.getM30() == null || bean15.getM30().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean15.getM30().size() - 1;
        } else {
            max = bean15.getM30().size();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append(bean15.getM30().get(i).get(type)).append("#");
        }
        return (builder.toString().substring(0, builder.toString().length() - 1));

    }
    public static String newD30(AllBean30 bean30,int type) {
        if (bean30.getM30() == null || bean30.getM30().size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int max = bean30.getM30().size();

        for (int i = 0; i < max; i++) {
            builder.append(bean30.getM30().get(i).get(type)).append("#");
        }

        return (builder.toString().substring(0, builder.toString().length() - 1));

    }

    public static String D60(AllBean60 bean15,int type, boolean ifCheck5) {
        if (bean15.getM60() == null || bean15.getM60().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean15.getM60().size() - 1;
        } else {
            max = bean15.getM60().size();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append(bean15.getM60().get(i).get(type)).append("#");
        }
        return (builder.toString().substring(0, builder.toString().length() - 1));

    }
    public static String newD60(AllBean60 bean60,int type) {
        if (bean60.getM60() == null || bean60.getM60().size() == 0) {
            return "";
        }
        int max = bean60.getM60().size();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < max; i++) {
            builder.append(bean60.getM60().get(i).get(type)).append("#");
        }

        return (builder.toString().substring(0, builder.toString().length() - 1));

    }


    public static String D120(AllBean60 bean120,int type, boolean ifCheck5) {
        if (bean120.getM60() == null || bean120.getM60().size() == 0) {
            return "";
        }
        String str;
        String code = "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bean120.getM60().size(); i++) {
            str = bean120.getM60().get(i).get(0).substring(8);
            if (str.equals("1130") || str.equals("1500")) {
                builder.append(bean120.getM60().get(i).get(type)).append("#");
                code = bean120.getM60().get(i).get(0);
            }
            if (i == bean120.getM60().size() - 1) {
                if (!code.equals(bean120.getM60().get(i).get(0))) {
                    builder.append(bean120.getM60().get(i).get(type)).append("#");
                }
            }
        }
        if(ifCheck5) {
            return (builder.toString().substring(0, builder.toString().length() - 1));
        }else{
            return builder.toString().substring(0, builder.toString().length() - 1 - bean120.getM60().get(0).get(type).length() - 1);
        }
    }

    public static String newD120(AllBean60 bean120,int type) {
        if (bean120.getM60() == null || bean120.getM60().size() == 0) {
            return "";
        }
        String str;
        String code = "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bean120.getM60().size(); i++) {
            str = bean120.getM60().get(i).get(0).substring(8);
            if (str.equals("1130") || str.equals("1500")) {
                builder.append(bean120.getM60().get(i).get(type)).append("#");
                code = bean120.getM60().get(i).get(0);
            }
            if (i == bean120.getM60().size() - 1) {
                if (!code.equals(bean120.getM60().get(i).get(0))) {
                    builder.append(bean120.getM60().get(i).get(type)).append("#");
                }
            }
        }
        return (builder.toString().substring(0, builder.toString().length() - 1));
    }
    public static String D240(AllBeanDay beanDay,int type, boolean ifCheck5) {
        if (beanDay.getQfqday() == null || beanDay.getQfqday().size() == 0) {
            if (beanDay.getDay() != null && beanDay.getDay().size() != 0) {
                int max;
                if (!ifCheck5) {
                    max = beanDay.getDay().size() - 1;
                } else {
                    max = beanDay.getDay().size();
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < max; i++) {
                    builder.append(beanDay.getDay().get(i).get(type)).append("#");
                }

                return (builder.toString().substring(0, builder.toString().length() - 1));
            }
        } else {
            int max;
            if (!ifCheck5) {
                max = beanDay.getQfqday().size() - 1;
            } else {
                max = beanDay.getQfqday().size();
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < max; i++) {
                builder.append(beanDay.getQfqday().get(i).get(type)).append("#");
            }

            return (builder.toString().substring(0, builder.toString().length() - 1));

        }
        return "";
    }

    public static String newD240(AllBeanDay beanDay,int type) {
        if (beanDay.getQfqday() == null || beanDay.getQfqday().size() == 0) {
            if (beanDay.getDay() != null && beanDay.getDay().size() != 0) {
                int max = beanDay.getDay().size();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < max; i++) {
                    builder.append(beanDay.getDay().get(i).get(type)).append("#");
                }

                return (builder.toString().substring(0, builder.toString().length() - 1));
            }
        } else {
            int max = beanDay.getQfqday().size();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < max; i++) {
                builder.append(beanDay.getQfqday().get(i).get(type)).append("#");
            }

            return (builder.toString().substring(0, builder.toString().length() - 1));

        }
        return "";
    }

    public static String D360(AllBeanWeek beanWeek,int type, boolean ifCheck5) {
        if (beanWeek.getQfqweek() == null || beanWeek.getQfqweek().size() == 0) {
            if (beanWeek.getWeek() != null && beanWeek.getWeek().size() != 0) {
                int max;
                if (!ifCheck5) {
                    max = beanWeek.getWeek().size() - 1;
                } else {
                    max = beanWeek.getWeek().size();
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < max; i++) {
                    builder.append(beanWeek.getWeek().get(i).get(type)).append("#");
                }

                return (builder.toString().substring(0, builder.toString().length() - 1));
            }
        } else {
            int max;
            if (!ifCheck5) {
                max = beanWeek.getQfqweek().size() - 1;
            } else {
                max = beanWeek.getQfqweek().size();
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < max; i++) {
                builder.append(beanWeek.getQfqweek().get(i).get(type)).append("#");
            }

            return (builder.toString().substring(0, builder.toString().length() - 1));
        }
        return "";
    }

    public static String newD360(AllBeanWeek beanWeek,int type) {
        if (beanWeek.getQfqweek() == null || beanWeek.getQfqweek().size() == 0) {
            if (beanWeek.getWeek() != null && beanWeek.getWeek().size() != 0) {
                int max = beanWeek.getWeek().size();

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < max; i++) {
                    builder.append(beanWeek.getWeek().get(i).get(type)).append("#");
                }

                return (builder.toString().substring(0, builder.toString().length() - 1));
            }
        } else {
            int max = beanWeek.getQfqweek().size();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < max; i++) {
                builder.append(beanWeek.getQfqweek().get(i).get(type)).append("#");
            }

            return (builder.toString().substring(0, builder.toString().length() - 1));
        }
        return "";
    }

    public static String D480(AllBeanMonth beanMonth,int type, boolean ifCheck5) {
        if (beanMonth.getQfqmonth() == null || beanMonth.getQfqmonth().size() == 0) {
            if (beanMonth.getMonth() != null && beanMonth.getMonth().size() != 0) {
                int max;
                if (!ifCheck5) {
                    max = beanMonth.getMonth().size() - 1;
                } else {
                    max = beanMonth.getMonth().size();
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < max; i++) {
                    builder.append(beanMonth.getMonth().get(i).get(type)).append("#");
                }

                return (builder.toString().substring(0, builder.toString().length() - 1));
            }
        } else {
            int max;
            if (!ifCheck5) {
                max = beanMonth.getQfqmonth().size() - 1;
            } else {
                max = beanMonth.getQfqmonth().size();
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < max; i++) {
                builder.append(beanMonth.getQfqmonth().get(i).get(type)).append("#");
            }

            return (builder.toString().substring(0, builder.toString().length() - 1));
        }
        return "";
    }
    public static String newD480(AllBeanMonth beanMonth,int type) {
        if (beanMonth.getQfqmonth() == null || beanMonth.getQfqmonth().size() == 0) {
            if (beanMonth.getMonth() != null && beanMonth.getMonth().size() != 0) {
                int max = beanMonth.getMonth().size();

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < max; i++) {
                    builder.append(beanMonth.getMonth().get(i).get(type)).append("#");
                }

                return (builder.toString().substring(0, builder.toString().length() - 1));
            }
        } else {
            int max = beanMonth.getQfqmonth().size();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < max; i++) {
                builder.append(beanMonth.getQfqmonth().get(i).get(type)).append("#");
            }

            return (builder.toString().substring(0, builder.toString().length() - 1));
        }
        return "";
    }

    public static String getData(double price) {
        return decimalFormat.format(price);
    }
    public static String getData(String price) {
        return decimalFormat.format(Double.parseDouble(price));
    }

    public static String getDataNew(double price) {
        return decimalFormatNew.format(price);
    }

    public static String getString_15_number(String[] str_15, int number, int number6) { //根据输入框内容以及数字算出对应均线
        StringBuilder builder_15_3 = new StringBuilder();
        int start = number == number6 ? 0 : number6 - number;
        for (int i = start; i < str_15.length - (number - 1); i++) {
            double sum = 0;
            for (int j = i; j < number + i; j++) {
                sum += Double.parseDouble(str_15[j]);
            }
            builder_15_3.append(getDataNew(sum / (double) number)).append("->");
        }
        if(builder_15_3.toString().length()>2) {

            return builder_15_3.toString().substring(0, builder_15_3.toString().length() - 2);
        }else {
            return builder_15_3.toString();
        }
    }

    public static double getMin_number(String[] str_15, int number3) { //根据天数获取最小值
        double min = Double.parseDouble(str_15[str_15.length - 1]);
        for (int i = 1; i <= number3; i++) {
            if (Double.parseDouble(str_15[str_15.length - i]) < min) {
                min = Double.parseDouble(str_15[str_15.length - i]);
            }
        }
        return min;
    }


    public static double getMan_number(String[] str_15, int number3) {//根据天数获取最大值
        double max = Double.parseDouble(str_15[str_15.length - 1]);
        for (int i = 1; i <= number3; i++) {
            if (Double.parseDouble(str_15[str_15.length - i]) > max) {
                max = Double.parseDouble(str_15[str_15.length - i]);
            }
        }
        return max;
    }

    public static String getD5Time(AllBean5 bean5){
        if (bean5.getM5() == null || bean5.getM5().size() == 0) {
            return "";
        }
        int max = bean5.getM5().size();

        return bean5.getM5().get(max-1).get(0).substring(10);
    }

    public static String getD15Time(String result, boolean ifCheck5){
        AllBean15 bean15 = JSON.parseObject(result, AllBean15.class);
        if (bean15.getM15() == null || bean15.getM15().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean15.getM15().size() - 1;
        } else {
            max = bean15.getM15().size();
        }
        return bean15.getM15().get(max-1).get(0).substring(8,10)+":"+bean15.getM15().get(max-1).get(0).substring(10);
    }

    public static String getD30Time(String result, boolean ifCheck5){
        AllBean30 bean15 = JSON.parseObject(result, AllBean30.class);
        if (bean15.getM30() == null || bean15.getM30().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean15.getM30().size() - 1;
        } else {
            max = bean15.getM30().size();
        }
        return bean15.getM30().get(max-1).get(0).substring(8,10)+":"+bean15.getM30().get(max-1).get(0).substring(10);
    }

    public static String getD60Time(String result, boolean ifCheck5){
        AllBean60 bean15 = JSON.parseObject(result, AllBean60.class);
        if (bean15.getM60() == null || bean15.getM60().size() == 0) {
            return "";
        }
        int max;
        if (!ifCheck5) {
            max = bean15.getM60().size() - 1;
        } else {
            max = bean15.getM60().size();
        }
        return bean15.getM60().get(max-1).get(0).substring(8,10)+":"+bean15.getM60().get(max-1).get(0).substring(10);
    }


    public static double getMean(double a, double b, double number) {//根据天数获取平均值
        return (a + b) / number;
    }

    public static double getSum_number(String[] newString_15_3, int number) {//根据天数获取总和
        double sum = 0;
        for (int i = 1; i <= number; i++) {
            sum += Double.parseDouble(newString_15_3[newString_15_3.length - i]);
        }
        return sum;
    }
    public static String getMin(String... str) {
        double result = 20;
        for (String s : str) {
            if (Double.parseDouble(s) < result) {
                result = Double.parseDouble(s);
            }
        }
        return getData(result);
    }

    public static String getZhou1() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getYue1(){
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 1);

        cal.getTime();
        return dateFormater.format(cal.getTime());
    }

    public static String getTimeOneMonth() {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
        //获取一个月前的时间
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -2);
        Date m = c.getTime();
        return dateFormater.format(m)+"-01";

    }

    //第二个参数是设置到小数点后几位 0则不显示 1则显示0.1位
    public static String formatNumberWithUnit(String needFormatNumber,int point) {

        String nuit = "",formatNumStr = "";
        StringBuffer sb = new StringBuffer();
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal needNum = new BigDecimal(needFormatNumber);

        if (needNum.compareTo(b1) == -1) {
            sb.append(needNum.toString());
        }
        // 以万为单位处理
        else if ((needNum.compareTo(b1) == 0 || needNum.compareTo(b1) == 1)) {
            formatNumStr = needNum.divide(b1).setScale(point, BigDecimal.ROUND_HALF_UP).toString();
            nuit = "万手";
            sb.append(formatNumStr).append(nuit);
        }

        return sb.toString();
    }
}
