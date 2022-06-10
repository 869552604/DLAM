package com.first.dlam.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.first.dlam.R;
import com.first.dlam.databinding.ActivityShareBinding;
import com.first.dlam.utils.Utils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShareActivity extends BaseActivity implements View.OnClickListener {
    private ActivityShareBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share);

        binding.back.setOnClickListener(this);
        binding.tvZFB.setOnClickListener(this);
        binding.tvWX.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tvZFB:
                saveImage(((BitmapDrawable)binding.imgZFB.getDrawable()).getBitmap());
                break;
            case R.id.tvWX:
                saveImage(((BitmapDrawable)binding.imgWX.getDrawable()).getBitmap());
                break;
        }
    }

    private void saveImage(Bitmap screenShot) {
        RxPermissions rxPermissions = new RxPermissions(this);
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
                            Toast.makeText(ShareActivity.this, "拒绝权限", Toast.LENGTH_SHORT).show();
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
            boolean state = Utils.addPictureToAlbum(this, screenShot, fileName);
            if (state) {
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                screenShot=null;
            } else {
                Toast.makeText(this,"保存失败",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
