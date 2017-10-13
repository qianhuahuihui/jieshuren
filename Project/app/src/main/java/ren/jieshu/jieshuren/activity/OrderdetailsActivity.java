package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.MediaType;
import ren.jieshu.jieshuren.Adapter.ItemReturnbookAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.AdressBean;
import ren.jieshu.jieshuren.entity.BookidBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.OrderBean;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/7/5.
 */

public class OrderdetailsActivity extends BaseActivity {
    @ViewInject(R.id.orderdetails_select_address)
    private TextView orderdetails_select_address;
    @ViewInject(R.id.orderdetails_tv_username)
    private TextView orderdetails_tv_username;
    @ViewInject(R.id.orderdetails_tv_phonenumber)
    private TextView orderdetails_tv_phonenumber;
    @ViewInject(R.id.orderdetails_tv_address)
    private TextView orderdetails_tv_address;
    @ViewInject(R.id.orderdetails_booknumber)
    private TextView orderdetails_booknumber;
    @ViewInject(R.id.orderdetails_needPrice)
    private TextView orderdetails_needPrice;
    @ViewInject(R.id.orderdetails_service_charge)
    private TextView orderdetails_service_charge;
    @ViewInject(R.id.orderdetails_total_price)
    private TextView orderdetails_total_price;
    @ViewInject(R.id.orderdetails_recyclerview)
    private RecyclerView orderdetails_recyclerview;
    @ViewInject(R.id.orderdetails_rl_address)
    private RelativeLayout orderdetails_rl_address;
    private AdressBean adressBean;
    private SharedPreferences sp;
    private OrderBean orderBean;
    private String addr = "";
    private String json;

    @OnClick(R.id.orderdetails_back)
    private void  orderdetails_back(View view){
        finish();
    }
    @OnClick(R.id.orderdetails_select_address_onclick)
    private void  orderdetails_select_address_onclick(View view){
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),AdressActivity.class);
        intent.putExtra("activity","OrderdetailsActivity");
        startActivityForResult(intent,0);
    }
    @OnClick(R.id.orderdetails_create)
    private void  orderdetails_create(View view){
        List<BookidBean> beanList = new ArrayList<>();
        for (int i = 0 ; i <orderBean.getResult().getList().size(); i++){
                BookidBean bookidBean = new BookidBean();
                bookidBean.setBookid(orderBean.getResult().getList().get(i).getBookid());
                beanList.add(bookidBean);
        }
        if (!addr.equals("")) {
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
            String timestamp = System.currentTimeMillis() / 1000 + "";
            String json = new Gson().toJson(beanList);
            SortedMap<String, Object> sort = new TreeMap<String, Object>();
            sort.put("mid", sp.getInt("mid", -1) + "");
            sort.put("timestamp", timestamp);
            sort.put("addr", addr);
//            sort.put("total_price", "0.01");
            sort.put("total_price", orderBean.getResult().getTotal_price());
            String sign = Sign.delsign(sort, sp.getString("token", ""));
            OkHttpUtils.postString()
                    .url(HttpURLConfig.URL + "private/borrow/book/create?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign + "&addr=" + addr + "&total_price=" + orderBean.getResult().getTotal_price())
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
                            OrderBean order = gson.fromJson(response, OrderBean.class);
                            if (order.getStatus() == 1) {
                                Intent intent = new Intent();
                                intent.setClass(getBaseContext(), PayActivity.class);
                                intent.putExtra("PAYTYPE","0");
                                intent.putExtra("id",order.getOrderid().toString());
                                intent.putExtra("price",orderBean.getResult().getTotal_price().toString());
                                intent.putExtra("j_available_blance",orderBean.getBlance().getJ_available_blance().toString());
                                startActivity(intent);
                            } else if (order.getStatus() == 0) {
                                Toast.makeText(getBaseContext(), order.getError(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else {
            Toast.makeText(getBaseContext(), "请选择收货地址", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_orderdetails);

    }


    protected void initView() {

    }

    @Override
    protected void initData() {
        json =  getIntent().getStringExtra("json");
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        addData();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                if (data != null) {
                    adressBean = (AdressBean) data.getExtras().getSerializable("AdressBean");
                    addr = adressBean.getAddr_id().toString();
                    addData();
                }
                break;
        }
    }
    protected void addData() {
        String timestamp = System.currentTimeMillis() / 1000 + "";
        SortedMap<String, Object> sort = new TreeMap<String, Object>();
        sort.put("mid", sp.getInt("mid", -1) + "");
        sort.put("timestamp", timestamp);
        String url = "";
        if (!addr.equals("")){
            sort.put("addr",addr);
        }
        String sign = Sign.delsign(sort, sp.getString("token", ""));
        if (!addr.equals("")){
            url = HttpURLConfig.URL + "private/borrow/book/confirm?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign+ "&addr=" + addr;
        }else {
            url = HttpURLConfig.URL + "private/borrow/book/confirm?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign;
        }
        OkHttpUtils.postString()
                .url(url)
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
                        orderBean = gson.fromJson(response, OrderBean.class);
                        if (orderBean.getStatus() == 1) {
                            if (orderBean.getResult().getAddress()!=null) {
                                orderdetails_tv_username.setText("收货人:"+orderBean.getResult().getAddress().getJ_gain_name());
                                orderdetails_tv_phonenumber.setText("电话:"+orderBean.getResult().getAddress().getJ_gain_mobile());
                                orderdetails_tv_address.setText("地址："+orderBean.getResult().getAddress().getJ_merger_addr());
                                addr = orderBean.getResult().getAddress().getJ_id()+"";
                                orderdetails_select_address.setVisibility(View.INVISIBLE);
                                orderdetails_rl_address.setVisibility(View.VISIBLE);
                            }else {
                                orderdetails_select_address.setVisibility(View.VISIBLE);
                                orderdetails_rl_address.setVisibility(View.INVISIBLE);
                            }
                            orderdetails_booknumber.setText(orderBean.getResult().getList().size()+"本");
                            orderdetails_needPrice.setText("￥"+orderBean.getResult().getNeedPrice());
                            orderdetails_service_charge.setText("￥"+orderBean.getResult().getService_charge());
                            orderdetails_total_price.setText("￥"+orderBean.getResult().getTotal_price());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
                            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            orderdetails_recyclerview.setLayoutManager(linearLayoutManager);
                            //设置适配器
                            ItemReturnbookAdapter booksAdapter = new ItemReturnbookAdapter(getBaseContext(), orderBean.getResult().getList());
                            orderdetails_recyclerview.setAdapter(booksAdapter);
                        } else if (orderBean.getStatus() == 0) {
                            Toast.makeText(getBaseContext(), orderBean.getError(), Toast.LENGTH_SHORT).show();
                        }else if (orderBean.getStatus() == 2){
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(),BorrowbookActivity.class);
                            startActivity(intent);
                            Toast.makeText(getBaseContext(),"您有未支付的订单，请支付，或取消后再下新的订单",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }
}
