package com.first.dlam.base.fragment;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.first.dlam.ConfigUtils;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.bean.AllBean15;
import com.first.dlam.bean.AllBean30;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.bean.AllBean60;
import com.first.dlam.bean.AllBeanDay;
import com.first.dlam.bean.AllBeanMonth;
import com.first.dlam.bean.AllBeanWeek;
import com.first.dlam.databinding.Fragment16Binding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;
import com.first.dlam.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

import static com.blankj.utilcode.util.ViewUtils.runOnUiThread;


public class Fragment1_6 extends BaseFragment implements View.OnClickListener {

    private Fragment16Binding binding;
    private String codeName;
    private int number3 = 3;
    private int number4 = 4;
    private int number6 = 6;
    private String string3 = "3";
    private String string4 = "4";
    private String string6 = "6";
    private static MainHandler handler;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Bitmap screenShot = null;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment1_6,container,false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        binding.editText3.setText(sharedPreferencesUtil.getET_3());
        binding.editText4.setText(sharedPreferencesUtil.getET_4());
        binding.editText6.setText(sharedPreferencesUtil.getET_6());
        binding.etCode.setText(sharedPreferencesUtil.getCode_name());
        binding.notes.setText(sharedPreferencesUtil.getNotes());
        binding.time.setText(sharedPreferencesUtil.getTimes());

        binding.getCodeDetail.setOnClickListener(this);
        binding.remember.setOnClickListener(this);
        binding.back.setOnClickListener(this);

