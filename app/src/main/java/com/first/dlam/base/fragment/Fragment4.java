package com.first.dlam.base.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.first.dlam.R;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.databinding.Fragment4Binding;

import java.util.ArrayList;


public class Fragment4 extends BaseFragment implements View.OnClickListener {

    private Fragment4Binding binding;
    private ArrayList<BaseFragment> fragmentList;
    private Fragment4_1 Fragment4_1;
    private Fragment4_2 Fragment4_2;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_4,container,false);
        binding.day.setOnClickListener(this);
        binding.time.setOnClickListener(this);
        intData();
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void intData() {
        initFragment();
        ChangeFragment(0);// 默认点击第一个
    }

    private void initFragment() {
        fragmentList=new ArrayList<>();
        fragmentList.add(new Fragment4_1());
        fragmentList.add(new Fragment4_2());
    }

    //替换指定的Fragment布局
    public void ChangeFragment(int position) {
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if(Fragment4_1==null){
                    Fragment4_1=new Fragment4_1();
                    transaction.add(R.id.Fragment3,Fragment4_1);
                }
                hindFragment(transaction);
                transaction.show(Fragment4_1);
                transaction.commit();
                break;
            case 1:
                if(Fragment4_2==null){
                    Fragment4_2=new Fragment4_2();
                    transaction.add(R.id.Fragment3,Fragment4_2);
                }
                hindFragment(transaction);
                transaction.show(Fragment4_2);
                transaction.commit();
                break;

        }
    }

    private void hindFragment(androidx.fragment.app.FragmentTransaction transaction){
        if(Fragment4_1!=null){
            transaction.hide(Fragment4_1);
        }
        if(Fragment4_2!=null){
            transaction.hide(Fragment4_2);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.day:
                ChangeFragment(0);
                binding.day.setTextColor(getResources().getColor(R.color.write));
                binding.time.setTextColor(getResources().getColor(R.color.back));
                binding.day.setBackgroundColor(Color.parseColor("#FE8C45"));
                binding.time.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case R.id.time:
                ChangeFragment(1);
                binding.day.setTextColor(getResources().getColor(R.color.back));
                binding.time.setTextColor(getResources().getColor(R.color.write));
                binding.day.setBackgroundColor(Color.parseColor("#ffffff"));
                binding.time.setBackgroundColor(Color.parseColor("#FE8C45"));
                break;
        }
    }
}
