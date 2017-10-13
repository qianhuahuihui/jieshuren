package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ren.jieshu.jieshuren.R;


/**
 * Created by laomaotao on 2017/7/20.
 */

public class ThisBookFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public ThisBookFriendAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thisbookfriend,parent,false);
        RecyclerView.ViewHolder viewHolder = new ThisBookFriendAdapter.ThisBookFriendViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ThisBookFriendViewHolder extends RecyclerView.ViewHolder{
        public ThisBookFriendViewHolder(View itemView) {
            super(itemView);
        }
    }
}
