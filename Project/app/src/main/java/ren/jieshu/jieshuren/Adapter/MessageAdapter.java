package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.MessageBean;

/**
 * Created by laomaotao on 2017/10/11.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageBean> messageBeanList;
    private Context context;
    public MessageAdapter(Context context,  List<MessageBean> messageBeanList){
        this.context = context;
        this.messageBeanList = messageBeanList;

    } public MessageAdapter(Context context){
        this.context = context;

    }
    public void setList(List<MessageBean> messageBeanList){
        this.messageBeanList = messageBeanList;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_message,parent,false);
        RecyclerView.ViewHolder newbookHolder = new MessageAdapter.MessageHolder(view);
        return newbookHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MessageAdapter.MessageHolder)holder).item_message_message.setText(messageBeanList.get(position).getContent());
        ((MessageAdapter.MessageHolder)holder).item_message_time.setText(messageBeanList.get(position).getCreate_time());

    }

    @Override
    public int getItemCount() {
        if (messageBeanList != null) {
            return messageBeanList.size();
        }else {
            return 0;
        }
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        TextView item_message_message;
        TextView item_message_time;
        public MessageHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//                    intent.setClass(context,BookDetailsActivity.class);
//                    intent.putExtra("bookID",booksList.get(getAdapterPosition()).getBookid());
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//
//                }
//            });
            item_message_message = (TextView) itemView.findViewById(R.id.item_message_message);
            item_message_time = (TextView) itemView.findViewById(R.id.item_message_time);
        }

    }


}
