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
import ren.jieshu.jieshuren.entity.HttpURLConfig;

/**
 * Created by laomaotao on 2017/9/21.
 */

public class ConfirmationReturnBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BooksBean> booksList;
    public ConfirmationReturnBookAdapter(Context context, List<BooksBean> booksList){
        this.context = context;
        this.booksList = booksList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_confirmationreturnbook,parent,false);
        RecyclerView.ViewHolder holder = new ConfirmationReturnBookAdapter.ConfirmationReturnHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String sdfsd= booksList.get(position).getBook().getJ_book_name();
        ((ConfirmationReturnBookAdapter.ConfirmationReturnHolder)holder).item_confirmationreturnbook_simpledraweeview.setImageURI(Uri.parse(HttpURLConfig.URL + booksList.get(position).getBook().getJ_book_cover_image()));
        ((ConfirmationReturnBookAdapter.ConfirmationReturnHolder)holder).item_confirmationreturnbook_bookname.setText(sdfsd);

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    class ConfirmationReturnHolder extends RecyclerView.ViewHolder{

        TextView item_confirmationreturnbook_bookname;
        SimpleDraweeView item_confirmationreturnbook_simpledraweeview;
        public ConfirmationReturnHolder(View itemView) {
            super(itemView);

            item_confirmationreturnbook_simpledraweeview = (SimpleDraweeView) itemView.findViewById(R.id.item_confirmationreturnbook_simpledraweeview);
            item_confirmationreturnbook_bookname = (TextView) itemView.findViewById(R.id.item_confirmationreturnbook_bookname);
        }
    }
}
