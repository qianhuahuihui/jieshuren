package ren.jieshu.jieshuren.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lidroid.xutils.ViewUtils;

import ren.jieshu.jieshuren.loading.IOSLoadingDialog;

/**
 * Created by laomaotao on 2017/7/11.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static IOSLoadingDialog iosLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView();
        // IOC注解注入
        ViewUtils.inject(this);
        iosLoadingDialog = new IOSLoadingDialog();
        initView();
        initData();
    }
    // 设置界面视图
    protected abstract void setContentView();
    // 初始化界面
    protected abstract void initView();
    // 初始化数据
    protected abstract void initData();





}
