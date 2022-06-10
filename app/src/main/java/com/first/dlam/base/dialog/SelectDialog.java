package com.first.dlam.base.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.first.dlam.R;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.databinding.DialogSelectBinding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;

import java.io.IOException;

import okhttp3.Call;

public class SelectDialog extends DialogFragment implements View.OnClickListener {

    private DialogSelectBinding binding;

    private Updata updata;
    private String code;

    public SelectDialog(Updata updata,String code) {
        this.updata=updata;
        this.code=code;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCancelable(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.inflate(inflater, R.layout.dialog_select,container,false);
        binding.etCode.setText(code);

        binding.left.setOnClickListener(this);
        binding.right.setOnClickListener(this);


        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left:
                dismiss();
                break;

            case R.id.right:

                if (TextUtils.isEmpty(binding.etCode.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确的code值", Toast.LENGTH_SHORT).show();
                    return;
                }
                getCodeDetails(binding.etCode.getText().toString());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Window mWindow = getDialog().getWindow();

        WindowManager windowManager = mWindow.getWindowManager();
        Display d = windowManager.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.width = (int) (d.getWidth() * 0.80); //设置宽度占比
        mLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;//设置高度自适应
        mLayoutParams.gravity = Gravity.CENTER; //设置显示位置

        // 设置activity变暗的效果
        mLayoutParams.dimAmount = 0.6f;  //0是毫无效果  1是全黑
        mLayoutParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        mWindow.setAttributes(mLayoutParams);
        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//隐藏dialog自带的背景，才会显示xml设置的背景

    }

    public interface Updata{
        void upData(String code);
    }

    private void getCodeDetails(final String codeName) {
        Http.getBeanByMM(codeName + ",m5,,2", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                        try {

                            JSONObject jsonObject = JSONObject.parseObject(result);
                            jsonObject.getJSONObject("data").getJSONObject(codeName);
                            JSONObject a = jsonObject.getJSONObject("data").getJSONObject(codeName);
                            AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                            if (bean5.getM5() == null || bean5.getM5().size() == 0) {
                                Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();
                                return ;
                            }

                            updata.upData(binding.etCode.getText().toString());
                            dismiss();

                        } catch (ClassCastException e) {
                            Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();

                        }
            }
        });
    }
}
