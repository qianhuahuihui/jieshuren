package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.WearPaymentAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.WearPayBean;
import ren.jieshu.jieshuren.entity.WearPaymentBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

import static ren.jieshu.jieshuren.R.id.wearpaymentlist_load_more;

/**
 * Created by laomaotao on 2017/8/28.
 */

public class WearPaymentListActivity extends BaseActivity {
    @OnClick(R.id.wearpaymentlist_back)
    private void wearpaymentlist_back(View view){
        finish();
    }
    @ViewInject(wearpaymentlist_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.wearpaymentlist_bookstotal)
    private TextView wearpaymentlist_bookstotal;
    @ViewInject(R.id.wearpaymentlist_booksnumber)
    private TextView wearpaymentlist_booksnumber;
    @ViewInject(R.id.wearpaymentlist_pricetotalall)
    private TextView wearpaymentlist_pricetotalall;
    private ArrayList<WearPaymentBean> listall;
    private WearPaymentAdapter wearPaymentAdapter;
    private Integer page;
    private String p;
    private Integer ordernumber = 0;
    private Double money =0.00;
    private HashMap<String,Integer> map;
    private RequestCall booklistCall;
    private SharedPreferences sp;
    @OnClick(R.id.wearpayment_returnbook)
    private void wearpayment_returnbook(View view){
        List<WearPayBean> beanList = new ArrayList<>();
        for (int i = 0 ; i <listall.size(); i++){
            if (listall.get(i).getNumber() > 0){
                WearPayBean wearPayBean = new WearPayBean();
                wearPayBean.setOrderId(listall.get(i).getReturn_order_id());
                wearPayBean.setOrderCompensatePrice(new BigDecimal(Double.toString(listall.get(i).getOrderCompensatePrice())));
                beanList.add(wearPayBean);
            }
        }
        String json = new Gson().toJson(beanList);

        if (beanList.size() > 0) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), PayWearActivity.class);
            intent.putExtra("json",json);
            intent.putExtra("total_price",p);
            startActivity(intent);
        }else {
            Toast.makeText(getBaseContext(), "请选择订单支付磨损费用", Toast.LENGTH_SHORT).show();
        }

    }

    private WearPaymentBean wearPaymentBean;
    private StringCallback booklistCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
        }
        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            wearPaymentBean = gson.fromJson(response, WearPaymentBean.class);
            if (wearPaymentBean.getStatus() == 1){
                List<WearPaymentBean> list = wearPaymentBean.getList();
                for (int i = 0; i <list.size(); i++){
                    list.get(i).setNumber(0);
                }
                if (list.size() > 0) {
                    if (page == 1) {
                        listall.removeAll(listall);
                    }
                    listall.addAll(list);
                    page = page + 1;
                    mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data),R.drawable.nullpoint);
                    }else {
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }
                }
                wearpaymentlist_booksnumber.setText("共 " + listall.size() + " 个还书订单");
            }else if (wearPaymentBean.getStatus() == 0){
                Toast.makeText(getBaseContext(),wearPaymentBean.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_wearpaymentlist);
    }

    @Override
    protected void initView() {
        map = new HashMap<>();
        page = 1;
        listall = new ArrayList<>();
        wearPaymentAdapter = new WearPaymentAdapter(getBaseContext(),listall);
        mRecyclerViewWithFooter.setAdapter(wearPaymentAdapter);
        //        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        mRecyclerViewWithFooter.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page);
            }
        });
        addData(1);
        wearPaymentAdapter.setCheckInterface(new WearPaymentAdapter.CheckInterface() {
            @Override
            public void add(Integer number, Integer position, Double sum) {
                listall.get(position).setNumber(number);
                listall.get(position).setOrderCompensatePrice(sum*number/100);
                wearPaymentAdapter.notifyDataSetChanged();
                map.put(position.toString(),number);
                money = money+sum/100;
                DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                p=decimalFormat.format(money);//format 返回的是字符串
                wearpaymentlist_pricetotalall.setText("￥"+p);
                wearpaymentlist_bookstotal.setText("订单合计："+map.size()+" 个");
            }

            @Override
            public void del(Integer number, Integer position, Double sum) {
                listall.get(position).setNumber(number);
                listall.get(position).setOrderCompensatePrice(sum*number/100);
                wearPaymentAdapter.notifyDataSetChanged();
                map.put(position.toString(),number);
                if (map.get(position.toString()) < 1){
                    map.remove(position.toString());
                }
                money = money-sum/100;
                DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                p=decimalFormat.format(money);//format 返回的是字符串
                wearpaymentlist_pricetotalall.setText("￥"+p);
                wearpaymentlist_bookstotal.setText("订单合计："+map.size()+" 个");

            }
        });
    }

    private void addData(Integer page) {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);

        if (sp.getInt("mid",-1) != -1) {
            String timestamp = System.currentTimeMillis() / 1000 + "";
            Map<String, String> map = new HashMap<>();
            map.put("mid", sp.getInt("mid", -1) + "");
            map.put("timestamp", timestamp);
            map.put("page", "50");
            map.put("page",page.toString());
            String sign = Sign.sign(map, sp.getString("token", ""));
            map.put("sign", sign);
            booklistCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/bookabrasion/list")
                    .params(map)
                    .build();
            booklistCall.execute(booklistCallback);
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

        }else {
            Toast.makeText(getBaseContext(),"请您先登录，再查看磨损支付",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void initData() {

    }
}
