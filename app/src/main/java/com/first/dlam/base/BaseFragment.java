package com.first.dlam.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.first.dlam.base.dialog.MyProgressDialog;
import com.first.dlam.bean.BaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2022/4/25.
 * xfs
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            init(inflater,container,savedInstanceState);
            mRootView = getLayoutResId();

        }
        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return mRootView;
    }

    /**
     * 初始化操作
     */
    public abstract void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * @return 布局文件id
     */
    public abstract View getLayoutResId();


    private long DIFF = 1000;
    private int lastButtonId = -1;
    private long lastClickTime = 0;
    private MyProgressDialog waitDialog;

    public boolean isFastDoubleClick(int buttonId) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < DIFF) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(BaseEvent event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    public void showLoadingDialog(String msg) {

        if (waitDialog == null) {
            waitDialog = new MyProgressDialog(getContext(), msg);
        }else{
            waitDialog.setText(msg);
        }
        if (!waitDialog.isShowing()) {
            waitDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (waitDialog != null) {
            if (waitDialog.isShowing()) {
                waitDialog.dismiss();
            }
        }
    }
}
