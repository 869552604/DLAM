package com.first.dlam;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;
    private SharedPreferences daySharedPreference;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat SSDateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String CODE1 = "CODE1";
    private static final String CODE2 = "CODE2";


    private static final String DAY_MAX = "DAY_MAX";
    private static final String DAY_MIN = "DAY_MIN";
    private static final String DAY_HUI_DIAO_MIN = "DAY_HUI_DIAO_MIN";//最高点回调时最低点。
    private static final String DAY_HUI_DIAO_MAX = "DAY_HUI_DIAO_MAX";//最低点回调时最高点。
    private static final String DAY_MAX_TIME_NUMBER = "DAY_MAX_TIME_NUMBER";//最高抛压时触发次数
    private static final String DAY_MIN_TIME = "DAY_MIN_TIME";//最低抛压时所对应的时间
    private static final String DAY_MIN_TIME_NUMBER = "DAY_MIN_TIME_NUMBER";//最低抛压时触发次数
    private static final String DAY_MAX_TIME = "DAY_MAX_TIME";//最高抛压时所对应的时间


    private static final String SHOW_PAN = "SHOW_PAN";//收盘价D
    private static final String JUN_JIA = "JUN_JIA";//均价
    private static final String ZONG_SHOU = "ZONG_SHOU";//总手
    private static final String QIANG_CHOU = "QIANG_CHOU";//抢筹
    private static final String GE_ROU = "GE_ROU";//割肉

    private SharedPreferences notesSP;
    private static final String NOTES = "NOTES";//笔记
    private static final String TIMES = "TIMES";//时间

    private static final String HSAHMAP="HSAHMAP"; //hashMap


    public SharedPreferencesUtil(Context context){
        sharedPreferences = context.getSharedPreferences(ConfigUtils.SP_RX, Context.MODE_PRIVATE);
        daySharedPreference = context.getSharedPreferences(dateformat.format(System.currentTimeMillis())+getCode_name(), Context.MODE_PRIVATE);
        notesSP=context.getSharedPreferences(ConfigUtils.SP_RX+getCode_name(),Context.MODE_PRIVATE);
    }
    public SharedPreferencesUtil(Context context, String code) {
        sharedPreferences = context.getSharedPreferences(ConfigUtils.SP_RX, Context.MODE_PRIVATE);
        daySharedPreference = context.getSharedPreferences(code, Context.MODE_PRIVATE);
        notesSP=context.getSharedPreferences(ConfigUtils.SP_RX+code.substring(10,18),Context.MODE_PRIVATE);
    }




    public void save_notes(String str) {
        notesSP.edit().putString(NOTES, str).apply();
    }

    public String getNotes() {
        return notesSP.getString(NOTES, "");
    }

    public void save_times(String str) {
        notesSP.edit().putString(TIMES, "记录时间: "+str).apply();
    }

    public String getTimes() {
        return notesSP.getString(TIMES, "暂无记录");
    }
    public void save_code_name(String token) {
        sharedPreferences.edit().putString(ConfigUtils.SP_CODE, token).apply();
    }

    public String getCode_name() {
        return sharedPreferences.getString(ConfigUtils.SP_CODE, "sz000683");
    }

    public void save_code1(String token) {
        sharedPreferences.edit().putString(CODE1, token).apply();
    }

    public String getCode1() {
        return sharedPreferences.getString(CODE1, "sz002239");
    }

    public void save_code2(String token) {
        sharedPreferences.edit().putString(CODE2, token).apply();
    }

    public String getCode2() {
        return sharedPreferences.getString(CODE2, "sh600477");
    }

    public void save_et_3(String token) {
        sharedPreferences.edit().putString(ConfigUtils.string3, token).apply();
    }

    public String getET_3() {
        return sharedPreferences.getString(ConfigUtils.string3, "3");
    }

    public void save_et_4(String token) {
        sharedPreferences.edit().putString(ConfigUtils.string4, token).apply();
    }

    public String getET_4() {
        return sharedPreferences.getString(ConfigUtils.string4, "4");
    }

    public void save_et_6(String token) {
        sharedPreferences.edit().putString(ConfigUtils.string6, token).apply();
    }

    public String getET_6() {
        return sharedPreferences.getString(ConfigUtils.string6, "6");
    }

    public void saveDayMax(int number) {
        daySharedPreference.edit().putInt(DAY_MAX, number).apply();
    }

    public int getDayMax() {
        return daySharedPreference.getInt(DAY_MAX, -100000);
    }

    public void saveDayMIN(int number) {
        daySharedPreference.edit().putInt(DAY_MIN, number).apply();
    }

    public int getDayMIN() {
        return daySharedPreference.getInt(DAY_MIN, 100000);
    }

    public void saveDAY_HUI_DIAO_MIN(int number){
        daySharedPreference.edit().putInt(DAY_HUI_DIAO_MIN,number).apply();
    }
    public int getDAY_HUI_DIAO_MIN(){
        return daySharedPreference.getInt(DAY_HUI_DIAO_MIN,100000);
    }

    public void saveDAY_HUI_DIAO_MAX(int number){
        daySharedPreference.edit().putInt(DAY_HUI_DIAO_MAX,number).apply();
    }
    public int getDAY_HUI_DIAO_MAX() {
        return daySharedPreference.getInt(DAY_HUI_DIAO_MAX, -100000);
    }

    public void saveDAY_MAX_TIME_NUMBER(int number){
        daySharedPreference.edit().putInt(DAY_MAX_TIME_NUMBER,number).apply();
    }
    public int getDAY_MAX_TIME_NUMBER(){
        return daySharedPreference.getInt(DAY_MAX_TIME_NUMBER,0);
    }

    public void saveDAY_MIN_TIME_NUMBER(int number){
        daySharedPreference.edit().putInt(DAY_MIN_TIME_NUMBER,number).apply();
    }

    public int getDAY_MIN_TIME_NUMBER(){
        return daySharedPreference.getInt(DAY_MIN_TIME_NUMBER,0);
    }

    public void saveDAY_MIN_TIME(String number) {
        daySharedPreference.edit().putString(DAY_MIN_TIME, number).apply();
    }

    public String getDAY_MIN_TIME() {
        return daySharedPreference.getString(DAY_MIN_TIME, SSDateFormat.format(System.currentTimeMillis()));
    }

    public void saveDAY_MAX_TIME(String number) {
        daySharedPreference.edit().putString(DAY_MAX_TIME, number).apply();
    }

    public String getDAY_MAX_TIME() {
        return daySharedPreference.getString(DAY_MAX_TIME, SSDateFormat.format(System.currentTimeMillis()));//System.currentTimeMillis()
    }

    public void saveSHOW_PAN(String number){
        daySharedPreference.edit().putString(SHOW_PAN,number).apply();
    }
    public String getSHOW_PAN(){
        return daySharedPreference.getString(SHOW_PAN,"0");
    }

    public void saveJUN_JIA(String number){
        daySharedPreference.edit().putString(JUN_JIA,number).apply();
    }
    public String getJUN_JIA(){
        return daySharedPreference.getString(JUN_JIA,"0");
    }

    public void saveZONG_SHOU(String number){
        daySharedPreference.edit().putString(ZONG_SHOU,number).apply();
    }
    public String getZONG_SHOU(){
        return daySharedPreference.getString(ZONG_SHOU,"0");
    }

    public void saveQIANG_CHOU(String number){
        daySharedPreference.edit().putString(QIANG_CHOU,number).apply();
    }
    public String getQIANG_CHOU(){
        return daySharedPreference.getString(QIANG_CHOU,"0");
    }

    public void saveGE_ROU(String number){
        daySharedPreference.edit().putString(GE_ROU,number).apply();
    }
    public String getGE_ROU(){
        return daySharedPreference.getString(GE_ROU,"0");
    }

    //将 HashMap 用sp存储起来
    private void saveHashMap(HashMap<String,Integer> map){

        Gson gson = new Gson();
        String json = gson.toJson(map);

        daySharedPreference.edit().putString(HSAHMAP,json).apply();



    }


    //将json格式的字符串从sp中取出来转化为hashMap
    public HashMap<String,Integer> getHashMap(){
        String json =daySharedPreference.getString(HSAHMAP,"");
        HashMap<String, Integer> map;
        if(json!="") {
            Type type = new TypeToken<LinkedHashMap<String, Integer>>() {
            }.getType();
            Gson gson = new Gson();
            map = gson.fromJson(json, type);

        }else{
            map = new LinkedHashMap<>();

        }
        return map;
    }

    //改变其中一个值
    public void changeValue(String name,int number){
        HashMap<String, Integer> hashMap = getHashMap();
        hashMap.put(name,(hashMap.containsKey(name)?hashMap.get(name): 0)+number);
        saveHashMap(hashMap);
    };

}
