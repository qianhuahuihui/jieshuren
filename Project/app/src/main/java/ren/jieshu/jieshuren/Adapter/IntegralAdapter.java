package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.ConsumeBean;
import ren.jieshu.jieshuren.entity.IntegralRecord;

/**
 * Author: shinianPan on 2017/10/21.
 * email : snow_psn@163.com
 */

public class IntegralAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<IntegralRecord> integralRecordList;
    public IntegralAdapter(Context context, List<IntegralRecord> integralRecordList){
        this.context = context;
        this.integralRecordList = integralRecordList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_integral,parent,false);
        RecyclerView.ViewHolder viewHolder = new IntegralAdapter.IntegralViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((IntegralAdapter.IntegralViewHolder)holder).item_integral_explain.setText(integralRecordList.get(position).getJ_explain());
        DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ((IntegralAdapter.IntegralViewHolder)holder).item_integral_consume_time.setText(dataFormat.format(integralRecordList.get(position).getCreate_time()).toString());
        if(integralRecordList.get(position).getJ_behavior() == 1){
            ((IntegralAdapter.IntegralViewHolder) holder).item_integral_j_fronze_amount.setText("-"+integralRecordList.get(position).getFronze_integral().toString());
            ((IntegralAdapter.IntegralViewHolder) holder).item_integral_service_charge.setText("-"+integralRecordList.get(position).getIntegral().toString());
        }else{
            ((IntegralAdapter.IntegralViewHolder) holder).item_integral_j_fronze_amount.setText("+"+integralRecordList.get(position).getFronze_integral().toString());
            ((IntegralAdapter.IntegralViewHolder) holder).item_integral_service_charge.setText("+"+integralRecordList.get(position).getIntegral().toString());
        }
        ((IntegralAdapter.IntegralViewHolder)holder).item_integral_surplus_blance.setText(integralRecordList.get(position).getSurplus_integral().toString());
    }

    @Override
    public int getItemCount() {
        return integralRecordList.size();
    }


    class IntegralViewHolder extends RecyclerView.ViewHolder{
        TextView item_integral_explain;
        TextView item_integral_consume_time;
        TextView item_integral_j_fronze_amount;
        TextView item_integral_service_charge;
        TextView item_integral_surplus_blance;
        public IntegralViewHolder(View itemView) {
            super(itemView);
            item_integral_explain = (TextView) itemView.findViewById(R.id.item_integral_explain);
            item_integral_consume_time = itemView.findViewById(R.id.item_integral_consume_time);
            item_integral_j_fronze_amount = itemView.findViewById(R.id.item_integral_j_fronze_amount);
            item_integral_service_charge = itemView.findViewById(R.id.item_integral_service_charge);
            item_integral_surplus_blance = itemView.findViewById(R.id.item_integral_surplus_blance);
        }
    }
}