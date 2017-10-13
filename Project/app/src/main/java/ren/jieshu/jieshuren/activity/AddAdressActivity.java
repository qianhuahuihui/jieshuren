package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.CityAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.AdressBean;
import ren.jieshu.jieshuren.entity.CityBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.util.IsPhone;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/8/10.
 */

public class AddAdressActivity extends BaseActivity implements AdapterView.OnItemSelectedListener{

    private String lat;
    private String lon;

    @OnClick(R.id.addadress_back)
    private void addadress_back(View view){
        finish();
    }
    @ViewInject(R.id.addadress_name)
    private EditText addadress_name;
    @ViewInject(R.id.addadress_phone)
    private EditText addadress_phone;
    @ViewInject(R.id.addadress_adress)
    private EditText addadress_adress;
    @ViewInject(R.id.addadress_title)
    private TextView addadress_title;
    private SharedPreferences sp;
    private CityBean provinceBean;
    private CityBean cityBean;
    private String timestamp;
    private CityBean countyBean;
    private Integer provinceID;
    private Integer cityID;
    private Integer countyID;
    private RequestCall addadressCall;
    private Bundle bundle;
    @ViewInject(R.id.addadress_addadress)
    private Button addadress_addadress;
    @OnClick(R.id.addadress_addadress)
    private void addadress_addadress(View view){
        if (IsPhone.isPhone(addadress_phone.getText().toString().trim())) {
                if (addadress_name.getText().toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "请填写收货人姓名", Toast.LENGTH_SHORT).show();
                } else if (addadress_phone.getText().toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "请填写收货人电话", Toast.LENGTH_SHORT).show();
                } else if (addadress_adress.getText().toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "请填写收货人详细地址", Toast.LENGTH_SHORT).show();
                } else if (provinceID.toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "请选择省份地址", Toast.LENGTH_SHORT).show();
                } else if (cityID.toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "请选择城市地址", Toast.LENGTH_SHORT).show();
                } else if (countyID.toString().trim().equals("")) {
                    Toast.makeText(getBaseContext(), "请选择区县地址", Toast.LENGTH_SHORT).show();
                } else {
                    getLatlon(provinceID.toString().trim()+cityID.toString().trim()+countyID.toString().trim()+addadress_adress.getText().toString().trim());
                }
        }else {
            Toast.makeText(getBaseContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
        }
    }
    @ViewInject(R.id.addadress_province)
    private Spinner addadress_province;
    @ViewInject(R.id.addadress_city)
    private Spinner addadress_city;
    @ViewInject(R.id.addadress_county)
    private Spinner addadress_county;

    private CityAdapter cityAdapter;
    private RequestCall provinceCall;
    private RequestCall cityCall;
    private RequestCall countyCall;
    private AdressBean adressBean;
    private String isUpdate = null;



    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_addadress);

    }
    protected void initView() {
        bundle = getIntent().getExtras();
        if (bundle != null){
            isUpdate = bundle.getString("更新");
            if( isUpdate!= null) {
                adressBean = (ren.jieshu.jieshuren.entity.AdressBean) bundle.getSerializable("AdressBean");
                addadress_name.setText(adressBean.getName());
                addadress_phone.setText(adressBean.getMobile());
                addadress_adress.setText(adressBean.getAddress());
            }
            addadress_title.setText("更新地址");
            addadress_addadress.setText("更新");
        }else {
            addadress_title.setText("添加地址");
            addadress_addadress.setText("添加");
        }
        addadress_province.setOnItemSelectedListener(this);
        addadress_city.setOnItemSelectedListener(this);
        addadress_county.setOnItemSelectedListener(this);
    }

    protected void initData() {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);

        String timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> privateMap = new HashMap<>();
        privateMap.put("mid",sp.getInt("mid",-1)+"");
        privateMap.put("timestamp",timestamp);
        String sign = Sign.sign(privateMap,sp.getString("token",""));
        provinceCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/city/get")
                .addParams("mid", sp.getInt("mid",-1)+"")
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .build();
        provinceCall.execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {

                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                provinceBean = gson.fromJson(response, CityBean.class);
                if (provinceBean.getStatus() == 1){
                    cityAdapter = new CityAdapter(getBaseContext(),provinceBean.getList());
                    addadress_province.setAdapter(cityAdapter);

                    if (isUpdate != null){
                        for(int i=0;i<provinceBean.getList().size();i++){
                            if(adressBean.getProvince().equals(provinceBean.getList().get(i).getId().toString())){
                                addadress_province.setSelection(i,true);// 默认选中项
                                break;
                            }
                        }
//                        setSpinnerItemSelectedByValue(addadress_province,adressBean.getProvince(),provinceBean.getList());

                    }
                }else if (provinceBean.getStatus() == 0){
                    Toast.makeText(getBaseContext(),provinceBean.getError(),Toast.LENGTH_SHORT).show();

                }

            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.addadress_province:
                provinceID = provinceBean.getList().get(position).getId();
                timestamp = System.currentTimeMillis()/1000+"";
                Map<String,String> map = new HashMap<>();
                map.put("mid",sp.getInt("mid",-1)+"");
                map.put("timestamp",timestamp);
                map.put("id",provinceID+"");
                map.put("level",2+"");
                String sign = Sign.sign(map,sp.getString("token",""));
                cityCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/city/get")
                        .addParams("mid", sp.getInt("mid",-1)+"")
                        .addParams("timestamp", timestamp)
                        .addParams("sign", sign)
                        .addParams("id",provinceID+"")
                        .addParams("level",2+"")
                        .build();
                cityCall.execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        cityBean = gson.fromJson(response, CityBean.class);
                        if (cityBean.getStatus() == 1){
                            cityAdapter = new CityAdapter(getBaseContext(),cityBean.getList());
                            addadress_city.setAdapter(cityAdapter);

                            if (isUpdate != null){
                                for(int i=0;i<cityBean.getList().size();i++){
                                    if(adressBean.getCity().equals(cityBean.getList().get(i).getId().toString())){
                                        addadress_city.setSelection(i,true);// 默认选中项
                                        break;
                                    }
                                }
//                                setSpinnerItemSelectedByValue(addadress_city,adressBean.getCity(),cityBean.getList());

                            }
                        }else if (cityBean.getStatus() == 0){
                            Toast.makeText(getBaseContext(),cityBean.getError(),Toast.LENGTH_SHORT).show();

                        }

                    }

                });
                break;
            case R.id.addadress_city:
                cityID = cityBean.getList().get(position).getId();
                timestamp = System.currentTimeMillis()/1000+"";
                map = new HashMap<>();
                map.put("mid",sp.getInt("mid",-1)+"");
                map.put("timestamp",timestamp);
                map.put("id",cityID+"");
                map.put("level",3+"");
                sign = Sign.sign(map,sp.getString("token",""));
                countyCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/city/get")
                        .addParams("mid", sp.getInt("mid",-1)+"")
                        .addParams("timestamp", timestamp)
                        .addParams("sign", sign)
                        .addParams("id",cityID+"")
                        .addParams("level",3+"")
                        .build();
                countyCall.execute(new StringCallback() {


                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        countyBean = gson.fromJson(response, CityBean.class);
                        if (countyBean.getStatus() == 1){
                            cityAdapter = new CityAdapter(getBaseContext(),countyBean.getList());
                            addadress_county.setAdapter(cityAdapter);

                            if (isUpdate != null){
                                for(int i=0;i<countyBean.getList().size();i++){
                                    if(adressBean.getArea().equals(countyBean.getList().get(i).getId().toString())){
                                        addadress_county.setSelection(i,true);// 默认选中项
                                        break;
                                    }
                                }
//                                setSpinnerItemSelectedByValue(addadress_county,adressBean.getArea(),countyBean.getList());

                            }
                            isUpdate = null;
                        }else if (countyBean.getStatus() == 0){
                            Toast.makeText(getBaseContext(),countyBean.getError(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.addadress_county:
                countyID = countyBean.getList().get(position).getId();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    /**
//     * 根据值, 设置spinner默认选中:
//     * @param spinner
//     * @param value
//     */
//    private void setSpinnerItemSelectedByValue(Spinner spinner,String value,List<CityBean> cityBeanList){
//        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
//        int k= apsAdapter.getCount();
//        for(int i=0;i<k;i++){
//            if(value.equals(cityBeanList.get(i).getName())){
//                spinner.setSelection(i,true);// 默认选中项
//                break;
//            }
//        }
//    }
private void getLatlon(String cityName){

    GeocodeSearch geocodeSearch=new GeocodeSearch(getBaseContext());
    geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            Toast.makeText(getBaseContext(),regeocodeResult.toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            if (i==1000){
                if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
                        geocodeResult.getGeocodeAddressList().size()>0){

                    GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                    double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                    double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                    String adcode= geocodeAddress.getAdcode();//区域编码


                    Log.e("地理编码", geocodeAddress.getAdcode()+"");
                    Log.e("纬度latitude",latitude+"");
                    Log.e("经度longititude",longititude+"");
                    lat = latitude+"";
                    lon = longititude+"";
                    iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
                    String timestamp = System.currentTimeMillis() / 1000 + "";
                    Map<String, String> map = new HashMap<>();
                    map.put("mid", sp.getInt("mid", -1) + "");
                    map.put("timestamp", timestamp);
                    map.put("name", addadress_name.getText().toString().trim());
                    map.put("mobile", addadress_phone.getText().toString().trim());
                    map.put("address", addadress_adress.getText().toString().trim());
                    map.put("province", provinceID + "");
                    map.put("city", cityID + "");
                    map.put("area", countyID + "");
                    String URL = "";
                    if (addadress_addadress.getText().equals("添加")) {
                        URL = HttpURLConfig.URL + "private/address/save";
                    }else {
                        map.put("id", adressBean.getAddr_id().toString());
                        URL = HttpURLConfig.URL + "private/address/update";
                    }
                    String sign = Sign.sign(map, sp.getString("token", ""));
                    map.put("sign", sign);
                    addadressCall = OkHttpUtils.post().url(URL)
                            .params(map)
                            .build();
                    addadressCall.execute(stringCallback);
                }else {
                    Toast.makeText(getBaseContext(),"地址名出错",Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
    geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
}
    private StringCallback stringCallback=new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            iosLoadingDialog.dismiss();
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            CityBean msg = gson.fromJson(response, CityBean.class);
            if (msg.getStatus() == 1) {
                Toast.makeText(getBaseContext(), msg.getMsg(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("isSuccess", "isSuccess");
                setResult(0, intent);
                finish();
            } else if (msg.getStatus() == 0) {
                Toast.makeText(getBaseContext(), msg.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
