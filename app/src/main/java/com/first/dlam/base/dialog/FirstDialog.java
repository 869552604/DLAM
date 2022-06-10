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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.first.dlam.R;
import com.first.dlam.databinding.WindowLayoutBinding;

public class FirstDialog extends DialogFragment {
    private Context context;
    private String url;
    private WindowLayoutBinding binding;
    private String tag;
    public FirstDialog(Context context, String url) {
        this.context=context;
        this.url=url;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.inflate(inflater, R.layout.window_layout,container,false);

                Glide.with(context)
                .load("http://image.sinajs.cn/newchart/min/n/"+url+".gif")
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .into(binding.image);

        String b ="http://image.sinajs.cn/newchart/daily/n/"+url+".gif";
        Glide.with(getContext())
                .load(b)
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .into(binding.image2);

        String c ="http://image.sinajs.cn/newchart/weekly/n/"+url+".gif";
        Glide.with(getContext())
                .load(c)
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .into(binding.image3);

        String d ="http://image.sinajs.cn/newchart/monthly/n/"+url+".gif";
        Glide.with(getContext())
                .load(d)
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .into(binding.image4);

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        Window mWindow = getDialog().getWindow();

        WindowManager windowManager = mWindow.getWindowManager();
        Display d = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.width = (int) (d.getWidth() * 0.95); //设置宽度占比
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;//设置高度自适应
        mLayoutParams.gravity = Gravity.CENTER; //设置显示位置

        // 设置activity变暗的效果
        mLayoutParams.dimAmount = 0.6f;  //0是毫无效果  1是全黑
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        mWindow.setAttributes(mLayoutParams);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//隐藏dialog自带的背景，才会显示xml设置的背景

    }

}
