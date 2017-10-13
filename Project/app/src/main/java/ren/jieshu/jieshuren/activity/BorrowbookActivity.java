package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import ren.jieshu.jieshuren.Adapter.BooklistPagerAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class BorrowbookActivity extends BaseActivity {

    private BooklistPagerAdapter booklistPagerAdapter;

    @OnClick(R.id.libraryorderfragment_back)
    private void libraryorderfragment_back(View view) {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),MainActivity.class);
        intent.putExtra("PAYTYPE","0");
        startActivity(intent);
        finish();
    }
    @ViewInject(R.id.libraryorderfragment_tablayout)
    private TabLayout libraryorderfragment_tablayout;
    @ViewInject(R.id.libraryorderfragment_viewPager)
    private ViewPager libraryorderfragment_viewPager;


    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_libraryorder);

    }

    public void initView() {
        List<String> list = new ArrayList<>();
        list.add("未付款");
        list.add("待发货");
        list.add("待收货");
        list.add("已完成");
        booklistPagerAdapter = new BooklistPagerAdapter(getSupportFragmentManager(),list);
        libraryorderfragment_viewPager.setAdapter(booklistPagerAdapter);
        libraryorderfragment_tablayout.setupWithViewPager(libraryorderfragment_viewPager);
    }
    @Override
    protected void initData() {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),MainActivity.class);
            intent.putExtra("PAYTYPE","0");
            startActivity(intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}