        handler=new MainHandler(this);
    }

    public static class MainHandler extends Handler {
        WeakReference<Fragment1_6> fragment;

        private MainHandler(Fragment1_6 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            if (fragment.get() == null) {
                return;
            }
            if(msg.arg1==5000){
                Toast.makeText(fragment.get().getContext(), "请输入正确的code", Toast.LENGTH_SHORT).show();
            }
            if (msg.arg1 == 51) {
                fragment.get().binding.nomal5.setText(msg.obj.toString());
            }
            if(msg.arg1==52){
                fragment.get().binding.max5.setText(msg.obj.toString());
            }
            if(msg.arg1==53){
                fragment.get().binding.min5.setText(msg.obj.toString());
            }
            if (msg.arg1 == 151) {
                fragment.get().binding.nomal15.setText(msg.obj.toString());
            }
            if(msg.arg1==152){
                fragment.get().binding.max15.setText(msg.obj.toString());
            }
            if(msg.arg1==153){
                fragment.get().binding.min15.setText(msg.obj.toString());
            }
            if (msg.arg1 == 301) {
                fragment.get().binding.nomal30.setText(msg.obj.toString());
            }
            if(msg.arg1==302){
                fragment.get().binding.max30.setText(msg.obj.toString());
            }
            if(msg.arg1==303){
                fragment.get().binding.min30.setText(msg.obj.toString());
            }
            if (msg.arg1 == 601) {
                fragment.get().binding.nomal60.setText(msg.obj.toString());
            }
            if(msg.arg1==602){
                fragment.get().binding.max60.setText(msg.obj.toString());
            }
            if(msg.arg1==603){
                fragment.get().binding.min60.setText(msg.obj.toString());
            }
            if (msg.arg1 == 1201) {
                fragment.get().binding.nomal120.setText(msg.obj.toString());
            }
            if(msg.arg1==1202){
                fragment.get().binding.max120.setText(msg.obj.toString());
            }
            if(msg.arg1==1203){
                fragment.get().binding.min120.setText(msg.obj.toString());
            }
            if (msg.arg1 == 2401) {
                fragment.get().binding.nomal240.setText(msg.obj.toString());
            }
            if(msg.arg1==2402){
                fragment.get().binding.max240.setText(msg.obj.toString());
            }
            if(msg.arg1==2403){
                fragment.get().binding.min240.setText(msg.obj.toString());
            }
            if (msg.arg1 == 3601) {
                fragment.get().binding.nomal360.setText(msg.obj.toString());
            }
            if(msg.arg1==3602){
                fragment.get().binding.max360.setText(msg.obj.toString());
            }
            if(msg.arg1==3603){
                fragment.get().binding.min360.setText(msg.obj.toString());
            }
            if (msg.arg1 == 4801) {
                fragment.get().binding.nomal480.setText(msg.obj.toString());
            }
            if(msg.arg1==4802){
                fragment.get().binding.max480.setText(msg.obj.toString());
            }
            if(msg.arg1==4803){
                fragment.get().binding.min480.setText(msg.obj.toString());
            }

            if(msg.arg1==50){
                fragment.get().binding.title5.setText(msg.obj.toString());
            }
            if(msg.arg1==150){
                fragment.get().binding.title15.setText(msg.obj.toString());
            }
            if(msg.arg1==300){
                fragment.get().binding.title30.setText(msg.obj.toString());
            }

            if(msg.arg1==600){
                fragment.get().binding.title60.setText(msg.obj.toString());
            }
            if(msg.arg1==1200){
                fragment.get().binding.title120.setText(msg.obj.toString());
            }
            if(msg.arg1==2400){
                fragment.get().binding.title240.setText(msg.obj.toString());
            }
            if(msg.arg1==3600){
                fragment.get().binding.title360.setText(msg.obj.toString());
            }
            if(msg.arg1==4800){
                fragment.get().binding.title480.setText(msg.obj.toString());
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getCodeDetail:
                if (TextUtils.isEmpty(binding.etCode.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确的code值", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(binding.editText3.getText().toString()) || TextUtils.isEmpty(binding.editText4.getText().toString()) || TextUtils.isEmpty(binding.editText6.getText().toString()) ){
                    Toast.makeText(getContext(), "日线不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText3.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "第一个日线不能大于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText3.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "第一个日线不能小于2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText4.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "第二个日线不能大于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText4.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "第二个日线不能小于2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText6.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "第三个日线不能大于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText6.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "第三个日线不能小于2", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(binding.editText6.getText().toString())>Integer.parseInt(binding.editText4.getText().toString())
                        && Integer.parseInt(binding.editText6.getText().toString())>Integer.parseInt(binding.editText3.getText().toString())
                        &&Integer.parseInt(binding.editText4.getText().toString())>Integer.parseInt(binding.editText3.getText().toString())) {

                    number3=Integer.parseInt(binding.editText3.getText().toString());
                    number4=Integer.parseInt(binding.editText4.getText().toString());
                    number6=Integer.parseInt(binding.editText6.getText().toString());
                    string3=binding.editText3.getText().toString();
                    string4=binding.editText4.getText().toString();
                    string6=binding.editText6.getText().toString();
                    codeName=binding.etCode.getText().toString();
                    showLoadingDialog("获取数据中...");
                    initCode(codeName);
                }else{
                    Toast.makeText(getContext(), "日线从左到右应该是从小到大排序", Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.remember:
                sharedPreferencesUtil.save_notes(binding.notes.getText().toString());
                sharedPreferencesUtil.save_times(ConfigUtils.getTime());
                binding.time.setText(sharedPreferencesUtil.getTimes());
                Toast.makeText(getContext(), "笔记保存成功，祝您做T成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back:
                screenShot = viewConversionBitmap(binding.contentLayout);
                saveImage();
                break;
        }
    }

    private void initCode(final String codeName) {
        Http.getBeanByMM(codeName + ",m5,,2", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            jsonObject.getJSONObject("data").getJSONObject(codeName);
                            JSONObject a = jsonObject.getJSONObject("data").getJSONObject(codeName);
                            AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                            if (bean5.getM5() == null || bean5.getM5().size() == 0) {
                                dismissLoadingDialog();
                                Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();
                                return ;
                            }

                            getCodeDetails(codeName);

                        } catch (ClassCastException e) {
                            dismissLoadingDialog();
                            Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void getCodeDetails(String codeName) {
        Http.getBeanByMM(codeName + ",m5,,28", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "失败" + e.toString());
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                String data = ResultUtils.D5(bean5,2,binding.switchButton5.isChecked());
                if (data.isEmpty()) {
                    return;
                }
                String[] str_5 = data.split("#");
                String[] max_str_5 = ResultUtils.D5(bean5,3,binding.switchButton5.isChecked()).split("#");
                String[] min_str_5=ResultUtils.D5(bean5,4,binding.switchButton5.isChecked()).split("#");

                sendMessage(str_5,51);
                sendMessage(max_str_5,52);
                sendMessage(min_str_5,53);

                String[] time = ResultUtils.D5(bean5,0,binding.switchButton5.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=50;
                message.obj="5分时\n\n"+time[time.length-1].substring(8,10)+":"+time[time.length-1].substring(10)+"开始到后一个5分时之内适用";
                handler.sendMessage(message);
            }
        });

        Http.getBeanByMM(codeName + ",m15,,28", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean15 bean15 = JSON.parseObject(a.toJSONString(), AllBean15.class);
                String data = ResultUtils.D15(bean15,2,binding.switchButton15.isChecked());
                if (data.isEmpty()) return;
                String[] str_15 = data.split("#");
                String[] max_str_15 = ResultUtils.D15(bean15,3,binding.switchButton15.isChecked()).split("#");
                String[] min_str_15=ResultUtils.D15(bean15,4,binding.switchButton15.isChecked()).split("#");

                sendMessage(str_15,151);
                sendMessage(max_str_15,152);
                sendMessage(min_str_15,153);
                String[] time = ResultUtils.D15(bean15,0,binding.switchButton15.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=150;
                message.obj="15分时\n\n"+time[time.length-1].substring(8,10)+":"+time[time.length-1].substring(10)+"开始到后一个15分时之内适用";
                handler.sendMessage(message);
            }
        });

        Http.getBeanByMM(codeName + ",m30,,28", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean30 bean30 = JSON.parseObject(a.toJSONString(), AllBean30.class);
                String data = ResultUtils.D30(bean30,2,binding.switchButton30.isChecked());
                if (data.isEmpty()) return;
                String[] str_30 = data.split("#");
                String[] max_str_30 = ResultUtils.D30(bean30,3,binding.switchButton30.isChecked()).split("#");
                String[] min_str_30=ResultUtils.D30(bean30,4,binding.switchButton30.isChecked()).split("#");

                sendMessage(str_30,301);
                sendMessage(max_str_30,302);
                sendMessage(min_str_30,303);

                String[] time = ResultUtils.D30(bean30,0,binding.switchButton30.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=300;
                message.obj="30分时\n\n"+time[time.length-1].substring(8,10)+":"+time[time.length-1].substring(10)+"开始到后一个30分时之内适用";
                handler.sendMessage(message);
            }
        });

        Http.getBeanByMM(codeName + ",m60,,58", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean60 bean60 = JSON.parseObject(a.toJSONString(), AllBean60.class);
                String data = ResultUtils.D60(bean60,2,binding.switchButton60.isChecked());
                if (data.isEmpty()) return;
                String[] str_60 = data.split("#");
                String[] max_str_60 = ResultUtils.D60(bean60,3,binding.switchButton60.isChecked()).split("#");
                String[] min_str_60=ResultUtils.D60(bean60,4,binding.switchButton60.isChecked()).split("#");
                sendMessage(str_60,601);
                sendMessage(max_str_60,602);
                sendMessage(min_str_60,603);

                String[] time = ResultUtils.D60(bean60,0,binding.switchButton60.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=600;
                message.obj="60分时\n"+time[time.length-1].substring(8,10)+":"+time[time.length-1].substring(10)+"开始到后一个60分时之内适用";
                handler.sendMessage(message);


                String data120 = ResultUtils.D120(bean60,2,binding.switchButton120.isChecked());
                if (data120.isEmpty()) return;
                String[] str_120 = data120.split("#");
                String[] max_str_120 = ResultUtils.D120(bean60,3,binding.switchButton120.isChecked()).split("#");
                String[] min_str_120=ResultUtils.D120(bean60,4,binding.switchButton120.isChecked()).split("#");
                sendMessage(str_120,1201);
                sendMessage(max_str_120,1202);
                sendMessage(min_str_120,1203);

                String[] time120 = ResultUtils.D120(bean60,0,binding.switchButton120.isChecked()).split("#");
                Message message2 =Message.obtain();
                message2.arg1=1200;
                message2.obj="120分时\n\n"+time120[time120.length-1].substring(8,10)+":"+time120[time120.length-1].substring(10)+"开始到后一个120分时之内适用";
                handler.sendMessage(message2);
            }
        });

        Http.getBeanByDay(codeName + ",day,,,28,qfq", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBeanDay beanDay = JSON.parseObject(a.toJSONString(), AllBeanDay.class);
                String data = ResultUtils.D240(beanDay,2,binding.switchButton240.isChecked());
                if (data.isEmpty()) return;
                String[] str_240 = data.split("#");
                String[] max_str_240= ResultUtils.D240(beanDay,3,binding.switchButton240.isChecked()).split("#");
                String[] min_str_240= ResultUtils.D240(beanDay,4,binding.switchButton240.isChecked()).split("#");
                sendMessage(str_240,2401);
                sendMessage(max_str_240,2402);
                sendMessage(min_str_240,2403);

//                String[] time = ResultUtils.D240(beanDay,0,binding.switchButton240.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=2400;
                message.obj="日线\n\n"+(binding.switchButton240.isChecked()?"明天盘中适用":"今天盘中适用");
                handler.sendMessage(message);
            }
        });

        Http.getBeanByWeek(codeName + ",week,,,28,qfq", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBeanWeek beanWeek = JSON.parseObject(a.toJSONString(), AllBeanWeek.class);
                String data = ResultUtils.D360(beanWeek,2,binding.switchButton360.isChecked());
                if (data.isEmpty()) return;
                String[] str_360 = data.split("#");
                String[] max_str_360= ResultUtils.D360(beanWeek,3,binding.switchButton360.isChecked()).split("#");
                String[] min_str_360= ResultUtils.D360(beanWeek,4,binding.switchButton360.isChecked()).split("#");
                sendMessage(str_360,3601);
                sendMessage(max_str_360,3602);
                sendMessage(min_str_360,3603);

//                String[] time = ResultUtils.D360(beanWeek,0,binding.switchButton360.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=3600;
                message.obj="周线\n\n"+(binding.switchButton360.isChecked()?"下周适用":"本周之内盘中适用");
                handler.sendMessage(message);
            }
        });

        Http.getBeanByMonth(codeName + ",month,,,28,qfq", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBeanMonth beanMonth = JSON.parseObject(a.toJSONString(), AllBeanMonth.class);
                String data = ResultUtils.D480(beanMonth,2,binding.switchButton480.isChecked());
                if (data.isEmpty()) return;
                String[] str_480 = data.split("#");
                String[] max_str_480= ResultUtils.D480(beanMonth,3,binding.switchButton480.isChecked()).split("#");
                String[] min_str_480= ResultUtils.D480(beanMonth,4,binding.switchButton480.isChecked()).split("#");
                sendMessage(str_480,4801);
                sendMessage(max_str_480,4802);
                sendMessage(min_str_480,4803);
                dismissLoadingDialog();

//                String[] time = ResultUtils.D480(beanMonth,0,binding.switchButton480.isChecked()).split("#");
                Message message =Message.obtain();
                message.arg1=4800;
                message.obj="月线\n\n"+(binding.switchButton480.isChecked()?"下月适用":"本月之内盘中适用");
                handler.sendMessage(message);
            }
        });
    }

    private void sendMessage(String[] str_5,int type){
        String[] newString_15_3 = ResultUtils.getString_15_number(str_5, number3, number6).split("->");
        String[] newString_15_4 = ResultUtils.getString_15_number(str_5, number4, number6).split("->");
        String[] newString_15_6 = ResultUtils.getString_15_number(str_5, number6, number6).split("->");
        String a1 = getOther_One_two(str_5, newString_15_3, newString_15_4, number3, number4, string3, string4);
        String a2 = getOther_One_two(str_5, newString_15_3, newString_15_6, number3, number6, string3, string6);
        String a3 = getOther_One_two(str_5, newString_15_4, newString_15_6, number4, number6, string4, string6);

        Message message =Message.obtain();
        message.arg1=type;
        message.obj=a1 + "\n" + a2 + "\n" + a3;
        handler.sendMessage(message);
    }


    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private String getOther_One_two(String[] str_15, String[] newString_15_3, String[] newString_15_4, int number3, int number4, String string3, String string4) {
        StringBuilder builder_content_15 = new StringBuilder();

        builder_content_15.append("⭐").append(string3).append("与").append(string4).append("⭐\n");

        if (Double.parseDouble(newString_15_3[newString_15_3.length - 1]) - Double.parseDouble(newString_15_4[newString_15_4.length - 1]) > 0) {
            //3在4上方，所以应该考虑什么情况下死-- 4穿3
            double number = Double.parseDouble(str_15[str_15.length - 1]);
            double size = 0;
            boolean jisuan = true;
            while (jisuan) {
                if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number4 - 1), number, number4) - ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number, number3) <= 0) {
                    number -= 0.001;
                    if (number < size) {
                        jisuan = false;
                        builder_content_15.append("下方割肉点:").append(ResultUtils.getMin(newString_15_3[newString_15_3.length - 1], newString_15_4[newString_15_4.length - 1])); //从3、4日线平均值最后一位取最小值
                    }
                } else {
                    jisuan = false;
                    builder_content_15.append("死(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number, number3))).append(")支:").append(ResultUtils.getData(number));
                }
            }

        } else if (Double.parseDouble(newString_15_3[newString_15_3.length - 1]) - Double.parseDouble(newString_15_4[newString_15_4.length - 1]) < 0) {
            //3在4下方，所以应该考虑什么情况下金-- 3穿4
            double number = Double.parseDouble(newString_15_3[newString_15_3.length - number3]);
            double size = Double.parseDouble(newString_15_3[newString_15_3.length - number3]) * 2;
            boolean jisuan = true;
            while (jisuan) {
                if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number, number3) - ResultUtils.getMean(ResultUtils.getSum_number(str_15, number4 - 1), number, number4) <= 0) {
                    number += 0.001;
                    if (number > size) {
                        jisuan = false;
                        builder_content_15.append("上方追涨点:").append(ResultUtils.getData(Double.parseDouble(str_15[str_15.length - 1])));
                    }
                } else {
                    jisuan = false;
                    builder_content_15.append("金(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number, number3))).append(")压:").append(ResultUtils.decimalFormat.format(number));
                    boolean A = true;
                    double max = Double.parseDouble(str_15[str_15.length - (number4)]);
                    double min = Double.parseDouble(str_15[str_15.length - (number4)]) * 0.8;
                    while (A) {
                        if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number4 - 1), max, number4) - ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3) >= 0.0001) {
                            max -= 0.001;
                            if (max < min) {
                                A = false;
                            }
                        } else {
                            A = false;
                            if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3) - Double.parseDouble(newString_15_3[newString_15_3.length - 1]) > 0) {
                                builder_content_15.append("\n").append("金(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3))).append(")压:").append(ResultUtils.decimalFormat.format(max));
                            } else {
                                builder_content_15.append("\n").append("死(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3))).append(")支:").append(ResultUtils.decimalFormat.format(max));
                            }
                        }
                    }
                }
            }

        } else {
            double number = Double.parseDouble(str_15[str_15.length - 1]);
            double size = 0;
            boolean jisuan = true;
            while (jisuan) {
                if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number4 - 1), number, number4) - ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number, number3) <= 0) {
                    number -= 0.001;
                    if (number < size) {
                        jisuan = false;
                        builder_content_15.append("下方割肉点:").append(ResultUtils.getMin(newString_15_3[newString_15_3.length - 1], newString_15_4[newString_15_4.length - 1])); //从3、4日线平均值最后一位取最小值
                    }
                } else {
                    jisuan = false;
                    builder_content_15.append("死(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number, number3))).append(")支:").append(ResultUtils.getData(number));
                }
            }


            //3在4下方，所以应该考虑什么情况下金-- 3穿4
            double number1 = Double.parseDouble(newString_15_3[newString_15_3.length - number3]);
            double size1 = Double.parseDouble(newString_15_3[newString_15_3.length - number3]) * 2;
            boolean jisuan1 = true;
            while (jisuan1) {
                if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number1, number3) - ResultUtils.getMean(ResultUtils.getSum_number(str_15, number4 - 1), number1, number4) <= 0) {
                    number1 += 0.001;
                    if (number1 > size1) {
                        jisuan1 = false;
                        builder_content_15.append("\n上方追涨点:").append(ResultUtils.getData(Double.parseDouble(str_15[str_15.length - 1])));
                    }
                } else {
                    jisuan1 = false;
                    builder_content_15.append("\n金(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), number1, number3))).append(")压:").append(ResultUtils.decimalFormat.format(number1));
                    boolean A = true;
                    double max = Double.parseDouble(str_15[str_15.length - (number4)]);
                    double min = Double.parseDouble(str_15[str_15.length - (number4)]) * 0.8;
                    while (A) {
                        if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number4 - 1), max, number4) - ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3) >= 0.0001) {
                            max -= 0.001;
                            if (max < min) {
                                A = false;
                            }
                        } else {
                            A = false;
                            if (ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3) - Double.parseDouble(newString_15_3[newString_15_3.length - 1]) > 0) {
                                builder_content_15.append("\n").append("金(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3))).append(")压:").append(ResultUtils.decimalFormat.format(max));
                            } else {
                                builder_content_15.append("\n").append("死(").append(ResultUtils.getData(ResultUtils.getMean(ResultUtils.getSum_number(str_15, number3 - 1), max, number3))).append(")支:").append(ResultUtils.decimalFormat.format(max));
                            }
                        }
                    }
                }
            }
        }
        return builder_content_15.toString();
    }

    private void saveImage() {
        RxPermissions rxPermissions = new RxPermissions((Activity) getContext());
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value) {
                            startPermissionSuccess();
                        } else {
                            Toast.makeText(getContext(), "拒绝权限", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void startPermissionSuccess() {//获取读写sd卡权限成功回调
        if (screenShot == null) {
            return;
        }
        String fileName = "dlam" + TimeUtils.getNowMills() + ".jpg";
        try {
            boolean state = Utils.addPictureToAlbum(getContext(), screenShot, fileName);
            if (state) {
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                screenShot = null;
            } else {
                Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Bitmap viewConversionBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
}
