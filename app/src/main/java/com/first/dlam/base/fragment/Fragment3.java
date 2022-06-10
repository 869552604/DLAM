package com.first.dlam.base.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.first.dlam.R;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.ShareActivity;
import com.first.dlam.databinding.Fragment3Binding;
import com.first.dlam.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends BaseFragment implements View.OnClickListener {

    private Fragment3Binding binding;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_3,container,false);

        binding.tvZFB.setOnClickListener(this);
        binding.share.setOnClickListener(this);
        binding.tvMT.setOnClickListener(this);
        binding.tvELM.setOnClickListener(this);
        binding.tvFQ.setOnClickListener(this);
        binding.tvcode.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvZFB:
                saveImage(((BitmapDrawable)binding.imgZFB.getDrawable()).getBitmap());
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Intent intent =new Intent(getContext(), ShareActivity.class);
                startActivity(intent);
                break;

            case R.id.tvMT:
                saveImage(((BitmapDrawable)binding.imgMT.getDrawable()).getBitmap());
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvELM:
                saveImage(((BitmapDrawable)binding.imgELM.getDrawable()).getBitmap());
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
             case R.id.tvFQ:
                saveImage(((BitmapDrawable)binding.imgFQ.getDrawable()).getBitmap());
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvcode:
                if (Build.VERSION.SDK_INT >= 11) {
                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText("7191598758");
                } else {
                    // 得到剪贴板管理器
                    android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "7191598758"));
                }
                Toast.makeText(getContext(), "复制成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void saveImage(Bitmap screenShot) {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        if(value){
                            startPermissionSuccess(screenShot);
                        }else {
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

    private void startPermissionSuccess(Bitmap screenShot) {//获取读写sd卡权限成功回调
        if (screenShot == null) {
            return;
        }
        String fileName = "dlam" + TimeUtils.getNowMills() + ".jpg";
        try {
            boolean state = Utils.addPictureToAlbum(getContext(), screenShot, fileName);
            if (state) {
                Toast.makeText(getContext(),"保存成功",Toast.LENGTH_SHORT).show();
                screenShot=null;
            } else {
                Toast.makeText(getContext(),"保存失败",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
