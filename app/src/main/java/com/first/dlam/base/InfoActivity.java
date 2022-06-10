package com.first.dlam.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.first.dlam.R;
import com.first.dlam.databinding.ActivityInfoBinding;

public class InfoActivity extends BaseActivity {
    private ActivityInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info);

        binding.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(InfoActivity.this,ShareActivity.class);
                startActivity(intent);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
