package com.first.dlam.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.first.dlam.R;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.databinding.Fragment17Binding;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1_7 extends BaseFragment {
    private Fragment17Binding binding;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment1_7,container,false);
        initData();
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    private void initData() {



    }


}
