package com.first.dlam.base.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.adapter.FirstAdapter;
import com.first.dlam.bean.AdapterBean1;
import com.first.dlam.bean.AllBeanDay;
import com.first.dlam.data.CompositeIndexBean;
import com.first.dlam.data.IncomeBean;
import com.first.dlam.databinding.Fragment41Binding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;
import com.first.dlam.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

public class Fragment4_1 extends BaseFragment implements View.OnClickListener {
    private Fragment41Binding binding;
    private Bitmap screenShot = null;
    private int K_form_position = 0;
    private MainHandler handler;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private String code = ",day,,,50,qfq";
    private int string3 =3;
    private int string4 =4;
    private int string6 =6;

    private FirstAdapter adapter;
    private List<AdapterBean1> list;
    private SharedPreferencesUtil sharedPreferencesUtil;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment4_1, container, false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());

        string3=Integer.parseInt(sharedPreferencesUtil.getET_3());
        string4=Integer.parseInt(sharedPreferencesUtil.getET_4());
        string6=Integer.parseInt(sharedPreferencesUtil.getET_6());
        handler = new MainHandler(this);
        initListener();
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void initListener() {

        binding.back.setOnClickListener(this);
        binding.getCodeDetail.setOnClickListener(this);
        binding.KForm1.setOnClickListener(this);
        binding.KForm2.setOnClickListener(this);
        binding.KForm3.setOnClickListener(this);
        binding.KForm4.setOnClickListener(this);
        binding.KForm5.setOnClickListener(this);
        binding.KForm6.setOnClickListener(this);
        binding.download.setOnClickListener(this);

        initRecyclerView();

        initadapter2();

        //解决跟ScrollView的滑动出冲突
        adapter.disableLoadMoreIfNotFullPage();
        binding.recyclerView.setNestedScrollingEnabled(false);
//        binding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    if(list.size()>10){
//                        adapter.disableLoadMoreIfNotFullPage();
//                        for (int i = 0; i < 10; i++) {
//                            adapter.addData(list.get(i));
//                        }
//                        for (int i = 0; i < 10; i++) {
//                            list.remove(0);
//
//                        }
//
//                        Log.i("TAG","加载更多");
//                    }else{
//                        adapter.loadMoreEnd();
//                        for (int i = 0; i < list.size(); i++) {
//                            adapter.addData(list.get(i));
//                        }
//                        list.clear();
//                        Log.i("TAG","加载完成");
//                    }
//                }
//            }
//        });

    }
    private void initadapter2(){
        adapter = new FirstAdapter(getContext());
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setNotDoAnimationCount(3);
        adapter.bindToRecyclerView(binding.recyclerView);
        binding.recyclerView.setAdapter(adapter);

    }

    public static class MainHandler extends Handler {
        WeakReference<Fragment4_1> fragment;

        private MainHandler(Fragment4_1 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            Fragment4_1 theActivity = fragment.get();
            if (theActivity == null) {
                return;
            }
            if (msg.arg1 == 0) {
                theActivity.addData((AdapterBean1) msg.obj);
            }
        }
    }

    private void addData(AdapterBean1 bean) {
        adapter.addData(bean);

//        if(adapter.getItemCount()<11) {
//            adapter.addData(bean);
//
//        }else{
//
//            list.add(bean);
//        }
        binding.number.setText("数量:" + list.size());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                screenShot = viewConversionBitmap(binding.contentLayout);
                saveImage();
                break;
            case R.id.getCodeDetail:
                if (TextUtils.isEmpty(binding.editText1.getText().toString()) || TextUtils.isEmpty(binding.editText2.getText().toString()) || TextUtils.isEmpty(binding.editText3.getText().toString()) || TextUtils.isEmpty(binding.editText4.getText().toString()) || TextUtils.isEmpty(binding.editText5.getText().toString()) || TextUtils.isEmpty(binding.editText6.getText().toString())) {
                    Toast.makeText(getContext(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(binding.editText1.getText().toString()) < 0 || Integer.parseInt(binding.editText2.getText().toString()) < 0 || Integer.parseInt(binding.editText3.getText().toString()) < 0 || Integer.parseInt(binding.editText4.getText().toString()) < 0) {
                    Toast.makeText(getContext(), "不能小于0", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(binding.editText1.getText().toString()) - Integer.parseInt(binding.editText2.getText().toString()) > 0) {
                    Toast.makeText(getContext(), "左边换手率不能高于右边换手率", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(binding.editText3.getText().toString()) - Integer.parseInt(binding.editText4.getText().toString()) > 0) {
                    Toast.makeText(getContext(), "左边量能不能高于右边量能", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "正在筛选上证跟深证符合要求的股票", Toast.LENGTH_SHORT).show();
                list =new ArrayList<>();
                adapter.setNewData(list);
                binding.number.setText("数量:" + list.size());
                showLoadingDialog("筛选中...");
                getHttp2();
                break;
            case R.id.K_form_1:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.editText1.setText("3");
                binding.editText2.setText("5");
                binding.editText3.setText("2");
                binding.editText4.setText("10");
                binding.editText5.setText("3");
                binding.editText6.setText("100");
                K_form_position = 0;

                binding.KForm1.setTextColor(getResources().getColor(R.color.write));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                binding.KForm5.setTextColor(getResources().getColor(R.color.back));
                binding.KForm6.setTextColor(getResources().getColor(R.color.back));
                break;
            case R.id.K_form_2:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.editText1.setText("0");
                binding.editText2.setText("30");
                binding.editText3.setText("0");
                binding.editText4.setText("30");
                binding.editText5.setText("-0");
                binding.editText6.setText("0");
                K_form_position = 1;

                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.write));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                binding.KForm5.setTextColor(getResources().getColor(R.color.back));
                binding.KForm6.setTextColor(getResources().getColor(R.color.back));
                break;
            case R.id.K_form_3:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.editText1.setText("0");
                binding.editText2.setText("30");
                binding.editText3.setText("0");
                binding.editText4.setText("30");
                binding.editText5.setText("-0");
                binding.editText6.setText("0");
                K_form_position = 2;

                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.write));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                binding.KForm5.setTextColor(getResources().getColor(R.color.back));
                binding.KForm6.setTextColor(getResources().getColor(R.color.back));
                break;
            case R.id.K_form_4:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.editText1.setText("0");
                binding.editText2.setText("30");
                binding.editText3.setText("0");
                binding.editText4.setText("30");
                binding.editText5.setText("-0");
                binding.editText6.setText("0");
                K_form_position = 3;

                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.write));
                binding.KForm5.setTextColor(getResources().getColor(R.color.back));
                binding.KForm6.setTextColor(getResources().getColor(R.color.back));
                break;
            case R.id.K_form_5:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.editText1.setText("0");
                binding.editText2.setText("30");
                binding.editText3.setText("0");
                binding.editText4.setText("30");
                binding.editText5.setText("-0");
                binding.editText6.setText("0");
                K_form_position = 4;

                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                binding.KForm5.setTextColor(getResources().getColor(R.color.write));
                binding.KForm6.setTextColor(getResources().getColor(R.color.back));
                break;

            case R.id.K_form_6:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.editText1.setText("0");
                binding.editText2.setText("30");
                binding.editText3.setText("0");
                binding.editText4.setText("30");
                binding.editText5.setText("-0");
                binding.editText6.setText("0");
                K_form_position = 5;

                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                binding.KForm5.setTextColor(getResources().getColor(R.color.back));
                binding.KForm6.setTextColor(getResources().getColor(R.color.write));
                break;
        }
    }

    private int et_1;
    private int et_2;
    private int et_3;
    private int et_4;
    private int et_5;
    private int et_6;

    private void getOtherDetailByCode2(final String codeName, final String status) {
        Http.getDetails(codeName, new LogCallBack() {//获取买方卖方、内外盘、换手率等
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", "错误" + e.toString());
                if(codeName.contains("sh603499")){
                    dismissLoadingDialog();
                }
            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                if(codeName.contains("sh603499")){
                    dismissLoadingDialog();
                }
                getOtherDetail2(result, status);

            }
        });
    }


    private void getOtherDetail2(String result, String codeName) {
        if (!result.contains(";")) {
            return;
        }
        String[] info = result.split(";");
        for (int i = 0; i < info.length; i++) {
            if (!info[i].contains("~")) {
                continue;
            }
            String[] details = info[i].split("~");
            if (TextUtils.isEmpty(details[38]) || TextUtils.isEmpty(details[49]) || details[38].equals("")) {
                continue;
            }

            switch (K_form_position) {
                case 0:
                    if (Double.parseDouble(details[38]) >= et_1 && Double.parseDouble(details[38]) <= et_2
                            && Double.parseDouble(details[49]) >= et_3 && Double.parseDouble(details[49]) <= et_4
                            && Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6) {

                        sendMessage4(codeName, details);
                    }
                    break;
                case 1: //锤子： 最高=最新价  开盘-最低 大于 最高-最新 1倍
                    if (Double.parseDouble(details[38]) >= et_1 && Double.parseDouble(details[38]) <= et_2
                            && Double.parseDouble(details[49]) >= et_3 && Double.parseDouble(details[49]) <= et_4
                            && Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[33]) == Double.parseDouble(details[3]) //当前价等于最高价
                            && (((Double.parseDouble(details[5]) - Double.parseDouble(details[34])) == 0 ? 0.01 : (Double.parseDouble(details[5]) - Double.parseDouble(details[34]))) / (Double.parseDouble(details[33]) - Double.parseDouble(details[5]) == 0 ? 0.01 : Math.abs(Double.parseDouble(details[33]) - Double.parseDouble(details[5]) == 0 ? 0.01 : Double.parseDouble(details[33]) - Double.parseDouble(details[5])))) > 2
                            && ((Double.parseDouble(details[5]) - Double.parseDouble(details[34])) > (Double.parseDouble(details[33]) - Double.parseDouble(details[5])))
                            && ! details[35].split("/")[0].equals("0.00")) {

                        sendMessage4(codeName, details);
                    }
                    break;
                case 2://光脚:  最低价= 开盘价
                    Log.i("TAG", "出错=" + details[38]);
                    if (Double.parseDouble(details[38]) >= et_1 && Double.parseDouble(details[38]) <= et_2
                            && Double.parseDouble(details[49]) >= et_3 && Double.parseDouble(details[49]) <= et_4
                            && Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[5]) == Double.parseDouble(details[34])
                            && Double.parseDouble(details[5]) != 0
                            && ! details[35].split("/")[0].equals("0.00")) {

                        sendMessage4(codeName, details);
                    }
                    break;

                case 3://光头光脚:  最低价= 开盘价 && 最高价=当前价
                    if (Double.parseDouble(details[38]) >= et_1 && Double.parseDouble(details[38]) <= et_2
                            && Double.parseDouble(details[49]) >= et_3 && Double.parseDouble(details[49]) <= et_4
                            && Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[5]) == Double.parseDouble(details[34])
                            && Double.parseDouble(details[5]) != 0
                            && Double.parseDouble(details[3]) == Double.parseDouble(details[33])
                            && ! details[35].split("/")[0].equals("0.00")) {

                        sendMessage4(codeName, details);
                    }
                    break;

                case 4://光头 当前价=最高价
                    if (Double.parseDouble(details[38]) >= et_1 && Double.parseDouble(details[38]) <= et_2
                            && Double.parseDouble(details[49]) >= et_3 && Double.parseDouble(details[49]) <= et_4
                            && Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[3]) == Double.parseDouble(details[33])
                            && ! details[35].split("/")[0].equals("0.00")) {

                        sendMessage4(codeName, details);
                    }
                    break;

                case 5://十字星
                    if (Double.parseDouble(details[38]) >= et_1 && Double.parseDouble(details[38]) <= et_2
                            && Double.parseDouble(details[49]) >= et_3 && Double.parseDouble(details[49]) <= et_4
                            && Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[3]) == Double.parseDouble(details[5])
                            && ! details[35].split("/")[0].equals("0.00")) {

                        sendMessage4(codeName, details);
                    }
                    break;
            }
        }
    }
    private void sendMessage4(String codeName, String[] details) {
        Http.getBeanByDay(codeName+details[2] + code, new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int c, final String result) {

                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONObject a = jsonObject.getJSONObject("data").getJSONObject(codeName+details[2]);
                AllBeanDay beanDay = JSON.parseObject(a.toJSONString(), AllBeanDay.class);

                String data = ResultUtils.newD240(beanDay,2);
                if (data.isEmpty()) {

                    return;
                }
                String[] str_15 = data.split("#");
                if(str_15.length<45){
                    return;
                }
                String[] newString_15_600 = ResultUtils.getString_15_number(str_15, 5, 5).split("->");

                List<IncomeBean> incomeBean_5 = new ArrayList<>();
                IncomeBean bean;
                for (int i = newString_15_600.length - 40; i < newString_15_600.length; i++) {
                    bean = new IncomeBean("string30", Double.parseDouble(str_15[str_15.length-1]));
                    incomeBean_5.add(bean);
                }

                String[] max_str_240= ResultUtils.getString_15_number(ResultUtils.newD240(beanDay,3).split("#"), 5, 5).split("->");
                String[] min_str_240= ResultUtils.getString_15_number(ResultUtils.newD240(beanDay,4).split("#"), 5, 5).split("->");

                List<CompositeIndexBean> incomeBean_max =new ArrayList<>();
                for (int i = 0; i < 40; i++) {
                    incomeBean_max.add(new CompositeIndexBean("max_str_240",Double.parseDouble(max_str_240[max_str_240.length-1])));
                }

                List<CompositeIndexBean> incomeBean_min =new ArrayList<>();
                for (int i = 0; i < 40; i++) {
                    incomeBean_min.add(new CompositeIndexBean("max_str_240",Double.parseDouble(min_str_240[min_str_240.length-1])));
                }


                float min_5 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMin_number(str_15, str_15.length) - 0.1));
                float max_5 = Float.parseFloat(ResultUtils.getData(ResultUtils.getMan_number(str_15, str_15.length) + 0.1));

                List<CompositeIndexBean> incomeBean_3_5 = new ArrayList<>(40);
                List<CompositeIndexBean> incomeBean_4_5 = new ArrayList<>(40);
                List<CompositeIndexBean> incomeBean_6_5 = new ArrayList<>(40);
                String[] newString_15_3 = ResultUtils.getString_15_number(str_15, string3, string6).split("->");
                String[] newString_15_4 = ResultUtils.getString_15_number(str_15, string4, string6).split("->");
                String[] newString_15_6 = ResultUtils.getString_15_number(str_15, string6, string6).split("->");

                for (int i = newString_15_3.length - 40; i < newString_15_3.length; i++) {
                    incomeBean_3_5.add(new CompositeIndexBean(String.valueOf(string3), Double.parseDouble(newString_15_3[i])));
                }

                for (int i = newString_15_4.length - 40; i < newString_15_4.length; i++) {
                    incomeBean_4_5.add(new CompositeIndexBean(String.valueOf(string4), Double.parseDouble(newString_15_4[i])));
                }

                for (int i = newString_15_6.length - 40; i < newString_15_6.length; i++) {
                    incomeBean_6_5.add(new CompositeIndexBean(String.valueOf(string6), Double.parseDouble(newString_15_6[i])));
                }

                AdapterBean1 detail=new AdapterBean1(codeName+details[2],details,max_5,min_5,incomeBean_5,incomeBean_3_5,incomeBean_4_5,incomeBean_6_5,string3,string4,string6);
                detail.setIncomeBean_max(incomeBean_max);
                detail.setIncomeBean_min(incomeBean_min);
                Message msg = Message.obtain();
                msg.arg1 = 0;
                msg.obj = detail;
                if (handler != null) {
                    handler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    private String getData(double price) {
        return decimalFormat.format(price);
    }


    @SuppressLint("WrongConstant")
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);

    }


    private void getHttp2() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder;

                et_1 = Integer.parseInt(binding.editText1.getText().toString());
                et_2 = Integer.parseInt(binding.editText2.getText().toString());
                et_3 = Integer.parseInt(binding.editText3.getText().toString());
                et_4 = Integer.parseInt(binding.editText4.getText().toString());
                et_5 = Integer.parseInt(binding.editText5.getText().toString());
                et_6 = Integer.parseInt(binding.editText6.getText().toString());

                for (int i = 1; i <= 35; i++) {
                    builder = new StringBuilder();
                    for (int j = 100 * (i - 1); j < 100 * i; j++) {
                        if (j < 10) {
                            builder.append("sz00000").append(j).append(",");
                        } else if (j < 100) {
                            builder.append("sz0000").append(j).append(",");
                        } else if (j < 1000) {
                            builder.append("sz000").append(j).append(",");
                        } else {
                            builder.append("sz00").append(j).append(",");
                        }
                    }
                    getOtherDetailByCode2(builder.toString(), "sz");
                }

                for (int i = 1; i <= 35; i++) {
                    builder = new StringBuilder();
                    for (int j = 100 * (i - 1); j < 100 * i; j++) {
                        if (j < 10) {
                            builder.append("sh60000").append(j).append(",");
                        } else if (j < 100) {
                            builder.append("sh6000").append(j).append(",");
                        } else if (j < 1000) {
                            builder.append("sh600").append(j).append(",");
                        } else {
                            builder.append("sh60").append(j).append(",");
                        }
                    }
                    getOtherDetailByCode2(builder.toString(), "sh");
                }
            }
        }).start();
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

    public Bitmap viewConversionBitmap(View v) {
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