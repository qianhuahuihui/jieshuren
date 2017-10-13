package ren.jieshu.jieshuren.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import ren.jieshu.jieshuren.Adapter.ThisBookFriendAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookBean;
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
    @ViewInject(R.id.thisbookfriend_tablayout)
    private TabLayout thisbookfriend_tablayout;
    @ViewInject(R.id.thisbookfriend_recyclerview)
    private RecyclerView thisbookfriend_recyclerview;
    @OnClick(R.id.thisbookfriend_back)
    private void thisbookfriend_back(View view){
        finish();
    }

    private ThisBookFriendAdapter thisBookFriendAdapter;
    private BookBean bookDetails;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_thisbookfriend);

    }

    protected void initView() {
        bookDetails = (BookBean) getIntent().getExtras().getSerializable("bookDetails");
        thisBookFriendAdapter = new ThisBookFriendAdapter(getBaseContext());
        thisbookfriend_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        thisbookfriend_recyclerview.setAdapter(thisBookFriendAdapter);
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("借阅中");
        list.add("已归还");
        for (int i = 0 ; i < list.size() ;  i++){
            TabLayout.Tab tab = thisbookfriend_tablayout.newTab();
            tab.setText(list.get(i));
            tab.setTag(list.get(i));
            if (i == 0) {
                thisbookfriend_tablayout.addTab(tab);
            }else {
                thisbookfriend_tablayout.addTab(tab);
            }
        }
        thisbookfriend_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                thisBookFriendAdapter = new ThisBookFriendAdapter(getBaseContext());
                thisbookfriend_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                thisbookfriend_recyclerview.setAdapter(thisBookFriendAdapter);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    protected void initData() {
        thisbookfriend_simpledraweeview.setImageURI(Uri.parse(bookDetails.getBookImage()));
        thisbookfriend_bookname.setText(bookDetails.getBookName());
        thisbookfriend_average_star.setStarMark(Float.parseFloat(bookDetails.getAverage())/2);
        thisbookfriend_average.setText(bookDetails.getAverage());
        thisbookfriend_numraters.setText(bookDetails.getAverage()+"人评价");
        thisbookfriend_bookAuthor.setText("作者："+bookDetails.getBookAuthor());
        thisbookfriend_use.setText("已借出："+bookDetails.getUse());
        thisbookfriend_readCount.setText("借阅人次："+bookDetails.getUse());
        thisbookfriend_bookPrice.setText("￥："+bookDetails.getBookPrice());
    }

}
