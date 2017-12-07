package ren.jieshu.jieshuren.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.LibraryOrderAdapter;
import ren.jieshu.jieshuren.Adapter.OrderListAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.ExpressActivity;
import ren.jieshu.jieshuren.activity.PayActivity;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.entity.ReturnbookBean;
import ren.jieshu.jieshuren.loading.IOSLoadingDialog;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/8/15.
 */

public class BooksFragment extends BaseFragment {

    @ViewInject(R.id.booksfragment_load_more)
    private RecyclerViewWithFooter booksfragment_load_more;
    private Integer type;
    private RequestCall stringcall;
    private LibraryOrderAdapter libraryorderAdapter;
    private List<ReturnbookBean> listall;
    private Integer page = 1;
    private SharedPreferences sp;
    private OrderListAdapter orderListAdapter;
    private IOSLoadingDialog iosLoadingDialog;

    public static BooksFragment newInstance(Integer type) {
        BooksFragment newFragment = new BooksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    private ReturnbookBean books;
    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            booksfragment_load_more.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            books = gson.fromJson(response, ReturnbookBean.class);
            if (books.getStatus() == 1){
                //刷新数据
                if (books.getList().size() > 0) {
                    page = page + 1;
                    listall.addAll( books.getList());

                    booksfragment_load_more.getAdapter().notifyDataSetChanged();
                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        booksfragment_load_more.setEmpty(getString(R.string.no_data),R.drawable.nullpoint);
                    }else {
                        booksfragment_load_more.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }
                }
            }else if (books.getStatus() == 0){
                Toast.makeText(getContext(),books.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void init() {
        Bundle args = getArguments();
        iosLoadingDialog = new IOSLoadingDialog();
        if (args != null) {
            type = args.getInt("type");
        }
        page = 1;
        listall = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getContext(),listall,type.toString());
        orderListAdapter.setUpdateInterface(new OrderListAdapter.UpdateInterface() {

            @Override
            public void removeOrderGroup(Integer position, ReturnbookBean returnbookBean) {
                String timestamp = System.currentTimeMillis() / 1000 + "";
                Map<String, String> map = new HashMap<>();
                map.put("mid", sp.getInt("mid", -1) + "");
                map.put("timestamp", timestamp);
                map.put("id", returnbookBean.getId().toString());
                String sign = Sign.sign(map, sp.getString("token", ""));
                map.put("sign", sign);
                stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/order/cancel")
                        .params(map)
                        .build();
                stringcall.execute(new StringCallback() {
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
                        if (messageBean.getStatus() == 1){
                            Toast.makeText(getContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                            //刷新数据
                            listall.clear();
                            addData(1);
                        }else if (messageBean.getStatus() == 0){
                            Toast.makeText(getContext(),messageBean.getError(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

            }

            @Override
            public void payOrderGroup(Integer position, ReturnbookBean returnbookBean) {
                Intent intent = new Intent();
                intent.setClass(getContext(),PayActivity.class);
                intent.putExtra("id",returnbookBean.getId().toString());
                intent.putExtra("price",returnbookBean.getTotalPrice());
                intent.putExtra("PAYTYPE","0");
                intent.putExtra("j_available_blance",books.getAvailable_blance().toString());

                startActivity(intent);
            }

            @Override
            public void searchExpress(Integer position, ReturnbookBean returnbookBean) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ExpressActivity.class);
                intent.putExtra("express_code",returnbookBean.getExpress_code().toString());
                intent.putExtra("express_number",returnbookBean.getExpress_number().toString());

                startActivity(intent);
            }

            @Override
            public void confirm(Integer position, ReturnbookBean returnbookBean) {
                String timestamp = System.currentTimeMillis() / 1000 + "";
                Map<String, String> map = new HashMap<>();
                map.put("mid", sp.getInt("mid", -1) + "");
                map.put("timestamp", timestamp);
                map.put("order_id", returnbookBean.getId().toString());
                String sign = Sign.sign(map, sp.getString("token", ""));
                map.put("sign", sign);
                stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/order/confirmOrder")
                        .params(map)
                        .build();
                stringcall.execute(new StringCallback() {
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
                        if (messageBean.getStatus() == 1){
                            Toast.makeText(getContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                            //刷新数据
                            listall.clear();
                            addData(1);
                        }else if (messageBean.getStatus() == 0){
                            Toast.makeText(getContext(),messageBean.getError(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");
            }
        });
        booksfragment_load_more.setAdapter(orderListAdapter);
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        booksfragment_load_more.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        booksfragment_load_more.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page);
            }
        });
        addData(page);

    }

    protected void addData(Integer page) {
        iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");
        sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
        String timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> map = new HashMap<>();
        map.put("mid", sp.getInt("mid", -1) + "");
        map.put("timestamp", timestamp);
        map.put("status", type.toString());
        map.put("page",page.toString());
        map.put("row", "50");
        String sign = Sign.sign(map, sp.getString("token", ""));
        map.put("sign", sign);
        stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/order/list")
                .params(map)
                .build();
        stringcall.execute(stringCallback);

    }
}