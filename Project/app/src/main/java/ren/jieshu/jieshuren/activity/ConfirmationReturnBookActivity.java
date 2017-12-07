package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.MediaType;
import ren.jieshu.jieshuren.Adapter.ConfirmationReturnBookAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookidBean;
import ren.jieshu.jieshuren.entity.ExpressBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.entity.OrderBean;
import ren.jieshu.jieshuren.util.Sign;


/**
 * Created by laomaotao on 2017/8/27.
 */

public class ConfirmationReturnBookActivity extends BaseActivity {
    @ViewInject(R.id.confirmationreturnbook_name)
    private TextView confirmationreturnbook_name;
    @ViewInject(R.id.confirmationreturnbook_needPrice)
    private TextView confirmationreturnbook_needPrice;
    @ViewInject(R.id.confirmationreturnbook_bookcount)
    private TextView confirmationreturnbook_bookcount;
    @ViewInject(R.id.confirmationreturnbook_et_code)
    private EditText confirmationreturnbook_et_code;
    @ViewInject(R.id.confirmationreturnbook_mobile)
    private TextView confirmationreturnbook_mobile;
    @ViewInject(R.id.confirmationreturnbook_kuaidi)
    private TextView confirmationreturnbook_kuaidi;
    @ViewInject(R.id.confirmationreturnbook_address)
    private TextView confirmationreturnbook_address;
    @ViewInject(R.id.confirmationreturnbook_load_more)
    private RecyclerView confirmationreturnbook_load_more;
    private CaptureActivity mCaptureContext;
    private OrderBean orderBean;
    private SharedPreferences sp;
    //private ExpressBean expressBean;
    private QrScan qrScan;
    private String express_number = "";
    private String express_name = "",express_code="";
    private BigDecimal priceAll;
    @OnClick(R.id.pay_back)
    private void pay_back(View view){
        finish();
    }
    @OnClick(R.id.confirmationreturnbook_returnbook)
    private void confirmationreturnbook_returnbook(View view){
        if (!express_number.equals("")) {
            List<BookidBean> beanList = new ArrayList<>();
            for (int i = 0; i < orderBean.getList().size(); i++) {
                BookidBean bookidBean = new BookidBean();
                bookidBean.setBookid(orderBean.getList().get(i).getJ_id());
                beanList.add(bookidBean);
            }
            String timestamp = System.currentTimeMillis() / 1000 + "";
            String json = new Gson().toJson(beanList);
            SortedMap<String, Object> sort = new TreeMap<String, Object>();
            sort.put("mid", sp.getInt("mid", -1) + "");
            sort.put("timestamp", timestamp);
            sort.put("express_code", express_code);
            sort.put("express_number", express_number);
            sort.put("express_name", express_name);
            sort.put("is_express", "0");
            //Log.e("psn",expressBean.getExpress().getCom_code()+"====="+expressBean.getExpress().getPinyin());
            String sign = Sign.delsign(sort, sp.getString("token", ""));
            OkHttpUtils.postString()
                    .url(HttpURLConfig.URL + "private/returnBook/finish?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign + "&express_code=" + express_code + "&express_name=" + express_name + "&express_number=" + express_number + "&is_express=0")
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            iosLoadingDialog.dismiss();
                            Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            iosLoadingDialog.dismiss();

                            Gson gson = new Gson();
                            MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                            if (messageBean.getStatus() == 1) {
                                Toast.makeText(getBaseContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("Books",new Gson().toJson(orderBean.getList()));
                                intent.putExtra("orderid",messageBean.getOrderid());
                                intent.putExtra("priceAll",priceAll.toString());
                                intent.setClass(getBaseContext(), WearPaymentActivity.class);
                                startActivity(intent);
                            } else if (messageBean.getStatus() == 0) {
                                Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }else {
            Toast.makeText(getBaseContext(),"请填写快递单号，或扫描快递二维码",Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.confirmationreturnbook_auto)
    private void confirmationreturnbook_auto(View view){
        qrScan =QrScan.getInstance();
        qrScan.launchScan(ConfirmationReturnBookActivity.this, new IScanModuleCallBack() {
            @Override
            public void OnReceiveDecodeResult(final Context context, final String result) {
                express_number = result;
                mCaptureContext = (CaptureActivity)context;
                QrScan.getInstance().finishScan(mCaptureContext);
                confirmationreturnbook_et_code.setText(result);
                //getExpress(result);
            }
        });

    }
    private RequestCall call;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_confirmationreturnbook);
    }

    @Override
    protected void initView() {
// 搜索框的键盘搜索键点击回调
        confirmationreturnbook_et_code.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    if (!confirmationreturnbook_et_code.getText().toString().trim().equals("")) {
                        // 先隐藏键盘
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                                getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        express_number = confirmationreturnbook_et_code.getText().toString().trim();
                        //getExpress(express_number);

                    }
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        orderBean = (OrderBean) getIntent().getExtras().getSerializable("OrderBean");
        confirmationreturnbook_bookcount.setText(orderBean.getList().size()+"");
        priceAll = new BigDecimal("0.00");
        for (int i = 0; i < orderBean.getList().size(); i++) {
            priceAll = priceAll.add(orderBean.getList().get(i).getJ_book_price());
        }
        confirmationreturnbook_needPrice.setText(priceAll.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        confirmationreturnbook_load_more.setLayoutManager(linearLayoutManager);
        //设置适配器
        ConfirmationReturnBookAdapter confirmationReturnBookAdapter = new ConfirmationReturnBookAdapter(getBaseContext(), orderBean.getList());
        confirmationreturnbook_load_more.setAdapter(confirmationReturnBookAdapter);
    }
//    private void getExpress(String express){
//
//        String timestamp = System.currentTimeMillis() / 1000 + "";
//        Map<String,String> sort=new HashMap<>();
//        sort.put("mid", sp.getInt("mid", -1) + "");
//        sort.put("timestamp", timestamp);
//        sort.put("code", express);
//        String sign = Sign.sign(sort, sp.getString("token", ""));
//        sort.put("sign", sign);
//        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/express/auto")
//                .params(sort)
//                .build();
//        call.execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Gson gson = new Gson();
//                expressBean = gson.fromJson(response, ExpressBean.class);
//                if (expressBean.getStatus() == 1) {
//                    confirmationreturnbook_kuaidi.setText(expressBean.getExpress().getName());
//                    express_name = expressBean.getExpress().getName();
//                } else if (expressBean.getStatus() == 0) {
//                    Toast.makeText(getBaseContext(), expressBean.getError(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}
