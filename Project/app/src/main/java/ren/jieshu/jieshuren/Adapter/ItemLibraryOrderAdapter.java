package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.BooksBean;

import java.util.List;

/**
 * Created by laomaotao on 2017/7/4.
 */

class ItemLibraryOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    public JsonObject object;
    private List<BooksBean> booksList;
    public ItemLibraryOrderAdapter(Context context, List<BooksBean> booksList){
        super();
        this.context = context;
        this.booksList = booksList;

    }    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_itemlibraryorder,parent,false);
        RecyclerView.ViewHolder itemLibraryOrderViewHolder = new ItemLibraryOrderAdapter.ItemLibraryOrderViewHolder(view);
        return itemLibraryOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ItemLibraryOrderViewHolder)holder).item_itemlibraryorder_simpledraweeview.setImageURI(Uri.parse(booksList.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    class ItemLibraryOrderViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView item_itemlibraryorder_simpledraweeview;
        public ItemLibraryOrderViewHolder(View itemView) {
            super(itemView);
            item_itemlibraryorder_simpledraweeview = (SimpleDraweeView) itemView.findViewById(R.id.item_itemlibraryorder_simpledraweeview);
        }
    }
}
