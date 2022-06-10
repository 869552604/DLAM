package com.first.dlam.base.dialog;
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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.first.dlam.MainActivity;
import com.first.dlam.R;
import com.first.dlam.databinding.DialogUpdateBinding;

public class UpdateDialog extends DialogFragment implements View.OnClickListener {

    private DialogUpdateBinding binding;

    private Updata updata;

    public UpdateDialog(Updata updata) {
        this.updata=updata;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setCancelable(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.inflate(inflater, R.layout.dialog_update,container,false);

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
                if(updata!=null){
                    updata.upData();
                    dismiss();
                }
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
        void upData();
    }

}
