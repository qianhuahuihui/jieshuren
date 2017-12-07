package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.BookList;


/**
 * Created by laomaotao on 2017/7/20.
 */

public class ThisBookFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BookList> dataList;
    public ThisBookFriendAdapter(Context context, List<BookList> bookFriends){
        this.context = context;
        this.dataList = bookFriends;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thisbookfriend,parent,false);
        RecyclerView.ViewHolder viewHolder = new ThisBookFriendAdapter.ThisBookFriendViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ThisBookFriendViewHolder)holder).item_thisbookfriend_headview.setImageURI(dataList.get(position).getMember().getHeadimgurl());
        ((ThisBookFriendViewHolder)holder).item_thisbookfriend_name.setText(dataList.get(position).getMember().getName());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ((ThisBookFriendViewHolder)holder).item_book_friend_creat_time.setText(format.format(dataList.get(position).getJ_create_time()));
        ((ThisBookFriendViewHolder)holder).item_book_friend_address.setText(dataList.get(position).getMember().getAddress());
        int status = dataList.get(position).getJ_book_status();
        if(status == 0){
            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText("未处理");
        }else if(status == 1){
            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText("申请还书");
        }else if(status == 2){
            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText("图书归还");
        }else if(status == 3){
            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText("借阅中");
        }else if(status == 4){
            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText("转借中");
        }
//        else if(status == 5){
//            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText(dataList.get(position).getJ_book_status());
//        }else if(status == 6){
//            ((ThisBookFriendViewHolder)holder).item_thisbookfriend_time.setText(dataList.get(position).getJ_book_status());
//        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ThisBookFriendViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView item_thisbookfriend_headview;
        TextView item_thisbookfriend_name;
        TextView item_book_friend_creat_time;
        TextView item_thisbookfriend_time;
        TextView item_book_friend_address;

        public ThisBookFriendViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //updateInterface.itemclick(getAdapterPosition(),adressBeanList.get(getAdapterPosition()));
                }
            });
            item_thisbookfriend_headview = (SimpleDraweeView) itemView.findViewById(R.id.item_thisbookfriend_headview);
            item_thisbookfriend_name = (TextView) itemView.findViewById(R.id.item_thisbookfriend_name);
            item_book_friend_creat_time = (TextView) itemView.findViewById(R.id.item_book_friend_creat_time);
            item_thisbookfriend_time = (TextView) itemView.findViewById(R.id.item_thisbookfriend_time);
            item_book_friend_address = (TextView) itemView.findViewById(R.id.item_book_friend_address);

        }
    }
}
