package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.TraceListAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.ExpressBean;
import ren.jieshu.jieshuren.entity.ExpressResult;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.Data;
import ren.jieshu.jieshuren.util.Sign;

public class ExpressActivity extends BaseActivity {

    @ViewInject(R.id.lvTrace)
    private ListView listView;
    @ViewInject(R.id.e_code)
    private TextView e_code;
    @ViewInject(R.id.e_number)
    private TextView e_number;

    @OnClick(R.id.express_back)
    private void express_back(View view) {
        finish();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_express);
    }

    @Override
    protected void initView() {


    }


    private SharedPreferences sp;
    private RequestCall provinceCall;
    private TraceListAdapter adapter;
    private ExpressResult expressResult;
    private List<Data> traceList = new ArrayList<>(10);
    private String code, postid;

    protected void initData() {

        sp = getSharedPreferences("member", Context.MODE_PRIVATE);

        Intent intent = getIntent();
         code = intent.getStringExtra("express_code").trim();
         postid = intent.getStringExtra("express_number").trim();

        Log.e("psn", code + "    " + postid);
        if ("".equals(code) || code == null) {
            getExpress(postid);

        } else {
            getExpressInfo();
        }


    }

    private void getExpressInfo() {


        String timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> sort = new HashMap<>();
        sort.put("mid", sp.getInt("mid", -1) + "");
        sort.put("timestamp", timestamp);
        sort.put("e_code", code);
        sort.put("e_number", postid);
        String sign = Sign.sign(sort, sp.getString("token", ""));
        sort.put("sign", sign);
        provinceCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/express/getExpressInfo")
                .params(sort)
                .build();
        provinceCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                expressResult = gson.fromJson(response, ExpressResult.class);
                if (expressResult.getStatus() == 1) {

                    e_code.setText(expressResult.getName());
                    e_number.setText("运单编码：" + expressResult.getE_number());

                    traceList = expressResult.getData();
                    adapter = new TraceListAdapter(ExpressActivity.this, traceList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else if (expressResult.getStatus() == 0) {
                    Toast.makeText(getBaseContext(), expressResult.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private ExpressBean expressBean;

    private void getExpress(String express) {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> sort = new HashMap<>();
        sort.put("mid", sp.getInt("mid", -1) + "");
        sort.put("timestamp", timestamp);
        sort.put("code", express);
        String sign = Sign.sign(sort, sp.getString("token", ""));
        sort.put("sign", sign);
        RequestCall call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/express/auto")
                .params(sort)
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                expressBean = gson.fromJson(response, ExpressBean.class);
                if (expressBean.getStatus() == 1) {
                    code = expressBean.getExpress().getCom_code();
                    getExpressInfo();
                } else if (expressBean.getStatus() == 0) {
                    Toast.makeText(getBaseContext(), "暂时没有快递信息！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
