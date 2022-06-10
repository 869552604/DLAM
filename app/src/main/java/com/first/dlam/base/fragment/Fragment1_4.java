package com.first.dlam.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.HistoryActivity;
import com.first.dlam.base.SuperActivity;
import com.first.dlam.base.views.HorizontalBarView;
import com.first.dlam.base.views.TimeBarView;
import com.first.dlam.bean.BaseEvent;
import com.first.dlam.databinding.Fragment14Binding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;


public class Fragment1_4 extends BaseFragment {

    private Fragment14Binding binding;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public static StringBuilder key =new StringBuilder();
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment1_4,container,false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());

        binding.superActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Fragment1_3.isDestory){
                    Toast.makeText(getContext(),"请点击趋势模块的[已停止更新] 按钮来获取数据先",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent =new Intent(getContext(), SuperActivity.class);
                startActivity(intent);

            }
        });


        binding.dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }


    private StringBuilder builder;
    private StringBuilder builderInfo;
    private String[] details;
    private int qiangChou = 0;
    private int geRou = 0;
    ArrayList<HorizontalBarView.HoBarEntity> hoBarEntities;
    HorizontalBarView.HoBarEntity hoBarEntity;


    ArrayList<TimeBarView.HoBarEntity> TileBarEntities;

    private double remmenber=0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(BaseEvent event) {
        details=event.message;

        if (sharedPreferencesUtil.getDayMax() < (Integer.parseInt(details[7]) - Integer.parseInt(details[8]))) {//保存当天最高价
            sharedPreferencesUtil.saveDayMax(Integer.parseInt(details[7]) - Integer.parseInt(details[8]));
            sharedPreferencesUtil.saveDAY_MAX_TIME(dateFormat.format(new Date()));
            sharedPreferencesUtil.saveDAY_HUI_DIAO_MIN(Integer.parseInt(details[7]) - Integer.parseInt(details[8]));//将回调最低值设置为当前值。
            sharedPreferencesUtil.saveDAY_MAX_TIME_NUMBER(sharedPreferencesUtil.getDAY_MAX_TIME_NUMBER() + 1);
            remmenber=Double.parseDouble(details[35].split("/")[0]);
        }
        if (sharedPreferencesUtil.getDayMIN() > (Integer.parseInt(details[7]) - Integer.parseInt(details[8]))) {//保存当天最低价
            sharedPreferencesUtil.saveDayMIN(Integer.parseInt(details[7]) - Integer.parseInt(details[8]));
            sharedPreferencesUtil.saveDAY_MIN_TIME(dateFormat.format(new Date()));
            sharedPreferencesUtil.saveDAY_MIN_TIME_NUMBER(sharedPreferencesUtil.getDAY_MIN_TIME_NUMBER() + 1);
        }

        if (sharedPreferencesUtil.getDAY_HUI_DIAO_MIN() > (Integer.parseInt(details[7]) - Integer.parseInt(details[8]))) {
            sharedPreferencesUtil.saveDAY_HUI_DIAO_MIN(Integer.parseInt(details[7]) - Integer.parseInt(details[8]));
            sharedPreferencesUtil.saveDAY_HUI_DIAO_MAX(Integer.parseInt(details[7]) - Integer.parseInt(details[8]));//将回调最高值设置为当前值。
            remmenber=Double.parseDouble(details[35].split("/")[0]);
        }

        if (sharedPreferencesUtil.getDAY_HUI_DIAO_MAX() < (Integer.parseInt(details[7]) - Integer.parseInt(details[8]))) {
            sharedPreferencesUtil.saveDAY_HUI_DIAO_MAX(Integer.parseInt(details[7]) - Integer.parseInt(details[8]));
        }

        builder = new StringBuilder();
        builder .append("------[波段回调力度]------").append("\n [高点").append(sharedPreferencesUtil.getDayMax()).append("回调]最低点: ").append(sharedPreferencesUtil.getDAY_HUI_DIAO_MIN()).append("手,回调").append(sharedPreferencesUtil.getDAY_HUI_DIAO_MIN() - sharedPreferencesUtil.getDayMax()).append("手\n")
                .append(" [低点").append(sharedPreferencesUtil.getDAY_HUI_DIAO_MIN()).append("反弹]最高点: ").append(sharedPreferencesUtil.getDAY_HUI_DIAO_MAX()).append("手,反弹").append(sharedPreferencesUtil.getDAY_HUI_DIAO_MAX() - sharedPreferencesUtil.getDAY_HUI_DIAO_MIN()).append("手\n\n")
                .append("   反弹价位").append(remmenber).append(" 当前价: ").append(details[35].split("/")[0]).append(" 相差: ").append(ResultUtils.getData(Double.parseDouble(details[35].split("/")[0])-remmenber)).append("\n")
                .append("   实时反弹").append(Integer.parseInt(details[7]) - Integer.parseInt(details[8]) - sharedPreferencesUtil.getDAY_HUI_DIAO_MIN()).append(" 反弹: ").append(Integer.parseInt(details[7]) - Integer.parseInt(details[8]) - sharedPreferencesUtil.getDAY_HUI_DIAO_MIN() - (sharedPreferencesUtil.getDAY_HUI_DIAO_MAX() - sharedPreferencesUtil.getDAY_HUI_DIAO_MIN())).append("手 回调: ").append(Integer.parseInt(details[7]) - Integer.parseInt(details[8]) - sharedPreferencesUtil.getDAY_HUI_DIAO_MIN() + (sharedPreferencesUtil.getDAY_HUI_DIAO_MIN() - sharedPreferencesUtil.getDayMax())).append("手");

        builderInfo =new StringBuilder();
        builderInfo.append("涨停价: ").append(details[47]).append("      ").append("跌停价: ").append(details[48]).append("      ").append("涨跌价: ").append(details[31]).append("\n")
                .append("昨收价: ").append(details[4]).append("      ").append("今开价: ").append(details[5]).append("      ").append("当前价: ").append(details[3]).append("\n")
                .append("平均价: ").append(details[51]).append("      ").append("最高价: ").append(details[33]).append("      ").append("最低价: ").append(details[34]).append("\n")
                .append("换手率: ").append(details[38]).append("%").append("   ").append("量能比: ").append(details[49]).append("      ").append("振幅率: ").append(details[32]).append("%\n")
                .append("市盈率: ").append(details[39]).append("      ").append("市净率: ").append(details[46]).append("\n")
                .append("动态市盈率: ").append(details[52]).append("  ").append("静态市盈率: ").append(details[53]).append("\n")
                .append("总市值: ").append(details[45]).append("亿");

        binding.tvInfo.setText(builderInfo.toString());

        if(qiangChou!=0 && geRou!=0) {
            sharedPreferencesUtil.changeValue(details[35].split("/")[0], ((Integer.parseInt(details[7]) - qiangChou) + (Integer.parseInt(details[8]) - geRou)));
            key =new StringBuilder();
            key.append(details[35].split("/")[0]);
        }

        TileBarEntities=new ArrayList<>();
        TileBarEntities.add(new TimeBarView.HoBarEntity("卖五:（"+details[27]+")",Integer.parseInt(details[28]),0,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("卖四:（"+details[25]+")",Integer.parseInt(details[26]),0,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("卖三:（"+details[23]+")",Integer.parseInt(details[24]),0,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("卖二:（"+details[21]+")",Integer.parseInt(details[22]),0,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("卖一:（"+details[19]+")",Integer.parseInt(details[20]),0,""));

        TileBarEntities.add(new TimeBarView.HoBarEntity("当前:（"+details[35].split("/")[0]+")",((Integer.parseInt(details[7]) - qiangChou) + (Integer.parseInt(details[8]) - geRou)),2,"[抢筹:"+(Integer.parseInt(details[7]) - qiangChou)+",割肉:"+(Integer.parseInt(details[8]) - geRou)+"]  "+getString(R.string.share)));

        TileBarEntities.add(new TimeBarView.HoBarEntity("买一:（"+details[9]+")",Integer.parseInt(details[10]),1,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("买二:（"+details[11]+")",Integer.parseInt(details[12]),1,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("买三:（"+details[13]+")",Integer.parseInt(details[14]),1,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("买四:（"+details[15]+")",Integer.parseInt(details[16]),1,""));
        TileBarEntities.add(new TimeBarView.HoBarEntity("买五:（"+details[17]+")",Integer.parseInt(details[18]),1,""));
        binding.time.setHoBarData(TileBarEntities);


        sharedPreferencesUtil.saveZONG_SHOU(details[6]);
        sharedPreferencesUtil.saveQIANG_CHOU(String.valueOf(details[7]));
        sharedPreferencesUtil.saveGE_ROU(String.valueOf(details[8]));
        sharedPreferencesUtil.saveSHOW_PAN(details[35].split("/")[0]);
        sharedPreferencesUtil.saveJUN_JIA(details[51]);

        qiangChou = Integer.parseInt(details[7]);
        geRou = Integer.parseInt(details[8]);
        binding.tvOtherDetail.setText(builder.toString());

        binding.tvBottomV11.setText(details[6]);
        binding.tvBottomV12.setText(String.valueOf(details[7]));
        binding.tvBottomV13.setText(String.valueOf(details[8]));
        binding.tvBottomV14.setText(String.valueOf(Integer.parseInt(details[7]) - Integer.parseInt(details[8])));

        binding.tvBottomV21.setText("最高抛压力度\n" + sharedPreferencesUtil.getDAY_MAX_TIME() + "(" + sharedPreferencesUtil.getDAY_MAX_TIME_NUMBER() + "次)");
        binding.tvBottomV22.setText(String.valueOf(sharedPreferencesUtil.getDayMax()));
        binding.tvBottomV23.setText(ResultUtils.getData(Double.parseDouble(details[33]) - Double.parseDouble(details[4])));
        binding.tvBottomV24.setText(String.valueOf((Double.parseDouble(details[33]) - Double.parseDouble(details[4]) != 0 ? (int) Math.abs(sharedPreferencesUtil.getDayMax() / ((Double.parseDouble(details[33]) - Double.parseDouble(details[4])) * 100)) : sharedPreferencesUtil.getDayMax())));

        binding.tvBottomV31.setText("最低支撑力度\n" + sharedPreferencesUtil.getDAY_MIN_TIME() + "(" + sharedPreferencesUtil.getDAY_MIN_TIME_NUMBER() + "次)");
        binding.tvBottomV32.setText(String.valueOf(sharedPreferencesUtil.getDayMIN()));
        binding.tvBottomV33.setText(ResultUtils.getData(Double.parseDouble(details[34]) - Double.parseDouble(details[4])));
        binding.tvBottomV34.setText(String.valueOf((Double.parseDouble(details[4]) - Double.parseDouble(details[34]) != 0 ? (int) Math.abs(sharedPreferencesUtil.getDayMIN() / ((Double.parseDouble(details[4]) - Double.parseDouble(details[34])) * 100)) : sharedPreferencesUtil.getDayMIN())));

        binding.tvBottomV41.setText("均价力度\n" + details[51]);
        binding.tvBottomV42.setText(String.valueOf(Integer.parseInt(details[7]) - Integer.parseInt(details[8])));
        binding.tvBottomV43.setText(ResultUtils.getData(Double.parseDouble(details[51]) - Double.parseDouble(details[4])));
        binding.tvBottomV44.setText(String.valueOf(Double.parseDouble(details[51]) - Double.parseDouble(details[4]) != 0 ? (int) Math.abs((Integer.parseInt(details[7]) - Integer.parseInt(details[8])) / ((Double.parseDouble(details[51]) - Double.parseDouble(details[4])) * 100)) : Math.abs(Integer.parseInt(details[7]) - Integer.parseInt(details[8]))));

        binding.tvBottomV51.setText("实时力度\n" + details[35].split("/")[0]);
        binding.tvBottomV52.setText(String.valueOf(Integer.parseInt(details[7]) - Integer.parseInt(details[8])));
        binding.tvBottomV53.setText(ResultUtils.getData(Double.parseDouble(details[3]) - Double.parseDouble(details[4])));
        binding.tvBottomV54.setText(String.valueOf((Double.parseDouble(details[3]) - Double.parseDouble(details[4]) != 0 ? (int) Math.abs((Integer.parseInt(details[7]) - Integer.parseInt(details[8])) / ((Double.parseDouble(details[3]) - Double.parseDouble(details[4])) * 100)) : Math.abs(Integer.parseInt(details[7]) - Integer.parseInt(details[8])))));



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

}
