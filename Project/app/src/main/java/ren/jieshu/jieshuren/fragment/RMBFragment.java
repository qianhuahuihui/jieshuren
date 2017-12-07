package ren.jieshu.jieshuren.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.RMBAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.RechargeActivity;
import ren.jieshu.jieshuren.activity.WithdrawActivity;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.ConsumeBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.loading.IOSLoadingDialog;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/7/21.
 */

public class RMBFragment extends BaseFragment {
    private String sign;
    private String timestamp;
    private SharedPreferences sp;
    private Integer page = 1;
    private IOSLoadingDialog iosLoadingDialog;
    private List<ConsumeBean> listall;
    private RequestCall call;
    @ViewInject(R.id.rmb_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.rmb_frozen)
    private TextView rmb_frozen;
    @ViewInject(R.id.rmb_count)
    private TextView rmb_count;
    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout swipeRefreshLayout;
    @OnClick(R.id.rmb_recharge)
    private void rmb_recharge(View view){
        Intent intent = new Intent();
        intent.setClass(getContext(), RechargeActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.rmb_withdraw)
    private void rmb_withdraw(View view){

        Toast.makeText(getContext(),"请到公众号中去提现，感谢您的配合。",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent();
//        intent.putExtra("count",count);
//        intent.setClass(getContext(), WithdrawActivity.class);
//        startActivity(intent);
    }

    private String count;
    private String J_frozen_blance;
    private StringCallback callback = new StringCallback() {


        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();

            mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();

            Gson gson = new Gson();
            ConsumeBean consumeBean = gson.fromJson(response, ConsumeBean.class);
            if (consumeBean.getStatus() == 1){
                count = consumeBean.getBlance().getJ_available_blance().toString();
                J_frozen_blance = consumeBean.getBlance().getJ_frozen_blance().toString();
                rmb_frozen.setText(J_frozen_blance);
                rmb_count.setText(count);
                //刷新数据
                if (consumeBean.getList().size() > 0) {
                    page = page + 1;
                    listall.addAll(consumeBean.getList());
                    mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data),R.drawable.nullpoint);
                    }else {
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }
                }
            }
            else if (consumeBean.getStatus() == 0){
                Toast.makeText(getContext(),consumeBean.getError(),Toast.LENGTH_SHORT).show();

            }

        }

    };
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rmb,container,false);
    }


    @Override
    public void init() {
        iosLoadingDialog = new IOSLoadingDialog();
        page = 1;
        listall = new ArrayList<>();
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_green_light);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                addData(1);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
        mRecyclerViewWithFooter.setAdapter(new RMBAdapter(getContext(), listall));
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
        timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        map.put("page",page.toString());
        map.put("row","50");
        sign = Sign.sign(map,sp.getString("token",""));
        map.put("sign",sign);
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/consume/get")
                .params(map)
                .build();
        call.execute(callback);
        iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

    }
}
