package com.first.dlam.base;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.adapter.HistoryAdapter;
import com.first.dlam.base.views.HorizontalBarView;
import com.first.dlam.bean.HistoryBean;
import com.first.dlam.databinding.ActivityHistoryBinding;
import com.first.dlam.utils.MyHashMap;
import com.first.dlam.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HistoryActivity extends BaseActivity implements View.OnClickListener {
    private ActivityHistoryBinding binding;
    private int currentPosition = 0;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private List<HistoryBean> datas = new ArrayList<>();
    private HistoryAdapter adapter;

    private SharedPreferencesUtil sp;
    private ArrayList<HorizontalBarView.HoBarEntity> hoBarEntities;
    private HorizontalBarView.HoBarEntity hoBarEntity;

    private Bitmap screenShot = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);


        sharedPreferencesUtil = new SharedPreferencesUtil(this);

        binding.back.setOnClickListener(this);
        binding.all.setOnClickListener(this);
        binding.download.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new HistoryAdapter(HistoryActivity.this);
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
        //??????recycleView????????????  ????????????recycleView????????????
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                adapter.notifyDataSetChanged();

                setData(position);
            }
        });

        //????????????RecycleView??????????????????
        adapter.setItemSelectedCallBack(new HistoryAdapter.ItemSelectedCallBack() {
            @Override
            public void convert(BaseViewHolder holder, int position) {

                if (position == currentPosition) {
                    ((TextView) holder.getView(R.id.content)).setTextColor(getResources().getColor(R.color.ceng));
                    holder.getView(R.id.view).setVisibility(View.VISIBLE);

                } else {
                    ((TextView) holder.getView(R.id.content)).setTextColor(getResources().getColor(R.color.back));
                    holder.getView(R.id.view).setVisibility(View.INVISIBLE);
                }

            }
        });

        try {
            getSp();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.all:
                if(datas.size()>0){
                    hoBarEntities = new ArrayList<>();
                    MyHashMap hashMap =new MyHashMap();
                    for (int i = 0; i < datas.size(); i++) {
                        sp = new SharedPreferencesUtil(HistoryActivity.this, datas.get(i).getTime());
                        if (sp.getHashMap() != null) {
                            for (Map.Entry<String, Integer> entry : sp.getHashMap().entrySet()) {
                                hashMap.put(entry.getKey(),entry.getValue());
                            }
                        }
                    }

                    for (MyHashMap.Entry<String, Integer> entry : hashMap.entrySet()){
                        hoBarEntity = new HorizontalBarView.HoBarEntity();
                        hoBarEntity.content = entry.getKey();
                        hoBarEntity.count = entry.getValue();
                        hoBarEntities.add(hoBarEntity);
                    }

                    Collections.sort(hoBarEntities, new HorizontalBarView.order());
                    binding.bar.setHoBarData(hoBarEntities);

                    currentPosition = -1;
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.download:
                screenShot = viewConversionBitmap(binding.contentLayout);
                saveImage();
                break;
        }
    }

    private void getSp() throws ParseException {
        // ??????
        File file = new File("/data/data/com.first.dlam/shared_prefs");
        // ???file??????????????????????????????????????????list???
        File[] listFile = file.listFiles();
        String fileName;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String week;
        // ???????????????????????????
        assert listFile != null;
        for (File file1 : listFile) {
            fileName = file1.toString().substring(file1.toString().lastIndexOf("/") + 1);
            if (!fileName.trim().equals(".xml") && fileName.length() >= 18) {
                if (fileName.substring(10, 18).equals(sharedPreferencesUtil.getCode_name())) {
                    Log.d("TAG", "findPreferenceData: " + fileName.substring(0, 18));
                    week=sdf.format(format.parse(fileName.substring(0,10)));
                    if(!week.contains("??????")
                    && !week.contains("??????")){

                        sp = new SharedPreferencesUtil(HistoryActivity.this, fileName.substring(0, 18));
                        if(sp.getHashMap().size()>1)
                        datas.add(new HistoryBean(fileName.substring(0, 18),week,"???: "+sp.getSHOW_PAN()+"??????:"+ResultUtils.formatNumberWithUnit(sp.getQIANG_CHOU(),1)
                        +"\n???: "+sp.getJUN_JIA()+"??????:"+ResultUtils.formatNumberWithUnit(sp.getGE_ROU(),1)
                        +"\n???: "+ ResultUtils.formatNumberWithUnit(sp.getZONG_SHOU(),1)+"?????????:"+ResultUtils.formatNumberWithUnit(String.valueOf(Integer.parseInt(sp.getQIANG_CHOU())-Integer.parseInt(sp.getGE_ROU())),1)));

                    }
                }
            }
        }
        adapter.setNewData(datas);

        if(datas.size()>0){
            setData(0);
        }
    }

    private void setData(int position){
        sp = new SharedPreferencesUtil(HistoryActivity.this, datas.get(position).getTime());
        hoBarEntities = new ArrayList<>();
        if (sp.getHashMap() != null) {
            for (Map.Entry<String, Integer> entry : sp.getHashMap().entrySet()) {
                hoBarEntity = new HorizontalBarView.HoBarEntity();
                hoBarEntity.content = entry.getKey();
                hoBarEntity.count = entry.getValue();
                hoBarEntities.add(hoBarEntity);
            }
            Collections.sort(hoBarEntities, new HorizontalBarView.order());
            binding.bar.setHoBarData(hoBarEntities);
        }
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
                            Toast.makeText(HistoryActivity.this, "????????????", Toast.LENGTH_SHORT).show();
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

    private void startPermissionSuccess() {//????????????sd?????????????????????
        if (screenShot == null) {
            return;
        }
        String fileName = "dlam" + TimeUtils.getNowMills() + ".jpg";
        try {
            boolean state = Utils.addPictureToAlbum(HistoryActivity.this, screenShot, fileName);
            if (state) {
                Toast.makeText(HistoryActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                screenShot = null;
            } else {
                Toast.makeText(HistoryActivity.this, "????????????", Toast.LENGTH_SHORT).show();
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
        /** ???????????????canvas????????????????????????????????? */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}