package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ren.jieshu.jieshuren.Adapter.ConfirmationReturnBookAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.entity.WearPayBean;

/**
 * Created by laomaotao on 2017/8/27.
 */

public class WearPaymentActivity extends BaseActivity {
    @ViewInject(R.id.wearpayment_tv_03)
    private TextView wearpayment_tv_03;
    @ViewInject(R.id.wearpayment_booknumber)
    private TextView wearpayment_booknumber;
    @ViewInject(R.id.wearpayment_bookstotal)
    private TextView wearpayment_bookstotal;
    @ViewInject(R.id.wearpayment_wantpay)
    private TextView wearpayment_wantpay;
    @ViewInject(R.id.wearpayment_pricetotalall)
    private TextView wearpayment_pricetotalall;
    @ViewInject(R.id.wearpayment_price)
    private TextView wearpayment_price;
    @ViewInject(R.id.wearpayment_load_more)
    private RecyclerView wearpayment_load_more;
    private String priceAll;
    private String p;
    private Integer orderid;

    @OnClick(R.id.wearpayment_add)
    private void wearpayment_add(View view){

        if (number < 100){
            number = number+1;
            money = Double.parseDouble(priceAll)*number/100;
            DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            p=decimalFormat.format(money);//format 返回的是字符串
            wearpayment_tv_03.setText(number+"");
            wearpayment_pricetotalall.setText(p);
            wearpayment_wantpay.setText(p);
        }
    }
    @OnClick(R.id.wearpayment_del)
    private void wearpayment_del(View view){
        if (number >0){
            number = number-1;
            money = Double.parseDouble(priceAll)*number/100;
            DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            p=decimalFormat.format(money);//format 返回的是字符串
            wearpayment_tv_03.setText(number+"");
            wearpayment_pricetotalall.setText(p);
            wearpayment_wantpay.setText(p);
        }
    }
    @OnClick(R.id.wearpayment_returnbook)
    private void wearpayment_returnbook(View view){
        List<WearPayBean> beanList = new ArrayList<>();
                WearPayBean wearPayBean = new WearPayBean();
                wearPayBean.setOrderId(orderid);
                wearPayBean.setOrderCompensatePrice(new BigDecimal(Double.toString(money)));
                beanList.add(wearPayBean);
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
    @OnClick(R.id.wearpayment_back)
    private void wearpayment_back(View view){
        finish();
    }
    private double money ;
    private double number = 0;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_wearpayment);
    }

    @Override
    protected void initView() {
        orderid = getIntent().getIntExtra("orderid",-1);
        priceAll = getIntent().getStringExtra("priceAll");
        String Books = getIntent().getStringExtra("Books");
        Gson gson = new Gson();
        List<BooksBean> booksList = gson.fromJson(Books, new TypeToken<ArrayList<BooksBean>>(){}.getType());
        wearpayment_price.setText(priceAll);
        wearpayment_booknumber.setText(booksList.size()+"");
        wearpayment_bookstotal.setText("图书合计："+booksList.size()+" 本");
        money = Double.parseDouble(priceAll)*number/100;
        DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(money);//format 返回的是字符串
        wearpayment_tv_03.setText(number+"");
        wearpayment_pricetotalall.setText(p);
        wearpayment_wantpay.setText(p);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        wearpayment_load_more.setLayoutManager(linearLayoutManager);
        //设置适配器
        ConfirmationReturnBookAdapter confirmationReturnBookAdapter = new ConfirmationReturnBookAdapter(getBaseContext(), booksList);
        wearpayment_load_more.setAdapter(confirmationReturnBookAdapter);
    }

    @Override
    protected void initData() {

    }
}
