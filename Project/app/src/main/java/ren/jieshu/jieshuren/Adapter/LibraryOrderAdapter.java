package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.OrderdetailsActivity;
import ren.jieshu.jieshuren.entity.BooksBean;

import java.util.List;

/**
 * Created by laomaotao on 2017/7/4.
 */

public class LibraryOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<BooksBean> booksList;
    public LibraryOrderAdapter(Context context, List<BooksBean> booksList){
        super();
        this.context = context;
        this.booksList = booksList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_libraryorder,parent,false);
        RecyclerView.ViewHolder libraryOrderHolder = new LibraryOrderAdapter.LibraryOrderHolder(view);
        return libraryOrderHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ((LibraryOrderAdapter.LibraryOrderHolder)holder).item_libraryorder_recyclerview.setLayoutManager(linearLayoutManager);
        //设置适配器
        if (booksList.get(position).getBooks() != null) {
            ItemLibraryOrderAdapter booksAdapter = new ItemLibraryOrderAdapter(context, booksList.get(position).getBooks());
            ((LibraryOrderAdapter.LibraryOrderHolder) holder).item_libraryorder_recyclerview.setAdapter(booksAdapter);
        }
        ((LibraryOrderAdapter.LibraryOrderHolder)holder).item_libraryorder_button_orderdetails.setOnClickListener(this);
        ((LibraryOrderAdapter.LibraryOrderHolder)holder).item_libraryorder_j_order_number.setText("订单编号："+booksList.get(position).getJ_order_number());
        ((LibraryOrderHolder)holder).item_libraryorder_book_count.setText("共"+booksList.get(position).getBook_count()+"本书");
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_libraryorder_button_orderdetails:
                Intent intent = new Intent();
                intent.setClass(context,OrderdetailsActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    class LibraryOrderHolder extends RecyclerView.ViewHolder{

        RecyclerView item_libraryorder_recyclerview;
        Button item_libraryorder_button_orderdetails;
        TextView item_libraryorder_j_order_number;
        TextView item_libraryorder_book_count;
        public LibraryOrderHolder(View itemView) {
            super(itemView);
            item_libraryorder_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_libraryorder_recyclerview);
            item_libraryorder_button_orderdetails = (Button) itemView.findViewById(R.id.item_libraryorder_button_orderdetails);
            item_libraryorder_j_order_number = (TextView) itemView.findViewById(R.id.item_libraryorder_j_order_number);
            item_libraryorder_book_count = (TextView) itemView.findViewById(R.id.item_libraryorder_book_count);
        }
    }
}
