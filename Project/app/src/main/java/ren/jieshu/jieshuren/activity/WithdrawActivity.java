package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/9/25.
 */

public class WithdrawActivity extends BaseActivity {
    private String count;
    private SharedPreferences sp;
    private RequestCall call;

    @OnClick(R.id.withdraw_back)
    private void withdraw_back(View view){
        finish();
    }
    @ViewInject(R.id.withdraw_sum)
    private TextView withdraw_sum;
    @ViewInject(R.id.withdraw_money)
    private EditText withdraw_money;
    @OnClick(R.id.withdraw_withdrawall)
    private void withdraw_withdrawall(View view){
        withdraw_money.setText(withdraw_sum.getText().toString().trim());
    }
    @OnClick(R.id.withdraw_withdraw)
    private void withdraw_withdraw(View view){
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        String timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        map.put("price",withdraw_money.getText().toString().trim());
        String sign = Sign.sign(map,sp.getString("token",""));
        map.put("sign",sign);
        call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/withdraw/confirm")
                .params(map)
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
                iosLoadingDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                iosLoadingDialog.dismiss();
                Gson gson = new Gson();
                MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                if (messageBean.getStatus() == 1){
                    Toast.makeText(getBaseContext(),messageBean.getMsg(),Toast.LENGTH_SHORT).show();
                    finish();
                }else if (messageBean.getStatus() == 0){
                    Toast.makeText(getBaseContext(),messageBean.getError(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_withdraw);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        count = getIntent().getStringExtra("count");
        withdraw_sum.setText(count);
    }
}
