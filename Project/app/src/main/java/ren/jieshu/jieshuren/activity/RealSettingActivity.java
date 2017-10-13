package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.lidroid.xutils.view.annotation.event.OnClick;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;

/**
 * Created by laomaotao on 2017/8/26.
 */

public class RealSettingActivity extends BaseActivity {
    private Integer mid;

    @OnClick(R.id.realsetting_rl_updateuser)
    public void realsetting_rl_updateuser(View arg0){
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),SettingActivity.class);
            startActivity(intent);
    }
    @OnClick(R.id.realsetting_rl_versionupdate)
    public void realsetting_rl_versionupdate(View arg0){
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),SettingActivity.class);
            startActivity(intent);
    }
    @OnClick(R.id.realsetting_rl_about)
    public void realsetting_rl_about(View arg0){
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),AboutActivity.class);
            startActivity(intent);
    }
    @OnClick(R.id.realsetting_rl_finish)
    public void realsetting_rl_finish(View arg0){
        SharedPreferences.Editor editor = getSharedPreferences("member",MODE_PRIVATE).edit();
        editor .clear();
        editor .commit();
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.TAG_EXIT, true);
        startActivity(intent);
    }
    @OnClick(R.id.realsetting_rl_location)
    public void realsetting_rl_location(View arg0){
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),AdressActivity.class);
        intent.putExtra("activity","RealSettingActivity");
        startActivity(intent);
    }
    @OnClick(R.id.realsetting_rl_dataclean)
    public void realsetting_rl_dataclean(View arg0){
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),CacheActivity.class);
            startActivity(intent);
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_realsetting);
    }

    @Override
    protected void initView() {
        SharedPreferences sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        mid = sp.getInt("mid", -1);
    }

    @Override
    protected void initData() {

    }
}
