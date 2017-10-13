package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.BooklistAdapter;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.ListBean;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class MyBookshelfActivity extends BaseActivity {

    @OnClick(R.id.mybookshelffragment_back)
    private void mybookshelffragment_back(View view) {

        finish();
    }
    @ViewInject(R.id.mybookshelffragment_recyclerview)
    private RecyclerView mybookshelffragment_recyclerview;
    @ViewInject(R.id.mybookshelffragment_booksnumber)
    private TextView mybookshelffragment_booksnumber;
    @ViewInject(R.id.mybookshelffragment_bookstotal)
    private TextView mybookshelffragment_bookstotal;
    @ViewInject(R.id.mybookshelffragment_pricetotal)
    private TextView mybookshelffragment_pricetotal;

    private RequestCall call;
    private BigDecimal prices;

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_mybookshelf);

    }

    protected void initView() {
        SharedPreferences sp = getSharedPreferences("member", Context.MODE_PRIVATE);

        String timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        String sign = Sign.sign(map,sp.getString("token",""));
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/bookrack/list")
                .addParams("mid", sp.getInt("mid",-1)+"")
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .build();
        call.execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {

                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                ListBean list = gson.fromJson(response, ListBean.class);
                if (list.getStatus() == 1){
                    if (list.getTotal() > 0){
                        BooklistAdapter booklistAdapter = new BooklistAdapter(getBaseContext(),list.getList());
                        mybookshelffragment_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                        mybookshelffragment_recyclerview.setAdapter(booklistAdapter);
                        mybookshelffragment_booksnumber.setText("图书："+list.getTotal()+ "本");
                        mybookshelffragment_bookstotal.setText("图书合计："+list.getList().size()+ "本");
                        prices = new BigDecimal("0.0");
                        for (int i = 0 ; i < list.getList().size() ; i++){
                            prices = prices.add(list.getList().get(i).getPrice());
                        }
                        mybookshelffragment_pricetotal.setText(prices.toString());
                    }
                }else if (list.getStatus() == 0){
                    Toast.makeText(getBaseContext(),list.getError(),Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    @Override
    protected void initData() {

    }


}
