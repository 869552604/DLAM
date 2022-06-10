package com.first.dlam.base.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.first.dlam.MainActivity;
import com.first.dlam.R;
import com.first.dlam.base.dialog.FirstDialog;
import com.first.dlam.base.views.TimeBarView;
import com.first.dlam.bean.QueueBean;

public class QueueAdapter extends BaseQuickAdapter<QueueBean, BaseViewHolder> {
    private Context context;
    public QueueAdapter(Context context) {
        super(R.layout.adapter_queue);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, QueueBean item) {
        ((TimeBarView)helper.getView(R.id.queueBar)).setHoBarData(item.getTileBarEntities());
        helper.setText(R.id.codeName,item.getTitle());
        helper.setText(R.id.info,item.getInfo());
        helper.setText(R.id.codeContent,item.getContent());

        helper.setOnClickListener(R.id.onclick, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstDialog dialog =new FirstDialog(context,item.getCodeName());
                dialog.show(((MainActivity)context).getSupportFragmentManager(),"i");
            }
        });
    }
}
