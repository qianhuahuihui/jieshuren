package ren.jieshu.jieshuren.activity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import ren.jieshu.jieshuren.Manager.DataCleanManager;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;

/**
 * Created by laomaotao on 2017/9/6.
 */

public class CacheActivity extends BaseActivity {

    @OnClick(R.id.cache_back)
    private void cache_back(View view){
        finish();
    }
    @ViewInject(R.id.cache_cachesize)
    private TextView cache_cachesize;
    @OnClick(R.id.cache_removecache)
    private void cache_removecache(View view){
        boolean isSuccess = DataCleanManager.clearAllCache(getBaseContext());
        if (isSuccess){
            Toast.makeText(getBaseContext(),"缓存清理成功",Toast.LENGTH_SHORT).show();
            getTotalCacheSize();
        }else {
            Toast.makeText(getBaseContext(),"缓存清理失败",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_cache);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
      getTotalCacheSize();
    }
    private void getTotalCacheSize (){
        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(CacheActivity.this);
            cache_cachesize.setText(cacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
