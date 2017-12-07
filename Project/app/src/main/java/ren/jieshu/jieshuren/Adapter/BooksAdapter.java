package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.BookDetailsActivity;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.widget.StarBar;

/**
 * Created by laomaotao on 2017/7/1.
 */

public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BooksBean> booksList;
    public BooksAdapter(Context context, List<BooksBean> booksList){
        this.context = context;
        this.booksList = booksList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_books,parent,false);
        RecyclerView.ViewHolder booksHolder = new BooksHolder(view);
        return booksHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ((BooksHolder)holder).item_books_simpledraweeview.setImageURI(Uri.parse(booksList.get(position).getBookImage()));
        ((BooksHolder)holder).item_books_bookname.setText(booksList.get(position).getBookName());
        if (booksList.get(position).getAverage() != null) {
            ((BooksHolder) holder).item_books_ratingBar.setStarMark(Float.parseFloat(booksList.get(position).getAverage()) / 2);
            ((BooksHolder) holder).item_books_average.setText(booksList.get(position).getAverage());
        }

    }

    @Override
    public int getItemCount() {
        if (booksList.size() > 4){
            return 4;
        }else {
            return booksList.size();
        }
    }
    class BooksHolder extends RecyclerView.ViewHolder{

        TextView item_books_bookname;
        TextView item_books_average;
        SimpleDraweeView item_books_simpledraweeview;
        StarBar item_books_ratingBar;
        public BooksHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, BookDetailsActivity.class);
                    Log.e("psn","同城页书详情id："+booksList.get(getAdapterPosition()).getBookid());
                    intent.putExtra("bookID",booksList.get(getAdapterPosition()).getBookid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            item_books_simpledraweeview = (SimpleDraweeView) itemView.findViewById(R.id.item_books_simpledraweeview);
            item_books_bookname = (TextView) itemView.findViewById(R.id.item_books_bookname);
            item_books_ratingBar = (StarBar ) itemView.findViewById(R.id.item_books_ratingBar);
            item_books_average =  itemView.findViewById(R.id.item_books_average);
        }
    }
}
