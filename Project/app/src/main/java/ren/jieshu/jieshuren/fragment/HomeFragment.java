package ren.jieshu.jieshuren.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zaaach.citypicker.CityPickerActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.HomeFragmentAdapter;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;

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
                    SharedPreferences preferences = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("lat", lat);
                    editor.putString("lon", lon);
                    editor.commit();
                    addData(1);
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
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

            Gson gson = new Gson();
            books = gson.fromJson(response, BooksBean.class);
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
    public void init() {

        page = 1;
        listall = new ArrayList<>();
        SharedPreferences sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                listall.removeAll(listall);
                addData(page);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerViewWithFooter.setAdapter(new HomeFragmentAdapter(getContext(), listall));
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        mRecyclerViewWithFooter.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page);
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
//启动定位
        if (!lat.equals("")) {
            addData(1);
        }else {
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
                getLatlon(city);
            }
        }
    }
    private void getLatlon(String cityName){

        GeocodeSearch geocodeSearch=new GeocodeSearch(getContext());
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

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
                        page = 1;
                        listall.removeAll(listall);
                        addData(page);

                    }else {
                        Toast.makeText(getContext(),"地址名出错",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery=new GeocodeQuery(cityName.trim(),"29");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);


    }
}
