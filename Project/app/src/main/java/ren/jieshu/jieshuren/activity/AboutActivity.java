package ren.jieshu.jieshuren.activity;

import android.view.View;

import com.lidroid.xutils.view.annotation.event.OnClick;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;

/**
 * Created by laomaotao on 2017/8/23.
 */

public class AboutActivity extends BaseActivity {
    @OnClick(R.id.about_back)
    private void about_back(View view){
        finish();
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
