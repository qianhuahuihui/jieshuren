package ren.jieshu.jieshuren.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.ClassifiedqueryActivity;
import ren.jieshu.jieshuren.activity.ItemHomeFragmentActivity;
import ren.jieshu.jieshuren.activity.LenbookActivity;
import ren.jieshu.jieshuren.activity.NewbookActivity;
import ren.jieshu.jieshuren.activity.SearchActivity;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;

/**
 * Created by laomaotao on 2017/7/1.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<BooksBean> booksList = new ArrayList<>();
    private static final int TYPE_INFO = 0;
    private static final int TYPE_IMGS = 1;
    public HomeFragmentAdapter(Context context, List<BooksBean> booksList){
        super();
        this.context = context;
        this.booksList = booksList;

    }
    public void setList(List<BooksBean> booksList){
        this.booksList = booksList;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = getViewHolderByViewType(viewType);

        return holder;
    }
    private RecyclerView.ViewHolder getViewHolderByViewType(int viewType) {
        View item_homefragment_one = View.inflate(context, R.layout.item_homefragment_one, null);
        View item_homefragment_two = View.inflate(context, R.layout.item_homefragment_two, null);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_INFO:
                holder = new HomeFragmentViewHolderOne(item_homefragment_one);
                break;
            case TYPE_IMGS:
                holder = new HomeFragmentViewHolderTwo(item_homefragment_two);
                break;
        }
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == 0){
            ((HomeFragmentAdapter.HomeFragmentViewHolderOne) holder).item_homefragment_newbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, NewbookActivity.class);
                    intent.putExtra("flag","1");
                    context.startActivity(intent);
                }
            });
            ((HomeFragmentAdapter.HomeFragmentViewHolderOne) holder).item_homefragment_loanlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, NewbookActivity.class);
                    intent.putExtra("flag","2");
                    context.startActivity(intent);
                }
            });
            ((HomeFragmentAdapter.HomeFragmentViewHolderOne) holder).item_homefragment_lendbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, LenbookActivity.class);
                    context.startActivity(intent);
                }
            });
            ((HomeFragmentAdapter.HomeFragmentViewHolderOne) holder).item_homefragment_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, SearchActivity.class);
                    context.startActivity(intent);
                }
            });
            ((HomeFragmentAdapter.HomeFragmentViewHolderOne) holder).item_homefragment_classifiedquery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, ClassifiedqueryActivity.class);
                    context.startActivity(intent);
                }
            });
        }else {
            position = position-1;
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_member_name.setText(booksList.get(position).getMember_name());
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_book_count.setText("借了" + booksList.get(position).getBook_count() + "本书");
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_area_name.setText(booksList.get(position).getArea_name());
            java.text.DecimalFormat df=new java.text.DecimalFormat("#.0");
            double d=Double.parseDouble(booksList.get(position).getDistance())/1000;
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_distancce.setText(df.format(d)+"km");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_time.setText(sdf.format(booksList.get(position).getTime()));
            if (booksList.get(position).getMember_image() != null) {
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_headview.setImageURI(Uri.parse(HttpURLConfig.URL + booksList.get(position).getMember_image()));
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_recyclerview.setLayoutManager(linearLayoutManager);
            //设置适配器
            BooksAdapter booksAdapter = new BooksAdapter(context, booksList.get(position).getBooks());
            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_recyclerview.setAdapter(booksAdapter);
            final int finalPosition = position;
            ((HomeFragmentViewHolderTwo) holder).item_homefragment_two_showall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                            intent.setClass(context,ItemHomeFragmentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("BooksBean",booksList.get(finalPosition));
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                }
            });

//            ((HomeFragmentAdapter.HomeFragmentViewHolderTwo) holder).item_homefragment_two_recyclerview.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    switch (motionEvent.getAction()) {//第一个触摸点
//                        case MotionEvent.ACTION_DOWN:  //按下 = 0
//                            break;
//                        case MotionEvent.ACTION_MOVE:  //移动 = 2
//
//                            break;
//                        case MotionEvent.ACTION_UP:    // 抬起 = 1
//                            Intent intent = new Intent();
//                            intent.setClass(context,ItemHomeFragmentActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("BooksBean",booksList.get(finalPosition));
//                            intent.putExtras(bundle);
//                            context.startActivity(intent);
//                            break;
//                    }
//                    return true;
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        if (booksList == null){
            return 1;
        }else {
            return 1 + booksList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_INFO;
        }
        if (position > 0 && position <= booksList.size()) {//mImgList){
            return TYPE_IMGS;
        }
        return 0;
    }

    class HomeFragmentViewHolderTwo extends RecyclerView.ViewHolder{
        TextView item_homefragment_two_member_name;
        TextView item_homefragment_two_book_count;
        TextView item_homefragment_two_area_name;
        RecyclerView item_homefragment_two_recyclerview;
        SimpleDraweeView item_homefragment_two_headview;
        TextView item_homefragment_two_showall;
        TextView item_homefragment_two_distancce;
        TextView item_homefragment_two_time;
        public HomeFragmentViewHolderTwo(View itemView) {
            super(itemView);

            item_homefragment_two_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_homefragment_two_recyclerview);
            item_homefragment_two_member_name = (TextView) itemView.findViewById(R.id.item_homefragment_two_member_name);
            item_homefragment_two_book_count = (TextView) itemView.findViewById(R.id.item_homefragment_two_book_count);
            item_homefragment_two_area_name = (TextView) itemView.findViewById(R.id.item_homefragment_two_area_name);
            item_homefragment_two_headview = (SimpleDraweeView) itemView.findViewById(R.id.item_homefragment_two_headview);
            item_homefragment_two_showall = itemView.findViewById(R.id.item_homefragment_two_showall);
            item_homefragment_two_distancce = itemView.findViewById(R.id.item_homefragment_two_distancce);
            item_homefragment_two_time = itemView.findViewById(R.id.item_homefragment_two_time);

        }
    }
    class HomeFragmentViewHolderOne extends RecyclerView.ViewHolder{
        RelativeLayout item_homefragment_search;
        LinearLayout item_homefragment_newbook;
        LinearLayout item_homefragment_loanlist;
        LinearLayout item_homefragment_lendbook;
        LinearLayout item_homefragment_classifiedquery;

        public HomeFragmentViewHolderOne(View itemView) {
            super(itemView);
            item_homefragment_search = (RelativeLayout) itemView.findViewById(R.id.item_homefragment_search);
            item_homefragment_newbook = (LinearLayout) itemView.findViewById(R.id.item_homefragment_newbook);
            item_homefragment_loanlist = (LinearLayout) itemView.findViewById(R.id.item_homefragment_loanlist);
            item_homefragment_lendbook = (LinearLayout) itemView.findViewById(R.id.item_homefragment_lendbook);
            item_homefragment_classifiedquery = (LinearLayout) itemView.findViewById(R.id.item_homefragment_classifiedquery);
        }
    }


}