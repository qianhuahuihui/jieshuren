package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class LenbookActivity extends BaseActivity {
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private RequestCall call;
    private CaptureActivity mCaptureContext;

    @OnClick(R.id.lenbook_sao)
    private void lenbook_sao(View view){
        QrScan.getInstance().launchScan(LenbookActivity.this, new IScanModuleCallBack() {
            @Override
            public void OnReceiveDecodeResult(final Context context, final String result) {
                mCaptureContext = (CaptureActivity)context;
                QrScan.getInstance().restartScan(mCaptureContext);
                SharedPreferences sp = getSharedPreferences("member", Context.MODE_PRIVATE);
                String timestamp = System.currentTimeMillis() / 1000 + "";
                Map<String, String> map = new HashMap<>();
                map.put("mid", sp.getInt("mid", -1) + "");
                map.put("timestamp", timestamp);
                map.put("private_isbn", result);
                String sign = Sign.sign(map, sp.getString("token", ""));
                map.put("sign", sign);
                call = OkHttpUtils.get()
                        .url(HttpURLConfig.URL + "private/subtenancy/createBook")
                        .params(map)
                        .build();
                call.execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        Log.e("aaaaaaaa",response);
                        BookBean bookBean = gson.fromJson(response, BookBean.class);
                        if (bookBean.getStatus() == 1) {
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(),LenDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bookBean.getBook().setIsbn(result);
                            bundle.putSerializable("book",bookBean.getBook());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else if (bookBean.getStatus() == 0) {
                            Toast.makeText(getBaseContext(), bookBean.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    @OnClick(R.id.lenbook_back)
    private void lenbook_back(View view){
        finish();
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lenbook);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}

