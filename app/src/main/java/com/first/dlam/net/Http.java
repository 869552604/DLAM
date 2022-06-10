package com.first.dlam.net;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;


/**
 * @Author: Parawayne
 * @CreateDate: 2020/1/15 17:31
 * @Description: 描述:
 */
public class Http {

    public static void getBeanByMM(String Code,  Callback callback) {

        Map<String, Object> map = new HashMap<>();
        map.put("param", Code);
        try {
            OkHttp.get("https://ifzq.gtimg.cn/appstock/app/kline/mkline", map, callback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OkHttpException e) {
            e.printStackTrace();
        }
    }


    public static void getBeanByDay(String Code,  Callback callback) throws ClassCastException{ // sz002239,day,,,40,qfq

        Map<String, Object> map = new HashMap<>();
        map.put("param", Code);
        try {
            OkHttp.get("https://ifzq.gtimg.cn/appstock/app/fqkline/get", map, callback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OkHttpException e) {
            e.printStackTrace();
        }
    }

    public static void getBeanByWeek(String Code,  Callback callback) { // sz002239,week,,,30,qfq

        Map<String, Object> map = new HashMap<>();
        map.put("param", Code);
        try {
            OkHttp.get("https://ifzq.gtimg.cn/appstock/app/fqkline/get", map, callback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OkHttpException e) {
            e.printStackTrace();
        }
    }

    public static void getBeanByMonth(String Code,  Callback callback){
        Map<String, Object> map = new HashMap<>();
        map.put("param", Code);
        try {
            OkHttp.get("https://ifzq.gtimg.cn/appstock/app/fqkline/get", map, callback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OkHttpException e) {
            e.printStackTrace();
        }
    }

    public static void getDetails(String Code,  Callback callback){//获取最新行情:买方卖方、内盘外盘
        Map<String, Object> map = new HashMap<>();
        map.put("q", Code);
        try {
            OkHttp.get("http://qt.gtimg.cn/", map, callback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OkHttpException e) {
            e.printStackTrace();
        }
    }
}
