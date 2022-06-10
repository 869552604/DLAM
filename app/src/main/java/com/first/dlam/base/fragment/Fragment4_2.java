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
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.first.dlam.bean.AllBean15;
import com.first.dlam.bean.AllBean30;
import com.first.dlam.bean.AllBean60;
import com.first.dlam.data.CompositeIndexBean;
import com.first.dlam.data.IncomeBean;
import com.first.dlam.databinding.Fragment42Binding;
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

public class Fragment4_2 extends BaseFragment implements View.OnClickListener {
    private Fragment42Binding binding;
    private Bitmap screenShot = null;
    private MainHandler handler;
    private String code = ",m30,,70";
    private int model = 2; // 1:预测模式：那收盘时的K线数据，因为最后一条数据是当前分时，可以变化的。  2.实时模式： 不需要判断，直接拿全部数据。
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private boolean ifCheck5 = false;
    private int string3 = 3;
    private int string4 = 4;
    private int string6 = 6;

    private int K_form_position = 0;

    private FirstAdapter adapter;
    private List<AdapterBean1> list;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment4_2,container,false);
        handler = new MainHandler(this);
        initListener();
        binding.switchButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ifCheck5 = b;
            }
        });
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void initListener(){
        binding.back.setOnClickListener(this);
        binding.getCodeDetail.setOnClickListener(this);
        binding.KModel1.setOnClickListener(this);
        binding.KModel2.setOnClickListener(this);
        binding.KModel3.setOnClickListener(this);


        binding.KForm1.setOnClickListener(this);
        binding.KForm2.setOnClickListener(this);
        binding.KForm3.setOnClickListener(this);
        binding.KForm4.setOnClickListener(this);
        binding.KForm5.setOnClickListener(this);
        binding.KForm6.setOnClickListener(this);


        initRecyclerView();
        initAdapter();

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());

        binding.editText31.setText(sharedPreferencesUtil.getET_3());
        binding.editText41.setText(sharedPreferencesUtil.getET_4());
        binding.editText61.setText(sharedPreferencesUtil.getET_6());
    }

    @SuppressLint("WrongConstant")
    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setNestedScrollingEnabled(false);

    }

    private void initAdapter() {
        adapter = new FirstAdapter(getContext());
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setNotDoAnimationCount(3);
        adapter.bindToRecyclerView(binding.recyclerView);
        binding.recyclerView.setAdapter(adapter);
    }

    private void addData(AdapterBean1 bean) {
        adapter.addData(bean);
        binding.number.setText("数量:" + list.size());
    }

    public static class MainHandler extends Handler {
        WeakReference<Fragment4_2> fragment;

        private MainHandler(Fragment4_2 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            Fragment4_2 theActivity = fragment.get();
            if (theActivity == null) {
                return;
            }
            if (msg.arg1 == 0) {
                theActivity.addData((AdapterBean1) msg.obj);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                screenShot = viewConversionBitmap(binding.contentLayout);
                saveImage();
                break;
            case R.id.getCodeDetail:
                if (TextUtils.isEmpty(binding.editText31.getText().toString())) {
                    Toast.makeText(getContext(), "第一个日线不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(binding.editText41.getText().toString())) {
                    Toast.makeText(getContext(), "第二个日线不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(binding.editText61.getText().toString())) {
                    Toast.makeText(getContext(), "第三个日线不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(binding.editText5.getText().toString()) || TextUtils.isEmpty(binding.editText6.getText().toString())) {
                    Toast.makeText(getContext(), "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                if (Integer.parseInt(binding.editText31.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "第一个日线不能大于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText31.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "第一个日线不能小于2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText41.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "第二个日线不能大于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText41.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "第二个日线不能小于2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText61.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "第三个日线不能大于20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText61.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "第三个日线不能小于2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(binding.editText61.getText().toString())>Integer.parseInt(binding.editText41.getText().toString())
                        && Integer.parseInt(binding.editText61.getText().toString())>Integer.parseInt(binding.editText31.getText().toString())
                        &&Integer.parseInt(binding.editText41.getText().toString())>Integer.parseInt(binding.editText31.getText().toString())) {

                    Toast.makeText(getContext(), "正在筛选上证跟深证符合要求的股票", Toast.LENGTH_LONG).show();

                    string3 = Integer.parseInt(binding.editText31.getText().toString());
                    string4 = Integer.parseInt(binding.editText41.getText().toString());
                    string6 = Integer.parseInt(binding.editText61.getText().toString());


                    list =new ArrayList<>();
                    adapter.setNewData(list);
                    binding.number.setText("数量:" + list.size());
                    showLoadingDialog("筛选中...");
                    getHttp2();
                }else{
                    Toast.makeText(getContext(), "日线从左到右应该是从小到大排序", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.K_model_1:
                binding.KModel1.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KModel2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KModel3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                code = ",m15,,70";
                binding.KModel1.setTextColor(getResources().getColor(R.color.write));
                binding.KModel2.setTextColor(getResources().getColor(R.color.back));
                binding.KModel3.setTextColor(getResources().getColor(R.color.back));
                break;
            case R.id.K_model_2:
                binding.KModel1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KModel2.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KModel3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                code = ",m30,,70";
                binding.KModel1.setTextColor(getResources().getColor(R.color.back));
                binding.KModel2.setTextColor(getResources().getColor(R.color.write));
                binding.KModel3.setTextColor(getResources().getColor(R.color.back));
                break;
            case R.id.K_model_3:
                binding.KModel1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KModel2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KModel3.setBackground(getResources().getDrawable(R.drawable.button_press));
                code = ",m60,,70";
                binding.KModel1.setTextColor(getResources().getColor(R.color.back));
                binding.KModel2.setTextColor(getResources().getColor(R.color.back));
                binding.KModel3.setTextColor(getResources().getColor(R.color.write));
                break;
            case R.id.K_form_1:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm5.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm6.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.editText5.setText("2");
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
                binding.editText5.setText("-10");
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
                binding.editText5.setText("-10");
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
                binding.editText5.setText("-10");
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
                binding.editText5.setText("-10");
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
                binding.editText5.setText("-10");
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

    private int et_5;
    private int et_6;

    private void getHttp2() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder;


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
                    if (Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6) {

                        sendMessage4(codeName + details[2], details);
                    }
                    break;
                case 1: //锤子： 最高=最新价  开盘-最低 大于 最高-最新 1倍
                    if (Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[33]) == Double.parseDouble(details[3]) //当前价等于最高价
                            && (((Double.parseDouble(details[5]) - Double.parseDouble(details[34])) == 0 ? 0.01 : (Double.parseDouble(details[5]) - Double.parseDouble(details[34]))) / (Double.parseDouble(details[33]) - Double.parseDouble(details[5]) == 0 ? 0.01 : Math.abs(Double.parseDouble(details[33]) - Double.parseDouble(details[5]) == 0 ? 0.01 : Double.parseDouble(details[33]) - Double.parseDouble(details[5])))) > 2
                            && ((Double.parseDouble(details[5]) - Double.parseDouble(details[34])) > (Double.parseDouble(details[33]) - Double.parseDouble(details[5])))) {

                        sendMessage4(codeName + details[2], details);
                    }
                    break;
                case 2://光脚:  最低价= 开盘价
                    if (Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[5]) == Double.parseDouble(details[34])
                            && Double.parseDouble(details[5]) != 0) {

                        sendMessage4(codeName + details[2], details);
                    }
                    break;

                case 3://光头光脚:  最低价= 开盘价 && 最高价=当前价
                    if (Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[5]) == Double.parseDouble(details[34])
                            && Double.parseDouble(details[5]) != 0
                            && Double.parseDouble(details[3]) == Double.parseDouble(details[33])) {

                        sendMessage4(codeName + details[2], details);
                    }
                    break;

                case 4://光头 当前价=最高价
                    if (Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[3]) == Double.parseDouble(details[33])) {

                        sendMessage4(codeName + details[2], details);
                    }
                    break;

                case 5://十字星
                    if (Double.parseDouble(details[32]) >= et_5
                            && Double.parseDouble(details[45]) >= et_6
                            && Double.parseDouble(details[3]) == Double.parseDouble(details[5])) {

                        sendMessage4(codeName + details[2], details);
                    }
                    break;

            }
        }
    }

    private void sendMessage4(String codeName, String[] details) {
        Http.getBeanByMM(codeName + code, new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int c, final String result) {


                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);

                if (code.equals(",m15,,70")) {
                    AllBean15 bean15 = JSON.parseObject(a.toJSONString(), AllBean15.class);
                    String data = ResultUtils.D15(bean15,2,ifCheck5);
                    if (data.isEmpty()) {

                        return;
                    }
                    String[] str_15 = data.split("#");
                    double mean3 = getString_15_number(str_15, string3, string6);
                    double mean4 = getString_15_number(str_15, string4, string6);
                    double mean6 = getString_15_number(str_15, string6, string6);
                    if (mean3 > mean4 && mean3 > mean6 && mean4 > mean6) {
                        String[] max_str_240= ResultUtils.getString_15_number(ResultUtils.D15(bean15,3,ifCheck5).split("#"), 5, 5).split("->");
                        String[] min_str_240= ResultUtils.getString_15_number(ResultUtils.D15(bean15,4,ifCheck5).split("#"), 5, 5).split("->");


                        setData(data,codeName,details,ResultUtils.getD15Time(a.toJSONString(),ifCheck5),max_str_240,min_str_240);
                    }
                } else if (code.equals(",m30,,70")) {
                    AllBean30 bean15 = JSON.parseObject(a.toJSONString(), AllBean30.class);
                    String data = ResultUtils.D30(bean15,2,ifCheck5);
                    if (data.isEmpty()) {

                        return;
                    }
                    String[] str_30 = data.split("#");
                    double mean3 = getString_15_number(str_30, string3, string6);
                    double mean4 = getString_15_number(str_30, string4, string6);
                    double mean6 = getString_15_number(str_30, string6, string6);
                    if (mean3 > mean4 && mean3 > mean6 && mean4 > mean6) {
                        String[] max_str_240= ResultUtils.getString_15_number(ResultUtils.D30(bean15,3,ifCheck5).split("#"), 5, 5).split("->");
                        String[] min_str_240= ResultUtils.getString_15_number(ResultUtils.D30(bean15,4,ifCheck5).split("#"), 5, 5).split("->");

                        setData(data,codeName,details,ResultUtils.getD30Time(a.toJSONString(),ifCheck5),max_str_240,min_str_240);
                    }
                } else if (code.equals(",m60,,70")) {
                    AllBean60 bean15 = JSON.parseObject(a.toJSONString(), AllBean60.class);
                    String data = ResultUtils.D60(bean15,2,ifCheck5);
                    if (data.isEmpty()) {

                        return;
                    }
                    String[] str_60 = data.split("#");
                    double mean3 = getString_15_number(str_60, string3, string6);
                    double mean4 = getString_15_number(str_60, string4, string6);
                    double mean6 = getString_15_number(str_60, string6, string6);
                    if (mean3 > mean4 && mean3 > mean6 && mean4 > mean6) {
                        String[] max_str_240= ResultUtils.getString_15_number(ResultUtils.D60(bean15,3,ifCheck5).split("#"), 5, 5).split("->");
                        String[] min_str_240= ResultUtils.getString_15_number(ResultUtils.D60(bean15,4,ifCheck5).split("#"), 5, 5).split("->");
                        setData(data,codeName,details,ResultUtils.getD60Time(a.toJSONString(),ifCheck5),max_str_240,min_str_240);
                    }
                }

            }
        });
    }


    private Double getString_15_number(String[] str_15, int number, int string6) { //根据输入框内容以及数字算出对应均线
        StringBuilder builder_15_3 = new StringBuilder();
        int start = number == string6 ? 0 : string6 - number;
        for (int i = start; i < str_15.length - (number - 1); i++) {
            double sum = 0;
            for (int j = i; j < number + i; j++) {
                sum += Double.parseDouble(str_15[j]);
            }
            builder_15_3.append(getData(sum / (double) number)).append("->");
        }
        if (builder_15_3.toString().length() == 0) {
            return 0.0;
        }
        String[] a = builder_15_3.toString().substring(0, builder_15_3.toString().length() - 2).split("->");

        return Double.parseDouble(a[a.length - 1]);
    }


    private String getData(double price) {
        return decimalFormat.format(price);
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
        String fileName = "study_report" + TimeUtils.getNowMills() + ".jpg";
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }


    public void setData(String data,String codeName, String[] details,String time,String[] max_str_240,String[] min_str_240){
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

        AdapterBean1 detail=new AdapterBean1(codeName,details,max_5,min_5,incomeBean_5,incomeBean_3_5,incomeBean_4_5,incomeBean_6_5,string3,string4,string6);
        detail.setTime(time);
        detail.setIncomeBean_max(incomeBean_max);
        detail.setIncomeBean_min(incomeBean_min);
        Message msg = Message.obtain();
        msg.arg1 = 0;
        msg.obj = detail;
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }
}
