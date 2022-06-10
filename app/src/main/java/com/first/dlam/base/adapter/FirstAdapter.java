package com.first.dlam.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.first.dlam.MainActivity;
import com.first.dlam.R;
import com.first.dlam.base.dialog.FirstDialog;
import com.first.dlam.bean.AdapterBean1;
import com.first.dlam.manager.LineChartManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;

import java.text.DecimalFormat;

public class FirstAdapter extends BaseQuickAdapter<AdapterBean1, BaseViewHolder> {
    private Context context;
    DecimalFormat decimalFormat;
    public FirstAdapter(Context context){
        super(R.layout.adapter_sx_1);
        this.context=context;
        decimalFormat = new DecimalFormat("0.00");
    }
    @Override
    protected void convert(BaseViewHolder helper, AdapterBean1 item) {
        helper.setText(R.id.title,item.getCode()+"   总市值:"+item.getTitle()[45] + "亿"
        +"\n上影线:"+decimalFormat.format((Double.parseDouble(item.getTitle()[33]) - Double.parseDouble(item.getTitle()[3])))
        +"   下影线:"+decimalFormat.format(Double.parseDouble(item.getTitle()[5]) - Double.parseDouble(item.getTitle()[34]))
//        +"\n实体:"+decimalFormat.format(Double.parseDouble(item.getTitle()[3]) - Double.parseDouble(item.getTitle()[5]))
        +"\n换手率:"+ item.getTitle()[38] + "% 量比: " + item.getTitle()[49]
        +"\n压力位:"+decimalFormat.format(item.getIncomeBean_max().get(0).getRate())
        +"\n支撑位:"+decimalFormat.format(item.getIncomeBean_min().get(0).getRate())
        +"\n"+item.getString3()+"、"+item.getString4()+"、"+item.getString6()+"日线分别是:\n"
        +decimalFormat.format(item.getIncomeBean_3_5().get(item.getIncomeBean_3_5().size()-1).getRate())
        +"、"+decimalFormat.format(item.getIncomeBean_4_5().get(item.getIncomeBean_4_5().size()-1).getRate())
        +"、"+decimalFormat.format(item.getIncomeBean_6_5().get(item.getIncomeBean_6_5().size()-1).getRate()));


        LineChartManager lineChartManager_5 = new LineChartManager(helper.getView(R.id.lineChart_5), 3.6F, 8);


        YAxis yAxis=((LineChart)helper.getView(R.id.lineChart_5)).getAxisLeft();
        yAxis.setAxisMinimum(item.getMin());
        yAxis.setAxisMaximum(item.getMax());
//展示图表
        lineChartManager_5.showLineChart3(item.getIncomeBean_5(), "String120", context.getResources().getColor(R.color.orange));
        lineChartManager_5.addLine(item.getIncomeBean_3_5(), "string3", context.getResources().getColor(R.color.back));
        lineChartManager_5.addLine(item.getIncomeBean_4_5(), "string4", context.getResources().getColor(R.color.red));
        lineChartManager_5.addLine(item.getIncomeBean_6_5(), "string6", context.getResources().getColor(R.color.blue));

        lineChartManager_5.addLine(item.getIncomeBean_max(), "string_max", context.getResources().getColor(R.color.colorPrimary));
        lineChartManager_5.addLine(item.getIncomeBean_min(), "string_min", context.getResources().getColor(R.color.lv));

        //设置曲线填充色 以及 MarkerView
        lineChartManager_5.setChartFillDrawable(context.getDrawable(R.drawable.fade_orange));

        helper.setText(R.id.content,item.getTitle()[35].split("/")[0]+"  [  "+decimalFormat.format((Double.parseDouble(item.getTitle()[3]) - Double.parseDouble(item.getTitle()[4])))
        +"  "+item.getTitle()[32] + "%  ]");
        helper.setText(R.id.top,item.getTitle()[1]);
        helper.setText(R.id.time,item.getTime());

        helper.setOnClickListener(R.id.onclick, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstDialog dialog =new FirstDialog(context,item.getCode());
                dialog.show(((MainActivity)context).getSupportFragmentManager(),"i");
            }
        });
    }
}
