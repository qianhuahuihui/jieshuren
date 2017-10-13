package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.ConsumeBean;


/**
 * Created by laomaotao on 2017/7/23.
 */

public class RMBAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ConsumeBean> consumeBeanList;
    public RMBAdapter(Context context, List<ConsumeBean> consumeBeanList){
        this.context = context;
        this.consumeBeanList = consumeBeanList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rmb,parent,false);
        RecyclerView.ViewHolder viewHolder = new RMBAdapter.RMBViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((RMBAdapter.RMBViewHolder)holder).item_rmb_explain.setText(consumeBeanList.get(position).getExplain());
        ((RMBAdapter.RMBViewHolder)holder).item_rmb_consume_time.setText(consumeBeanList.get(position).getConsume_time());
        if(consumeBeanList.get(position).getBehavior() == 2){
            ((RMBAdapter.RMBViewHolder) holder).item_rmb_j_available_amount.setText("-"+consumeBeanList.get(position).getJ_available_amount().toString());
            ((RMBAdapter.RMBViewHolder) holder).item_rmb_service_charge.setText("-"+consumeBeanList.get(position).getService_charge().toString());
        }else{
            ((RMBAdapter.RMBViewHolder) holder).item_rmb_j_available_amount.setText("+"+consumeBeanList.get(position).getJ_available_amount().toString());
            ((RMBAdapter.RMBViewHolder) holder).item_rmb_service_charge.setText("+"+consumeBeanList.get(position).getService_charge().toString());
        }
            ((RMBAdapter.RMBViewHolder)holder).item_rmb_surplus_blance.setText(consumeBeanList.get(position).getSurplus_blance().toString());
    }

    @Override
    public int getItemCount() {
        return consumeBeanList.size();
    }


    class RMBViewHolder extends RecyclerView.ViewHolder{
        TextView item_rmb_explain;
        TextView item_rmb_consume_time;
        TextView item_rmb_j_available_amount;
        TextView item_rmb_service_charge;
        TextView item_rmb_surplus_blance;
        public RMBViewHolder(View itemView) {
            super(itemView);
            item_rmb_explain = (TextView) itemView.findViewById(R.id.item_rmb_explain);
            item_rmb_consume_time = itemView.findViewById(R.id.item_rmb_consume_time);
            item_rmb_j_available_amount = itemView.findViewById(R.id.item_rmb_j_available_amount);
            item_rmb_service_charge = itemView.findViewById(R.id.item_rmb_service_charge);
            item_rmb_surplus_blance = itemView.findViewById(R.id.item_rmb_surplus_blance);
        }
    }
}