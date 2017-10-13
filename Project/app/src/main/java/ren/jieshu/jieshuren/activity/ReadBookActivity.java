package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
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
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.MediaType;
import ren.jieshu.jieshuren.Adapter.BooklistAdapter;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookidBean;
import ren.jieshu.jieshuren.entity.ListBean;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;
import ren.jieshu.jieshuren.util.Sign;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class ReadBookActivity extends BaseActivity {
    @ViewInject(R.id.readbookfragment_load_more)
    private RecyclerViewWithFooter mRecyclerViewWithFooter;
    @ViewInject(R.id.readbookfragment_booksnumber)
    private TextView readbookfragment_booksnumber;
    @ViewInject(R.id.readbookfragment_bookstotal)
    private TextView readbookfragment_bookstotal;
    @ViewInject(R.id.readbookfragment_pricetotal)
    private TextView readbookfragment_pricetotal;
    @ViewInject(R.id.readbookfragment_remove)
    private TextView readbookfragment_remove;
    private RequestCall readCall;

    private StringCallback readCallBack = new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                iosLoadingDialog.dismiss();
                mRecyclerViewWithFooter.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
            }

            @Override
            public void onResponse(String response, int id) {
                iosLoadingDialog.dismiss();
                Gson gson = new Gson();
                ListBean listBean = gson.fromJson(response, ListBean.class);
                if (listBean.getStatus() == 1) {
                    list = listBean.getList();
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            prices = prices.add(list.get(i).getPrice());
                        }
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setChoosed(false);
                        }
                        if (page == 1) {
                            listall.removeAll(listall);
                        }
                        listall.addAll(list);
                        page = page + 1;
                        mRecyclerViewWithFooter.getAdapter().notifyDataSetChanged();
                    } else {
                        if (page == 1) {
                            listall.removeAll(listall);
                            mRecyclerViewWithFooter.setEmpty(getString(R.string.no_data), R.drawable.nullpoint);
                        } else {
                            mRecyclerViewWithFooter.setEmpty(getString(R.string.no_more_data), R.drawable.nullpoint);
                        }
                    }
                    readbookfragment_booksnumber.setText("图书：" + listBean.getTotal() + "本");
                    readbookfragment_bookstotal.setText("图书合计：" + listBean.getList().size() + "本");
                    readbookfragment_pricetotal.setText(prices + "");
                } else if (listBean.getStatus() == 0) {
                    Toast.makeText(getBaseContext(), listBean.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    private Integer page;
    private List<ListBean> listall;
    private List<ListBean> list;
    private BooklistAdapter booklistAdapter;
    private SharedPreferences sp;

    @OnClick(R.id.readbookfragment_remove)
    private void readbookfragment_remove(View view){
        if (listall != null) {
            String timestamp = System.currentTimeMillis() / 1000 + "";
            List<BookidBean> beanList = new ArrayList<>();
            for (int i = 0; i < listall.size(); i++) {
                if (listall.get(i).getChoosed()) {
                    BookidBean bookidBean = new BookidBean();
                    bookidBean.setBookid(listall.get(i).getBook_id());
                    beanList.add(bookidBean);
                }
            }
            String json = new Gson().toJson(beanList);
            SortedMap<String, Object> sort = new TreeMap<String, Object>();
            sort.put("mid", sp.getInt("mid", -1) + "");
            sort.put("timestamp", timestamp);
            String sign = Sign.delsign(sort, sp.getString("token", ""));
            call = OkHttpUtils.postString()
                    .url(HttpURLConfig.URL + "private/book/delRead?mid=" + sp.getInt("mid", -1) + "&timestamp=" + timestamp + "&sign=" + sign)
                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
                    .content(json)
                    .build();
            call.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    iosLoadingDialog.dismiss();
                    Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    iosLoadingDialog.dismiss();
                    Gson gson = new Gson();
                    MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                    if (messageBean.getStatus() == 1) {
                        Toast.makeText(getBaseContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                        page = 1;
                        addData(page);
                    } else if (messageBean.getStatus() == 0) {
                        Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }

    }
//    private boolean isShow = false;//是否显示编辑/完成
    private boolean choose = false;//是否全选

    @OnClick(R.id.readbookfragment_back)
    private void readbookfragment_back(View view) {
        finish();
    }
    @ViewInject(R.id.readbookfragment_choose)
    private ImageView readbookfragment_choose;
    @OnClick(R.id.booklistfragment_choose_rl)
    private void booklistfragment_choose_rl(View view) {
        choose =!choose;
        if (choose) {
            readbookfragment_choose.setImageResource(R.drawable.checked);
        }else {
            readbookfragment_choose.setImageResource(R.drawable.unchecked);
        }
        if (listall != null) {
            for (int i = 0; i < listall.size(); i++) {
                if (choose) {
                    listall.get(i).setChoosed(true);
                }else {
                    listall.get(i).setChoosed(false);
                }            }
        }
        booklistAdapter.setList(listall);
    }

    private RequestCall call;
    private BigDecimal prices;

    @Override
    protected void setContentView() {
        setContentView(R.layout.fragment_readbook);
    }

    protected void initView() {
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        page = 1;
        prices = new BigDecimal("0.0");
        listall = new ArrayList<>();
        booklistAdapter = new BooklistAdapter(getBaseContext(), listall);
        mRecyclerViewWithFooter.setAdapter(booklistAdapter);

        booklistAdapter.setCheckInterface(new BooklistAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {

                listall.get(position).setChoosed(isChecked);
            }
        });
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

    @Override
    protected void initData() {

    }
    protected void addData(Integer page) {

        String timestamp = System.currentTimeMillis()/1000+"";
        Map<String,String> map = new HashMap<>();
        map.put("mid",sp.getInt("mid",-1)+"");
        map.put("timestamp",timestamp);
        map.put("page",page.toString());
        map.put("row", "50");
        String sign = Sign.sign(map,sp.getString("token",""));
        map.put("sign",sign);
        readCall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/book/read")
                .params(map)
                .build();
        readCall.execute(readCallBack);
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
    }

}
