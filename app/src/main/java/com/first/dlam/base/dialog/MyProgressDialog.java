package com.first.dlam.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.first.dlam.R;

public class MyProgressDialog {

    private Dialog mDialog;
    private TextView title;
    private AnimationDrawable animationDrawable = null;

    public MyProgressDialog(Context context, String message) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_loading, null);

        title =view.findViewById(R.id.tvMessage);
        title.setText(message);

        mDialog = new Dialog(context, R.style.dialog);
        mDialog.getWindow().setDimAmount(0);
        mDialog.setContentView(view);//设置该进度对话框的要显示的view
        mDialog.setCanceledOnTouchOutside(false);

    }

    public void setText(String msg){
        title.setText(msg);
    }

    /**
     * 显示出进度对话框
     */
    public void show() {
        try {
            if (null != mDialog) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
    }

    /**
     * 将进度对话框给取消显示
     */
    public void dismiss() {
        try {
            if ((this.mDialog != null) && this.mDialog.isShowing()) {
                this.mDialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            // Handle or log or ignore
        } catch (final Exception e) {
            // Handle or log or ignore
        } finally {

        }


    }

    /**
     * 判断进度对话框是否正在显示
     *
     * @return
     */
    public boolean isShowing() {
        if (null != mDialog && mDialog.isShowing()) {
            return true;
        }
        return false;
    }
}