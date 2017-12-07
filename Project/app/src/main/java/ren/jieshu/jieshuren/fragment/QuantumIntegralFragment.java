package ren.jieshu.jieshuren.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.IntegralAdapter;
import ren.jieshu.jieshuren.Adapter.RMBAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.TransferActivity;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.BlanceBean;
import ren.jieshu.jieshuren.entity.ConsumeBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.IntegralRecord;
import ren.jieshu.jieshuren.loading.IOSLoadingDialog;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;


/**
 * Created by laomaotao on 2017/7/21.
 */

public class QuantumIntegralFragment extends BaseFragment {

    @ViewInject(R.id.integral_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.quantumintegral_integral_available)
    private TextView quantumintegral_integral_available;
    @ViewInject(R.id.quantumintegral_frozen)
    private TextView quantumintegral_frozen;

    @OnClick(R.id.quantumintegral_switchto)
    private void quantumintegral_switchto(View view) {
//        Intent intent = new Intent();
//        intent.setClass(getContext(),TransferActivity.class);
//        intent.putExtra("integral_available",quantumintegral_integral_available.getText());
//        startActivity(intent);
    }

    @OnClick(R.id.quantumintegral_out)
    private void quantumintegral_out(View view) {
        Intent intent = new Intent();
        intent.setClass(getContext(), TransferActivity.class);
        intent.putExtra("integral_available", quantumintegral_integral_available.getText());
        startActivity(intent);
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quantumintegral, container, false);
    }


    private BlanceBean blance;
    private String sign;
    private String timestamp;
    private SharedPreferences sp;
    private Integer page = 1;
    private IOSLoadingDialog iosLoadingDialog;
    private List<IntegralRecord> listall;
    private RequestCall call;
    private String integralcount;
    private String frozen_integral;
    private String total_integral;

    public void AsynaddIntegral() {
        sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
        timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> map = new HashMap<>();
        map.put("mid", sp.getInt("mid", -1) + "");
        map.put("timestamp", timestamp);
        sign = Sign.sign(map, sp.getString("token", ""));
        map.put("sign", sign);
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/integral/getIntegral")
                .params(map)
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error), R.drawable.nullpoint);
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                blance = gson.fromJson(response, BlanceBean.class);
                if (blance.getStatus() == 1) {
                    integralcount = blance.getBlance().getIntegral().toString();
                    frozen_integral = blance.getBlance().getFrozen_integral().toString();
                    quantumintegral_frozen.setText(frozen_integral);
                    quantumintegral_integral_available.setText(integralcount);
                } else if (blance.getStatus() == 0) {
                    Toast.makeText(getContext(), blance.getError(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void init() {
        iosLoadingDialog = new IOSLoadingDialog();
        AsynaddIntegral();

        page = 1;
        listall = new ArrayList<>();
        addData(1);
        mRecyclerViewWithFooter.setAdapter(new IntegralAdapter(getContext(), listall));
        mRecyclerViewWithFooter.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page);
            }
        });
       // addData(1);
    }

    protected void addData(Integer page) {
        sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
        timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> map = new HashMap<>();
        map.put("mid", sp.getInt("mid", -1) + "");
        map.put("timestamp", timestamp);
        map.put("page", page.toString());
        map.put("row", "10");
        sign = Sign.sign(map, sp.getString("token", ""));
        map.put("sign", sign);
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/integral/getRecord")
                .params(map)
                .build();
        call.execute(callback);
        iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

    }

    private StringCallback callback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();

            mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error), R.drawable.nullpoint);
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();

            Gson gson = new Gson();
            IntegralRecord integralRecord = gson.fromJson(response, IntegralRecord.class);
            if (integralRecord.getStatus() == 1) {
                //刷新数据
                if (integralRecord.getList().size() > 0) {
                    page = page + 1;
                    listall.addAll(integralRecord.getList());
                    mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        listall.removeAll(listall);
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data), R.drawable.nullpoint);
                    } else {
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data), R.drawable.nullpoint);
                    }
                }
            } else if (integralRecord.getStatus() == 0) {
                Toast.makeText(getContext(), integralRecord.getError(), Toast.LENGTH_SHORT).show();

            }
        }
    };
}

