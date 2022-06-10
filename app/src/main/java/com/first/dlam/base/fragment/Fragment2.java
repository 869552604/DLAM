package com.first.dlam.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.adapter.QueueAdapter;
import com.first.dlam.base.dialog.SelectDialog;
import com.first.dlam.base.views.TimeBarView;
import com.first.dlam.bean.FilterBean;
import com.first.dlam.bean.QueueBean;
import com.first.dlam.databinding.Fragment2Binding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 *
 */
public class Fragment2 extends BaseFragment implements View.OnClickListener {
    private Fragment2Binding binding;
    private volatile String list;
    private QueueAdapter adapter;
    private List<QueueBean> datas;
    public static boolean isDestory =true;
    private MyRunnable myRunnable;
    private static MainHandler handler;
    private SharedPreferencesUtil sharedPreferencesUtil;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_2,container,false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new QueueAdapter(getContext());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setNestedScrollingEnabled(false);

        binding.otherDetailButton.setOnClickListener(this);
        binding.code1.setOnClickListener(this);
        binding.code2.setOnClickListener(this);

        handler = new MainHandler(this);

        binding.code1.setText(sharedPreferencesUtil.getCode1());
        binding.code2.setText(sharedPreferencesUtil.getCode2());

        getList();
        initData();
    }


    private void getList(){
        StringBuilder builder =new StringBuilder();
        if(!TextUtils.isEmpty(binding.code1.getText().toString())){
            builder.append(binding.code1.getText().toString());
        }
        if(!TextUtils.isEmpty(binding.code2.getText().toString())){
            builder.append(",").append(binding.code2.getText().toString());
        }
        list =builder.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otherDetailButton:
                if(TextUtils.isEmpty(list)){
                    Toast.makeText(getContext(), "请先添加自选股", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (binding.otherDetailButton.getText().toString().equals("自动更新中")) {
                    binding.otherDetailButton.setText("已停止更新");
                    isDestory = true;

                } else {
                    binding.otherDetailButton.setText("自动更新中");
                    isDestory = false;

                    if (myRunnable == null) {
                        myRunnable = new MyRunnable();
                    }
                    new Thread(myRunnable).start();

                }
                break;
            case R.id.code1: {
                SelectDialog dialog = new SelectDialog(new SelectDialog.Updata() {
                    @Override
                    public void upData(String code) {
                        binding.code1.setText(code);
                        sharedPreferencesUtil.save_code1(code);
                        getList();
                        if(isDestory) initData();
                    }
                },binding.code1.getText().toString());
                dialog.show(getActivity().getSupportFragmentManager(), "code1");
                break;
            }
            case R.id.code2: {
                SelectDialog dialog = new SelectDialog(new SelectDialog.Updata() {
                    @Override
                    public void upData(String code) {
                        binding.code2.setText(code);
                        sharedPreferencesUtil.save_code2(code);
                        getList();
                        if(isDestory) initData();
                    }
                },binding.code2.getText().toString());
                dialog.show(getActivity().getSupportFragmentManager(), "code2");
                break;
            }
        }
    }

    private class MyRunnable implements Runnable {

        @Override
        public synchronized void run() {
            while (!isDestory) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initData();
            }
        }
    }

    public static class MainHandler extends Handler {
        WeakReference<Fragment2> fragment;

        private MainHandler(Fragment2 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            Fragment2 theActivity = fragment.get();
            if (theActivity == null) {
                return;
            }
            if (msg.arg1 == 0) {
                theActivity.adapter.setNewData(theActivity.datas);
            }
        }

    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void initData() {
        if(TextUtils.isEmpty(list)){
            return;
        }
        Http.getDetails(list, new LogCallBack() {//获取买方卖方、内外盘、换手率等
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                getDetail(result);
                Log.i("TAG","result"+result);
            }
        });
    }

    ArrayList<TimeBarView.HoBarEntity> TileBarEntities;
    Message msg;
    private void getDetail(String result) {
        if (!result.contains(";")) {
            return;
        }
        datas =new ArrayList<>();

        String[] info = result.split(";");
        for (int i = 0; i < info.length; i++) {
            if (!info[i].contains("~")) {
                Log.i("TAG","return");
                continue;
            }
            String[] details = info[i].split("~");
            TileBarEntities=new ArrayList<>();
            TileBarEntities.add(new TimeBarView.HoBarEntity("卖五:（"+details[27]+")",Integer.parseInt(details[28]),0,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("卖四:（"+details[25]+")",Integer.parseInt(details[26]),0,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("卖三:（"+details[23]+")",Integer.parseInt(details[24]),0,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("卖二:（"+details[21]+")",Integer.parseInt(details[22]),0,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("卖一:（"+details[19]+")",Integer.parseInt(details[20]),0,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("买一:（"+details[9]+")",Integer.parseInt(details[10]),1,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("买二:（"+details[11]+")",Integer.parseInt(details[12]),1,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("买三:（"+details[13]+")",Integer.parseInt(details[14]),1,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("买四:（"+details[15]+")",Integer.parseInt(details[16]),1,""));
            TileBarEntities.add(new TimeBarView.HoBarEntity("买五:（"+details[17]+")",Integer.parseInt(details[18]),1,""));

            datas.add(new QueueBean(TileBarEntities,details[1] ,
                    details[35].split("/")[0] + "  [ " + details[31] + "   " + details[32] + "% ]"
                            ,"抢筹:"+ ResultUtils.formatNumberWithUnit(details[7],1)
                            +"  割肉:"+ResultUtils.formatNumberWithUnit(details[8],1)
                            +"\n总手:"+ ResultUtils.formatNumberWithUnit(details[6],1)
                            +"\n最高价:"+details[33]+"  最低价:"+details[34]
                            +"\n平均价:"+details[51]
                            +"\n换手率:"+details[38]+"  量能比:"+details[49]
                    ,list.split(",")[i]));

        }

//        adapter.setNewData(datas);

        msg = Message.obtain();
        msg.arg1 = 0;
        handler.sendMessage(msg);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FilterBean event) {
        if(event.getPosition()==1){


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestory=true;
    }
}
