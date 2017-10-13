package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.ConsumeBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.WechatBean;
import ren.jieshu.jieshuren.loading.IOSLoadingDialog;
import ren.jieshu.jieshuren.util.Sign;

import static ren.jieshu.jieshuren.MyApplication.api;

/**
 * Created by laomaotao on 2017/9/24.
 */

public class PayLenActivity extends BaseActivity {
    private String type = "0";
    private String timestamp;
    private RequestCall call;
    private SharedPreferences sp;
    private String price;
    private String private_isbn;
    private String j_available_blance;
    private IOSLoadingDialog iosLoadingDialog;

    @OnClick(R.id.pay_pay)
    private void pay_pay(View view){
        if (type.equals("1")){
            timestamp = System.currentTimeMillis()/1000+"";
            Map<String,String> map = new HashMap<>();
            map.put("mid",sp.getInt("mid",-1)+"");
            map.put("timestamp",timestamp);
            map.put("private_isbn",private_isbn);
            map.put("pay_type","1");
            map.put("price",price);
            String sign = Sign.sign(map,sp.getString("token",""));
            map.put("sign",sign);
            call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/wechat/pay/subtenancy")
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
                    WechatBean wechatBean = gson.fromJson(response, WechatBean.class);
                    if (wechatBean.getStatus() == 1){
                        PayReq request = new PayReq();
                        request.appId = wechatBean.getSuccess().getAppid();
                        request.partnerId = wechatBean.getSuccess().getPartnerid();
                        request.prepayId= wechatBean.getSuccess().getPrepayid();
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr= wechatBean.getSuccess().getNoncestr();
                        request.timeStamp= wechatBean.getSuccess().getTimestamp();
                        request.sign= wechatBean.getSuccess().getSign();
                        api.sendReq(request);
                    }else if (wechatBean.getStatus() == 0){
                        Toast.makeText(getBaseContext(),wechatBean.getError(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }else if(type.equals("0")){
            if (Double.parseDouble(j_available_blance) > Double.parseDouble(price)) {
                timestamp = System.currentTimeMillis()/1000+"";
                Map<String,String> map = new HashMap<>();
                map.put("mid",sp.getInt("mid",-1)+"");
                map.put("timestamp",timestamp);
                map.put("private_isbn",private_isbn);
                map.put("pay_type","0");
                map.put("price",price);
                String sign = Sign.sign(map,sp.getString("token",""));
                map.put("sign",sign);
                call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/wechat/pay/subtenancy")
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
                        WechatBean wechatBean = gson.fromJson(response, WechatBean.class);
                        if (wechatBean.getStatus() == 1){
                            Toast.makeText(getBaseContext(),wechatBean.getMsg(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(),BorrowbookActivity.class);
                            startActivity(intent);
                        }else if (wechatBean.getStatus() == 0){
                            Toast.makeText(getBaseContext(),wechatBean.getError(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
            }

        }
    }
    @OnClick(R.id.pay_recharge)
    private void pay_recharge(View view){
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), RechargeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.pay_Balance_payment)
    private void pay_Balance_payment(View view){
        pay_iv_Balance_payment.setImageResource(R.drawable.checked);
        pay_iv_WeChat_payment.setImageResource(R.drawable.unchoose);
        pay_iv_Alipay.setImageResource(R.drawable.unchoose);
        pay_iv_Integral_payment.setImageResource(R.drawable.unchoose);
        type = "0";
    }
    @OnClick(R.id.pay_WeChat_payment)
    private void pay_WeChat_payment(View view){
        pay_iv_Balance_payment.setImageResource(R.drawable.unchoose);
        pay_iv_WeChat_payment.setImageResource(R.drawable.checked);
        pay_iv_Alipay.setImageResource(R.drawable.unchoose);
        pay_iv_Integral_payment.setImageResource(R.drawable.unchoose);
        type = "1";
    }
    @OnClick(R.id.pay_Alipay)
    private void pay_Alipay(View view){
        pay_iv_Balance_payment.setImageResource(R.drawable.unchoose);
        pay_iv_WeChat_payment.setImageResource(R.drawable.unchoose);
        pay_iv_Alipay.setImageResource(R.drawable.checked);
        pay_iv_Integral_payment.setImageResource(R.drawable.unchoose);
        type = "2";
    }
    @OnClick(R.id.pay_Integral_payment)
    private void pay_Integral_payment(View view){
        pay_iv_Balance_payment.setImageResource(R.drawable.unchoose);
        pay_iv_WeChat_payment.setImageResource(R.drawable.unchoose);
        pay_iv_Alipay.setImageResource(R.drawable.unchoose);
        pay_iv_Integral_payment.setImageResource(R.drawable.checked);
        type = "3";
    }
    @ViewInject(R.id.pay_iv_Balance_payment)
    private ImageView pay_iv_Balance_payment;
    @ViewInject(R.id.pay_iv_WeChat_payment)
    private ImageView pay_iv_WeChat_payment;
    @ViewInject(R.id.pay_iv_Alipay)
    private ImageView pay_iv_Alipay;
    @ViewInject(R.id.pay_iv_Integral_payment)
    private ImageView pay_iv_Integral_payment;
    @ViewInject(R.id.pay_total_price)
    private TextView pay_total_price;
    @ViewInject(R.id.pay_blance)
    private TextView pay_blance;

    @OnClick(R.id.pay_back)
    private void pay_back(View view){
        finish();
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pay);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        addData(1);
    }
    @Override
    protected void initView() {
        iosLoadingDialog = new IOSLoadingDialog();
        iosLoadingDialog.setHintMsg("正在支付");
    }

    @Override
    protected void initData() {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        private_isbn = getIntent().getStringExtra("private_isbn");
        price = getIntent().getStringExtra("price");
        String PAYTYPE = getIntent().getStringExtra("PAYTYPE");
        pay_total_price.setText("￥"+price);
        SharedPreferences preferences = getSharedPreferences("member", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //PAYTYPE 0 订单支付，转借，调回借书订单
        //        1 磨损支付，充值，调回钱包
        editor.putString("PAYTYPE", PAYTYPE);
        editor.commit();
        addData(1);

    }

    protected void addData(Integer page) {
        timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        map.put("page",page.toString());
        map.put("row","50");
        String sign = Sign.sign(map,sp.getString("token",""));
        map.put("sign",sign);
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/consume/get")
                .params(map)
                .build();
        call.execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {
                iosLoadingDialog.dismiss();
                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                iosLoadingDialog.dismiss();

                Gson gson = new Gson();
                ConsumeBean consumeBean = gson.fromJson(response, ConsumeBean.class);
                if (consumeBean.getStatus() == 1) {
                    j_available_blance = consumeBean.getBlance().getJ_available_blance().toString();
                    pay_blance.setText("剩余余额￥"+j_available_blance);

                }
                else if (consumeBean.getStatus() == 0){
                    Toast.makeText(getBaseContext(),consumeBean.getError(),Toast.LENGTH_SHORT).show();

                }

            }

        });
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }
}

