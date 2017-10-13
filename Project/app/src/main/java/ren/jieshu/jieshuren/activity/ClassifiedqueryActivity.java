package ren.jieshu.jieshuren.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.BooksFragmentPagerAdapter;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.TypesBean;


/**
 * Created by laomaotao on 2017/8/13.
 */

public class ClassifiedqueryActivity extends BaseActivity {
    @OnClick(R.id.classifiedquery_back)
    private void classifiedquery_back(View view) {
        finish();
    }

    @ViewInject(R.id.classifiedquery_viewPager)
    private ViewPager classifiedquery_viewPager;
    @ViewInject(R.id.classifiedquery_tablayout)
    private TabLayout classifiedquery_tablayout;


    private RequestCall typecall = OkHttpUtils.get().url(HttpURLConfig.URL + "api/book/type/json")
//               .addParams("row", "0")
            .build();

    private BooksFragmentPagerAdapter booksFragmentPagerAdapter;

    private StringCallback typeCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            iosLoadingDialog.dismiss();

        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();

            Gson gson = new Gson();
            TypesBean types = gson.fromJson(response, TypesBean.class);
            if (types.getStatus() == 1){
                booksFragmentPagerAdapter = new BooksFragmentPagerAdapter(getSupportFragmentManager(),types.getTypes());
                classifiedquery_viewPager.setAdapter(booksFragmentPagerAdapter);
                classifiedquery_tablayout.setupWithViewPager(classifiedquery_viewPager);

            }else if (types.getStatus() == 0){
                Toast.makeText(getBaseContext(),types.getError(),Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_classifiedquery);

    }
    @Override
    protected void initView() {
        typecall.execute(typeCallback);
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
    }
    @Override
    protected void initData() {

    }
}
