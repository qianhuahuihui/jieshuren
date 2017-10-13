package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.AdressAdapter;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.AdressBean;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.util.Sign;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class AdressActivity extends BaseActivity {
    private AdressAdapter adressAdapter;
    private String sign;
    private String timestamp;
    private SharedPreferences sp;
    private String activity = "1";
    private ArrayList<AdressBean> adressBeanList;

    @OnClick(R.id.adressfragment_back)
    private void adressfragment_back(View view) {
        finish();
    }
    @ViewInject(R.id.adressfragment_recyclerview)
    private RecyclerView adressfragment_recyclerview;
    @OnClick(R.id.adressfragment_addadress)
    private void adressfragment_addadress(View view){

        Intent intent = new Intent();
        intent.setClass(this,AddAdressActivity.class);
        startActivityForResult(intent,0);
    }


    private RequestCall call;

    private StringCallback callback = new StringCallback() {


        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            AdressBean adressBean = gson.fromJson(response, AdressBean.class);
            if (adressBean.getStatus() == 1){
                adressBeanList.clear();
                adressBeanList.addAll(adressBean.getList());
                adressAdapter.notifyDataSetChanged();

            }else if (adressBean.getStatus() == 0){
                Toast.makeText(getContext(),adressBean.getError(),Toast.LENGTH_SHORT).show();

            }

        }

    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_adress);

    }
    protected void initView() {
       activity = getIntent().getStringExtra("activity");
        adressBeanList = new ArrayList<>();
        adressAdapter = new AdressAdapter(getBaseContext(),adressBeanList);
        adressfragment_recyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext() ));
        adressfragment_recyclerview.setAdapter(adressAdapter);
        adressAdapter.setUpdateInterface(new AdressAdapter.UpdateInterface() {
            @Override
            public void updateGroup(Integer position, AdressBean adressBean) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(),AddAdressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("AdressBean",adressBean);
                bundle.putString("更新","更新");
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }

            @Override
            public void itemclick(Integer position, AdressBean adressBean) {
                if (activity.equals("OrderdetailsActivity")) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("AdressBean",adressBean);
                    intent.putExtras(bundle);
                    setResult(0,intent);
                    finish();
                }
            }

            @Override
            public void deletGroup(Integer position, AdressBean adressBean) {
                String timestamp = System.currentTimeMillis()/1000+"";
                Map<String,String> map = new HashMap<>();
                map.put("mid",sp.getInt("mid",-1)+"");
                map.put("timestamp",timestamp);
                map.put("id",adressBean.getAddr_id().toString());
                String sign = Sign.sign(map,sp.getString("token",""));
                call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/address/del")
                        .addParams("mid", sp.getInt("mid",-1)+"")
                        .addParams("timestamp", timestamp)
                        .addParams("sign", sign)
                        .addParams("id",adressBeanList.get(position).getAddr_id().toString())
                        .build();
                call.execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.print("网络出现问题，请重试");
                        iosLoadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        iosLoadingDialog.dismiss();
                        Gson gson = new Gson();
                        MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                        if (messageBean.getStatus() == 1) {
                            Toast.makeText(getContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                            getAddress();

                        } else if (messageBean.getStatus() == 0) {
                            Toast.makeText(getContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
            }

            @Override
            public void defaultGroup(Integer position, AdressBean adressBean) {
                if (adressBean.getJ_defaultaddr() != 1){
                    String timestamp = System.currentTimeMillis()/1000+"";
                    Map<String,String> map = new HashMap<>();
                    map.put("mid",sp.getInt("mid",-1)+"");
                    map.put("timestamp",timestamp);
                    map.put("addr",adressBean.getAddr_id().toString());
                    map.put("j_defaultaddr",1+"");
                    String sign = Sign.sign(map,sp.getString("token",""));
                    map.put("sign", sign);
                    call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/address/defaultaddr")
                            .params(map)
                            .build();
                    call.execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.print("网络出现问题，请重试");
                            iosLoadingDialog.dismiss();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            iosLoadingDialog.dismiss();
                            Gson gson = new Gson();
                            MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                            if (messageBean.getStatus() == 1) {
                                Toast.makeText(getContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                                getAddress();
                            } else if (messageBean.getStatus() == 0) {
                                Toast.makeText(getContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
                }
            }
        });
    }

    @Override
    protected void initData() {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        getAddress();

    }
    private void getAddress(){
        timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        sign = Sign.sign(map,sp.getString("token",""));
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/address/list")
                .addParams("mid", sp.getInt("mid",-1)+"")
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .build();
        call.execute(callback);
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                if (data != null) {
                    getAddress();
                }
                break;
        }
    }
}
