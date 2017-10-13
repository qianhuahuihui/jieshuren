package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.OrderdetailsActivity;
import ren.jieshu.jieshuren.entity.ReturnbookBean;

/**
 * Created by laomaotao on 2017/8/5.
 */

public class ReturnbookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<ReturnbookBean> returnbookBeanList;

    public ReturnbookAdapter(Context context, List<ReturnbookBean> returnbookBeanList){
        super();
        this.context = context;
        this.returnbookBeanList = returnbookBeanList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_returnbook,parent,false);
        RecyclerView.ViewHolder returnbookHolder = new ReturnbookAdapter.ReturnbookHolder(view);
        return returnbookHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ((ReturnbookAdapter.ReturnbookHolder)holder).item_returnbook_recyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        ItemReturnbookAdapter itemReturnbookAdapter = new ItemReturnbookAdapter(context, returnbookBeanList.get(position).getBooks());
            ((ReturnbookAdapter.ReturnbookHolder) holder).item_returnbook_recyclerview.setAdapter(itemReturnbookAdapter);
        ((ReturnbookAdapter.ReturnbookHolder)holder).item_returnbook_button_orderdetails.setOnClickListener(this);
        ((ReturnbookAdapter.ReturnbookHolder)holder).item_returnbook_time.setText("还书时间："+returnbookBeanList.get(position).getReturn_book_time());
        ((ReturnbookAdapter.ReturnbookHolder)holder).item_returnbook_price.setText("押金合计："+returnbookBeanList.get(position).getPrice());
        ((ReturnbookAdapter.ReturnbookHolder)holder).item_returnbook_count.setText("共"+returnbookBeanList.get(position).getCount()+"本书");
    }

    @Override
    public int getItemCount() {
        return returnbookBeanList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_libraryorder_button_orderdetails:
                Intent intent = new Intent();
                intent.setClass(context,OrderdetailsActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    class ReturnbookHolder extends RecyclerView.ViewHolder{
        TextView item_returnbook_time;
        TextView item_returnbook_price;

        RecyclerView item_returnbook_recyclerview;
        Button item_returnbook_button_orderdetails;
        TextView item_returnbook_count;
        public ReturnbookHolder(View itemView) {
            super(itemView);
            item_returnbook_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_returnbook_recyclerview);
            item_returnbook_button_orderdetails = (Button) itemView.findViewById(R.id.item_returnbook_button_orderdetails);
            item_returnbook_time = (TextView) itemView.findViewById(R.id.item_returnbook_time);
            item_returnbook_count = (TextView) itemView.findViewById(R.id.item_returnbook_count);
            item_returnbook_price = (TextView) itemView.findViewById(R.id.item_returnbook_price);
        }
    }
}
