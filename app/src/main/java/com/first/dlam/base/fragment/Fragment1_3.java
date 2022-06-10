package com.first.dlam.base.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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
import com.first.dlam.MainActivity;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.SuperActivity;
import com.first.dlam.bean.AllBean15;
import com.first.dlam.bean.AllBean30;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.bean.AllBean60;
import com.first.dlam.bean.AllBeanDay;
import com.first.dlam.bean.AllBeanMonth;
import com.first.dlam.bean.AllBeanWeek;
import com.first.dlam.bean.BaseEvent;
import com.first.dlam.bean.FirstBean;
import com.first.dlam.data.CompositeIndexBean;
import com.first.dlam.data.IncomeBean;
import com.first.dlam.databinding.Fragment13Binding;
import com.first.dlam.manager.LineChartManager;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;
import com.first.dlam.utils.Utils;
import com.github.mikephil.charting.components.YAxis;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;


public class Fragment1_3 extends BaseFragment implements View.OnClickListener {

    private Fragment13Binding binding;
    public static volatile boolean isDestory = true;
    static MainHandler handler;
    static MyRunnable myRunnable;
    private float min = 0;
    private float max = 0;

    public static float min_5 = 0;
    public static float max_5 = 0;

    public static float min_15 = 0;
    public static float max_15 = 0;

    public static float min_30 = 0;
    public static float max_30 = 0;

    public static float min_60 = 0;
    public static float max_60 = 0;

    public static float min_120 = 0;
    public static float max_120 = 0;

    public static float min_240 = 0;
    public static float max_240 = 0;

    private float min_360 = 0;
    private float max_360 = 0;

    private float min_480 = 0;
    private float max_480 = 0;

    private List<CompositeIndexBean> incomeBean_5;
    private List<CompositeIndexBean> incomeBean_15;
    private List<CompositeIndexBean> incomeBean_30;
    private List<CompositeIndexBean> incomeBean_60;
    private List<CompositeIndexBean> incomeBean_120;
    private List<CompositeIndexBean> incomeBean_240; //第一个折线图的5日线趋势
    private List<CompositeIndexBean> incomeBean_360;
    private List<CompositeIndexBean> incomeBean_480;


    private float min_20 = 0;
    private float max_20 = 0;
    private List<CompositeIndexBean> incomeBean_20;
    private LineChartManager lineChartManager_20;
    private  List<CompositeIndexBean> incomeBean_3_20 = new ArrayList<>(40);
    private  List<CompositeIndexBean> incomeBean_4_20 = new ArrayList<>(40);
    private  List<CompositeIndexBean> incomeBean_6_20 = new ArrayList<>(40);
    private  List<CompositeIndexBean> incomeBean_20_max = new ArrayList<>(40);
    private  List<CompositeIndexBean> incomeBean_20_min = new ArrayList<>(40);

    private float min_40 = 0;
    private float max_40 = 0;
    private List<CompositeIndexBean> incomeBean_40;
    private LineChartManager lineChartManager_40;
    private List<CompositeIndexBean> incomeBean_3_40 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_4_40 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_6_40 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_40_max = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_40_min = new ArrayList<>(40);


    private List<IncomeBean> incomeBean_600;
    private LineChartManager lineChartManager_600;

    private LineChartManager lineChartManager_5;
    public static List<CompositeIndexBean> incomeBean_3_5 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_4_5 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_6_5 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_5_max = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_5_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_15;
    public static List<CompositeIndexBean> incomeBean_3_15 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_4_15 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_6_15 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_15_max = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_15_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_30;
    public static List<CompositeIndexBean> incomeBean_3_30 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_4_30 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_6_30 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_30_max = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_30_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_60;
    public static List<CompositeIndexBean> incomeBean_3_60 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_4_60 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_6_60 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_60_max = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_60_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_120;
    public static  List<CompositeIndexBean> incomeBean_3_120 = new ArrayList<>(40);
    public static  List<CompositeIndexBean> incomeBean_4_120 = new ArrayList<>(40);
    public static  List<CompositeIndexBean> incomeBean_6_120 = new ArrayList<>(40);
    public static  List<CompositeIndexBean> incomeBean_120_max = new ArrayList<>(40);
    public static  List<CompositeIndexBean> incomeBean_120_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_240;
    public static List<CompositeIndexBean> incomeBean_3_240 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_4_240 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_6_240 = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_240_max = new ArrayList<>(40);
    public static List<CompositeIndexBean> incomeBean_240_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_360;
    private List<CompositeIndexBean> incomeBean_3_360 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_4_360 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_6_360 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_360_max = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_360_min = new ArrayList<>(40);

    private LineChartManager lineChartManager_480;
    private List<CompositeIndexBean> incomeBean_3_480 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_4_480 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_6_480 = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_480_max = new ArrayList<>(40);
    private List<CompositeIndexBean> incomeBean_480_min = new ArrayList<>(40);

    private SharedPreferencesUtil sharedPreferencesUtil;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static String string3;
    public static String string4;
    public static String string6;

    private Bitmap screenShot = null;

    public static volatile float now=0;

    public static String[] str_5;
    public static String[] max_str_5;
    public static String[] min_str_5;
    public static String[] str_15;
    public static String[] max_str_15;
    public static String[] min_str_15;
    private String[] str_20;
    private String[] max_str_20;
    private String[] min_str_20;
    public static String[] str_30;
    public static String[] max_str_30;
    public static String[] min_str_30;
    private String[] str_40;
    private String[] max_str_40;
    private String[] min_str_40;
    public static String[] str_60;
    public static String[] max_str_60;
    public static String[] min_str_60;
    public static String[] str_120;
    public static String[] max_str_120;
    public static String[] min_str_120;
    public static String[] str_240;
    public static String[] max_str_240;
    public static String[] min_str_240;
    private String[] str_360;
    private String[] max_str_360;
    private String[] min_str_360;
    private String[] str_480;
    private String[] max_str_480;
    private String[] min_str_480;

    private boolean firstOpen=true;
    private String newData;
    private String time ="01";

    private float size=0.05f;
    private int otherTime=5;

