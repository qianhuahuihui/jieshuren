package ren.jieshu.jieshuren.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zaaach.citypicker.CityPickerActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.HomeFragmentAdapter;
import ren.jieshu.jieshuren.activity.LoginActivity;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

import static android.app.Activity.RESULT_OK;

/**
 * Created by laomaotao on 2017/7/3.
 */

public class HomeFragment extends BaseFragment {
    private static final int REQUEST_CODE_PICK_CITY = 0;
    @ViewInject(R.id.rv_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.home_city)
    private TextView home_city;
    private Dialog mDialog;

    @OnClick(R.id.home_location)
    private void home_location(View view){
        //启动
        startActivityForResult(new Intent(getContext(), CityPickerActivity.class),
                REQUEST_CODE_PICK_CITY);
    }
    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout swipeRefreshLayout;
    private RequestCall call;
    private BooksBean books;
    private SharedPreferences sp;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            iosLoadingDialog.dismiss();
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    lat = aMapLocation.getLatitude()+"";
                    lon = aMapLocation.getLongitude()+"";
                //    Log.e(logKey,"AMapLocationListener回调什么条件执行呀!经度"+lon+"经度："+lat);
                    SharedPreferences preferences = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("lat", lat);
                    editor.putString("lon", lon);
                    editor.commit();
                    //若登录更改用户当前位置
                    if (sp.getInt("mid",-1) != -1) {
                        String timestamp = System.currentTimeMillis()/1000+"";
                        Map<String,String> privateMap = new HashMap<>();
                        privateMap.put("mid",sp.getInt("mid",-1)+"");
                        privateMap.put("timestamp",timestamp);
                        privateMap.put("lat",lat);
                        privateMap.put("lng",lon);
                        Log.e(logKey,"修改当前经纬度"+lon+"维度"+lat);
                        String sign = Sign.sign(privateMap,sp.getString("token",""));
                        RequestCall callLocation = OkHttpUtils.get().url(HttpURLConfig.URL + "private/user/location")
                                .addParams("lat", lat)
                                .addParams("lng", lon)
                                .addParams("mid", sp.getInt("mid",-1)+"")
                                .addParams("timestamp", timestamp)
                                .addParams("sign", sign)
                                .build();
                        callLocation.execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getContext(),"位置更新失败",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                    Log.e("psn","location of user fix sessuss!");
                            }
                        });
                    }
                    Toast.makeText(getContext(),"同城定位刷新成功",Toast.LENGTH_SHORT).show();
                    if(ByCity_flag){
                        page = 1;
                        listall.removeAll(listall);
                        addDataByCity(page);
                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        page = 1;
                        listall.removeAll(listall);
                        addData(page);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("psn","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());

                    new AlertDialog.Builder(getActivity())
                            .setTitle("警告！").setIcon(R.drawable.jieshurenlogo)
                            .setMessage("请到设置->应用程序管理->权限管理允许借书人定位，否者同城借书功能不能正常使用。")
                            .setNegativeButton("我要去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create().show();
                }
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String lat;
    private String lon;
    private Integer page = 1;
    private List<BooksBean> listall;
    private StringCallback stringCallback= new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);

        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            books = gson.fromJson(response, BooksBean.class);
            Log.e("psn","初接收到："+books.getBooks().get(0).getTime());
            if (books.getStatus() == 1){
                //刷新数据
                if (books.getBooks().size() > 0) {
                    page = page + 1;
                    listall.addAll( books.getBooks());
                    mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data),R.drawable.nullpoint);
                    }else {
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }

                }
            }else if (books.getStatus() == 0){
                Toast.makeText(getContext(),books.getError(),Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //检测系统是否打开开启了地理定位权限
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Log.e("psn","是否开了网络定位");
            ActivityCompat.requestPermissions(getActivity(), new String []{android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }else if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Log.e("psn","是否开了GPS定位");
            ActivityCompat.requestPermissions(getActivity(), new String []{android.Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }

    private String logKey = "HomeFragment";
    private boolean ByCity_flag = false;
    private boolean refresh_flag = false;
    @Override
    public void init() {

        ByCity_flag = false;
        page = 1;
        listall = new ArrayList<>();
        sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);

    //    Log.e(logKey,"生命周期：什么时候被销毁什么时候重新加载！");

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh_flag = true;
                // 启动定位
         //       Log.e(logKey,"下拉刷新，开始定位");
                try {
                    mLocationClient.startLocation();
                }catch (Exception e){
                    Log.e("psn","==================startflase");
                    e.printStackTrace();
                }
                iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");


            }
        });
        mRecyclerViewWithFooter.setAdapter(new HomeFragmentAdapter(getContext(), listall));
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        mRecyclerViewWithFooter.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if(ByCity_flag){
                    addDataByCity(page);
                }else {
                    addData(page);
                }
            }
        });

        lat = sp.getString("lat","");
        lon = sp.getString("lon","");
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        if (!lat.equals("")) {
            Log.e(logKey,"缓存的经纬度");
            addData(1);
        }else {
       // 启动定位
            Log.e(logKey,"经纬度为null，开始定位");
            mLocationClient.startLocation();
            iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

        }

    }
    protected void addData(Integer page) {
        call = OkHttpUtils.get().url(HttpURLConfig.URL + "api/order/get")
                .addParams("lat", lat)
                .addParams("lng", lon)
                .addParams("page", page.toString())
                .build();
        call.execute(stringCallback);
        iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
    }
    //重写onActivityResult方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                home_city.setText(city);
                getCity(city);
            }
        }
    }

    private String city;
    private void getCity(String cityname){
        city = cityname;
        ByCity_flag = true;
        page = 1;
        if(ByCity_flag){
            listall.clear();
            mRecyclerViewWithFooter.setRestartLoad();
            addDataByCity(page);
           /// mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
        }
    }
    protected void addDataByCity(Integer page) {
        RequestCall call = OkHttpUtils.get().url(HttpURLConfig.URL + "api/order/getByCity")
                .addParams("lat", lat)
                .addParams("lng", lon)
                .addParams("city", city)
                .addParams("page", page.toString())
                .build();
        call.execute(stringCallback);
        iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");
    }
//    private void getLatlon(String cityName){
//
//        GeocodeSearch geocodeSearch=new GeocodeSearch(getContext());
//        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//
//            }
//
//            @Override
//            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//
//                if (i==1000){
//                    if (geocodeResult!=null && geocodeResult.getGeocodeAddressList()!=null &&
//                            geocodeResult.getGeocodeAddressList().size()>0){
//
//                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
//                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
//                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
//                        String adcode= geocodeAddress.getAdcode();//区域编码
//
//
//                        Log.e("地理编码", geocodeAddress.getAdcode()+"");
//                        Log.e("纬度latitude",latitude+"");
//                        Log.e("经度longititude",longititude+"");
//                        lat = latitude+"";
//                        lon = longititude+"";
//                        page = 1;
//                        listall.removeAll(listall);
//                        addData(page);
//
//                    }else {
//                        Toast.makeText(getContext(),"地址名出错",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
//        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
//
//
//    }
}
