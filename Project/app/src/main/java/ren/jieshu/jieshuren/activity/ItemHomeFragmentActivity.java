package ren.jieshu.jieshuren.activity;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;

import ren.jieshu.jieshuren.Adapter.HomeFragmentBooksAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BooksBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;

/**
 * Created by laomaotao on 2017/8/20.
 */

public class ItemHomeFragmentActivity extends BaseActivity {
    @OnClick(R.id.itemhomefragment_back)
    private void itemhomefragment_back(View view){
        finish();
    }
    @ViewInject(R.id.itemhomefragment_title)
    private TextView itemhomefragment_title;
    @ViewInject(R.id.itemhomefragment_member_name)
    private TextView itemhomefragment_member_name;
    @ViewInject(R.id.itemhomefragment_book_count)
    private TextView itemhomefragment_book_count;
    @ViewInject(R.id.itemhomefragment_time)
    private TextView itemhomefragment_time;
    @ViewInject(R.id.itemhomefragment_area_name)
    private TextView itemhomefragment_area_name;
    @ViewInject(R.id.itemhomefragment_headview)
    private SimpleDraweeView itemhomefragment_headview;
    @ViewInject(R.id.itemhomefragment_rl)
    private RecyclerView itemhomefragment_rl;
    @ViewInject(R.id.item_distance)
    private TextView item_distance;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_itemhomefragment);

    }
    protected void initView() {
        BooksBean booksBean = (BooksBean) getIntent().getExtras().getSerializable("BooksBean");
        itemhomefragment_member_name.setText(booksBean.getMember_name());
        itemhomefragment_book_count.setText("借了" + booksBean.getBook_count() + "本书");
        itemhomefragment_area_name.setText(booksBean.getArea_name());
        itemhomefragment_headview.setImageURI(Uri.parse(HttpURLConfig.URL+booksBean.getJ_headimgurl()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        itemhomefragment_time.setText(sdf.format(booksBean.getTime()).toString());
        java.text.DecimalFormat df=new java.text.DecimalFormat("#0.0");
        double d=Double.parseDouble(booksBean.getDistance())/1000;
        item_distance.setText(df.format(d)+"km");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemhomefragment_rl.setLayoutManager(gridLayoutManager);
        //设置适配器
        HomeFragmentBooksAdapter booksAdapter = new HomeFragmentBooksAdapter(getBaseContext(), booksBean.getBooks());
        itemhomefragment_rl.setAdapter(booksAdapter);
    }
    @Override
    protected void initData() {

    }
}
