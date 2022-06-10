package com.first.dlam.base.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ViewUtils;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.bean.AllBeanDay;
import com.first.dlam.data.CompositeIndexBean;
import com.first.dlam.data.IncomeBean;
import com.first.dlam.databinding.Fragment11Binding;
import com.first.dlam.manager.LineChartManager;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.blankj.utilcode.util.ThreadUtils.runOnUiThread;


public class Fragment1_1 extends BaseFragment implements View.OnClickListener {
    private Fragment11Binding binding;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private List<TextView> list1;
    private List<TextView> list2;
    private List<TextView> list3;
    private List<List<CompositeIndexBean>> ListMap;
    private int number1 ;
    private int number2 ;
    private int number3 ;
    private int number4 ;
    private int number5 ;
    private int number6 ;
    private int number7 ;
    private int number8 ;
    private int max = 400;
    private List<CompositeIndexBean> compositeIndexBeans;
    private List<IncomeBean> incomeBeans;
    private double beanMax = 0;
    private double beanMin = 1000;
    private static MainHandler handler;
    private List<Integer> colour = new ArrayList<>();//折线颜色集合
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment1_1,container,false);
        sharedPreferencesUtil=new SharedPreferencesUtil(getContext());
        handler=new MainHandler(this);

        binding.etCode.setText(sharedPreferencesUtil.getCode_name());
        binding.lineChartNumber.setText("24");

        binding.getCodeDetail.setOnClickListener(this);

        list1=new ArrayList<>();
        list1.add(binding.tvH32);
        list1.add(binding.tvH42);
        list1.add(binding.tvH52);
        list1.add(binding.tvH62);
        list1.add(binding.tvH72);
        list1.add(binding.tvH82);
        list1.add(binding.tvH92);
        list1.add(binding.tvH102);

        list2=new ArrayList<>();
        list2.add(binding.tvH33);
        list2.add(binding.tvH43);
        list2.add(binding.tvH53);
        list2.add(binding.tvH63);
        list2.add(binding.tvH73);
        list2.add(binding.tvH83);
        list2.add(binding.tvH93);
        list2.add(binding.tvH103);

        list3=new ArrayList<>();
        list3.add(binding.tvH34);
        list3.add(binding.tvH44);
        list3.add(binding.tvH54);
        list3.add(binding.tvH64);
        list3.add(binding.tvH74);
        list3.add(binding.tvH84);
        list3.add(binding.tvH94);
        list3.add(binding.tvH104);

        //折线颜色
        colour.add(getResources().getColor(R.color.blue));
        colour.add(getResources().getColor(R.color.hong));
        colour.add(getResources().getColor(R.color.ceng));
        colour.add(getResources().getColor(R.color.huang));
        colour.add(getResources().getColor(R.color.lv));
        colour.add(getResources().getColor(R.color.qing));
        colour.add(getResources().getColor(R.color.lang));
        colour.add(getResources().getColor(R.color.zi));
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    public static class MainHandler extends Handler {
        WeakReference<Fragment1_1> fragment;

        private MainHandler(Fragment1_1 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            if (fragment.get() == null) {
                return;
            }
            if(msg.arg1==50){
                fragment.get().binding.title15.setText(msg.obj.toString());
            }
            if(msg.arg1==1){
                fragment.get().list1.get(msg.arg2).setText(msg.obj.toString());
            }
            if(msg.arg1==2){
                fragment.get().list2.get(msg.arg2).setText(msg.obj.toString());
            }
            if(msg.arg1==3){
                fragment.get().list3.get(msg.arg2).setText(msg.obj.toString());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.getCodeDetail:
                if (TextUtils.isEmpty(binding.lineChartNumber.getText().toString())) {
                    Toast.makeText(getContext(), "折线图数量不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.lineChartNumber.getText().toString()) < 12) {
                    Toast.makeText(getContext(), "折线图数量不能小于12", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.lineChartNumber.getText().toString()) > 96) {
                    Toast.makeText(getContext(), "折线图数量不能大于96", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(binding.day1.getText().toString()) || TextUtils.isEmpty(binding.day2.getText().toString()) || TextUtils.isEmpty(binding.day3.getText().toString()) || TextUtils.isEmpty(binding.day4.getText().toString())
                        || TextUtils.isEmpty(binding.day5.getText().toString()) || TextUtils.isEmpty(binding.day6.getText().toString()) || TextUtils.isEmpty(binding.day7.getText().toString()) || TextUtils.isEmpty(binding.day8.getText().toString())) {
                    Toast.makeText(getContext(), "天数不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(binding.day1.getText().toString()) < 1 || Integer.parseInt(binding.day2.getText().toString()) < 1 || Integer.parseInt(binding.day3.getText().toString()) < 1 || Integer.parseInt(binding.day4.getText().toString()) < 1
                        || Integer.parseInt(binding.day5.getText().toString()) < 1 || Integer.parseInt(binding.day6.getText().toString()) < 1 || Integer.parseInt(binding.day7.getText().toString()) < 1 || Integer.parseInt(binding.day8.getText().toString()) < 1) {
                    Toast.makeText(getContext(), "天数不能小于1", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(binding.day1.getText().toString()) > 400 || Integer.parseInt(binding.day2.getText().toString()) > 400 || Integer.parseInt(binding.day3.getText().toString()) > 400 || Integer.parseInt(binding.day4.getText().toString()) > 400
                        || Integer.parseInt(binding.day5.getText().toString()) > 400 || Integer.parseInt(binding.day6.getText().toString()) > 400 || Integer.parseInt(binding.day7.getText().toString()) > 400 || Integer.parseInt(binding.day8.getText().toString()) > 400) {
                    Toast.makeText(getContext(), "天数不能大于400", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day1.getText().toString()) && Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day2.getText().toString())
                        && Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day3.getText().toString())
                        && Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day4.getText().toString())
                        && Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day5.getText().toString())
                        && Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day6.getText().toString())
                        && Integer.parseInt(binding.day8.getText().toString())>Integer.parseInt(binding.day7.getText().toString())) {


                    binding.tvH31.setText(binding.day1.getText().toString());
                    binding.tvH41.setText(binding.day2.getText().toString());
                    binding.tvH51.setText(binding.day3.getText().toString());
                    binding.tvH61.setText(binding.day4.getText().toString());
                    binding.tvH71.setText(binding.day5.getText().toString());
                    binding.tvH81.setText(binding.day6.getText().toString());
                    binding.tvH91.setText(binding.day7.getText().toString());
                    binding.tvH101.setText(binding.day8.getText().toString());
                    number1 = Integer.parseInt(binding.day1.getText().toString());
                    number2 = Integer.parseInt(binding.day2.getText().toString());
                    number3 = Integer.parseInt(binding.day3.getText().toString());
                    number4 = Integer.parseInt(binding.day4.getText().toString());
                    number5 = Integer.parseInt(binding.day5.getText().toString());
                    number6 = Integer.parseInt(binding.day6.getText().toString());
                    number7 = Integer.parseInt(binding.day7.getText().toString());
                    number8 = Integer.parseInt(binding.day8.getText().toString());
                    beanMax = 0;
                    beanMin = 1000;
//                    max =Integer.parseInt(binding.lineChartNumber.getText().toString())+Integer.parseInt(binding.day8.getText().toString());
                    showLoadingDialog("正在解析...");
                    initCode(binding.etCode.getText().toString());
                    if (binding.switchButton.isChecked()) {
                        binding.tvH12.setText("昨天 -> 今天 -> [明天]");
                    } else {
                        binding.tvH12.setText("前天 -> 昨天 -> [今天]");
                    }
                }else{
                    Toast.makeText(getContext(), "日线从左到右应该是从小到大排序", Toast.LENGTH_SHORT).show();
                }
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
                ViewUtils.runOnUiThread(new Runnable() {
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

                            getDetail(codeName);

                        } catch (ClassCastException e) {
                            dismissLoadingDialog();
                            Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void getDetail(final String codeName){
        Http.getBeanByDay(codeName + ",day,,," + (max + Integer.parseInt(binding.lineChartNumber.getText().toString())) + ",qfq", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {
                dismissLoadingDialog();
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                dismissLoadingDialog();
                        JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                        AllBeanDay beanDay = JSON.parseObject(a.toJSONString(), AllBeanDay.class);
                        String data = ResultUtils.D240(beanDay,2,binding.switchButton.isChecked());
                        if (data.isEmpty()) return;
                        String[] str_240 = data.split("#");

                        String[] max_str_240= ResultUtils.D240(beanDay,3,binding.switchButton.isChecked()).split("#");
                        String[] min_str_240= ResultUtils.D240(beanDay,4,binding.switchButton.isChecked()).split("#");

                        showData2(str_240);
                        showData1(max_str_240,list1);
                        showData3(min_str_240,list3);

            }
        });
    }


    public void showData2(String[] str_15) {
        StringBuilder builder_15_3 = new StringBuilder();

        StringBuilder builder = new StringBuilder();
        
        ListMap = new ArrayList<>();

        double position = Double.parseDouble(str_15[str_15.length - 1]);
        double min = position * 0.89;
        while (position > min) {
            if (ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3]) + position + position) / 5.00).equals(ResultUtils.getData(position))) {
                builder_15_3.append(binding.switchButton.isChecked()?"当明天下跌收在：":"当今天下跌收在：").append(ResultUtils.getData(position)).append(binding.switchButton.isChecked()?"位置时，后天5日线开盘价就是：":"位置时，明天5日线开盘价就是：").append(ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3]) + position + position) / 5.00)).append("\n");
            }
            position -= 0.01;
        }

        double position2 = Double.parseDouble(str_15[str_15.length - 1]);
        double max1 = position2 * 1.11;
        while (position2 < max1) {
            if (ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3]) + position2 + position2) / 5.00).equals(ResultUtils.getData(position2))) {
                builder_15_3.append(binding.switchButton.isChecked()?"当明天上涨收在：":"当今天上涨收在：").append(ResultUtils.getData(position2)).append(binding.switchButton.isChecked()?"位置时，后天5日线开盘价就是：":"位置时，明天5日线开盘价就是").append(ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3]) + position2 + position2) / 5.00)).append("\n");
            }
            position2 += 0.01;
        }

        double position3 = Double.parseDouble(str_15[str_15.length - 1]);
        double min3 = position3 * 0.89;
        while (position3 > min3) {
            if (ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3])
                    + Double.parseDouble(str_15[str_15.length - 4])+ Double.parseDouble(str_15[str_15.length - 5])
                    + Double.parseDouble(str_15[str_15.length - 6])+ Double.parseDouble(str_15[str_15.length - 7])+ position3 + position3) / 10.00).equals(ResultUtils.getData(position3))) {
                builder_15_3.append(binding.switchButton.isChecked()?"当明天下跌收在：":"当今天下跌收在：").append(ResultUtils.getData(position3)).append(binding.switchButton.isChecked()?"位置时，后天10日线开盘价就是：":"位置时，明天10日线开盘价就是：").append(ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3])
                        + Double.parseDouble(str_15[str_15.length - 4])+ Double.parseDouble(str_15[str_15.length - 5])
                        + Double.parseDouble(str_15[str_15.length - 6])+ Double.parseDouble(str_15[str_15.length - 7])+ position3 + position3) / 10.00)).append("\n");
            }
            position3 -= 0.01;
        }

        double position4 = Double.parseDouble(str_15[str_15.length - 1]);
        double max4 = position4 * 1.11;
        while (position4 < max4) {
            if (ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3])
                    + Double.parseDouble(str_15[str_15.length - 4])+ Double.parseDouble(str_15[str_15.length - 5])
                    + Double.parseDouble(str_15[str_15.length - 6])+ Double.parseDouble(str_15[str_15.length - 7])+ position4 + position4) / 10.00).equals(ResultUtils.getData(position4))) {
                builder_15_3.append(binding.switchButton.isChecked()?"当明天上涨收在：":"当今天上涨收在：").append(ResultUtils.getData(position4)).append(binding.switchButton.isChecked()?"位置时，后天10日线开盘价就是：":"位置时，明天10日线开盘价就是").append(ResultUtils.getData((Double.parseDouble(str_15[str_15.length - 1]) + Double.parseDouble(str_15[str_15.length - 2]) + Double.parseDouble(str_15[str_15.length - 3])
                        + Double.parseDouble(str_15[str_15.length - 4])+ Double.parseDouble(str_15[str_15.length - 5])
                        + Double.parseDouble(str_15[str_15.length - 6])+ Double.parseDouble(str_15[str_15.length - 7])+ position4 + position4) / 10.00)).append("\n");
            }
            position4 += 0.01;
        }


        CompositeIndexBean bean;

        int number=0;
        for (int k = 0; k < str_15.length; k++) {
            if (k == number1 || k == number2 || k == number3 || k == number4 || k == number5 || k == number6 || k == number7 || k == number8) {
                int start = k == max ? 0 : max - k;

                compositeIndexBeans = new ArrayList<>();
                double mean3 = ResultUtils.getMean(ResultUtils.getSum_number(str_15, k - 1), Double.parseDouble(str_15[str_15.length - 1]), k);

                for (int i = start; i < str_15.length - k + 1; i++) {
                    double sum = 0;
                    for (int j = i; j < k + i; j++) {
                        sum += Double.parseDouble(str_15[j]);
                    }
                    String result = ResultUtils.getData(sum / ((double) k));
                    builder.append(result).append("->");
                    bean = new CompositeIndexBean(String.valueOf(k), Double.parseDouble(result));
                    if (beanMax < Double.parseDouble(result)) { //获取最大值
                        beanMax = Double.parseDouble(result);
                    }
                    if (beanMin > Double.parseDouble(result)) { //获取最小值
                        beanMin = Double.parseDouble(result);
                    }
                    compositeIndexBeans.add(bean);
                }
                ListMap.add(compositeIndexBeans);

                String [] a =builder.toString().split("->");
                Message message =Message.obtain();
                message.arg1=2;
                message.arg2=number;
                message.obj=a[a.length-2]+"->" +a[a.length-1]+"->["+ResultUtils.getData(mean3)+"]\n拐点: "+ResultUtils.getData(Double.parseDouble(str_15[str_15.length-k-1]));
                handler.sendMessage(message);

                number++;

            }
        }

        Message message =Message.obtain();
        message.arg1=50;
        message.obj=builder_15_3.toString();
        handler.sendMessage(message);
        showLineChart(Double.parseDouble(str_15[str_15.length-1]));
    }

    private void showData1(String[] str_15, List<TextView> list1){
        StringBuilder builder_15_3 = new StringBuilder();
        int number=0;
        for (int k = 0; k < str_15.length; k++) {
            if (k == number1 || k == number2 || k == number3 || k == number4 || k == number5 || k == number6 || k == number7 || k == number8) {
                int start = k == max ? 0 : max - k;

                double mean3 = ResultUtils.getMean(ResultUtils.getSum_number(str_15, k - 1), Double.parseDouble(str_15[str_15.length - 1]), k);

                for (int i = start; i < str_15.length - k + 1; i++) {
                    double sum = 0;
                    for (int j = i; j < k + i; j++) {
                        sum += Double.parseDouble(str_15[j]);
                    }
                    String result = ResultUtils.getData(sum / ((double) k));
                    builder_15_3.append(result).append("->");
                }
                String [] a =builder_15_3.toString().split("->");

                Message message =Message.obtain();
                message.arg1=1;
                message.arg2=number;
                message.obj=a[a.length-2]+"->" +a[a.length-1]+"->["+ResultUtils.getData(mean3)+"]\n拐点: "+ResultUtils.getData(Double.parseDouble(str_15[str_15.length-k-1]));
                handler.sendMessage(message);

                number++;

            }
        }
    }
    private void showData3(String[] str_15, List<TextView> list2){
        StringBuilder builder_15_3 = new StringBuilder();
        int number=0;
        for (int k = 0; k < str_15.length; k++) {
            if (k == number1 || k == number2 || k == number3 || k == number4 || k == number5 || k == number6 || k == number7 || k == number8) {
                int start = k == max ? 0 : max - k;

                double mean3 = ResultUtils.getMean(ResultUtils.getSum_number(str_15, k - 1), Double.parseDouble(str_15[str_15.length - 1]), k);

                for (int i = start; i < str_15.length - k + 1; i++) {
                    double sum = 0;
                    for (int j = i; j < k + i; j++) {
                        sum += Double.parseDouble(str_15[j]);
                    }
                    String result = ResultUtils.getData(sum / ((double) k));
                    builder_15_3.append(result).append("->");
                }
                String [] a =builder_15_3.toString().split("->");

                Message message =Message.obtain();
                message.arg1=3;
                message.arg2=number;
                message.obj=a[a.length-2]+"->" +a[a.length-1]+"->["+ResultUtils.getData(mean3)+"]\n拐点: "+ResultUtils.getData(Double.parseDouble(str_15[str_15.length-k-1]));
                handler.sendMessage(message);

                number++;

            }
        }
    }

    private void showLineChart(double mbunber) {
        if (ListMap == null || ListMap.isEmpty()) {
            return;
        }

        LineChartManager lineChartManager_360 = new LineChartManager(binding.lineChart, 3.6F, 24);
        lineChartManager_360.setYAxisData((float) (beanMax + 0.05), (float) (beanMin - 0.05), 8);
        incomeBeans = new ArrayList<>();
        IncomeBean bean;
        for (int j = 0; j < Integer.parseInt(binding.lineChartNumber.getText().toString()); j++) {
            bean = new IncomeBean("asd", mbunber);
            incomeBeans.add(bean);
        }
        lineChartManager_360.showLineChart(incomeBeans, "asd", getResources().getColor(R.color.orange));


        for (int i = 0; i < ListMap.size(); i++) {
            lineChartManager_360.addLine(ListMap.get(i), String.valueOf(i), colour.get(i));
        }

        //设置曲线填充色 以及 MarkerView
        Drawable drawable = getResources().getDrawable(R.drawable.fade_orange);
        lineChartManager_360.setChartFillDrawable(drawable);

    }
}
