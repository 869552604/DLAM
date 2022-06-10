package com.first.dlam.base;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.TimeUtils;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.base.fragment.Fragment1_3;
import com.first.dlam.bean.BaseEvent;
import com.first.dlam.data.CompositeIndexBean;
import com.first.dlam.data.IncomeBean;
import com.first.dlam.databinding.ActivitySuperBinding;
import com.first.dlam.manager.LineChartManager;
import com.first.dlam.utils.Utils;
import com.github.mikephil.charting.components.YAxis;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class SuperActivity extends BaseActivity implements View.OnClickListener {
    private Bitmap screenShot = null;
    private ActivitySuperBinding binding;
    private LineChartManager lineChartManagerDay;
    private LineChartManager lineChartManagerDay1;
    private LineChartManager lineChartManagerDay2;
    private MainHandler handler;
    private boolean closeChange = true; //true就是暂停自动更新
    private String number;
    private String number1;
    private String number2;

    public int otherTime=5;
    public String string3 ="3";
    public String string4 ="4";
    public String string6 ="6";

    private LineChartManager lineChartManager51;
    private LineChartManager lineChartManager52;
    private LineChartManager lineChartManager53;

    private LineChartManager lineChartManager151;
    private LineChartManager lineChartManager152;
    private LineChartManager lineChartManager153;

    private LineChartManager lineChartManager301;
    private LineChartManager lineChartManager302;
    private LineChartManager lineChartManager303;

    private LineChartManager lineChartManager601;
    private LineChartManager lineChartManager602;
    private LineChartManager lineChartManager603;

    private LineChartManager lineChartManager1201;
    private LineChartManager lineChartManager1202;
    private LineChartManager lineChartManager1203;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_super);
        handler = new MainHandler(this);
        closeChange = Fragment1_3.isDestory;
        string3=Fragment1_3.string3;
        string4=Fragment1_3.string4;
        string6=Fragment1_3.string6;

        if (Fragment1_3.isDestory) {
            binding.getCodeDetail.setText("已停止更新");
        } else {
            binding.getCodeDetail.setText("自动更新中");
        }

        binding.back.setOnClickListener(this);
        binding.getCodeDetail.setOnClickListener(this);
        binding.download.setOnClickListener(this);

        initData();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void initData() {
        lineChartManagerDay = new LineChartManager(binding.lineChartDay, 3.6F, 8);
        lineChartManagerDay1 = new LineChartManager(binding.lineChartDay1, 3.6F, 8);
        lineChartManagerDay2 = new LineChartManager(binding.lineChartDay2, 3.6F, 8);
        YAxis yAxis=binding.lineChartDay.getAxisLeft();
        yAxis.setAxisMinimum(Fragment1_3.min_240);
        yAxis.setAxisMaximum(Fragment1_3.max_240);

        YAxis yAxis1=binding.lineChartDay1.getAxisLeft();
        yAxis1.setAxisMinimum(Fragment1_3.min_240);
        yAxis1.setAxisMaximum(Fragment1_3.max_240);

        YAxis yAxis2=binding.lineChartDay2.getAxisLeft();
        yAxis2.setAxisMinimum(Fragment1_3.min_240);
        yAxis2.setAxisMaximum(Fragment1_3.max_240);


        lineChartManager51= new LineChartManager(binding.lineChart51, 3.6F, 8);
        lineChartManager52 = new LineChartManager(binding.lineChart52, 3.6F, 8);
        lineChartManager53 = new LineChartManager(binding.lineChart53, 3.6F, 8);
        YAxis yAxis51=binding.lineChart51.getAxisLeft();
        yAxis51.setAxisMinimum(Fragment1_3.min_5);
        yAxis51.setAxisMaximum(Fragment1_3.max_5);

        YAxis yAxis52=binding.lineChart52.getAxisLeft();
        yAxis52.setAxisMinimum(Fragment1_3.min_5);
        yAxis52.setAxisMaximum(Fragment1_3.max_5);

        YAxis yAxis53=binding.lineChart53.getAxisLeft();
        yAxis53.setAxisMinimum(Fragment1_3.min_5);
        yAxis53.setAxisMaximum(Fragment1_3.max_5);


        lineChartManager151= new LineChartManager(binding.lineChart151, 3.6F, 8);
        lineChartManager152 = new LineChartManager(binding.lineChart152, 3.6F, 8);
        lineChartManager153 = new LineChartManager(binding.lineChart153, 3.6F, 8);
        YAxis yAxis151=binding.lineChart151.getAxisLeft();
        yAxis151.setAxisMinimum(Fragment1_3.min_15);
        yAxis151.setAxisMaximum(Fragment1_3.max_15);

        YAxis yAxis152=binding.lineChart152.getAxisLeft();
        yAxis152.setAxisMinimum(Fragment1_3.min_15);
        yAxis152.setAxisMaximum(Fragment1_3.max_15);

        YAxis yAxis153=binding.lineChart153.getAxisLeft();
        yAxis153.setAxisMinimum(Fragment1_3.min_15);
        yAxis153.setAxisMaximum(Fragment1_3.max_15);


        lineChartManager301= new LineChartManager(binding.lineChart301, 3.6F, 8);
        lineChartManager302 = new LineChartManager(binding.lineChart302, 3.6F, 8);
        lineChartManager303 = new LineChartManager(binding.lineChart303, 3.6F, 8);
        YAxis yAxis301=binding.lineChart301.getAxisLeft();
        yAxis301.setAxisMinimum(Fragment1_3.min_30);
        yAxis301.setAxisMaximum(Fragment1_3.max_30);


        YAxis yAxis302=binding.lineChart302.getAxisLeft();
        yAxis302.setAxisMinimum(Fragment1_3.min_30);
        yAxis302.setAxisMaximum(Fragment1_3.max_30);

        YAxis yAxis303=binding.lineChart303.getAxisLeft();
        yAxis303.setAxisMinimum(Fragment1_3.min_30);
        yAxis303.setAxisMaximum(Fragment1_3.max_30);

        lineChartManager601= new LineChartManager(binding.lineChart601, 3.6F, 8);
        lineChartManager602 = new LineChartManager(binding.lineChart602, 3.6F, 8);
        lineChartManager603 = new LineChartManager(binding.lineChart603, 3.6F, 8);
        YAxis yAxis601=binding.lineChart601.getAxisLeft();
        yAxis601.setAxisMinimum(Fragment1_3.min_60);
        yAxis601.setAxisMaximum(Fragment1_3.max_60);

        YAxis yAxis602=binding.lineChart602.getAxisLeft();
        yAxis602.setAxisMinimum(Fragment1_3.min_60);
        yAxis602.setAxisMaximum(Fragment1_3.max_60);

        YAxis yAxis603=binding.lineChart603.getAxisLeft();
        yAxis603.setAxisMinimum(Fragment1_3.min_60);
        yAxis603.setAxisMaximum(Fragment1_3.max_60);

        lineChartManager1201= new LineChartManager(binding.lineChart1201, 3.6F, 8);
        lineChartManager1202 = new LineChartManager(binding.lineChart1202, 3.6F, 8);
        lineChartManager1203 = new LineChartManager(binding.lineChart1203, 3.6F, 8);
        YAxis yAxis1201=binding.lineChart1201.getAxisLeft();
        yAxis1201.setAxisMinimum(Fragment1_3.min_120);
        yAxis1201.setAxisMaximum(Fragment1_3.max_120);

        YAxis yAxis1202=binding.lineChart1202.getAxisLeft();
        yAxis1202.setAxisMinimum(Fragment1_3.min_120);
        yAxis1202.setAxisMaximum(Fragment1_3.max_120);

        YAxis yAxis1203=binding.lineChart1203.getAxisLeft();
        yAxis1203.setAxisMinimum(Fragment1_3.min_120);
        yAxis1203.setAxisMaximum(Fragment1_3.max_120);


        changeData();
    }

    public static class MainHandler extends Handler {
        WeakReference<SuperActivity> fragment;

        private MainHandler(SuperActivity activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            SuperActivity theActivity = fragment.get();
            if (theActivity == null) {
                return;
            }else if(msg.arg1==51){
                theActivity.binding.content51.setText(msg.obj.toString());
                theActivity.binding.Title5.setText(theActivity.getSection(Fragment1_3.str_5));
            }else if(msg.arg1==52){
                theActivity.binding.content52.setText(msg.obj.toString());
            }else if(msg.arg1==53){
                theActivity.binding.content53.setText(msg.obj.toString());
            }else if(msg.arg1==151){
                theActivity.binding.content151.setText(msg.obj.toString());
                theActivity.binding.Title15.setText(theActivity.getSection(Fragment1_3.str_15));
            }else if(msg.arg1==152){
                theActivity.binding.content152.setText(msg.obj.toString());
            }else if(msg.arg1==153){
                theActivity.binding.content153.setText(msg.obj.toString());
            }else if(msg.arg1==301){
                theActivity.binding.content301.setText(msg.obj.toString());
                theActivity.binding.Title30.setText(theActivity.getSection(Fragment1_3.str_30));
            }else if(msg.arg1==302){
                theActivity.binding.content302.setText(msg.obj.toString());
            }else if(msg.arg1==303){
                theActivity.binding.content303.setText(msg.obj.toString());
            }else if(msg.arg1==601){
                theActivity.binding.content601.setText(msg.obj.toString());
                theActivity.binding.Title60.setText(theActivity.getSection(Fragment1_3.str_60));
            }else if(msg.arg1==602){
                theActivity.binding.content602.setText(msg.obj.toString());
            }else if(msg.arg1==603){
                theActivity.binding.content603.setText(msg.obj.toString());
            }else if(msg.arg1==1201){
                theActivity.binding.content1201.setText(msg.obj.toString());
                theActivity.binding.Title120.setText(theActivity.getSection(Fragment1_3.str_120));
            }else if(msg.arg1==1202){
                theActivity.binding.content1202.setText(msg.obj.toString());
            }else if(msg.arg1==1203){
                theActivity.binding.content1203.setText(msg.obj.toString());
            }else if(msg.arg1==2401){
                theActivity.binding.contentDay.setText(msg.obj.toString());
                theActivity.binding.TitleDay.setText(theActivity.getSection(Fragment1_3.str_240));
            }else if(msg.arg1==2402){
                theActivity.binding.contentDay1.setText(msg.obj.toString());
            }else if(msg.arg1==2403){
                theActivity.binding.contentDay2.setText(msg.obj.toString());
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.getCodeDetail:
                if (binding.getCodeDetail.getText().toString().equals("已停止更新")) {
                    if (Fragment1_3.isDestory) {
                        Toast.makeText(SuperActivity.this, "请先去趋势模块启动自动更新！", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.getCodeDetail.setText("自动更新中");
                        closeChange = false;
                        //这里不可以手动调价格，因此需要隐藏起来，并把 下个、下下个的价格统一位当前价
                    }
                } else {
                    binding.getCodeDetail.setText("已停止更新");
                    closeChange = true;
                    //这里应该可以手动调价格，因此需要显示出来
                }
                break;
            case R.id.download:
                screenShot = viewConversionBitmap(binding.contentLayout);
                saveImage();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(BaseEvent event) {
        if (closeChange) {
            //说明关掉了自动更新 就不做任何操作|

        } else {
            //这里应该自动更新
            changeData();

        }
    }


    private void changeData() {
        number = ResultUtils.getData(Fragment1_3.now);
        number1 = ResultUtils.getData(Fragment1_3.now);
        number2 = ResultUtils.getData(Fragment1_3.now);
        binding.layout.setText("当前价:\n" + number);
        binding.layout1.setText("下个K线价:\n" + number1);
        binding.layout2.setText("下下个K线价:\n" + number2);


        showData1(lineChartManager51,Fragment1_3.str_5,Fragment1_3.incomeBean_3_5,Fragment1_3.incomeBean_4_5,Fragment1_3.incomeBean_6_5,Fragment1_3.incomeBean_5_max,Fragment1_3.incomeBean_5_min,51);
        showData2(lineChartManager52, Fragment1_3.str_5,Fragment1_3.max_str_5,Fragment1_3.min_str_5,52);
        showData3(lineChartManager53, Fragment1_3.str_5,Fragment1_3.max_str_5,Fragment1_3.min_str_5,53);

        showData1(lineChartManager151,Fragment1_3.str_15,Fragment1_3.incomeBean_3_15,Fragment1_3.incomeBean_4_15,Fragment1_3.incomeBean_6_15,Fragment1_3.incomeBean_15_max,Fragment1_3.incomeBean_15_min,151);
        showData2(lineChartManager152, Fragment1_3.str_15,Fragment1_3.max_str_15,Fragment1_3.min_str_15,152);
        showData3(lineChartManager153, Fragment1_3.str_15,Fragment1_3.max_str_15,Fragment1_3.min_str_15,153);

        showData1(lineChartManager301,Fragment1_3.str_30,Fragment1_3.incomeBean_3_30,Fragment1_3.incomeBean_4_30,Fragment1_3.incomeBean_6_30,Fragment1_3.incomeBean_30_max,Fragment1_3.incomeBean_30_min,301);
        showData2(lineChartManager302, Fragment1_3.str_30,Fragment1_3.max_str_30,Fragment1_3.min_str_30,302);
        showData3(lineChartManager303, Fragment1_3.str_30,Fragment1_3.max_str_30,Fragment1_3.min_str_30,303);

        showData1(lineChartManager601,Fragment1_3.str_60,Fragment1_3.incomeBean_3_60,Fragment1_3.incomeBean_4_60,Fragment1_3.incomeBean_6_60,Fragment1_3.incomeBean_60_max,Fragment1_3.incomeBean_60_min,601);
        showData2(lineChartManager602, Fragment1_3.str_60,Fragment1_3.max_str_60,Fragment1_3.min_str_60,602);
        showData3(lineChartManager603, Fragment1_3.str_60,Fragment1_3.max_str_60,Fragment1_3.min_str_60,603);

        showData1(lineChartManager1201,Fragment1_3.str_120,Fragment1_3.incomeBean_3_120,Fragment1_3.incomeBean_4_120,Fragment1_3.incomeBean_6_120,Fragment1_3.incomeBean_120_max,Fragment1_3.incomeBean_120_min,1201);
        showData2(lineChartManager1202, Fragment1_3.str_120,Fragment1_3.max_str_120,Fragment1_3.min_str_120,1202);
        showData3(lineChartManager1203, Fragment1_3.str_120,Fragment1_3.max_str_120,Fragment1_3.min_str_120,1203);

        showData1(lineChartManagerDay,Fragment1_3.str_240,Fragment1_3.incomeBean_3_240,Fragment1_3.incomeBean_4_240,Fragment1_3.incomeBean_6_240,Fragment1_3.incomeBean_240_max,Fragment1_3.incomeBean_240_min,2401);
        showData2(lineChartManagerDay1,Fragment1_3.str_240,Fragment1_3.max_str_240,Fragment1_3.min_str_240,2402);
        showData3(lineChartManagerDay2,Fragment1_3.str_240,Fragment1_3.max_str_240,Fragment1_3.min_str_240,2403);
    }


    //TODO
    private void showLineChart3(LineChartManager lineChartManager_360, List<IncomeBean> incomeBean360, List<CompositeIndexBean> incomeBean_3_360, List<CompositeIndexBean> incomeBean_4_360, List<CompositeIndexBean> incomeBean_6_360, List<CompositeIndexBean> incomeBean_5_max, List<CompositeIndexBean> incomeBean_5_min) {
        //展示图表
        lineChartManager_360.showLineChart3(incomeBean360, "String120", getResources().getColor(R.color.orange));
        lineChartManager_360.addLine(incomeBean_3_360, "string3", getResources().getColor(R.color.back));
        lineChartManager_360.addLine(incomeBean_4_360, "string4", getResources().getColor(R.color.red));
        lineChartManager_360.addLine(incomeBean_6_360, "string6", getResources().getColor(R.color.blue));

        lineChartManager_360.addLine(incomeBean_5_max, "string_max", getResources().getColor(R.color.colorPrimary));
        lineChartManager_360.addLine(incomeBean_5_min, "string_min", getResources().getColor(R.color.lv));

        //设置曲线填充色 以及 MarkerView
        lineChartManager_360.setChartFillDrawable(getResources().getDrawable(R.drawable.fade_orange));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    private String getSection(String[] str_15) { //获取对应区间
        StringBuilder builder_content_15 = new StringBuilder();
        double arr[]=new double[str_15.length];

        builder_content_15
                .append(ResultUtils.getData(str_15[str_15.length - Integer.parseInt(Fragment1_3.string6)-1]));

        arr[str_15.length - Integer.parseInt(Fragment1_3.string6)-1]=Double.parseDouble(str_15[str_15.length - Integer.parseInt(Fragment1_3.string6)-1]);

        builder_content_15.append(" [");
        for (int i = str_15.length - Integer.parseInt(Fragment1_3.string6); i < str_15.length; i++) {
            builder_content_15.append(ResultUtils.getData(str_15[i]));
            arr[i]=Double.parseDouble(str_15[i]);
            if (i < str_15.length - 1) {
                if (i == str_15.length - Integer.parseInt(Fragment1_3.string4) - 1 || i == str_15.length - Integer.parseInt(Fragment1_3.string3) - 1) {
                    builder_content_15.append("、[");
                } else {
                    builder_content_15.append("、");
                }
            }
        }

        Arrays.sort(arr);
        builder_content_15.append("]]]");
        builder_content_15.append("\n区间排序\n[ "+ResultUtils.getData(arr[str_15.length - Integer.parseInt(Fragment1_3.string6)-1]))
        .append("、").append(ResultUtils.getData(arr[str_15.length - Integer.parseInt(Fragment1_3.string6)]))
        .append("...")
        .append(ResultUtils.getData(arr[arr.length-2]))
        .append("、").append(ResultUtils.getData(arr[arr.length-1])).append("]");
        return builder_content_15.toString();
    }

    //今天的数据
    private void showData1(LineChartManager lineChartManagerDay,String[] str_240,List<CompositeIndexBean> incomeBean_3_240,List<CompositeIndexBean> incomeBean_4_240,List<CompositeIndexBean> incomeBean_6_240,List<CompositeIndexBean> incomeBean_240_max,List<CompositeIndexBean> incomeBean_240_min,int message){
        List<IncomeBean> incomeBeanDay1 = new ArrayList<>();
        IncomeBean bean;
        for (int i = 0; i < 40; i++) {
            bean = new IncomeBean("String120", Double.parseDouble(str_240[str_240.length - 1]));
            incomeBeanDay1.add(bean);
        }
        showLineChart3(lineChartManagerDay, incomeBeanDay1, incomeBean_3_240, incomeBean_4_240, incomeBean_6_240, incomeBean_240_max, incomeBean_240_min);
        Message msg2 = Message.obtain();
        msg2.arg1 = message;
        msg2.obj="压:" + ResultUtils.getData(incomeBean_240_max.get(0).getRate()) + " 支:" + ResultUtils.getData(incomeBean_240_min.get(0).getRate());
        handler.sendMessage(msg2);
    }

    //明天的数据
    private void showData2(LineChartManager lineChartManagerDay1,String[] str_240,String[] max_str_240,String[] min_str_240,int message){
        String[] str_240_day3 =new String[str_240.length+1];
        for (int i = 0; i < str_240.length; i++) {
            str_240_day3[i]=str_240[i];
        }
        str_240_day3[str_240.length]=number2;

        String[] str_240_day3_max =new String[max_str_240.length+1];
        for (int i = 0; i < max_str_240.length; i++) {
            str_240_day3_max[i]=max_str_240[i];
        }
        str_240_day3_max[max_str_240.length]=number2;

        String[] str_240_day3_min=new String[min_str_240.length+1];
        for (int i = 0; i < min_str_240.length; i++) {
            str_240_day3_min[i]=min_str_240[i];
        }
        str_240_day3_min[min_str_240.length]=number2;

        String[] str_max_240_day3 = ResultUtils.getString_15_number(str_240_day3_max, otherTime+1, otherTime+1).split("->");
        String[] str_min_240_day3 = ResultUtils.getString_15_number(str_240_day3_min, otherTime+1, otherTime+1).split("->");
        List<IncomeBean> incomeBeanDay3 =new ArrayList<>();  //当前价
        IncomeBean bean3;
        List<CompositeIndexBean> incomeBean_240_max_day3 = new ArrayList<>(40);
        List<CompositeIndexBean> incomeBean_240_min_day3= new ArrayList<>(40);
        for (int i = 0; i < 40; i++) {
            bean3 = new IncomeBean("String120", Double.parseDouble(number2));
            incomeBeanDay3.add(bean3);
            incomeBean_240_max_day3.add(new CompositeIndexBean("stringmax",Double.parseDouble(str_max_240_day3[str_max_240_day3.length-1])));
            incomeBean_240_min_day3.add(new CompositeIndexBean("stringmin",Double.parseDouble(str_min_240_day3[str_min_240_day3.length-1])));
        }


        List<CompositeIndexBean> incomeBean_3_240_day3 = new ArrayList<>(40);
        List<CompositeIndexBean> incomeBean_4_240_day3 = new ArrayList<>(40);
        List<CompositeIndexBean> incomeBean_6_240_day3= new ArrayList<>(40);

        String[] newString_15_3 = ResultUtils.getString_15_number(str_240_day3, Integer.parseInt(string3)+1, Integer.parseInt(string6)+1).split("->");
        String[] newString_15_4 = ResultUtils.getString_15_number(str_240_day3, Integer.parseInt(string4)+1, Integer.parseInt(string6)+1).split("->");
        String[] newString_15_6 = ResultUtils.getString_15_number(str_240_day3, Integer.parseInt(string6)+1, Integer.parseInt(string6)+1).split("->");

        for (int i = newString_15_3.length - 40; i < newString_15_3.length; i++) {
            incomeBean_3_240_day3.add(new CompositeIndexBean(string3, Double.parseDouble(newString_15_3[i])));
        }

        for (int i = newString_15_4.length - 40; i < newString_15_4.length; i++) {
            incomeBean_4_240_day3.add(new CompositeIndexBean(string4, Double.parseDouble(newString_15_4[i])));
        }

        for (int i = newString_15_6.length - 40; i < newString_15_6.length; i++) {
            incomeBean_6_240_day3.add(new CompositeIndexBean(string6, Double.parseDouble(newString_15_6[i])));
        }
        showLineChart3(lineChartManagerDay1,incomeBeanDay3,incomeBean_3_240_day3,incomeBean_4_240_day3,incomeBean_6_240_day3,incomeBean_240_max_day3,incomeBean_240_min_day3);

        Message msg2 = Message.obtain();
        msg2.arg1 = message;
        msg2.obj="压:" + ResultUtils.getData(incomeBean_240_max_day3.get(0).getRate()) + " 支:" + ResultUtils.getData(incomeBean_240_min_day3.get(0).getRate());
        handler.sendMessage(msg2);
    }

    //后天的数据
    private void showData3(LineChartManager lineChartManagerDay2,String[] str_240,String[] max_str_240,String[] min_str_240,int message){
        String[] str_240_day3 =new String[str_240.length+2];
        for (int i = 0; i < str_240.length; i++) {
            str_240_day3[i]=str_240[i];
        }
        str_240_day3[str_240.length]=number2;
        str_240_day3[str_240.length+1]=number2;

        String[] str_240_day3_max =new String[max_str_240.length+2];
        for (int i = 0; i < max_str_240.length; i++) {
            str_240_day3_max[i]=max_str_240[i];
        }
        str_240_day3_max[max_str_240.length]=number2;
        str_240_day3_max[max_str_240.length+1]=number2;

        String[] str_240_day3_min=new String[min_str_240.length+2];
        for (int i = 0; i < min_str_240.length; i++) {
            str_240_day3_min[i]=min_str_240[i];
        }
        str_240_day3_min[min_str_240.length]=number2;
        str_240_day3_min[min_str_240.length+1]=number2;

        String[] str_max_240_day3 = ResultUtils.getString_15_number(str_240_day3_max, otherTime+2, otherTime+2).split("->");
        String[] str_min_240_day3 = ResultUtils.getString_15_number(str_240_day3_min, otherTime+2, otherTime+2).split("->");
        List<IncomeBean> incomeBeanDay3 =new ArrayList<>();  //当前价
        IncomeBean bean3;
        List<CompositeIndexBean> incomeBean_240_max_day3 = new ArrayList<>(40);
        List<CompositeIndexBean> incomeBean_240_min_day3= new ArrayList<>(40);
        for (int i = 0; i < 40; i++) {
            bean3 = new IncomeBean("String120", Double.parseDouble(number2));
            incomeBeanDay3.add(bean3);
            incomeBean_240_max_day3.add(new CompositeIndexBean("stringmax",Double.parseDouble(str_max_240_day3[str_max_240_day3.length-1])));
            incomeBean_240_min_day3.add(new CompositeIndexBean("stringmin",Double.parseDouble(str_min_240_day3[str_min_240_day3.length-1])));
        }


        List<CompositeIndexBean> incomeBean_3_240_day3 = new ArrayList<>(40);
        List<CompositeIndexBean> incomeBean_4_240_day3 = new ArrayList<>(40);
        List<CompositeIndexBean> incomeBean_6_240_day3= new ArrayList<>(40);

        String[] newString_15_3 = ResultUtils.getString_15_number(str_240_day3, Integer.parseInt(string3)+2, Integer.parseInt(string6)+2).split("->");
        String[] newString_15_4 = ResultUtils.getString_15_number(str_240_day3, Integer.parseInt(string4)+2, Integer.parseInt(string6)+2).split("->");
        String[] newString_15_6 = ResultUtils.getString_15_number(str_240_day3, Integer.parseInt(string6)+2, Integer.parseInt(string6)+2).split("->");

        for (int i = newString_15_3.length - 40; i < newString_15_3.length; i++) {
            incomeBean_3_240_day3.add(new CompositeIndexBean(string3, Double.parseDouble(newString_15_3[i])));
        }

        for (int i = newString_15_4.length - 40; i < newString_15_4.length; i++) {
            incomeBean_4_240_day3.add(new CompositeIndexBean(string4, Double.parseDouble(newString_15_4[i])));
        }

        for (int i = newString_15_6.length - 40; i < newString_15_6.length; i++) {
            incomeBean_6_240_day3.add(new CompositeIndexBean(string6, Double.parseDouble(newString_15_6[i])));
        }
        showLineChart3(lineChartManagerDay2,incomeBeanDay3,incomeBean_3_240_day3,incomeBean_4_240_day3,incomeBean_6_240_day3,incomeBean_240_max_day3,incomeBean_240_min_day3);

        Message msg2 = Message.obtain();
        msg2.arg1 = message;
        msg2.obj="压:" + ResultUtils.getData(incomeBean_240_max_day3.get(0).getRate()) + " 支:" + ResultUtils.getData(incomeBean_240_min_day3.get(0).getRate());
        handler.sendMessage(msg2);
    }

    private void saveImage() {
        RxPermissions rxPermissions = new RxPermissions(this);
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
                            Toast.makeText(SuperActivity.this, "拒绝权限", Toast.LENGTH_SHORT).show();
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
            boolean state = Utils.addPictureToAlbum(SuperActivity.this, screenShot, fileName);
            if (state) {
                Toast.makeText(SuperActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                screenShot = null;
            } else {
                Toast.makeText(SuperActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
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