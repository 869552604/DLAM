package com.first.dlam.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.first.dlam.R;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.databinding.Fragment15Binding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;

import java.io.IOException;

import okhttp3.Call;

import static com.blankj.utilcode.util.ThreadUtils.runOnUiThread;

public class Fragment1_5 extends BaseFragment {

    private Fragment15Binding binding;
    private SharedPreferencesUtil sharedPreferencesUtil;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment1_5,container,false);

        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        getDetail(sharedPreferencesUtil.getCode_name());

        binding.getCodeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetail(sharedPreferencesUtil.getCode_name());
            }
        });
    }

    private void getDetail(final String codeName){
        Http.getBeanByDay(codeName + ",day,,,10,qfq", new LogCallBack() {
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

                            String url = "http://image.sinajs.cn/newchart/min/n/"+codeName+".gif";
                            Glide.with(getContext())
                                    .load(url)
                                    .skipMemoryCache(true) // 不使用内存缓存
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                                    .into(binding.fenshi);

                            String b ="http://image.sinajs.cn/newchart/daily/n/"+codeName+".gif";
                            Glide.with(getContext())
                                    .load(b)
                                    .skipMemoryCache(true) // 不使用内存缓存
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                                    .into(binding.rixian);

                            String c ="http://image.sinajs.cn/newchart/weekly/n/"+codeName+".gif";
                            Glide.with(getContext())
                                    .load(c)
                                    .skipMemoryCache(true) // 不使用内存缓存
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                                    .into(binding.zhouxian);

                            String d ="http://image.sinajs.cn/newchart/monthly/n/"+codeName+".gif";
                            Glide.with(getContext())
                                    .load(d)
                                    .skipMemoryCache(true) // 不使用内存缓存
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                                    .into(binding.yuexian);

                        } catch (ClassCastException e) {
                            Toast.makeText(getContext(), "请输入正确的code值", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }
}
