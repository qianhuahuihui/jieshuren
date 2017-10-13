package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import ren.jieshu.jieshuren.Adapter.MyWalletFragmentPagerAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class MyWalletActivity extends BaseActivity {
    @OnClick(R.id.mywalletfragment_back)
    private void mywalletfragment_back(View view) {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),MainActivity.class);
        intent.putExtra("PAYTYPE","0");
        startActivity(intent);
        finish();
        }
    @ViewInject(R.id.mywalletfragment_tablayout)
    private TabLayout mywalletfragment_tablayout;
    @ViewInject(R.id.mywalletfragment_viewPager)
    private ViewPager mywalletfragment_viewPager;

    private MyWalletFragmentPagerAdapter myWalletFragmentPagerAdapter;

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_mywallet);

    }

    protected void initView() {
        myWalletFragmentPagerAdapter = new MyWalletFragmentPagerAdapter(getSupportFragmentManager());
        mywalletfragment_viewPager.setAdapter(myWalletFragmentPagerAdapter);
        mywalletfragment_tablayout.setupWithViewPager(mywalletfragment_viewPager);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myWalletFragmentPagerAdapter = new MyWalletFragmentPagerAdapter(getSupportFragmentManager());
        mywalletfragment_viewPager.setAdapter(myWalletFragmentPagerAdapter);
        mywalletfragment_tablayout.setupWithViewPager(mywalletfragment_viewPager);
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
