package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.ThisBookFriendAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookBean;
import ren.jieshu.jieshuren.entity.BookFriends;
import ren.jieshu.jieshuren.entity.BookList;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.widget.StarBar;

/**
 * Created by laomaotao on 2017/7/20.
 */

public class ThisBookFriendActivity extends BaseActivity {
    @ViewInject(R.id.thisbookfriend_simpledraweeview)
    private SimpleDraweeView thisbookfriend_simpledraweeview;
    @ViewInject(R.id.thisbookfriend_bookname)
    private TextView thisbookfriend_bookname;
    @ViewInject(R.id.thisbookfriend_average_star)
    private StarBar thisbookfriend_average_star;
    @ViewInject(R.id.thisbookfriend_average)
    private TextView thisbookfriend_average;
    @ViewInject(R.id.thisbookfriend_numraters)
    private TextView thisbookfriend_numraters;
    @ViewInject(R.id.thisbookfriend_bookAuthor)
    private TextView thisbookfriend_bookAuthor;
    @ViewInject(R.id.thisbookfriend_use)
    private TextView thisbookfriend_use;
    @ViewInject(R.id.thisbookfriend_readCount)
    private TextView thisbookfriend_readCount;
    @ViewInject(R.id.thisbookfriend_bookPrice)
    private TextView thisbookfriend_bookPrice;
    //    @ViewInject(R.id.thisbookfriend_tablayout)
//    private TabLayout thisbookfriend_tablayout;
    @ViewInject(R.id.thisbookfriend_recyclerview)
    private RecyclerView thisbookfriend_recyclerview;
//    @ViewInject(R.id.book_friends_load_more)
//    private RecyclerViewWithFooter book_friends_load_more;

    @OnClick(R.id.thisbookfriend_back)
    private void thisbookfriend_back(View view) {
        finish();
    }

    private ThisBookFriendAdapter thisBookFriendAdapter;

    private RequestCall stringcall;
    private SharedPreferences sp;
    private List<BookList> listall;
    private Integer page = 1;

    @OnClick(R.id.returnbook_back)
    private void returnbook_back(View view) {
        finish();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_thisbookfriend);

    }

    //    protected void initView() {
//        bookDetails = (BookBean) getIntent().getExtras().getSerializable("bookDetails");
//        thisBookFriendAdapter = new ThisBookFriendAdapter(getBaseContext());
//        thisbookfriend_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//        thisbookfriend_recyclerview.setAdapter(thisBookFriendAdapter);
//        List<String> list = new ArrayList<>();
//        list.add("全部");
//        list.add("借阅中");
//        list.add("已归还");
//        for (int i = 0 ; i < list.size() ;  i++){
//            TabLayout.Tab tab = thisbookfriend_tablayout.newTab();
//            tab.setText(list.get(i));
//            tab.setTag(list.get(i));
//            if (i == 0) {
//                thisbookfriend_tablayout.addTab(tab);
//            }else {
//                thisbookfriend_tablayout.addTab(tab);
//            }
//        }
//        thisbookfriend_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                thisBookFriendAdapter = new ThisBookFriendAdapter(getBaseContext());
//                thisbookfriend_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//                thisbookfriend_recyclerview.setAdapter(thisBookFriendAdapter);
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }
    private BookBean bookDetails;
    protected void initView() {
        Intent intent = getIntent();
        bookDetails = (BookBean) intent.getExtras().getSerializable("bookDetails");
        thisbookfriend_simpledraweeview.setImageURI(Uri.parse(bookDetails.getBookImage()));
        thisbookfriend_bookname.setText(bookDetails.getBookName());
        thisbookfriend_average_star.setStarMark(Float.parseFloat(bookDetails.getAverage())/2);
        thisbookfriend_average.setText(bookDetails.getAverage());
        thisbookfriend_numraters.setText(bookDetails.getNumraters()+"人评价");
        thisbookfriend_bookAuthor.setText("作者："+bookDetails.getBookAuthor());
        thisbookfriend_use.setText("已借出："+bookDetails.getUse());
        thisbookfriend_readCount.setText("借阅人次："+bookDetails.getReadCount());
        thisbookfriend_bookPrice.setText("￥："+bookDetails.getBookPrice());

    }

    @Override
    protected void initData() {

        listall = new LinkedList<>();
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        //  sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        //  Map<String, String> map = new HashMap<>();
        // map.put("page", page.toString());
        //map.put("row", "50");
        //  map.put("book_id",String.valueOf(bookId));
//        Log.e("psn","图书id："+String.valueOf(bookId));
        stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "api/book/friends")
                .addParams("book_id",String.valueOf(bookDetails.getBookid()))
                .build();
        stringcall.execute(stringCallback);


        //returnbookAdapter = new ReturnbookAdapter(getBaseContext(),listall);
   //     book_friends_load_more.setAdapter(returnbookAdapter);
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
 //       book_friends_load_more.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
//        book_friends_load_more.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                addData(page);
//            }
//        });

    }

    protected void addData() {

    }

    private BookFriends bookFriends;
    private StringCallback stringCallback = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            //thisbookfriend_recyclerview.setEmpty(getString(R.string.internet_error), R.drawable.nullpoint);

        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();

            Gson gson = new Gson();
            bookFriends = gson.fromJson(response, BookFriends.class);

            Log.e("psn","图书评分："+bookFriends.getBookAverage());
            if (bookFriends.getStatus() == 1) {
              //  Log.e("psn","========="+bookFriends.getData().getBookList().get(0).getMember().getName());
                //刷新数据
                if (bookFriends.getData().getBookList().size() > 0) {
             //       Log.e("psn",bookFriends.getData().getBookList().size()+"========="+bookFriends.getData().getBookList().get(0).getMember().getName());

                    listall = bookFriends.getData().getBookList();
                    thisBookFriendAdapter = new ThisBookFriendAdapter(getBaseContext(),listall);
                    thisbookfriend_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    thisbookfriend_recyclerview.setAdapter(thisBookFriendAdapter);
                    thisbookfriend_recyclerview.getAdapter().notifyDataSetChanged();
                } else {

                       // thisbookfriend_recyclerview.setEmpty(getString(R.string.no_data), R.drawable.nullpoint);
                    }
            } else if (bookFriends.getStatus() == 0) {
                Toast.makeText(ThisBookFriendActivity.this, bookFriends.getError(), Toast.LENGTH_SHORT).show();
            }

        }
    };
//    protected void initData() {
//        thisbookfriend_simpledraweeview.setImageURI(Uri.parse(bookDetails.getBookImage()));
//        thisbookfriend_bookname.setText(bookDetails.getBookName());
//        thisbookfriend_average_star.setStarMark(Float.parseFloat(bookDetails.getAverage())/2);
//        thisbookfriend_average.setText(bookDetails.getAverage());
//        thisbookfriend_numraters.setText(bookDetails.getAverage()+"人评价");
//        thisbookfriend_bookAuthor.setText("作者："+bookDetails.getBookAuthor());
//        thisbookfriend_use.setText("已借出："+bookDetails.getUse());
//        thisbookfriend_readCount.setText("借阅人次："+bookDetails.getUse());
//        thisbookfriend_bookPrice.setText("￥："+bookDetails.getBookPrice());
//    }

}
