package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
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
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.db.RecordSQLiteOpenHelper;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.loadmore.DefaultFootItem;
import ren.jieshu.jieshuren.loadmore.OnLoadMoreListener;
import ren.jieshu.jieshuren.loadmore.RecyclerViewWithFooter;

import static com.umeng.socialize.utils.ContextUtil.getContext;
import static ren.jieshu.jieshuren.R.drawable.search;

/**
 * Created by laomaotao on 2017/8/14.
 */

public class SearchActivity extends BaseActivity {
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);;
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    @ViewInject(R.id.search_edittext)
    private EditText search_edittext;
    @ViewInject(R.id.search_lv)
    private GridView search_lv;
    @ViewInject(R.id.search_load_more)
    private RecyclerViewWithFooter search_load_more;
//    @ViewInject(R.id.search_swipe)
//    private SwipeRefreshLayout search_swipe;
    @ViewInject(R.id.search_number)
    private TextView search_number;
    @ViewInject(R.id.search_relativelayout)
    private RelativeLayout search_relativelayout;
    @ViewInject(R.id.search_searchbutton)
    private ImageView search_searchbutton;
    private Integer page = 1;
    private List<BooksBean> listall;
    private RequestCall stringcall;
    private NewbookAdapter newbookAdapter;
    private int isSearched = 0;
    private StringCallback stringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

            iosLoadingDialog.dismiss();
            search_load_more.setEmpty(getString(R.string.internet_error),R.drawable.nullpoint);
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            BooksBean books = gson.fromJson(response, BooksBean.class);
            if (books.getStatus() == 1){
                if (books.getBooks().size() > 0) {
                    search_load_more.setVisibility(View.VISIBLE);
                    search_lv.setVisibility(View.GONE);
                    isSearched = 1;
                    search_searchbutton.setImageResource(R.drawable.delet);
                    //刷新数据
                    page = page + 1;
                    listall.addAll( books.getBooks());
                    search_load_more.getAdapter().notifyDataSetChanged();

                    search_relativelayout.setVisibility(View.GONE);
                    search_number.setVisibility(View.VISIBLE);
                    search_number.setText("共"+listall.size()+"个结果");
                }else {
                    if (page == 1){
                        listall.removeAll(listall);
                        search_load_more.setEmpty("没有搜索到这本书",R.drawable.nullpoint);
                    }else {
                        search_load_more.setEmpty(getString(R.string.no_more_data),R.drawable.nullpoint);
                    }
                }
            }else if (books.getStatus() == 0){
                Toast.makeText(getBaseContext(),books.getError(),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @OnClick(R.id.search_searchbutton)
    public void search_searchbutton(View arg0) {
        if (!search_edittext.getText().toString().trim().equals("")) {
            if (isSearched == 0) {
                boolean hasData = hasData(search_edittext.getText().toString().trim());
                if (!hasData) {
                    insertData(search_edittext.getText().toString().trim());
                    queryData("");
                }
                // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                listall.clear();
                addData(1);

            } else {
                search_load_more.setVisibility(View.GONE);
                search_lv.setVisibility(View.VISIBLE);
                isSearched = 0;
                listall.removeAll(listall);
                page = 1;
                search_searchbutton.setImageResource(search);
                search_relativelayout.setVisibility(View.VISIBLE);
                search_number.setVisibility(View.GONE);
                search_edittext.setText("");

            }
        }
    }

    @OnClick(R.id.search_remove)
    public void search_remove(View arg0) {
        deleteData();
        queryData("");

    }

    @OnClick(R.id.search_back)
    private void searchf_back(View view) {
        finish();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_search);

    }


    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        search_lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    protected void initView() {
        page = 1;
        listall = new ArrayList<>();
        search_load_more.setAdapter(new NewbookAdapter(getContext(), listall));
//        mRecyclerViewWithFooter.setStaggeredGridLayoutManager(2);
        search_load_more.setFootItem(new DefaultFootItem());//默认是这种
//        mRecyclerViewWithFooter.setFootItem(new CustomFootItem());//自定义
        search_load_more.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addData(page);
            }
        });

// 搜索框的键盘搜索键点击回调
        search_edittext.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    if (!search_edittext.getText().toString().trim().equals("")) {
                        // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                        boolean hasData = hasData(search_edittext.getText().toString().trim());
                        if (!hasData) {
                            insertData(search_edittext.getText().toString().trim());
                            queryData("");
                        }
                        listall.clear();
                        // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                        addData(1);

                    }
                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                if (s.toString().trim().length() == 0) {
//                    tv_tip.setText("搜索历史");
//                } else {
//                    tv_tip.setText("搜索结果");
//                }
                String tempName = search_edittext.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        search_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                search_edittext.setText(name);
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
            }
        });

        // 第一次进入查询所有的历史记录
        queryData("");
    }

    @Override
    protected void initData() {

    }

    protected void addData(Integer page) {
        stringcall = OkHttpUtils.get().url(HttpURLConfig.URL + "api/book/list/json")
                .addParams("flag", "3")
                .addParams("p", search_edittext.getText().toString().trim())
                .addParams("page",page.toString())
                .build();

        stringcall.execute(stringCallback);
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }
}


