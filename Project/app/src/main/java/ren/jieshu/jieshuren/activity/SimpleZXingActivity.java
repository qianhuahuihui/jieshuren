package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/9/7.
 */

public class SimpleZXingActivity extends BaseActivity {
    @ViewInject(R.id.simplezxing_msg)
    private TextView simplezxing_msg;
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private RequestCall call;
    private CaptureActivity mCaptureContext;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_simplezxing);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        QrScan.getInstance().launchScan(SimpleZXingActivity.this, new IScanModuleCallBack() {
            @Override
            public void OnReceiveDecodeResult(final Context context, final String result) {
                mCaptureContext = (CaptureActivity)context;

                AlertDialog dialog = new AlertDialog.Builder(mCaptureContext)
                        .setMessage("是否转接这本图书？")
                        .setCancelable(false)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                dialog.dismiss();
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
                                        .url(HttpURLConfig.URL + "private/jieshu/subtenancyfinish")
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
                                        if (messageBean.getStatus() == 1) {
                                            Toast.makeText(getBaseContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                                            simplezxing_msg.setText(messageBean.getMsg());
                                        } else if (messageBean.getStatus() == 0) {
                                            Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                                            simplezxing_msg.setText(messageBean.getError());
                                        }
                                        dialog.dismiss();
                                    }
                                });
                                iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
                            }
                        })
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                QrScan.getInstance().finishScan(mCaptureContext);
                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }

}

