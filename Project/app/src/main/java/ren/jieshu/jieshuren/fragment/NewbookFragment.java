package ren.jieshu.jieshuren.fragment;

import android.os.Bundle;
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
import java.util.List;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.NewbookAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.loading.IOSLoadingDialog;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;

/**
 * Created by laomaotao on 2017/7/3.
 */

public class NewbookFragment extends BaseFragment {

    private String type;
    private RequestCall stringcall;
    private Integer page = 1;
    private Integer instock = 0;
    private boolean isSwitch = false;
    private List<BooksBean> listall;
    private IOSLoadingDialog iosLoadingDialog;

//    @ViewInject(R.id.newbookfragment_switchview)
//    private SwitchView newbookfragment_switchview;
    @ViewInject(R.id.newbookfragment_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
//    @OnClick(R.id.newbookfragment_switchview)
//    private void newbookfragment_switchview(View view){
//        isSwitch = newbookfragment_switchview.isOpened();
//        if (isSwitch){
//            instock = 1;
//        }else {
//            instock = 0;
//        }
//        page = 1;
//        listall.clear();
//        addData(page,instock);
//    }
    public static NewbookFragment newInstance(String type) {
        NewbookFragment newFragment = new NewbookFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        return newFragment;
    }

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
            BooksBean books = gson.fromJson(response, BooksBean.class);
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
        return inflater.inflate(R.layout.fragment_newbook,container,false);
    }

    @Override
    public void init() {
        iosLoadingDialog = new IOSLoadingDialog();
        Bundle args = getArguments();
        if (args != null) {
            type = args.getString("type");
        }
        page = 1;
        listall = new ArrayList<>();
        mRecyclerViewWithFooter.setAdapter(new NewbookAdapter(getContext(), listall));
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        mRecyclerViewWithFooter.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        mRecyclerViewWithFooter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page,instock);
            }
        });
        addData(page,instock);

    }


    protected void addData(Integer page,Integer instock) {
        iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");
        stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "api/book/list/json")
                .addParams("type", type)
                .addParams("flag", "0")
                .addParams("page",page.toString())
                .addParams("instock",instock.toString())
                .build();
        stringcall.execute(stringCallback);

    }
}
