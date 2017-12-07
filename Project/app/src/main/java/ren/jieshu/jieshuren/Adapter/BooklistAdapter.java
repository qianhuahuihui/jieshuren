package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.BookDetailsActivity;
import ren.jieshu.jieshuren.entity.ListBean;


/**
 * Created by laomaotao on 2017/7/13.
 */

public class BooklistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ListBean> listList;
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
    public void isShow(boolean flag,List<ListBean> listList){
        this.listList = listList;
        isShow = flag;
        notifyDataSetChanged();
    }

    public void setList(List<ListBean> listList){
        this.listList = listList;
        notifyDataSetChanged();
    }
    public BooklistAdapter(Context context, List<ListBean> listList){
        super();
        this.context = context;
        this.listList = listList;
    }
    public BooklistAdapter(Context context){
        super();
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booklist,parent,false);
        RecyclerView.ViewHolder viewHolder = new BooklistAdapter.BooklistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_simpledraweeview.setImageURI(Uri.parse(listList.get(position).getImage()));
        ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_bookname.setText(listList.get(position).getTitle());
        ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_writer.setText(listList.get(position).getAuthor());
        if(listList.get(position).getBookStatus()==0) {
            if (listList.get(position).getDbSize() != null) {
                if (listList.get(position).getDbSize() == 0) {
                    ((BooklistAdapter.BooklistViewHolder) holder).item_booklist_dbSize.setText("2-5天发货");
                } else {
                    ((BooklistAdapter.BooklistViewHolder) holder).item_booklist_dbSize.setText("24小时发货");
                }
            }
        }else{
            if (listList.get(position).getDbSize() <= 0) {
                ((BooklistAdapter.BooklistViewHolder) holder).item_booklist_dbSize.setText("暂不可借");
            } else {
                ((BooklistAdapter.BooklistViewHolder) holder).item_booklist_dbSize.setText("24小时发货");
            }
        }
        ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_price.setText("￥"+listList.get(position).getPrice());
        if (listList.get(position).getChoosed()){
            ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_choose.setImageResource(R.drawable.checked);
        }else {
            ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_choose.setImageResource(R.drawable.unchecked);
        }
        ((BooklistAdapter.BooklistViewHolder)holder).item_booklist_choose_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean choose = !listList.get(position).getChoosed();
                listList.get(position).setChoosed(choose);
                checkInterface.checkGroup(position,choose);
                ImageView item_booklist_choose = (ImageView) v.findViewById(R.id.item_booklist_choose);
                if (choose) {
                    item_booklist_choose.setImageResource(R.drawable.checked);
                }else {
                    item_booklist_choose.setImageResource(R.drawable.unchecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    class BooklistViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView item_booklist_simpledraweeview;
        TextView item_booklist_bookname;
        TextView item_booklist_writer;
        TextView item_booklist_dbSize;
        TextView item_booklist_price;
        ImageView item_booklist_choose;
        RelativeLayout item_booklist_choose_rl;
        public BooklistViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,BookDetailsActivity.class);
                    intent.putExtra("bookID",listList.get(getAdapterPosition()).getBook_id());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            item_booklist_simpledraweeview = (SimpleDraweeView) itemView.findViewById(R.id.item_booklist_simpledraweeview);
            item_booklist_bookname = (TextView) itemView.findViewById(R.id.item_booklist_bookname);
            item_booklist_writer = (TextView) itemView.findViewById(R.id.item_booklist_writer);
            item_booklist_dbSize = (TextView) itemView.findViewById(R.id.item_booklist_dbSize);
            item_booklist_price = (TextView) itemView.findViewById(R.id.item_booklist_price);
            item_booklist_choose = (ImageView) itemView.findViewById(R.id.item_booklist_choose);
            item_booklist_choose_rl = (RelativeLayout) itemView.findViewById(R.id.item_booklist_choose_rl);

        }
    }
    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

}
