package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
 * Created by laomaotao on 2017/8/20.
 */

public class HomeFragmentBooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<BooksBean> booksList;
    public HomeFragmentBooksAdapter(Context context, List<BooksBean> booksList){
        this.context = context;
        this.booksList = booksList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_homefragment_books,parent,false);
        RecyclerView.ViewHolder booksHolder = new HomeFragmentBooksViewHolder(view);
        return booksHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((HomeFragmentBooksViewHolder)holder).item_homefragment_books_simpledraweeview.setImageURI(Uri.parse(booksList.get(position).getBookImage()));
        ((HomeFragmentBooksViewHolder)holder).item_homefragment_books_bookname.setText(booksList.get(position).getBookName());
        if (booksList.get(position).getAverage() != null) {
            ((HomeFragmentBooksViewHolder) holder).item_homefragment_books_ratingBar.setStarMark(Float.parseFloat(booksList.get(position).getAverage()) / 2);
            ((HomeFragmentBooksViewHolder) holder).item_homefragment_books_average.setText(booksList.get(position).getAverage());
        }

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
    class HomeFragmentBooksViewHolder extends RecyclerView.ViewHolder{

        TextView item_homefragment_books_bookname;
        TextView item_homefragment_books_average;
        SimpleDraweeView item_homefragment_books_simpledraweeview;
        StarBar item_homefragment_books_ratingBar;
        public HomeFragmentBooksViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(context, BookDetailsActivity.class);
                    intent.putExtra("bookID",booksList.get(getAdapterPosition()).getBookid());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
            item_homefragment_books_simpledraweeview = (SimpleDraweeView) itemView.findViewById(R.id.item_homefragment_books_simpledraweeview);
            item_homefragment_books_bookname = (TextView) itemView.findViewById(R.id.item_homefragment_books_bookname);
            item_homefragment_books_ratingBar = (StarBar ) itemView.findViewById(R.id.item_homefragment_books_ratingBar);
            item_homefragment_books_average = itemView.findViewById(R.id.item_homefragment_books_average);
        }
    }
}