    public Map<String, Integer> hashMap;

    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment1_3, container, false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());

        hashMap=sharedPreferencesUtil.getHashMap();
        initData();
        initListener();


    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void initListener() {
        binding.otherDetailButton.setOnClickListener(this);
        binding.initialize.setOnClickListener(this);
        binding.download.setOnClickListener(this);
        binding.layout1.setOnClickListener(this);
        binding.checkbox1.setOnClickListener(this);
        binding.layout2.setOnClickListener(this);
        binding.checkbox2.setOnClickListener(this);
        binding.layout3.setOnClickListener(this);
        binding.checkbox3.setOnClickListener(this);
        binding.layout4.setOnClickListener(this);
        binding.checkbox4.setOnClickListener(this);
        binding.superActivity.setOnClickListener(this);
    }

    public void initData() {
        handler = new MainHandler(this);

        lineChartManager_600 = new LineChartManager(binding.lineChart600, 3.6F, 8);
        lineChartManager_5 = new LineChartManager(binding.lineChart5, 3.6F, 8);
        lineChartManager_15 = new LineChartManager(binding.lineChart15, 3.6F, 8);
        lineChartManager_30 = new LineChartManager(binding.lineChart30, 3.6F, 8);
        lineChartManager_60 = new LineChartManager(binding.lineChart60, 3.6F, 8);
        lineChartManager_120 = new LineChartManager(binding.lineChart120, 3.6F, 8);
        lineChartManager_240 = new LineChartManager(binding.lineChart240, 3.6F, 8);
        lineChartManager_360 = new LineChartManager(binding.lineChart360, 3.6F, 8);
        lineChartManager_480 = new LineChartManager(binding.lineChart480, 3.6F, 8);


        lineChartManager_20 = new LineChartManager(binding.lineChart20, 3.6F, 8);
        lineChartManager_40 = new LineChartManager(binding.lineChart40, 3.6F, 8);

        string3 = sharedPreferencesUtil.getET_3();
        string4 = sharedPreferencesUtil.getET_4();
        string6 = sharedPreferencesUtil.getET_6();

        binding.title.setText("⭐⭐⭐" + string3 + "日线:黑色  " + string4 + "日线:红色  " + string6 + "日线:蓝色  ⭐⭐⭐\n⭐⭐⭐压力位:最高价5日线   支撑位:最低价5日线⭐⭐⭐");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otherDetailButton:
                if (binding.otherDetailButton.getText().toString().equals("自动更新中")) {
                    binding.otherDetailButton.setText("已停止更新");
                    isDestory = true;

                } else {
                    binding.otherDetailButton.setText("自动更新中");
                    isDestory = false;
                    firstOpen=true;
                    if (myRunnable == null) {
                        myRunnable = new MyRunnable();
                    }
                    new Thread(myRunnable).start();
                    max = 0;max_5 = 0;max_15 = 0;max_20 = 0;max_30 = 0;max_40 = 0;max_60 = 0;max_120 = 0;max_240 = 0;max_360 = 0;max_480 = 0;
                    min = 0;min_5 = 0;min_15 = 0;min_20 = 0;min_30 = 0;min_40 = 0;min_60 = 0;min_120 = 0;min_240 = 0;min_360 = 0;min_480 = 0;
                    showLoadingDialog("初始化...");
                }
                break;
            case R.id.initialize:
                Toast.makeText(getContext(), "已经初始化", Toast.LENGTH_SHORT).show();
                initializeSharedPreferences();
                break;

            case R.id.download:
                screenShot = viewConversionBitmap(binding.contentLayout);
                saveImage();
                break;
            case R.id.layout1:
            case R.id.checkbox1:
                binding.checkbox1.setChecked(!binding.checkbox1.isChecked());
                break;
            case R.id.layout2:
            case R.id.checkbox2:
                binding.checkbox2.setChecked(!binding.checkbox2.isChecked());
                break;
            case R.id.layout3:
            case R.id.checkbox3:
                binding.checkbox3.setChecked(!binding.checkbox3.isChecked());
                break;
            case R.id.layout4:
            case R.id.checkbox4:
                binding.checkbox4.setChecked(!binding.checkbox4.isChecked());
                break;
            case R.id.superActivity:
                if(isDestory){
                    Toast.makeText(getContext(),"请点击[已停止更新]按钮来获取数据先",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent =new Intent(getContext(), SuperActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initializeSharedPreferences() {
        max = 0;max_5 = 0;max_15 = 0;max_20 = 0;max_30 = 0;max_40 = 0;max_60 = 0;max_120 = 0;max_240 = 0;max_360 = 0;max_480 = 0;
        min = 0;min_5 = 0;min_15 = 0;min_20 = 0;min_30 = 0;min_40 = 0;min_60 = 0;min_120 = 0;min_240 = 0;min_360 = 0;min_480 = 0;
        sharedPreferencesUtil.saveDayMax(-100000);
        sharedPreferencesUtil.saveDayMIN(100000);
        sharedPreferencesUtil.saveDAY_MAX_TIME(dateFormat.format(System.currentTimeMillis()));
        sharedPreferencesUtil.saveDAY_MIN_TIME(dateFormat.format(System.currentTimeMillis()));
        sharedPreferencesUtil.saveDAY_HUI_DIAO_MIN(100000);
        sharedPreferencesUtil.saveDAY_HUI_DIAO_MAX(-100000);
        sharedPreferencesUtil.saveDAY_MAX_TIME_NUMBER(0);
        sharedPreferencesUtil.saveDAY_MIN_TIME_NUMBER(0);
    }

    public static class MainHandler extends Handler {
        WeakReference<Fragment1_3> fragment;

        private MainHandler(Fragment1_3 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            Fragment1_3 theActivity = fragment.get();
            if (theActivity == null) {
                return;
            }
            if (msg.arg1 == 0) {
                theActivity.getOtherDetailByCode(theActivity.sharedPreferencesUtil.getCode_name());
                if(theActivity.max!=0 && theActivity.max_5!=0 && theActivity.max_15!=0 && theActivity.max_20!=0 && theActivity.max_30!=0 && theActivity.max_40!=0 && theActivity.max_60!=0 && theActivity.max_120!=0 &&theActivity.max_240!=0 && theActivity.max_360!=0 && theActivity.max_480!=0){
                    theActivity.firstOpen =false;
                }
            } else if (msg.arg1 == 1) {
                theActivity.binding.tvNow.setText(msg.obj.toString());
            } else if (msg.arg1 == 5) {
                theActivity.binding.textXNumber5.setText(msg.obj.toString());

                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0) {
                    if (theActivity.incomeBean_5 != null && theActivity.incomeBean_5.size() > 0) {
                        if (theActivity.incomeBean_3_5 != null && theActivity.incomeBean_3_5.size() > 0 && theActivity.incomeBean_4_5 != null && theActivity.incomeBean_4_5.size() > 0 && theActivity.incomeBean_6_5 != null && theActivity.incomeBean_6_5.size() > 0 && theActivity.incomeBean_5_max.size()>0) {
                            theActivity.showLineChart3(theActivity.lineChartManager_5, theActivity.incomeBean_600, theActivity.incomeBean_3_5, theActivity.incomeBean_4_5, theActivity.incomeBean_6_5, theActivity.incomeBean_5_max, theActivity.incomeBean_5_min);
                            theActivity.binding.content5.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_5_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_5_min.get(0).getRate()));
                        }
                    }
                }

            } else if (msg.arg1 == 15) {

                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0) {
                    theActivity.binding.textXNumber15.setText(msg.obj.toString());
                    theActivity.lineChartManager_600.showLineChart2(theActivity.incomeBean_600, "string120", theActivity.getResources().getColor(R.color.orange));
                    theActivity.lineChartManager_600.setChartFillDrawable(theActivity.getResources().getDrawable(R.drawable.fade_orange));

                    if (theActivity.incomeBean_5 != null && theActivity.incomeBean_5.size() > 0) {
                        theActivity.lineChartManager_600.addLine(theActivity.incomeBean_5, "string5", theActivity.getResources().getColor(R.color.back));
                    }
                    if (theActivity.incomeBean_15 != null && theActivity.incomeBean_15.size() > 0) {
                        theActivity.lineChartManager_600.addLine(theActivity.incomeBean_15, "string15", theActivity.getResources().getColor(R.color.red));
                    }
                    if (theActivity.incomeBean_30 != null && theActivity.incomeBean_30.size() > 0) {
                        theActivity.lineChartManager_600.addLine(theActivity.incomeBean_30, "string30", theActivity.getResources().getColor(R.color.qing));
                    }
                    if (theActivity.incomeBean_60 != null && theActivity.incomeBean_60.size() > 0) {
                        theActivity.lineChartManager_600.addLine(theActivity.incomeBean_60, "string60", theActivity.getResources().getColor(R.color.lang));
                    }
                    if(theActivity.binding.checkbox1.isChecked()) {
                        if (theActivity.incomeBean_20 != null && theActivity.incomeBean_20.size() > 0) {
                            theActivity.lineChartManager_600.addLine(theActivity.incomeBean_20, "string20", theActivity.getResources().getColor(R.color.lv));
                        }
                    }
                    if(theActivity.binding.checkbox2.isChecked()) {
                        if (theActivity.incomeBean_40 != null && theActivity.incomeBean_40.size() > 0) {
                            theActivity.lineChartManager_600.addLine(theActivity.incomeBean_40, "string40", theActivity.getResources().getColor(R.color.zi));
                        }
                    }

                    if(theActivity.binding.checkbox3.isChecked()) {
                        if (theActivity.incomeBean_120 != null && theActivity.incomeBean_120.size() > 0) {
                            theActivity.lineChartManager_600.addLine(theActivity.incomeBean_120, "string120", theActivity.getResources().getColor(R.color.shen));
                        }
                    }

                    if(theActivity.binding.checkbox4.isChecked()) {
                        if (theActivity.incomeBean_240 != null && theActivity.incomeBean_240.size() > 0) {
                            theActivity.lineChartManager_600.addLine(theActivity.incomeBean_240, "string240", theActivity.getResources().getColor(R.color.fen));
                        }
                    }


                    if (theActivity.incomeBean_3_15 != null && theActivity.incomeBean_3_15.size() > 0 && theActivity.incomeBean_4_15 != null && theActivity.incomeBean_4_15.size() > 0 && theActivity.incomeBean_6_15 != null && theActivity.incomeBean_6_15.size() > 0 && theActivity.incomeBean_15_max.size()>0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_15, theActivity.incomeBean_600, theActivity.incomeBean_3_15, theActivity.incomeBean_4_15, theActivity.incomeBean_6_15, theActivity.incomeBean_15_max, theActivity.incomeBean_15_min);
                        theActivity.binding.content15.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_15_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_15_min.get(0).getRate()));
                    }
                }
            } else if (msg.arg1 == 20) {
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_20 != null && theActivity.incomeBean_3_20.size() > 0 && theActivity.incomeBean_4_20 != null && theActivity.incomeBean_4_20.size() > 0 && theActivity.incomeBean_6_20 != null && theActivity.incomeBean_6_20.size() > 0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_20, theActivity.incomeBean_600, theActivity.incomeBean_3_20, theActivity.incomeBean_4_20, theActivity.incomeBean_6_20, theActivity.incomeBean_20_max, theActivity.incomeBean_20_min);
                        theActivity.binding.content20.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_20_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_20_min.get(0).getRate()));
                    }

            } else if (msg.arg1 == 30) {
                theActivity.binding.textXNumber30.setText(msg.obj.toString());
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_30 != null && theActivity.incomeBean_3_30.size() > 0 && theActivity.incomeBean_4_30 != null && theActivity.incomeBean_4_30.size() > 0 && theActivity.incomeBean_6_30 != null && theActivity.incomeBean_6_30.size() > 0&&theActivity.incomeBean_30_max.size()>0 ) {
                        theActivity.showLineChart3(theActivity.lineChartManager_30, theActivity.incomeBean_600, theActivity.incomeBean_3_30, theActivity.incomeBean_4_30, theActivity.incomeBean_6_30, theActivity.incomeBean_30_max, theActivity.incomeBean_30_min);
                        theActivity.binding.content30.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_30_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_30_min.get(0).getRate()));
                    }

            } else if (msg.arg1 == 40) {
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_40 != null && theActivity.incomeBean_3_40.size() > 0 && theActivity.incomeBean_4_40 != null && theActivity.incomeBean_4_40.size() > 0 && theActivity.incomeBean_6_40 != null && theActivity.incomeBean_6_40.size() > 0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_40, theActivity.incomeBean_600, theActivity.incomeBean_3_40, theActivity.incomeBean_4_40, theActivity.incomeBean_6_40, theActivity.incomeBean_40_max, theActivity.incomeBean_40_min);
                        theActivity.binding.content40.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_40_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_40_min.get(0).getRate()));
                    }

            }else if (msg.arg1 == 60) {
                theActivity.binding.textXNumber60.setText(msg.obj.toString());
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_60 != null && theActivity.incomeBean_3_60.size() > 0 && theActivity.incomeBean_4_60 != null && theActivity.incomeBean_4_60.size() > 0 && theActivity.incomeBean_6_60 != null && theActivity.incomeBean_6_60.size() > 0 &&theActivity.incomeBean_60_max.size()>0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_60, theActivity.incomeBean_600, theActivity.incomeBean_3_60, theActivity.incomeBean_4_60, theActivity.incomeBean_6_60, theActivity.incomeBean_60_max, theActivity.incomeBean_60_min);
                        theActivity.binding.content60.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_60_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_60_min.get(0).getRate()));
                    }

            } else if (msg.arg1 == 120) {
                theActivity.binding.textXNumber120.setText(msg.obj.toString());
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_120 != null && theActivity.incomeBean_3_120.size() > 0 && theActivity.incomeBean_4_120 != null && theActivity.incomeBean_4_120.size() > 0 && theActivity.incomeBean_6_120 != null && theActivity.incomeBean_6_120.size() > 0&& theActivity.incomeBean_120_max.size() > 0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_120, theActivity.incomeBean_600, theActivity.incomeBean_3_120, theActivity.incomeBean_4_120, theActivity.incomeBean_6_120, theActivity.incomeBean_120_max, theActivity.incomeBean_120_min);
                        theActivity.binding.content120.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_120_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_120_min.get(0).getRate()));
                    }
            } else if (msg.arg1 == 240) {
                theActivity.binding.textXNumber240.setText(msg.obj.toString());
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_240 != null && theActivity.incomeBean_3_240.size() > 0 && theActivity.incomeBean_4_240 != null && theActivity.incomeBean_4_240.size() > 0 && theActivity.incomeBean_6_240 != null && theActivity.incomeBean_6_240.size() > 0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_240, theActivity.incomeBean_600, theActivity.incomeBean_3_240, theActivity.incomeBean_4_240, theActivity.incomeBean_6_240, theActivity.incomeBean_240_max, theActivity.incomeBean_240_min);
                        theActivity.binding.content240.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_240_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_240_min.get(0).getRate()));
                    }
            } else if (msg.arg1 == 360) {
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_360 != null && theActivity.incomeBean_3_360.size() > 0 && theActivity.incomeBean_4_360 != null && theActivity.incomeBean_4_360.size() > 0 && theActivity.incomeBean_6_360 != null && theActivity.incomeBean_6_360.size() > 0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_360, theActivity.incomeBean_600, theActivity.incomeBean_3_360, theActivity.incomeBean_4_360, theActivity.incomeBean_6_360, theActivity.incomeBean_360_max, theActivity.incomeBean_360_min);
                        theActivity.binding.content360.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_360_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_360_min.get(0).getRate()));
                    }
            } else if (msg.arg1 == 480) {
                if (theActivity.incomeBean_600 != null && theActivity.incomeBean_600.size() > 0)
                    if (theActivity.incomeBean_3_480 != null && theActivity.incomeBean_3_480.size() > 0 && theActivity.incomeBean_4_480 != null && theActivity.incomeBean_4_480.size() > 0 && theActivity.incomeBean_6_480 != null && theActivity.incomeBean_6_480.size() > 0) {
                        theActivity.showLineChart3(theActivity.lineChartManager_480, theActivity.incomeBean_600, theActivity.incomeBean_3_480, theActivity.incomeBean_4_480, theActivity.incomeBean_6_480, theActivity.incomeBean_480_max, theActivity.incomeBean_480_min);
                        theActivity.binding.content480.setText("压力位:" + ResultUtils.getData(theActivity.incomeBean_480_max.get(0).getRate()) + "   支撑位:" + ResultUtils.getData(theActivity.incomeBean_480_min.get(0).getRate()));
                    }
            } else if (msg.arg1 == 5000) {
                Toast.makeText(theActivity.getContext(), "获取失败", Toast.LENGTH_SHORT).show();
                theActivity.binding.otherDetailButton.setText("已停止更新");
            }
        }
    }

    private static class MyRunnable implements Runnable {

        @Override
        public synchronized void run() {
            Message msg;
            while (!isDestory) {
                msg = Message.obtain();
                msg.arg1 = 0;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getOtherDetailByCode(final String codeName) {
        if(firstOpen){
            updateAllHttp(codeName);
        }else{
            if(!TextUtils.isEmpty(newData)){
                if(str_5.length>0){
                    str_5[str_5.length-1]=newData;
                    if(Double.parseDouble(max_str_5[max_str_5.length-1]) < Double.parseDouble(newData)) {
                        max_str_5[max_str_5.length - 1] = newData;
                    }
                    if(Double.parseDouble(min_str_5[min_str_5.length-1]) > Double.parseDouble(newData)) {
                        min_str_5[min_str_5.length - 1] = newData;
                    }
                    updateM5(str_5,max_str_5,min_str_5);
                }
                if(str_15.length>0){
                    str_15[str_15.length-1]=newData;
                    if(Double.parseDouble(max_str_15[max_str_15.length-1]) < Double.parseDouble(newData)) {
                        max_str_15[max_str_15.length - 1] = newData;
                    }
                    if(Double.parseDouble(min_str_15[min_str_15.length-1]) > Double.parseDouble(newData)) {
                        min_str_15[min_str_15.length - 1] = newData;
                    }
                    min_str_15[min_str_15.length-1]=newData;
                    updateM15(str_15,max_str_15,min_str_15);
                }
                if(str_20.length>0){
                    str_20[str_20.length-1]=newData;
                    if(Double.parseDouble(max_str_20[max_str_20.length-1]) < Double.parseDouble(newData)) {
                        max_str_20[max_str_20.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_20[min_str_20.length-1]) > Double.parseDouble(newData)){
                        min_str_20[min_str_20.length - 1] = newData;
                    }
                    updateM20(str_20,max_str_20,min_str_20);
                }
                if(str_30.length>0){
                    str_30[str_30.length-1]=newData;
                    if(Double.parseDouble(max_str_30[max_str_30.length-1]) < Double.parseDouble(newData)) {
                        max_str_30[max_str_30.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_30[min_str_30.length-1]) > Double.parseDouble(newData)){
                        min_str_30[min_str_30.length - 1] = newData;
                    }
                    updateM30(str_30,max_str_30,min_str_30);
                }
                if(str_40.length>0){
                    str_40[str_40.length-1]=newData;
                    if(Double.parseDouble(max_str_40[max_str_40.length-1]) < Double.parseDouble(newData)) {
                        max_str_40[max_str_40.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_40[min_str_40.length-1]) > Double.parseDouble(newData)){
                        min_str_40[min_str_40.length - 1] = newData;
                    }
                    updateM40(str_40,max_str_40,min_str_40);
                }
                if(str_60.length>0){
                    str_60[str_60.length-1]=newData;
                    if(Double.parseDouble(max_str_60[max_str_60.length-1]) < Double.parseDouble(newData)) {
                        max_str_60[max_str_60.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_60[min_str_60.length-1]) > Double.parseDouble(newData)){
                        min_str_60[min_str_60.length - 1] = newData;
                    }
                    updateM60(str_60,max_str_60,min_str_60);
                }
                if(str_120.length>0){
                    str_120[str_120.length-1]=newData;
                    if(Double.parseDouble(max_str_120[max_str_120.length-1]) < Double.parseDouble(newData)) {
                        max_str_120[max_str_120.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_120[min_str_120.length-1]) > Double.parseDouble(newData)){
                        min_str_120[min_str_120.length - 1] = newData;
                    }
                    updateM120(str_120,max_str_120,min_str_120);
                }
                if(str_240.length>0){
                    str_240[str_240.length-1]=newData;
                    if(Double.parseDouble(max_str_240[max_str_240.length-1]) < Double.parseDouble(newData)) {
                        max_str_240[max_str_240.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_240[min_str_240.length-1]) > Double.parseDouble(newData)){
                        min_str_240[min_str_240.length - 1] = newData;
                    }
                    updateM240(str_240,max_str_240,min_str_240);
                }
                if(str_360.length>0){
                    str_360[str_360.length-1]=newData;
                    if(Double.parseDouble(max_str_360[max_str_360.length-1]) < Double.parseDouble(newData)) {
                        max_str_360[max_str_360.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_360[min_str_360.length-1]) > Double.parseDouble(newData)){
                        min_str_360[min_str_360.length - 1] = newData;
                    }
                    updateM360(str_360,max_str_360,min_str_360);
                }
                if(str_480.length>0){
                    str_480[str_480.length-1]=newData;
                    if(Double.parseDouble(max_str_480[max_str_480.length-1]) < Double.parseDouble(newData)) {
                        max_str_480[max_str_480.length-1]=newData;
                    }
                    if(Double.parseDouble(min_str_480[min_str_480.length-1]) > Double.parseDouble(newData)){
                        min_str_480[min_str_480.length - 1] = newData;
                    }
                    updateM480(str_480,max_str_480,min_str_480);
                }

            }
        }

        Http.getBeanByMM(codeName + ",m5,,2", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "失败" + e.toString());
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                String data = ResultUtils.D5(bean5,2,true);
                if (data.isEmpty()) return;
                String[] str_5 = data.split("#");
                now=Float.parseFloat(str_5[str_5.length - 1]);
                newData=str_5[str_5.length - 1];
                incomeBean_600 = new ArrayList<>();
                IncomeBean bean2;
                for (int i = 0; i < 40; i++) {
                    bean2 = new IncomeBean("String120", Double.parseDouble(str_5[str_5.length - 1]));
                    incomeBean_600.add(bean2);
                }

                if(Integer.parseInt(ResultUtils.getD5Time(bean5))%5==0){
                    if(!ResultUtils.getD5Time(bean5).equals(time)){

                        updateAllHttp(codeName);

                    }
                }

            }
        });

        Http.getDetails(codeName, new LogCallBack() {//获取买方卖方、内外盘、换手率等
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "错误" + e.toString());
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                getOtherDetail(result);
            }
        });

        Http.getDetails("sh000001", new LogCallBack() {//获取买方卖方、内外盘、换手率等
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "错误" + e.toString());
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                getOtherDetail2(result);
            }
        });

        Http.getDetails("sz399001", new LogCallBack() {//获取买方卖方、内外盘、换手率等
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "错误" + e.toString());
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                getOtherDetail3(result);
            }
        });
    }

    private void setOtherData(String[] str_15, List<CompositeIndexBean> incomeBean_3_5, List<CompositeIndexBean> incomeBean_4_5, List<CompositeIndexBean> incomeBean_6_5) {
        String[] newString_15_3 = ResultUtils.getString_15_number(str_15, Integer.parseInt(string3), Integer.parseInt(string6)).split("->");
        String[] newString_15_4 = ResultUtils.getString_15_number(str_15, Integer.parseInt(string4), Integer.parseInt(string6)).split("->");
        String[] newString_15_6 = ResultUtils.getString_15_number(str_15, Integer.parseInt(string6), Integer.parseInt(string6)).split("->");

        for (int i = newString_15_3.length - 40; i < newString_15_3.length; i++) {
            incomeBean_3_5.add(new CompositeIndexBean(string3, Double.parseDouble(newString_15_3[i])));
        }

        for (int i = newString_15_4.length - 40; i < newString_15_4.length; i++) {
            incomeBean_4_5.add(new CompositeIndexBean(string4, Double.parseDouble(newString_15_4[i])));
        }

        for (int i = newString_15_6.length - 40; i < newString_15_6.length; i++) {
            incomeBean_6_5.add(new CompositeIndexBean(string6, Double.parseDouble(newString_15_6[i])));
        }

    }

    private void sendMessage(String[] str_15, String[] newString_15_600, int arg1) {
        if (Double.valueOf(str_15[str_15.length - 1]) >= Double.valueOf(newString_15_600[newString_15_600.length - 1])) {
            int number = 0;
            for (int i = 0; i < newString_15_600.length - 1; i++) {
                if (Double.valueOf(str_15[str_15.length - 1 - i]) >= Double.valueOf(newString_15_600[newString_15_600.length - 1 - i])) {
                    number++;
                } else {
                    break;
                }
            }

            Message msg = Message.obtain();
            msg.arg1 = arg1;
            msg.arg2 = 1;
            msg.obj = "[" + newString_15_600[newString_15_600.length - 1] + "]\n上升" + number + "次\n涨:" + ResultUtils.getData(Double.valueOf(newString_15_600[newString_15_600.length - 1]) - Double.valueOf(newString_15_600[newString_15_600.length - 1 - number])) + "\n\n预测下个\n拐:" + str_15[str_15.length - 5]
                    + "\n下:" + ResultUtils.getDataNew((Double.valueOf(str_15[str_15.length - 1]) + Double.valueOf(str_15[str_15.length - 2]) + Double.valueOf(str_15[str_15.length - 3]) + Double.valueOf(str_15[str_15.length - 4]) + Double.valueOf(str_15[str_15.length - 1])) / 5);
            handler.sendMessage(msg);


        } else {
            int number = 0;
            for (int i = 0; i < newString_15_600.length - 1; i++) {
                if (Double.valueOf(str_15[str_15.length - 1 - i]) < Double.valueOf(newString_15_600[newString_15_600.length - 1 - i])) {
                    number++;
                } else {
                    break;
                }
            }
            Message msg = Message.obtain();
            msg.arg1 = arg1;
            msg.arg2 = 2;
            msg.obj = "[" + newString_15_600[newString_15_600.length - 1] + "]\n下降" + number + "次\n跌:" + ResultUtils.getData(Double.valueOf(newString_15_600[newString_15_600.length - 1]) - Double.valueOf(newString_15_600[newString_15_600.length - 1 - number])) + "\n\n预测下个\n拐:" + str_15[str_15.length - 5]
                    + "\n下:" + ResultUtils.getDataNew((Double.valueOf(str_15[str_15.length - 1]) + Double.valueOf(str_15[str_15.length - 2]) + Double.valueOf(str_15[str_15.length - 3]) + Double.valueOf(str_15[str_15.length - 4]) + Double.valueOf(str_15[str_15.length - 1])) / 5);
            handler.sendMessage(msg);
        }
    }

    private void showLineChart3(LineChartManager lineChartManager_360, List<IncomeBean> incomeBean360, List<CompositeIndexBean> incomeBean_3_360, List<CompositeIndexBean> incomeBean_4_360, List<CompositeIndexBean> incomeBean_6_360) {
        //展示图表
        lineChartManager_360.showLineChart3(incomeBean360, "String120", getResources().getColor(R.color.orange));
        lineChartManager_360.addLine(incomeBean_3_360, string3, getResources().getColor(R.color.back));
        lineChartManager_360.addLine(incomeBean_4_360, string4, getResources().getColor(R.color.red));
        lineChartManager_360.addLine(incomeBean_6_360, string6, getResources().getColor(R.color.blue));

        //设置曲线填充色 以及 MarkerView
        lineChartManager_360.setChartFillDrawable(getResources().getDrawable(R.drawable.fade_orange));
    }

    //TODO
    private void showLineChart3(LineChartManager lineChartManager_360, List<IncomeBean> incomeBean360, List<CompositeIndexBean> incomeBean_3_360, List<CompositeIndexBean> incomeBean_4_360, List<CompositeIndexBean> incomeBean_6_360,List<CompositeIndexBean> incomeBean_5_max,List<CompositeIndexBean> incomeBean_5_min) {
        //展示图表
        lineChartManager_360.showLineChart3(incomeBean360, "String120", getResources().getColor(R.color.orange));
        lineChartManager_360.addLine(incomeBean_3_360, string3, getResources().getColor(R.color.back));
        lineChartManager_360.addLine(incomeBean_4_360, string4, getResources().getColor(R.color.red));
        lineChartManager_360.addLine(incomeBean_6_360, string6, getResources().getColor(R.color.blue));

        lineChartManager_360.addLine(incomeBean_5_max, "string_max", getResources().getColor(R.color.colorPrimary));
        lineChartManager_360.addLine(incomeBean_5_min, "string_min", getResources().getColor(R.color.lv));

        //设置曲线填充色 以及 MarkerView
        lineChartManager_360.setChartFillDrawable(getResources().getDrawable(R.drawable.fade_orange));
    }

    private void getOtherDetail(String result) {
        if (!result.contains("~")) {
            Message msg = Message.obtain();
            msg.arg1 = 5000;
            handler.sendMessage(msg);
            return;
        }
        String[] details = result.split("~");

        Message msg = Message.obtain();
        msg.arg1 = 1;
        msg.obj = details[35].split("/")[0] + "    [ " + details[31] + "   " + details[32] + "% ]";
        handler.sendMessage(msg);

        if (max == 0 && min == 0) {
            if (max_5 != 0 && max_15 != 0 && max_30 != 0 && min_5 != 0 && min_15 != 0 && min_30 != 0) {
                float[] arr = new float[]{max_5, max_15, max_30, min_5, min_15, min_30};
                Arrays.sort(arr);
                max = arr[arr.length - 1];
                min = arr[0];

                YAxis yAxis=binding.lineChart600.getAxisLeft();
                yAxis.setAxisMinimum(min);
                yAxis.setAxisMaximum(max);

                dismissLoadingDialog();

            }
        }else{
            if(now > max){
                YAxis yAxis=binding.lineChart600.getAxisLeft();
                max =max+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min);
                yAxis.setAxisMaximum(max);
            }
            if(now < min){
                YAxis yAxis=binding.lineChart600.getAxisLeft();
                min =min-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min);
                yAxis.setAxisMaximum(max);
            }
        }
