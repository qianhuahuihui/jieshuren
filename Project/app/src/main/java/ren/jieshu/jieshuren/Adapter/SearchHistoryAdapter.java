package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ren.jieshu.jieshuren.R;


/**
 * Created by laomaotao on 2017/7/11.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public SearchHistoryAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater.from(context);
        View view = LayoutInflater.from(context).inflate(R.layout.item_searchhistory,parent,false);
        RecyclerView.ViewHolder viewHolder = new SearchHistoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }
    class SearchHistoryViewHolder extends RecyclerView.ViewHolder{

        public SearchHistoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
