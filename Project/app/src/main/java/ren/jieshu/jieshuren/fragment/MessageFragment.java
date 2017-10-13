package ren.jieshu.jieshuren.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import ren.jieshu.jieshuren.Adapter.MessageAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;


/**
 * Created by laomaotao on 2017/6/29.
 */

public class MessageFragment extends BaseFragment {

    private String type;
    private RequestCall stringcall;
    private Integer page = 1;
    private List<MessageBean> listall;
    @ViewInject(R.id.rv_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.swipe)
    private SwipeRefreshLayout swipeRefreshLayout;

    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

            iosLoadingDialog.dismiss();
            mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            MessageBean messageBean = gson.fromJson(response, MessageBean.class);
            if (messageBean.getStatus() == 1){
                //刷新数据
                if (messageBean.getList().size() > 0) {
                    page = page + 1;
                    listall.addAll( messageBean.getList());
                    mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();

                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data),R.drawable.nullpoint);
                    }else {
                        mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }

                }
            }else if (messageBean.getStatus() == 0){
                Toast.makeText(getContext(),messageBean.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };
    private SharedPreferences sp;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message,container,false);
    }



    @Override
    public void init() {
//        Bundle args = getArguments();
//        if (args != null) {
//            type = args.getString("type");
//        }
        page = 1;
        listall = new ArrayList<>();
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
        mRecyclerViewWithFooter.setAdapter(new MessageAdapter(getContext(), listall));
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
            String timestamp = System.currentTimeMillis()/1000+"";
            Map<String,String> map = new HashMap<>();
            map.put("mid",sp.getInt("mid",-1)+"");
            map.put("timestamp",timestamp);
            map.put("page",page.toString());
            map.put("row", "50");
            String sign = Sign.sign(map,sp.getString("token",""));
            map.put("sign",sign);
            stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/sys/info/get")
                    .params(map)
                    .build();

            stringcall.execute(stringCallback);
            iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");

        }else {
            Toast.makeText(getContext(),"请您先登录，再查看系统消息",Toast.LENGTH_SHORT).show();
        }

    }
}