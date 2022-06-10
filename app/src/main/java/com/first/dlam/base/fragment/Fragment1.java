package com.first.dlam.base.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.first.dlam.MainActivity;
import com.first.dlam.R;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.dialog.FirstDialog;
import com.first.dlam.base.views.TabEntity;
import com.first.dlam.bean.FirstBean;
import com.first.dlam.databinding.Fragment1Binding;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends BaseFragment implements View.OnClickListener {
    private Fragment1Binding binding;
    private String[] mTitles;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_1,container,false);
        intData();

        binding.title1.setOnClickListener(this);
        binding.title2.setOnClickListener(this);
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    public void intData() {
        mTitles = new String[]{"趋势", "看盘","K图","做T","日线", "码峰", "分析"};
        mFragments.add(new Fragment1_3());
        mFragments.add(new Fragment1_4());
        mFragments.add(new Fragment1_5());
        mFragments.add(new Fragment1_6());
        mFragments.add(new Fragment1_1());
        mFragments.add(new Fragment1_2());
        mFragments.add(new Fragment1_7());
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        binding.tlMain.setTabData(mTabEntities, getActivity(), R.id.layout1, mFragments);
        final float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
        final float unSelectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 12, getResources().getDisplayMetrics());

        binding.tlMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                binding.tlMain.setCurrentTab(position);
                //不知道为什么for循环无法改变字体大小
                if(position==0){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);

                }else if(position==1){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                }else if(position==2){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                }else if(position==3){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                }else if(position==4){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                }else if(position==5){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                }else if(position==6){
                    binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(1).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(2).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(3).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(4).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(5).setTextSize(TypedValue.COMPLEX_UNIT_SP,unSelectedSize);
                    binding.tlMain.getTitleView(6).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                }

            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        binding.tlMain.setCurrentTab(0);
        binding.tlMain.getTitleView(0).setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FirstBean event) {
        if(event.type==0){
            binding.title1.setText(event.message);
        }else if(event.type==1){
            binding.title2.setText(event.message);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title1:
                FirstDialog dialog =new FirstDialog(getContext(),"sh000001");
                dialog.show(((MainActivity)getContext()).getSupportFragmentManager(),"title1");
                break;
            case R.id.title2:
                FirstDialog dialog2 =new FirstDialog(getContext(),"sz399001");
                dialog2.show(((MainActivity)getContext()).getSupportFragmentManager(),"title2");
                break;
        }
    }
}
