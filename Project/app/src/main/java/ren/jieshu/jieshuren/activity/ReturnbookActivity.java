package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.ReturnbookAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.ReturnbookBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class ReturnbookActivity extends BaseActivity {
    private RequestCall stringcall;
    private SharedPreferences sp;
    private ArrayList<ReturnbookBean> listall;
    private ReturnbookAdapter returnbookAdapter;
    private Integer page = 1;

    @OnClick(R.id.returnbook_back)
    private void returnbook_back(View view) {
        finish();
    }

    @ViewInject(R.id.returnbook_load_more)
    private RecyclerViewWithFooter returnbook_load_more;


    private ReturnbookBean books;
    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            returnbook_load_more.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            books = gson.fromJson(response, ReturnbookBean.class);
            if (books.getStatus() == 1){
                //刷新数据
                if (books.getList().size() > 0) {
                    page = page + 1;
                    listall.addAll( books.getList());
                    returnbook_load_more.getAdapter().notifyDataSetChanged();
                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        returnbook_load_more.setEmpty(getString(R.string.no_data),R.drawable.nullpoint);
                    }else {
                        returnbook_load_more.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }
                }
            }else if (books.getStatus() == 0){
                Toast.makeText(getBaseContext(),books.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_returnbook);

    }
    protected void initView() {
    }
    @Override
    protected void initData() {
        page = 1;
        listall = new ArrayList<>();
        returnbookAdapter = new ReturnbookAdapter(getBaseContext(),listall);
        returnbook_load_more.setAdapter(returnbookAdapter);
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        returnbook_load_more.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        returnbook_load_more.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page);
            }
        });
        addData(page);
    }
    protected void addData(Integer page) {
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        String timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> map = new HashMap<>();
        map.put("mid", sp.getInt("mid", -1) + "");
        map.put("timestamp", timestamp);
        map.put("page",page.toString());
        map.put("row", "50");
        String sign = Sign.sign(map, sp.getString("token", ""));
        map.put("sign", sign);
        stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/returnorder/list")
                .params(map)
                .build();
        stringcall.execute(stringCallback);

    }
}


