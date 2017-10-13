package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.request.RequestCall;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.AdressBean;

/**
 * Created by laomaotao on 2017/8/10.
 */

public class AdressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<AdressBean> adressBeanList;
    private RequestCall call;
    private FragmentActivity fragmentActivity;
    private AdressAdapter.UpdateInterface updateInterface;

    /**
     * 单选接口
     *
     * @param updateInterface
     */
    public void setUpdateInterface(AdressAdapter.UpdateInterface updateInterface) {
        this.updateInterface = updateInterface;
    }
    public AdressAdapter(Context context, List<AdressBean> adressBeanList){
        super();
        this.context = context;
        this.adressBeanList = adressBeanList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_adress,parent,false);
        RecyclerView.ViewHolder viewHolder = new AdressAdapter.AdressViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((AdressAdapter.AdressViewHolder)holder).item_adress_name.setText(adressBeanList.get(position).getName());
        ((AdressAdapter.AdressViewHolder)holder).item_adress_phone.setText(adressBeanList.get(position).getMobile());
        ((AdressAdapter.AdressViewHolder)holder).item_adress_adress.setText(adressBeanList.get(position).getMerger_addr());
        if (adressBeanList.get(position).getJ_defaultaddr() != null) {
            if (adressBeanList.get(position).getJ_defaultaddr() == 1) {
                ((AdressAdapter.AdressViewHolder) holder).item_adress_choose.setImageResource(R.drawable.checked);
                ((AdressAdapter.AdressViewHolder) holder).item_adress_tv_default.setText("默认地址");
            } else {
                ((AdressAdapter.AdressViewHolder) holder).item_adress_choose.setImageResource(R.drawable.unchecked);
                ((AdressAdapter.AdressViewHolder) holder).item_adress_tv_default.setText("设为默认");
            }
        }
        ((AdressAdapter.AdressViewHolder)holder).item_adress_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInterface.deletGroup(position, adressBeanList.get(position));
            }
        });
        ((AdressAdapter.AdressViewHolder)holder).item_adress_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInterface.updateGroup(position, adressBeanList.get(position));

            }
        });
        ((AdressAdapter.AdressViewHolder)holder).item_adress_choose_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInterface.defaultGroup(position, adressBeanList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return adressBeanList.size();
    }

    class AdressViewHolder extends RecyclerView.ViewHolder{
        TextView item_adress_name;
        TextView item_adress_phone;
        TextView item_adress_adress;
        TextView item_adress_delete;
        TextView item_adress_edit;
        RelativeLayout item_adress_choose_rl;
        ImageView item_adress_choose;
        TextView item_adress_tv_default;
        public AdressViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateInterface.itemclick(getAdapterPosition(),adressBeanList.get(getAdapterPosition()));
                }
            });
            item_adress_name = (TextView) itemView.findViewById(R.id.item_adress_name);
            item_adress_phone = (TextView) itemView.findViewById(R.id.item_adress_phone);
            item_adress_adress = (TextView) itemView.findViewById(R.id.item_adress_adress);
            item_adress_delete = (TextView) itemView.findViewById(R.id.item_adress_delete);
            item_adress_edit = (TextView) itemView.findViewById(R.id.item_adress_edit);
            item_adress_choose_rl = itemView.findViewById(R.id.item_adress_choose_rl);
            item_adress_choose =  itemView.findViewById(R.id.item_adress_choose);
            item_adress_tv_default = itemView.findViewById(R.id.item_adress_tv_default);

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
        void updateGroup(Integer position,AdressBean adressBean);
        void itemclick(Integer position,AdressBean adressBean);
        void deletGroup(Integer position,AdressBean adressBean);
        void defaultGroup(Integer position,AdressBean adressBean);
    }
}