package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
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
import ren.jieshu.jieshuren.Adapter.ReturnBookActionAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookidBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.ListBean;
import ren.jieshu.jieshuren.entity.OrderBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by laomaotao on 2017/7/13.
 */

public class ReturnBookActionActivity extends BaseActivity {
    @ViewInject(R.id.returnbookaction_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.returnbookaction_booksnumber)
    private TextView returnbookaction_booksnumber;
    @ViewInject(R.id.returnbookaction_bookstotal)
    private TextView returnbookaction_bookstotal;
    @ViewInject(R.id.returnbookaction_pricetotal)
    private TextView returnbookaction_pricetotal;
    @ViewInject(R.id.returnbookaction_pricetotalall)
    private TextView returnbookaction_pricetotalall;
    @ViewInject(R.id.returnbookaction_choose)
    private ImageView returnbookaction_choose;
    private SharedPreferences sp;
    private BigDecimal priceAll;

    @OnClick(R.id.returnbookaction_back)
    private void returnbookaction_back(View view){
        finish();
    }
    @OnClick(R.id.returnbookaction_choose_rl)
    private void returnbookaction_choose_rl(View view) {
        choose =!choose;
        if (choose) {
            returnbookaction_choose.setImageResource(R.drawable.checked);
            prices = new BigDecimal("0.00");
            for (int i = 0; i < listall.size(); i++) {
                prices = prices.add(listall.get(i).getPrice());
            }
            bookSize = listall.size();
        }else {
            prices = new BigDecimal("0.00");
            bookSize = 0;
            returnbookaction_choose.setImageResource(R.drawable.unchecked);
        }
        returnbookaction_bookstotal.setText("图书合计：" + bookSize + " 本");
        returnbookaction_pricetotalall.setText("￥"+prices.toString());
        if (listall != null) {
            for (int i = 0; i < listall.size(); i++) {
                if (choose) {
                    listall.get(i).setChoosed(true);
                }else {
                    listall.get(i).setChoosed(false);
                }            }
        }
        returnBookActionAdapter.setList(listall);
    }
    @OnClick(R.id.returnbookaction_returnbook)
    private void returnbookaction_returnbook(View view){
        if (sp.getInt("mid", -1) != -1) {
            List<BookidBean> beanList = new ArrayList<>();
            for (int i = 0 ; i <listall.size(); i++){
                if (listall.get(i).getChoosed()){
                    BookidBean bookidBean = new BookidBean();
                    bookidBean.setBookid(listall.get(i).getBook_id());
                    beanList.add(bookidBean);
                }
            }
            if (beanList.size() >0) {
                String timestamp = System.currentTimeMillis() / 1000 + "";
                String json = new Gson().toJson(beanList);
                SortedMap<String, Object> sort = new TreeMap<String, Object>();
                sort.put("mid", sp.getInt("mid", -1) + "");
                sort.put("timestamp", timestamp);
                String sign = Sign.delsign(sort, sp.getString("token", ""));

                    OkHttpUtils.postString()
                            .url(HttpURLConfig.URL + "private/returnBook/confirm?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign)
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
                                    OrderBean orderBean = gson.fromJson(response, OrderBean.class);
                                    if (orderBean.getStatus() == 1) {
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("OrderBean",orderBean);
                                        intent.putExtras(bundle);
                                        intent.setClass(getBaseContext(),ConfirmationReturnBookActivity.class);
                                        startActivity(intent);
                                    } else if (orderBean.getStatus() == 0) {
                                        Toast.makeText(getBaseContext(), orderBean.getError(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

            } else {
                Toast.makeText(getContext(),"请选择图书",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),"请您先登录，再提交订单",Toast.LENGTH_SHORT).show();
        }
    }
    private StringCallback callBack = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            ListBean listBean = gson.fromJson(response, ListBean.class);
            if (listBean.getStatus() == 1){
                list = listBean.getList();
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        priceAll = priceAll.add(list.get(i).getPrice());
                    }
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoosed(false);
                    }
                    if (page == 1) {
                        listall.removeAll(listall);
                    }
                    listall.addAll(list);
                    page = page + 1;
                    mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        listall.removeAll(listall);
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data), R.drawable.nullpoint);
                    } else {
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data), R.drawable.nullpoint);
                    }
                }
                returnbookaction_booksnumber.setText("借阅中："+listBean.getTotal()+ " 本");
                returnbookaction_pricetotal.setText("押金：￥"+priceAll);
            }else if (listBean.getStatus() == 0){
                Toast.makeText(getBaseContext(),listBean.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };
    private boolean choose = false;//是否全选
    private RequestCall call;
    private Integer page;
    private Integer bookSize;
    private List<ListBean> listall;
    private List<ListBean> list;
    private ReturnBookActionAdapter returnBookActionAdapter;
    private BigDecimal prices;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_returnbookaction);

    }

    protected void initView() {
        priceAll = new BigDecimal("0.00");
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
            page = 1;
        bookSize = 0;
        prices = new BigDecimal("0.00");
            listall = new ArrayList<>();
        returnBookActionAdapter = new ReturnBookActionAdapter(getBaseContext(), listall);
        mRecyclerViewWithFooter.setAdapter(returnBookActionAdapter);

        returnBookActionAdapter.setCheckInterface(new ReturnBookActionAdapter.CheckInterface() {
                @Override
                public void checkGroup(int position, boolean isChecked) {

                    listall.get(position).setChoosed(isChecked);
                    if (isChecked){
                        prices = prices.add(listall.get(position).getPrice());
                        bookSize = bookSize + 1;
                    }else {
                        prices = prices.subtract(listall.get(position).getPrice());
                        bookSize = bookSize - 1;
                    }
                    returnbookaction_bookstotal.setText("图书合计：" + bookSize + " 本");
                    returnbookaction_pricetotalall.setText("￥"+prices.toString());
                }
            });
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
    }

    @Override
    protected void initData() {

    }
    protected void addData(Integer page) {
        if (sp.getInt("mid",-1) != -1) {

        String timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        map.put("page",page.toString());
        map.put("row", "50");
        String sign = Sign.sign(map,sp.getString("token",""));
        map.put("sign",sign);
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/returnBook/list")
               .params(map)
                .build();
        call.execute(callBack);
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }else {
            Toast.makeText(getContext(),"请您先登录，再查看还书单",Toast.LENGTH_SHORT).show();
        }
    }
}
