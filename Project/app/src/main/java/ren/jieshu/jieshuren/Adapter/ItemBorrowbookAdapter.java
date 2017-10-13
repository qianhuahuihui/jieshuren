package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ren.jieshu.jieshuren.R;

/**
 * Created by laomaotao on 2017/9/21.
 */

public class ItemBorrowbookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<String> stringList;
    public ItemBorrowbookAdapter(Context context, List<String> stringList){
        super();
        this.context = context;
        this.stringList = stringList;

    }    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_itemrborrowbook,parent,false);
        RecyclerView.ViewHolder itemReturnbookViewHolder = new ItemBorrowbookAdapter.ItemBorrowbookViewHolder(view);
        return itemReturnbookViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ItemBorrowbookAdapter.ItemBorrowbookViewHolder)holder).item_itemborrowbook_simpledraweeview.setImageURI(Uri.parse(stringList.get(position)));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
    class ItemBorrowbookViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView item_itemborrowbook_simpledraweeview;
        TextView item_itemreturnbook_bookname;
        public ItemBorrowbookViewHolder(View itemView) {
            super(itemView);
            item_itemborrowbook_simpledraweeview = (SimpleDraweeView) itemView.findViewById(R.id.item_itemborrowbook_simpledraweeview);
        }
    }
}
