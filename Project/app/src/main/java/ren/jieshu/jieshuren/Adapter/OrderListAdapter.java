package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.ReturnbookBean;

/**
 * Created by laomaotao on 2017/9/3.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<ReturnbookBean> returnbookBeanList;
    private String type;
    public OrderListAdapter(Context context, List<ReturnbookBean> returnbookBeanList,String type){
        super();
        this.context = context;
        this.type = type;
        this.returnbookBeanList = returnbookBeanList;

    }
    private OrderListAdapter.UpdateInterface updateInterface;

    /**
     * 单选接口
     *
     * @param updateInterface
     */
    public void setUpdateInterface(OrderListAdapter.UpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_orderlist,parent,false);
        RecyclerView.ViewHolder returnbookHolder = new OrderListAdapter.OrderListHolder(view);
        return returnbookHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ((OrderListAdapter.OrderListHolder)holder).item_orderlist_list.setLayoutManager(linearLayoutManager);
        //设置适配器
        if (returnbookBeanList.get(position).getImage() != null) {
            ItemBorrowbookAdapter itemReturnbookAdapter = new ItemBorrowbookAdapter(context, returnbookBeanList.get(position).getImage());
            ((OrderListAdapter.OrderListHolder) holder).item_orderlist_list.setAdapter(itemReturnbookAdapter);
        }

        if (type.equals("0")){
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_status.setText("未付款");
        } else if (type.equals("1")){
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_status.setText("待发货");
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_logisticsquery.setVisibility(View.GONE);
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_orderdetails.setVisibility(View.GONE);
        } else if (type.equals("2")){
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_status.setText("待收获");
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_logisticsquery.setText("确认收货");
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_orderdetails.setText("物流查询");
        } else if (type.equals("3")){
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_status.setText("已完成");
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_logisticsquery.setVisibility(View.GONE);
            ((OrderListAdapter.OrderListHolder)holder).item_orderlist_orderdetails.setVisibility(View.GONE);
        }
        ((OrderListAdapter.OrderListHolder)holder).item_orderlist_logisticsquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("0")){
                    updateInterface.removeOrderGroup(position, returnbookBeanList.get(position));
                }else if(type.equals("2")){
                    updateInterface.confirm(position, returnbookBeanList.get(position));
                }
            }
        });
        ((OrderListAdapter.OrderListHolder)holder).item_orderlist_orderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("0")){
                    updateInterface.payOrderGroup(position,returnbookBeanList.get(position));
                }else if(type.equals("2")){
                }
            }
        });
        ((OrderListAdapter.OrderListHolder)holder).item_orderlist_orderSn.setText("订单编号："+returnbookBeanList.get(position).getOrderSn());
        ((OrderListAdapter.OrderListHolder)holder).item_orderlist_count.setText(returnbookBeanList.get(position).getCount().toString());
        ((OrderListAdapter.OrderListHolder)holder).item_orderlist_totalPrice.setText(returnbookBeanList.get(position).getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return returnbookBeanList.size();
    }



    class OrderListHolder extends RecyclerView.ViewHolder{
        TextView item_orderlist_orderSn;
        TextView item_orderlist_status;
        TextView item_orderlist_count;
        TextView item_orderlist_totalPrice;

        RecyclerView item_orderlist_list;
        Button item_orderlist_logisticsquery;
        Button item_orderlist_orderdetails;
        public OrderListHolder(View itemView) {
            super(itemView);
            item_orderlist_list = (RecyclerView) itemView.findViewById(R.id.item_orderlist_list);
            item_orderlist_logisticsquery = (Button) itemView.findViewById(R.id.item_orderlist_logisticsquery);
            item_orderlist_orderdetails = (Button) itemView.findViewById(R.id.item_orderlist_orderdetails);
            item_orderlist_orderSn = (TextView) itemView.findViewById(R.id.item_orderlist_orderSn);
            item_orderlist_status = (TextView) itemView.findViewById(R.id.item_orderlist_status);
            item_orderlist_count = (TextView) itemView.findViewById(R.id.item_orderlist_count);
            item_orderlist_totalPrice = (TextView) itemView.findViewById(R.id.item_orderlist_totalPrice);
        }
    }
    /**
     * 复选框接口
     */
    public interface UpdateInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         */
        void removeOrderGroup(Integer position,ReturnbookBean returnbookBean);
        void payOrderGroup(Integer position,ReturnbookBean returnbookBean);
        void confirm(Integer position,ReturnbookBean returnbookBean);

    }
}
