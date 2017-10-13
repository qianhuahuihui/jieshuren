package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.WearPaymentBean;

/**
 * Created by laomaotao on 2017/8/28.
 */

public class WearPaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<WearPaymentBean> listList;
    private CheckInterface checkInterface;
    private boolean isShow = true;//是否显示编辑/完成
    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag,List<WearPaymentBean> listList){
        this.listList = listList;
        isShow = flag;
        notifyDataSetChanged();
    }

    public void setList(List<WearPaymentBean> listList){
        this.listList = listList;
        notifyDataSetChanged();
    }
    public WearPaymentAdapter(Context context, List<WearPaymentBean> listList){
        super();
        this.context = context;
        this.listList = listList;
    }
    public WearPaymentAdapter(Context context){
        super();
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wearpayment,parent,false);
        RecyclerView.ViewHolder viewHolder = new WearPaymentAdapter.WearPaymentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        BigDecimal money;
        final Double sum = Double.parseDouble(listList.get(position).getPrice().toString());
            Integer number = listList.get(position).getNumber();
//        money = (sum.multiply(number)).divide(new BigDecimal(100));
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        final String p=decimalFormat.format(listList.get(position).getOrderCompensatePrice());//format 返回的是字符串
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_count.setText(listList.get(position).getCount().toString());
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_tv_number.setText(p);
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_return_order_id.setText("订单编号："+listList.get(position).getReturn_order_id());
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_tv_03.setText(number.toString());
//        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_compensate_pirce.setText("已支付"+listList.get(position).getReturn_order_id().toString()+"元");
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_price.setText(listList.get(position).getPrice().toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_list.setLayoutManager(linearLayoutManager);
        //设置适配器
        ItemReturnbookAdapter itemReturnbookAdapter = new ItemReturnbookAdapter(context, listList.get(position).getBooks());
            ((WearPaymentAdapter.WearPaymentViewHolder) holder).item_wearpayment_list.setAdapter(itemReturnbookAdapter);
        final Integer finalNumber = number;
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalNumber < 100) {
                    checkInterface.add(finalNumber+1,position,sum);
                }
            }
        });
        ((WearPaymentAdapter.WearPaymentViewHolder)holder).item_wearpayment_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalNumber >0) {
                    checkInterface.del(finalNumber-1,position,sum);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    class WearPaymentViewHolder extends RecyclerView.ViewHolder{
        TextView item_wearpayment_tv_number;
        TextView item_wearpayment_count;
        TextView item_wearpayment_return_order_id;
        TextView item_wearpayment_compensate_pirce;
        TextView item_wearpayment_tv_03;
        TextView item_wearpayment_price;
        ImageView item_wearpayment_add;
        ImageView item_wearpayment_del;
        RecyclerView item_wearpayment_list;
        public WearPaymentViewHolder(View itemView) {
            super(itemView);
            item_wearpayment_tv_number = itemView.findViewById(R.id.item_wearpayment_tv_number);
            item_wearpayment_return_order_id = itemView.findViewById(R.id.item_wearpayment_return_order_id);
            item_wearpayment_compensate_pirce = itemView.findViewById(R.id.item_wearpayment_compensate_pirce);
            item_wearpayment_tv_03 = itemView.findViewById(R.id.item_wearpayment_tv_03);
            item_wearpayment_price = itemView.findViewById(R.id.item_wearpayment_price);
            item_wearpayment_count = itemView.findViewById(R.id.item_wearpayment_count);
            item_wearpayment_add = itemView.findViewById(R.id.item_wearpayment_add);
            item_wearpayment_del = itemView.findViewById(R.id.item_wearpayment_del);
            item_wearpayment_list = itemView.findViewById(R.id.item_wearpayment_list);

        }
    }
    /**
     * 复选框接口
     */
    public interface CheckInterface {

        void add(Integer number,Integer position,Double sum);
        void del(Integer number,Integer position,Double sum);
    }

}