//        if (max == 0 && min == 0) {
//            max = Float.parseFloat(details[47]);
//            min = Float.parseFloat(details[48]);
//            YAxis yAxis = binding.lineChart600.getAxisLeft();
//            yAxis.setAxisMinimum(min);
//            yAxis.setAxisMaximum(max);
//        }

        if (max_5 != 0 && max_15 != 0 && max_30 != 0 && min_5 != 0 && min_15 != 0 && min_30 != 0) {
            dismissLoadingDialog();
        }

        ((MainActivity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.tv1.setText("卖一: " + details[19] + "  " + details[20] + "手");
                binding.tv2.setText(String.valueOf(details[35].split("/")[0]));
                binding.tv3.setText("买一: " + details[9] + "  " + details[10] + "手");
            }
        });

        EventBus.getDefault().postSticky(BaseEvent.getInstance(details));

//        if(hashMap==null){
//            int max = (int)(Double.parseDouble(details[47])*100);
//            int min = (int)(Double.parseDouble(details[48])*100);
//
//            hashMap=new LinkedHashMap<>(max-min);
//            for (int i = 0; i <= max-min; i++) {
//                hashMap.put(ResultUtils.getData((max-i)/100.0),0);
//            }
//        }
    }

    private void getOtherDetail2(String result) {
        if (!result.contains("~")) {
            Message msg = Message.obtain();
            msg.arg1 = 5000;
            handler.sendMessage(msg);
            return;
        }
        String[] details = result.split("~");

        EventBus.getDefault().postSticky(FirstBean.getInstance(0, "上证:" + details[35].split("/")[0] + "  [ " + details[31] + "   " + details[32] + "% ]"));
    }

    private void getOtherDetail3(String result) {
        if (!result.contains("~")) {
            Message msg = Message.obtain();
            msg.arg1 = 5000;
            handler.sendMessage(msg);
            return;
        }
        String[] details = result.split("~");

        EventBus.getDefault().postSticky(FirstBean.getInstance(1, "深证:" + details[35].split("/")[0] + "  [ " + details[31] + "   " + details[32] + "% ]"));
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

//        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bmp);


        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);

        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }


    private void get20(JSONObject result){
        AllBean5 bean5 = JSON.parseObject(result.toJSONString(), AllBean5.class);
        if (bean5.getM5() == null || bean5.getM5().size() == 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        StringBuilder builderMax = new StringBuilder();
        StringBuilder builderMin = new StringBuilder();
        int max = bean5.getM5().size();
        for (int i = 0; i < max; i++) {
            if(bean5.getM5().get(i).get(0).substring(10).equals("00") || bean5.getM5().get(i).get(0).substring(10).equals("20")
                    ||bean5.getM5().get(i).get(0).substring(10).equals("40") ||bean5.getM5().get(i).get(0).substring(10).equals("60")){
                builder.append(bean5.getM5().get(i).get(2)).append("#");
                builderMax.append(bean5.getM5().get(i).get(3)).append("#");
                builderMin.append(bean5.getM5().get(i).get(4)).append("#");
            }
        }

        if(!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(10).equals("00") || !bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(10).equals("20")
                ||!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(10).equals("40") ||!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(10).equals("60")){
            builder.append(bean5.getM5().get(bean5.getM5().size()-1).get(2)).append("#");
            builderMax.append(bean5.getM5().get(bean5.getM5().size()-1).get(3)).append("#");
            builderMin.append(bean5.getM5().get(bean5.getM5().size()-1).get(4)).append("#");
        }

        String data=(builder.toString().substring(0, builder.toString().length() - 1));

        if (data.isEmpty()) return;
        str_20 = data.split("#");
        max_str_20 = (builderMax.toString().substring(0, builderMax.toString().length() - 1)).split("#");
        min_str_20 = (builderMin.toString().substring(0, builderMin.toString().length() - 1)).split("#");

        updateM20(str_20,max_str_20,min_str_20);
    }

    private void get40(JSONObject result){
        AllBean5 bean5 = JSON.parseObject(result.toJSONString(), AllBean5.class);
        if (bean5.getM5() == null || bean5.getM5().size() == 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        StringBuilder builderMax = new StringBuilder();
        StringBuilder builderMin = new StringBuilder();
        int max = bean5.getM5().size();
        for (int i = 0; i < max; i++) {
            if(bean5.getM5().get(i).get(0).substring(8).equals("1010") || bean5.getM5().get(i).get(0).substring(8).equals("1050")
                    ||bean5.getM5().get(i).get(0).substring(8).equals("1130") ||bean5.getM5().get(i).get(0).substring(8).equals("1340")
                    ||bean5.getM5().get(i).get(0).substring(8).equals("1420") ||bean5.getM5().get(i).get(0).substring(8).equals("1500")){
                builder.append(bean5.getM5().get(i).get(2)).append("#");
                builderMax.append(bean5.getM5().get(i).get(3)).append("#");
                builderMin.append(bean5.getM5().get(i).get(4)).append("#");
            }
        }

        if(!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(8).equals("1010") || !bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(8).equals("1050")
                ||!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(8).equals("1130") ||!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(8).equals("1340")
                ||!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(8).equals("1420") ||!bean5.getM5().get(bean5.getM5().size()-1).get(0).substring(8).equals("1500")){
            builder.append(bean5.getM5().get(bean5.getM5().size()-1).get(2)).append("#");
            builderMax.append(bean5.getM5().get(bean5.getM5().size()-1).get(3)).append("#");
            builderMin.append(bean5.getM5().get(bean5.getM5().size()-1).get(4)).append("#");
        }

        String data=(builder.toString().substring(0, builder.toString().length() - 1));

        if (data.isEmpty()) return;
        str_40 = data.split("#");
        max_str_40 = (builderMax.toString().substring(0, builderMax.toString().length() - 1)).split("#");
        min_str_40 = (builderMin.toString().substring(0, builderMin.toString().length() - 1)).split("#");
        updateM40(str_40,max_str_40,min_str_40);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestory = true;
    }
    //=============================================================================================================================
    private void updateM5(String[] str_5,String[] max_str_5,String[] min_str_5){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_5, 5, 5).split("->");
        incomeBean_5 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string5", Double.parseDouble(newString_15_600[i]));
            incomeBean_5.add(bean);
        }

        incomeBean_5_max.clear();
        String[] str_max_5 = ResultUtils.getString_15_number(max_str_5, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_5;
        for (int i = 0; i < 40; i++) {
            bean_max_5 = new CompositeIndexBean("String120", Double.parseDouble(str_max_5[str_max_5.length - 1]));
            incomeBean_5_max.add(bean_max_5);
        }

        incomeBean_5_min.clear();
        String[] str_min_5 = ResultUtils.getString_15_number(min_str_5, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_5;
        for (int i = 0; i < 40; i++) {
            bean_min_5 = new CompositeIndexBean("String120", Double.parseDouble(str_min_5[str_min_5.length - 1]));
            incomeBean_5_min.add(bean_min_5);
        }


        if (min_5 == 0 && max_5 == 0) {
            min_5 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_5, str_5.length) - size));
            max_5 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_5, str_5.length) + size));
            YAxis yAxis=binding.lineChart5.getAxisLeft();
            yAxis.setAxisMinimum(min_5);
            yAxis.setAxisMaximum(max_5);

        }else{
            if(now > max_5){
                YAxis yAxis=binding.lineChart5.getAxisLeft();
                max_5=max_5+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min_5);
                yAxis.setAxisMaximum(max_5);

            }
            if(now < min_5){
                YAxis yAxis=binding.lineChart5.getAxisLeft();
                min_5=min_5-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min_5);
                yAxis.setAxisMaximum(max_5);

            }
        }
        incomeBean_3_5.clear();
        incomeBean_4_5.clear();
        incomeBean_6_5.clear();
        setOtherData(str_5, incomeBean_3_5, incomeBean_4_5, incomeBean_6_5);
        sendMessage(str_5, newString_15_600, 5);
    }

    private void updateM15(String[] str_15,String[] max_str_15,String[] min_str_15){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_15, 5, 5).split("->");
        incomeBean_15 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string15", Double.parseDouble(newString_15_600[i]));
            incomeBean_15.add(bean);

        }

        incomeBean_15_max.clear();
        String[] str_max_15 = ResultUtils.getString_15_number(max_str_15, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_15;
        for (int i = 0; i < 40; i++) {
            bean_max_15 = new CompositeIndexBean("String120", Double.parseDouble(str_max_15[str_max_15.length - 1]));
            incomeBean_15_max.add(bean_max_15);
        }

        incomeBean_15_min.clear();
        String[] str_min_15 = ResultUtils.getString_15_number(min_str_15, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_15;
        for (int i = 0; i < 40; i++) {
            bean_min_15 = new CompositeIndexBean("String120", Double.parseDouble(str_min_15[str_min_15.length - 1]));
            incomeBean_15_min.add(bean_min_15);
        }

        if (min_15 == 0 && max_15 == 0) {
            min_15 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_15, str_15.length) - size));
            max_15 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_15, str_15.length) + size));
            YAxis yAxis=binding.lineChart15.getAxisLeft();
            yAxis.setAxisMinimum(min_15);
            yAxis.setAxisMaximum(max_15);
        }else{
            if(now > max_15){
                YAxis yAxis=binding.lineChart15.getAxisLeft();
                max_15=max_15+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min_15);
                yAxis.setAxisMaximum(max_15);

            }
            if(now < min_15){
                YAxis yAxis=binding.lineChart15.getAxisLeft();
                min_15=min_15-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min_15);
                yAxis.setAxisMaximum(max_15);

            }
        }
        incomeBean_3_15.clear();
        incomeBean_4_15.clear();
        incomeBean_6_15.clear();
        setOtherData(str_15, incomeBean_3_15, incomeBean_4_15, incomeBean_6_15);
        sendMessage(str_15, newString_15_600, 15);
    }

    private void updateM20(String[] str_20,String[] max_str_20,String[] min_str_20){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_20, 5, 5).split("->");
        incomeBean_20 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string20", Double.parseDouble(newString_15_600[i]));
            incomeBean_20.add(bean);
        }
        incomeBean_20_max.clear();
        String[] str_max_20 = ResultUtils.getString_15_number(max_str_20, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_20;
        for (int i = 0; i < 40; i++) {
            bean_max_20 = new CompositeIndexBean("String120", Double.parseDouble(str_max_20[str_max_20.length - 1]));
            incomeBean_20_max.add(bean_max_20);
        }

        incomeBean_20_min.clear();
        String[] str_min_20 = ResultUtils.getString_15_number(min_str_20, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_20;
        for (int i = 0; i < 40; i++) {
            bean_min_20 = new CompositeIndexBean("String120", Double.parseDouble(str_min_20[str_min_20.length - 1]));
            incomeBean_20_min.add(bean_min_20);
        }

        if (min_20 == 0 && max_20 == 0) {
            min_20 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_20, str_20.length) - size));
            max_20 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_20, str_20.length) + size));

            YAxis yAxis=binding.lineChart20.getAxisLeft();
            yAxis.setAxisMinimum(min_20);
            yAxis.setAxisMaximum(max_20);
        }else{


            if(now > max_20){
                YAxis yAxis=binding.lineChart20.getAxisLeft();
                max_20 =max_20+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min_20);
                yAxis.setAxisMaximum(max_20);
            }
            if(now < min_20){
                YAxis yAxis=binding.lineChart20.getAxisLeft();
                min_20=(min_20-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_20);
                yAxis.setAxisMaximum(max_20);
            }
        }
        incomeBean_3_20.clear();
        incomeBean_4_20.clear();
        incomeBean_6_20.clear();
        setOtherData(str_20, incomeBean_3_20, incomeBean_4_20, incomeBean_6_20);
        sendMessage(str_20, newString_15_600, 20);
    }

    private void updateM30(String[] str_30,String[] max_str_30,String[] min_str_30){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_30, 5, 5).split("->");
        incomeBean_30 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string30", Double.parseDouble(newString_15_600[i]));
            incomeBean_30.add(bean);
        }
        incomeBean_30_max.clear();
        String[] str_max_30 = ResultUtils.getString_15_number(max_str_30, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_30;
        for (int i = 0; i < 40; i++) {
            bean_max_30 = new CompositeIndexBean("String120", Double.parseDouble(str_max_30[str_max_30.length - 1]));
            incomeBean_30_max.add(bean_max_30);
        }

        incomeBean_30_min.clear();
        String[] str_min_30 = ResultUtils.getString_15_number(min_str_30, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_30;
        for (int i = 0; i < 40; i++) {
            bean_min_30 = new CompositeIndexBean("String120", Double.parseDouble(str_min_30[str_min_30.length - 1]));
            incomeBean_30_min.add(bean_min_30);
        }

        if (min_30 == 0 && max_30 == 0) {
            min_30 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_30, str_30.length) - size));
            max_30 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_30, str_30.length) + size));
            YAxis yAxis=binding.lineChart30.getAxisLeft();
            yAxis.setAxisMinimum(min_30);
            yAxis.setAxisMaximum(max_30);
        }else{
            if(now > max_30){
                YAxis yAxis=binding.lineChart30.getAxisLeft();
                max_30=max_30+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 );
                yAxis.setAxisMinimum(min_30);
                yAxis.setAxisMaximum(max_30);
            }
            if(now < min_30){
                YAxis yAxis=binding.lineChart30.getAxisLeft();
                min_30=(min_30-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_30);
                yAxis.setAxisMaximum(max_30);
            }
        }
        incomeBean_3_30.clear();
        incomeBean_4_30.clear();
        incomeBean_6_30.clear();
        setOtherData(str_30, incomeBean_3_30, incomeBean_4_30, incomeBean_6_30);
        sendMessage(str_30, newString_15_600, 30);
    }

    private void updateM40(String[] str_40,String[] max_str_40,String[] min_str_40){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_40, 5, 5).split("->");
        incomeBean_40 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string40", Double.parseDouble(newString_15_600[i]));
            incomeBean_40.add(bean);
        }
        incomeBean_40_max.clear();
        String[] str_max_40 = ResultUtils.getString_15_number(max_str_40, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_40;
        for (int i = 0; i < 40; i++) {
            bean_max_40 = new CompositeIndexBean("String120", Double.parseDouble(str_max_40[str_max_40.length - 1]));
            incomeBean_40_max.add(bean_max_40);
        }

        incomeBean_40_min.clear();
        String[] str_min_40 = ResultUtils.getString_15_number(min_str_40, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_40;
        for (int i = 0; i < 40; i++) {
            bean_min_40 = new CompositeIndexBean("String120", Double.parseDouble(str_min_40[str_min_40.length - 1]));
            incomeBean_40_min.add(bean_min_40);
        }
        if (min_40 == 0 && max_40 == 0) {
            min_40 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_40, str_40.length) - size));
            max_40 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_40, str_40.length) + size));
            YAxis yAxis=binding.lineChart40.getAxisLeft();
            yAxis.setAxisMinimum(min_40);
            yAxis.setAxisMaximum(max_40);
        }else{
            if(now > max_40){
                YAxis yAxis=binding.lineChart40.getAxisLeft();
                max_40=max_40+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8);
                yAxis.setAxisMinimum(min_40);
                yAxis.setAxisMaximum(max_40);
            }
            if(now < min_40){
                YAxis yAxis=binding.lineChart40.getAxisLeft();
                min_40=(min_40-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_40);
                yAxis.setAxisMaximum(max_40);

            }
        }
        incomeBean_3_40.clear();
        incomeBean_4_40.clear();
        incomeBean_6_40.clear();
        setOtherData(str_40, incomeBean_3_40, incomeBean_4_40, incomeBean_6_40);
        sendMessage(str_40, newString_15_600, 40);
    }

    private void updateM60(String[] str_60,String[] max_str_60,String[] min_str_60){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_60, 5, 5).split("->");
        incomeBean_60 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string60", Double.parseDouble(newString_15_600[i]));
            incomeBean_60.add(bean);
        }
        incomeBean_60_max.clear();
        String[] str_max_60 = ResultUtils.getString_15_number(max_str_60, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_60;
        for (int i = 0; i < 40; i++) {
            bean_max_60 = new CompositeIndexBean("String120", Double.parseDouble(str_max_60[str_max_60.length - 1]));
            incomeBean_60_max.add(bean_max_60);
        }

        incomeBean_60_min.clear();
        String[] str_min_60 = ResultUtils.getString_15_number(min_str_60, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_60;
        for (int i = 0; i < 40; i++) {
            bean_min_60 = new CompositeIndexBean("String120", Double.parseDouble(str_min_60[str_min_60.length - 1]));
            incomeBean_60_min.add(bean_min_60);
        }
        if (min_60 == 0 && max_60 == 0) {
            min_60 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_60, str_60.length) - size));
            max_60 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_60, str_60.length) + size));
            YAxis yAxis=binding.lineChart60.getAxisLeft();
            yAxis.setAxisMinimum(min_60);
            yAxis.setAxisMaximum(max_60);
        }else{
            if(now > max_60){
                YAxis yAxis=binding.lineChart60.getAxisLeft();
                max_60=max_60+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8);
                yAxis.setAxisMinimum(min_60);
                yAxis.setAxisMaximum(max_60);
            }
            if(now < min_60){
                YAxis yAxis=binding.lineChart60.getAxisLeft();
                min_60=(min_60-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_60);
                yAxis.setAxisMaximum(max_60);
            }
        }
        incomeBean_3_60.clear();
        incomeBean_4_60.clear();
        incomeBean_6_60.clear();
        setOtherData(str_60, incomeBean_3_60, incomeBean_4_60, incomeBean_6_60);
        sendMessage(str_60, newString_15_600, 60);
    }

    private void updateM120(String[] str_120,String[] max_str_120,String[] min_str_120){
        String[] newString_120 = ResultUtils.getString_15_number(str_120, 5, 5).split("->");
        incomeBean_120 = new ArrayList<>();
        CompositeIndexBean bean120;
        for (int i = newString_120.length - 40; i < newString_120.length; i++) {
            bean120 = new CompositeIndexBean("string120", Double.parseDouble(newString_120[i]));
            incomeBean_120.add(bean120);
        }
        incomeBean_120_max.clear();
        String[] str_max_120 = ResultUtils.getString_15_number(max_str_120, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_120;
        for (int i = 0; i < 40; i++) {
            bean_max_120 = new CompositeIndexBean("String120", Double.parseDouble(str_max_120[str_max_120.length - 1]));
            incomeBean_120_max.add(bean_max_120);
        }

        incomeBean_120_min.clear();
        String[] str_min_120 = ResultUtils.getString_15_number(min_str_120, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_120;
        for (int i = 0; i < 40; i++) {
            bean_min_120 = new CompositeIndexBean("String120", Double.parseDouble(str_min_120[str_min_120.length - 1]));
            incomeBean_120_min.add(bean_min_120);
        }
        if (min_120 == 0 && max_120 == 0) {
            min_120 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_120, str_120.length) - size));
            max_120 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_120, str_120.length) + size));
            YAxis yAxis=binding.lineChart120.getAxisLeft();
            yAxis.setAxisMinimum(min_120);
            yAxis.setAxisMaximum(max_120);
        }else{
            if(now > max_120){
                YAxis yAxis=binding.lineChart120.getAxisLeft();
                max_120=max_120+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8);
                yAxis.setAxisMinimum(min_120);
                yAxis.setAxisMaximum(max_120);
            }
            if(now < min_120){
                YAxis yAxis=binding.lineChart120.getAxisLeft();
                min_120=(min_120-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_120);
                yAxis.setAxisMaximum(max_120);
            }
        }
        incomeBean_3_120.clear();
        incomeBean_4_120.clear();
        incomeBean_6_120.clear();
        setOtherData(str_120, incomeBean_3_120, incomeBean_4_120, incomeBean_6_120);
        sendMessage(str_120, newString_120, 120);
    }

    private void updateM240(String[] str_240,String[] max_str_240,String[] min_str_240){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_240, 5, 5).split("->");
        incomeBean_240 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string30", Double.parseDouble(newString_15_600[i]));
            incomeBean_240.add(bean);
        }

        incomeBean_240_max.clear();
        String[] str_max_240 = ResultUtils.getString_15_number(max_str_240, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_240;
        for (int i = 0; i < 40; i++) {
            bean_max_240 = new CompositeIndexBean("String120", Double.parseDouble(str_max_240[str_max_240.length - 1]));
            incomeBean_240_max.add(bean_max_240);
        }

        incomeBean_240_min.clear();
        String[] str_min_240 = ResultUtils.getString_15_number(min_str_240, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_240;
        for (int i = 0; i < 40; i++) {
            bean_min_240 = new CompositeIndexBean("String120", Double.parseDouble(str_min_240[str_min_240.length - 1]));
            incomeBean_240_min.add(bean_min_240);
        }

        if (min_240 == 0 && max_240 == 0) {
            min_240 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_240, str_240.length) - size));
            max_240 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_240, str_240.length) + size));
            YAxis yAxis=binding.lineChart240.getAxisLeft();
            yAxis.setAxisMinimum(min_240);
            yAxis.setAxisMaximum(max_240);
        }else{
            if(now > max_240){
                YAxis yAxis=binding.lineChart240.getAxisLeft();
                max_240=max_240+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8);
                yAxis.setAxisMinimum(min_240);
                yAxis.setAxisMaximum(max_240);
            }
            if(now < min_240){
                YAxis yAxis=binding.lineChart240.getAxisLeft();
                min_240=(min_240-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_240);
                yAxis.setAxisMaximum(max_240);
            }
        }
        incomeBean_3_240.clear();
        incomeBean_4_240.clear();
        incomeBean_6_240.clear();
        setOtherData(str_240, incomeBean_3_240, incomeBean_4_240, incomeBean_6_240);
        sendMessage(str_240, newString_15_600, 240);

    }

    private void updateM360(String[] str_360,String[] max_str_360,String[] min_str_360){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_360, 5, 5).split("->");
        incomeBean_360 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string240", Double.parseDouble(newString_15_600[i]));
            incomeBean_360.add(bean);
        }

        incomeBean_360_max.clear();
        String[] str_max_360 = ResultUtils.getString_15_number(max_str_360, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_360;
        for (int i = 0; i < 40; i++) {
            bean_max_360 = new CompositeIndexBean("String120", Double.parseDouble(str_max_360[str_max_360.length - 1]));
            incomeBean_360_max.add(bean_max_360);
        }

        incomeBean_360_min.clear();
        String[] str_min_360 = ResultUtils.getString_15_number(min_str_360, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_360;
        for (int i = 0; i < 40; i++) {
            bean_min_360 = new CompositeIndexBean("String120", Double.parseDouble(str_min_360[str_min_360.length - 1]));
            incomeBean_360_min.add(bean_min_360);
        }

        if (min_360 == 0 && max_360 == 0) {
            min_360 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_360, str_360.length) - size));
            max_360 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_360, str_360.length) + size));
            YAxis yAxis=binding.lineChart360.getAxisLeft();
            yAxis.setAxisMinimum(min_360);
            yAxis.setAxisMaximum(max_360);
        }else{
            if(now > max_360){
                YAxis yAxis=binding.lineChart360.getAxisLeft();
                max_360=max_360+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8);
                yAxis.setAxisMinimum(min_360);
                yAxis.setAxisMaximum(max_360);
            }
            if(now < min_360){
                YAxis yAxis=binding.lineChart360.getAxisLeft();
                min_360=(min_360-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_360);
                yAxis.setAxisMaximum(max_360);
            }
        }
        incomeBean_3_360.clear();
        incomeBean_4_360.clear();
        incomeBean_6_360.clear();
        setOtherData(str_360, incomeBean_3_360, incomeBean_4_360, incomeBean_6_360);
        sendMessage(str_360, newString_15_600, 360);
    }

    private void updateM480(String[] str_480,String[] max_str_480,String[] min_str_480){
        String[] newString_15_600 = ResultUtils.getString_15_number(str_480, 5, 5).split("->");
        incomeBean_480 = new ArrayList<>();
        CompositeIndexBean bean;
        for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
            bean = new CompositeIndexBean("string360", Double.parseDouble(newString_15_600[i]));
            incomeBean_480.add(bean);
        }
        incomeBean_480_max.clear();
        String[] str_max_480 = ResultUtils.getString_15_number(max_str_480, otherTime, otherTime).split("->");
        CompositeIndexBean bean_max_480;
        for (int i = 0; i < 40; i++) {
            bean_max_480 = new CompositeIndexBean("String120", Double.parseDouble(str_max_480[str_max_480.length - 1]));
            incomeBean_480_max.add(bean_max_480);
        }

        incomeBean_480_min.clear();
        String[] str_min_480 = ResultUtils.getString_15_number(min_str_480, otherTime, otherTime).split("->");
        CompositeIndexBean bean_min_480;
        for (int i = 0; i < 40; i++) {
            bean_min_480 = new CompositeIndexBean("String120", Double.parseDouble(str_min_480[str_min_480.length - 1]));
            incomeBean_480_min.add(bean_min_480);
        }

        if (min_480 == 0 && max_480 == 0) {
            min_480 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_480, str_480.length) - size));
            max_480 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_480, str_480.length) + size));
            YAxis yAxis=binding.lineChart480.getAxisLeft();
            yAxis.setAxisMinimum(min_480);
            yAxis.setAxisMaximum(max_480);


        }else{
            if(now > max_480){
                YAxis yAxis=binding.lineChart480.getAxisLeft();
                max_480=max_480+((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8);
                yAxis.setAxisMinimum(min_480);
                yAxis.setAxisMaximum(max_480);
            }
            if(now < min_480){
                YAxis yAxis=binding.lineChart480.getAxisLeft();
                min_480=(min_480-((yAxis.getAxisMaximum() - yAxis.getAxisMinimum())/8 ));
                yAxis.setAxisMinimum(min_480);
                yAxis.setAxisMaximum(max_480);
            }
        }
        incomeBean_3_480.clear();
        incomeBean_4_480.clear();
        incomeBean_6_480.clear();
        setOtherData(str_480, incomeBean_3_480, incomeBean_4_480, incomeBean_6_480);
        sendMessage(str_480, newString_15_600, 480);
    }

    private void updateAllHttp(final String codeName){
        Http.getBeanByMM(codeName + ",m5,," + (320 + Integer.parseInt(string6)*9), new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "失败" + e.toString());
            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                String data = ResultUtils.newD5(bean5,2);
                if (data.isEmpty()) return;
                str_5 = data.split("#");

                max_str_5 = ResultUtils.newD5(bean5,3).split("#");
                min_str_5=ResultUtils.newD5(bean5,4).split("#");

                time = ResultUtils.getD5Time(bean5);
                updateM5(str_5,max_str_5,min_str_5);

                get20(a);
                get40(a);

            }
        });

        Http.getBeanByMM(codeName + ",m15,," + (50 + Integer.parseInt(string6)), new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean15 bean15 = JSON.parseObject(a.toJSONString(), AllBean15.class);
                String data = ResultUtils.newD15(bean15,2);
                if (data.isEmpty()) return;
                str_15 = data.split("#");
                max_str_15 = ResultUtils.newD15(bean15,3).split("#");
                min_str_15=ResultUtils.newD15(bean15,4).split("#");
                updateM15(str_15,max_str_15,min_str_15);
            }
        });

        Http.getBeanByMM(codeName + ",m30,," + (50 + Integer.parseInt(string6)), new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean30 bean30 = JSON.parseObject(a.toJSONString(), AllBean30.class);
                String data = ResultUtils.newD30(bean30,2);
                if (data.isEmpty()) return;
                str_30 = data.split("#");
                max_str_30 = ResultUtils.newD30(bean30,3).split("#");
                min_str_30=ResultUtils.newD30(bean30,4).split("#");
                updateM30(str_30,max_str_30,min_str_30);

            }
        });

        Http.getBeanByMM(codeName + ",m60,," + (100 + Integer.parseInt(string6)), new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBean60 bean60 = JSON.parseObject(a.toJSONString(), AllBean60.class);
                String data = ResultUtils.newD60(bean60,2);
                if (data.isEmpty()) return;
                str_60 = data.split("#");
                max_str_60 = ResultUtils.newD60(bean60,3).split("#");
                min_str_60=ResultUtils.newD60(bean60,4).split("#");
                updateM60(str_60,max_str_60,min_str_60);


                String data120 = ResultUtils.newD120(bean60,2);
                if (data120.isEmpty()) return;
                str_120 = data120.split("#");
                max_str_120 = ResultUtils.newD120(bean60,3).split("#");
                min_str_120=ResultUtils.newD120(bean60,4).split("#");
                updateM120(str_120,max_str_120,min_str_120);
            }
        });

        if(min_240 ==0 && max_240 ==0) {
            Http.getBeanByDay(codeName + ",day,,," + (50 + Integer.parseInt(string6)) + ",qfq", new LogCallBack() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, int code, final String result) {

                    JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                    AllBeanDay beanDay = JSON.parseObject(a.toJSONString(), AllBeanDay.class);
                    String data = ResultUtils.newD240(beanDay, 2);
                    if (data.isEmpty()) {
                        binding.otherDetailButton.setText("已停止更新");
                        isDestory = true;
                        Message msg = Message.obtain();
                        msg.arg1 = 5000;
                        handler.sendMessage(msg);
                        return;
                    }
                    str_240 = data.split("#");
                    max_str_240 = ResultUtils.newD240(beanDay, 3).split("#");
                    min_str_240 = ResultUtils.newD240(beanDay, 4).split("#");
                    updateM240(str_240, max_str_240, min_str_240);
                }
            });

        }

        if(min_360 ==0 && max_360 ==0) {
            Http.getBeanByWeek(codeName + ",week,,," + (50 + Integer.parseInt(string6)) + ",qfq", new LogCallBack() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, int code, final String result) {

                    JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                    AllBeanWeek beanWeek = JSON.parseObject(a.toJSONString(), AllBeanWeek.class);
                    String data = ResultUtils.newD360(beanWeek, 2);
                    if (data.isEmpty()) return;
                    str_360 = data.split("#");
                    max_str_360 = ResultUtils.newD360(beanWeek, 3).split("#");
                    min_str_360 = ResultUtils.newD360(beanWeek, 4).split("#");
                    updateM360(str_360, max_str_360, min_str_360);


                }
            });
        }
        if(min_480 ==0 && max_480 ==0) {
            Http.getBeanByMonth(codeName + ",month,,," + (50 + Integer.parseInt(string6)) + ",qfq", new LogCallBack() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, int code, final String result) {
                    JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                    AllBeanMonth beanMonth = JSON.parseObject(a.toJSONString(), AllBeanMonth.class);
                    String data = ResultUtils.newD480(beanMonth, 2);
                    if (data.isEmpty()) return;
                    str_480 = data.split("#");
                    max_str_480 = ResultUtils.newD480(beanMonth, 3).split("#");
                    min_str_480 = ResultUtils.newD480(beanMonth, 4).split("#");
                    updateM480(str_480, max_str_480, min_str_480);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestory=true;
    }
}
