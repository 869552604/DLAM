package com.first.dlam.base.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.first.dlam.R;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.fragment.Fragment1_3;
import com.first.dlam.base.views.HorizontalBarView;
import com.first.dlam.bean.BaseEvent;
import com.first.dlam.databinding.DialogHashmapBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class HashMapDialog extends DialogFragment {
    private Context context;
    private DialogHashmapBinding binding;
    private SharedPreferencesUtil sharedPreferencesUtil;

    public HashMapDialog(Context context) {
        this.context=context;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        sharedPreferencesUtil = new SharedPreferencesUtil(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.inflate(inflater, R.layout.dialog_hashmap,container,false);

        if(Fragment1_3.isDestory){
            hoBarEntities = new ArrayList<>();
            if(sharedPreferencesUtil.getHashMap()!=null){
                for(Map.Entry<String, Integer> entry : sharedPreferencesUtil.getHashMap().entrySet()) {
                    hoBarEntity = new HorizontalBarView.HoBarEntity();
                    hoBarEntity.content =entry.getKey();
                    hoBarEntity.count = entry.getValue();
                    hoBarEntities.add(hoBarEntity);
                }
                Collections.sort(hoBarEntities, new HorizontalBarView.order());
                binding.bar.setHoBarData(hoBarEntities);
            }
        }

        return binding.getRoot();
    }
    ArrayList<HorizontalBarView.HoBarEntity> hoBarEntities;
    HorizontalBarView.HoBarEntity hoBarEntity;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(BaseEvent event) {
        hoBarEntities = new ArrayList<>();
        if(sharedPreferencesUtil.getHashMap()!=null){
            for(Map.Entry<String, Integer> entry : sharedPreferencesUtil.getHashMap().entrySet()) {
                hoBarEntity = new HorizontalBarView.HoBarEntity();
                hoBarEntity.content =entry.getKey();
                hoBarEntity.count = entry.getValue();
                hoBarEntities.add(hoBarEntity);
            }
            Collections.sort(hoBarEntities, new HorizontalBarView.order());
            binding.bar.setHoBarData(hoBarEntities);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Window mWindow = getDialog().getWindow();

        WindowManager windowManager = mWindow.getWindowManager();
        Display d = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.width = (int) (d.getWidth() * 0.95); //设置宽度占比
        mLayoutParams.height = (int) (d.getHeight() * 0.6);//设置高度自适应
        mLayoutParams.gravity = Gravity.CENTER; //设置显示位置

        // 设置activity变暗的效果
        mLayoutParams.dimAmount = 0.6f;  //0是毫无效果  1是全黑
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        mWindow.setAttributes(mLayoutParams);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//隐藏dialog自带的背景，才会显示xml设置的背景

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
