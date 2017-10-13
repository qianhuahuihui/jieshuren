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

public class SearchResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public SearchResultAdapter(Context context){
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_searchresult,parent,false);
        RecyclerView.ViewHolder viewHolder = new SearchResultViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder{
        public SearchResultViewHolder(View itemView) {
            super(itemView);
        }
    }

}
