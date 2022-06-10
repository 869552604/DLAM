package com.first.dlam.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.first.dlam.bean.BaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2022/4/24.
 * xfs
 */

public class BaseActivity extends FragmentActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().unregister(this);
    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void event(BaseEvent event) {
//
//    }
}
