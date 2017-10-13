package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.NewbookAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by laomaotao on 2017/8/13.
 */

public class NewbookActivity extends BaseActivity {

    private Integer page = 1;
    private Integer instock = 0;
    private List<BooksBean> list;
    private List<BooksBean> listall;
    @ViewInject(R.id.newbook_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    private String flag;
    private ArrayList<BooksBean> freeDblist;
    private boolean isSwitch = false;

    @OnClick(R.id.newbook_back)
    private void newbook_back(View view) {
        finish();
    }

//    @ViewInject(R.id.newbook_switchview)
//    private SwitchView newbook_switchview;
//    @OnClick(R.id.newbook_switchview)
//    private void newbook_switchview(View view){
//        isSwitch = newbook_switchview.isOpened();
//        if (isSwitch){
//            instock = 1;
//        }else {
//            instock = 0;
//        }
//        page = 1;
//        list.clear();
//        addData(page,instock);
//    }
    @ViewInject(R.id.newbook_title)
    private TextView newbook_title;
    @OnClick(R.id.newbook_classifiedquery)
    private void newbook_classifiedquery(View view){
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),ClassifiedqueryActivity.class);
        startActivity(intent);
    }
    private RequestCall stringcall;

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
                    list.addAll(books.getBooks());
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
                Toast.makeText(getBaseContext(),books.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_newbook);

    }

    @Override
    protected void initView() {
        flag = getIntent().getStringExtra("flag");
        if (flag.equals("1")){
            newbook_title.setText("新书排行");
        }else if(flag.equals("2")){
            newbook_title.setText("借阅排行");
        }
//        newbook_switchview.setOpened(false);

        list = new ArrayList<>();
        listall = new ArrayList<>();
        freeDblist = new ArrayList<>();
        mRecyclerViewWithFooter.setAdapter(new NewbookAdapter(getContext(), list));
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
        stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "api/book/list/json")
                .addParams("flag", flag)
                .addParams("page",page.toString())
                .addParams("instock",instock.toString())
                .build();
        stringcall.execute(stringCallback);
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }

    @Override
    protected void initData() {

    }

}
