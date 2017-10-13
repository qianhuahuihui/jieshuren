package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.RechargeAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.ConsumeBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.RechargeBean;
import ren.jieshu.jieshuren.entity.WechatBean;
import ren.jieshu.jieshuren.util.Sign;

import static ren.jieshu.jieshuren.MyApplication.api;

public class RechargeActivity extends BaseActivity {
    @ViewInject(R.id.recylerview)
    private RecyclerView recyclerView;
    @ViewInject(R.id.recharge_sum)
    private TextView recharge_sum;
    @ViewInject(R.id.recharge_j_available_blance)
    private TextView recharge_j_available_blance;
    private RechargeAdapter adapter;
    private SharedPreferences sp;
    private RequestCall call;

    @OnClick(R.id.recharge_back)
    private void recharge_back(View view){
        finish();
    }
    @OnClick(R.id.tvPay)
    private void tvPay(View view){
        String timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        map.put("price",recharge_sum.getText().toString().trim());
        String sign = Sign.sign(map,sp.getString("token",""));
        map.put("sign",sign);
        call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/wechat/pay/recharge")
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
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void initView() {

        SharedPreferences preferences = getSharedPreferences("member", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //PAYTYPE 0 订单支付，转借，调回借书订单
        //        1 磨损支付，调回钱包
        editor.putString("PAYTYPE", "");
        editor.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    protected void initData() {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter = new RechargeAdapter());
        adapter.replaceAll(getData(),this);
        EventBus.getDefault().register(this);
        addData(1);
    }

    public ArrayList<RechargeBean> getData() {
        String data = "50,100,200,300,500";
        // isDiscount ：1、有角标 2、无角标
        String isDiscount = "2";
        String dataArr[] = data.split(",");
        ArrayList<RechargeBean> list = new ArrayList<>();
        for (int i = 0; i < dataArr.length; i++) {
            String count = dataArr[i] + "元";
                list.add(new RechargeBean(RechargeBean.ONE, count, false));
        }
        list.add(new RechargeBean(RechargeBean.TWO, null, false));

        return list;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAdapterClickInfo(RechargeBean model) {
        String money = model.data.toString().replace("元", "");
        recharge_sum.setText(money);
//        if (model.isFree==true){
//            Toast.makeText(this,"点击的是带免费标签的",Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this,"点击的是正常标签的",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    protected void addData(Integer page) {
        String timestamp = System.currentTimeMillis()/1000+"";
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
                    recharge_j_available_blance.setText(consumeBean.getBlance().getJ_available_blance().toString());

                }
                else if (consumeBean.getStatus() == 0){
                    Toast.makeText(getBaseContext(),consumeBean.getError(),Toast.LENGTH_SHORT).show();

                }

            }

        });
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }
}
