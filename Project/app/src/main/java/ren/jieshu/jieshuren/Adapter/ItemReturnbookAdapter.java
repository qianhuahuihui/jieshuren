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
import ren.jieshu.jieshuren.entity.BooksBean;

/**
 * Created by laomaotao on 2017/8/5.
 */

public class ItemReturnbookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<BooksBean> stringList;
    public ItemReturnbookAdapter(Context context, List<BooksBean> stringList){
        super();
        this.context = context;
        this.stringList = stringList;

    }    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_itemreturnbook,parent,false);
        RecyclerView.ViewHolder itemReturnbookViewHolder = new ItemReturnbookAdapter.ItemReturnbookViewHolder(view);
        return itemReturnbookViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ItemReturnbookAdapter.ItemReturnbookViewHolder)holder).item_itemreturnbook_simpledraweeview.setImageURI(Uri.parse(stringList.get(position).getBookImage()));
        ((ItemReturnbookAdapter.ItemReturnbookViewHolder)holder).item_itemreturnbook_bookname.setText(stringList.get(position).getBookName());
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }
    class ItemReturnbookViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView item_itemreturnbook_simpledraweeview;
        TextView item_itemreturnbook_bookname;
        public ItemReturnbookViewHolder(View itemView) {
            super(itemView);
            item_itemreturnbook_bookname = itemView.findViewById(R.id.item_itemreturnbook_bookname);
            item_itemreturnbook_simpledraweeview = itemView.findViewById(R.id.item_itemreturnbook_simpledraweeview);
        }
    }
}