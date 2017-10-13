package ren.jieshu.jieshuren.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import ren.jieshu.jieshuren.Adapter.BooklistAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.OrderdetailsActivity;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.BookidBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.ListBean;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/6/29.
 */

public class BooklistFragment extends BaseFragment {

    @ViewInject(R.id.booklistfragment_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.booklistfragment_booksnumber)
    private TextView booklistfragment_booksnumber;
    @ViewInject(R.id.booklistfragment_bookstotal)
    private TextView booklistfragment_bookstotal;
    @ViewInject(R.id.booklistfragment_pricetotal)
    private TextView booklistfragment_pricetotal;
    @ViewInject(R.id.booklistfragment_remove)
    private TextView booklistfragment_remove;
    private BooklistAdapter booklistAdapter;
    private RequestCall booklistCall;
    private Integer page;
    private SharedPreferences sp;

    @OnClick(R.id.booklistfragment_remove)
    private void booklistfragment_remove(View view){
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
                if (isShow) {
                    OkHttpUtils.postString()
                            .url(HttpURLConfig.URL + "private/borrow/book/del?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign)
                            .mediaType(MediaType.parse("application/json; charset=utf-8"))
                            .content(json)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    iosLoadingDialog.dismiss();
                                    Toast.makeText(getContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    iosLoadingDialog.dismiss();
                                    Gson gson = new Gson();
                                    MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                                    if (messageBean.getStatus() == 1) {
                                        Toast.makeText(getContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                                        page = 1;
                                        prices = new BigDecimal("0.0");
                                        bookSize = 0;
                                        booklistfragment_bookstotal.setText("图书合计：" + bookSize + " 本");
                                        booklistfragment_pricetotal.setText("￥"+prices.toString());
                                        booklistfragment_choose.setImageResource(R.drawable.unchecked);
                                        addData(page);
                                    } else if (messageBean.getStatus() == 0) {
                                        Toast.makeText(getContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

                } else {
                    Intent intent = new Intent();
                    intent.putExtra("json",json);
                    intent.setClass(getContext(), OrderdetailsActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getContext(),"请选择图书",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(),"请您先登录，再提交订单",Toast.LENGTH_SHORT).show();
        }
    }
    @ViewInject(R.id.booklistfragment_isshow)
    private TextView booklistfragment_isshow;
    @OnClick(R.id.booklistfragment_isshow)
    private void booklistfragment_isshow(View view){
        isShow = !isShow;

        if (isShow){
            booklistfragment_isshow.setText("完成");
            booklistfragment_remove.setText("删除");
        }else {
            booklistfragment_isshow.setText("编辑");
            booklistfragment_remove.setText("结算");
        }
        if (listall != null) {
            for (int i = 0; i < listall.size(); i++) {
                listall.get(i).setChoosed(false);
            }
            booklistAdapter.setList(listall);
        }
        prices = new BigDecimal("0.0");
        bookSize = 0;
        booklistfragment_bookstotal.setText("图书合计：" + bookSize + " 本");
        booklistfragment_pricetotal.setText("￥"+prices.toString());
        booklistfragment_choose.setImageResource(R.drawable.unchecked);
    }
    @ViewInject(R.id.booklistfragment_choose)
    private ImageView booklistfragment_choose;
    @OnClick(R.id.booklistfragment_choose_rl)
    private void booklistfragment_choose_rl(View view){
        choose =!choose;
        if (choose) {
            booklistfragment_choose.setImageResource(R.drawable.checked);
            prices = new BigDecimal("0.0");
            for (int i = 0; i < listall.size(); i++) {
                prices = prices.add(listall.get(i).getPrice());
            }
            bookSize = listall.size();

        }else {
            prices = new BigDecimal("0.0");
            bookSize = 0;
            booklistfragment_choose.setImageResource(R.drawable.unchecked);
        }
        booklistfragment_bookstotal.setText("图书合计：" + bookSize + " 本");
        booklistfragment_pricetotal.setText("￥"+prices.toString());
        if (listall != null) {
            for (int i = 0; i < listall.size(); i++) {
                if (choose) {
                    listall.get(i).setChoosed(true);
                }else {
                    listall.get(i).setChoosed(false);
                }
            }
            booklistAdapter.setList(listall);

        }
    }

    private List<ListBean> listall;
    private List<ListBean> list;
    private BigDecimal prices ;
    private Integer bookSize;
    private boolean isShow = false;//是否显示编辑/完成
    private boolean choose = false;//是否全选

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
            ListBean listBean = gson.fromJson(response, ListBean.class);
            if (listBean.getStatus() == 1){
                list = listBean.getList();
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoosed(false);
                    }
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
                booklistfragment_booksnumber.setText("图书：" + listBean.getTotal() + " 本");
            }else if (listBean.getStatus() == 0){
                Toast.makeText(getContext(),listBean.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_booklist,container,false);
    }

    @Override
    public void init() {
        page = 1;
        prices = new BigDecimal("0.0");
        bookSize = 0;
        listall = new ArrayList<>();
        booklistAdapter = new BooklistAdapter(getContext(), listall);
        mRecyclerViewWithFooter.setAdapter(booklistAdapter);

        booklistAdapter.setCheckInterface(new BooklistAdapter.CheckInterface() {
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
                booklistfragment_bookstotal.setText("图书合计：" + bookSize + " 本");
                booklistfragment_pricetotal.setText("￥"+prices.toString());
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


    protected void addData(Integer page) {
        sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);

        if (sp.getInt("mid",-1) != -1) {
            String timestamp = System.currentTimeMillis() / 1000 + "";
            Map<String, String> map = new HashMap<>();
            map.put("mid", sp.getInt("mid", -1) + "");
            map.put("timestamp", timestamp);
            map.put("page",page.toString());
            map.put("row", "50");
            String sign = Sign.sign(map, sp.getString("token", ""));
            map.put("sign", sign);
            booklistCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/borrow/book/list")
                    .params(map)
                    .build();
            booklistCall.execute(booklistCallback);
            iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

        }else {
            Toast.makeText(getContext(),"请您先登录，再查看书单",Toast.LENGTH_SHORT).show();
        }
    }



}
