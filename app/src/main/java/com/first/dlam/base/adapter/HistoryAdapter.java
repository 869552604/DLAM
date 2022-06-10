package com.first.dlam.base.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.first.dlam.R;
import com.first.dlam.bean.HistoryBean;

public class HistoryAdapter extends BaseQuickAdapter<HistoryBean, BaseViewHolder> {
    private ItemSelectedCallBack mCallBack;
    private Context context;
    public HistoryAdapter(Context context) {
        super(R.layout.adapter_history_left);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean item) {
        helper.setText(R.id.content, item.getWeek()+"  "+item.getTime().substring(0,10));

        helper.setText(R.id.info,item.getInfo());


        if (mCallBack != null) {
            mCallBack.convert(helper, helper.getLayoutPosition());
        }

    }

    public void setItemSelectedCallBack(ItemSelectedCallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface ItemSelectedCallBack {
        void convert(BaseViewHolder holder, int position);
    }
}