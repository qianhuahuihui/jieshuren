package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.DoubanBook;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.util.Sign;
import ren.jieshu.jieshuren.widget.StarBar;

public class DouBanBookDetailActivity extends BaseActivity {

    @ViewInject(R.id.douban_bookdetails_bookImage)
    private SimpleDraweeView bookdetails_bookImage;
    @ViewInject(R.id.douban_bookdetails_bookName)
    private TextView bookdetails_bookName;
    @ViewInject(R.id.douban_bookdetails_title)
    private TextView bookdetails_title;
    @ViewInject(R.id.douban_bookdetails_numraters)
    private TextView bookdetails_numraters;
    @ViewInject(R.id.douban_bookdetails_bookAuthor)
    private TextView bookdetails_bookAuthor;
    @ViewInject(R.id.douban_bookdetails_bookPricint)
    private TextView bookdetails_bookPricint;
    @ViewInject(R.id.douban_bookdetails_bookPress)
    private TextView bookdetails_bookPress;
    @ViewInject(R.id.douban_bookdetails_bookPuttime)
    private TextView bookdetails_bookPuttime;
    @ViewInject(R.id.douban_bookdetails_Isbn)
    private TextView bookdetails_Isbn;
    @ViewInject(R.id.douban_bookdetails_bookPageCount)
    private TextView bookdetails_bookPageCount;
    @ViewInject(R.id.douban_bookdetails_bookBind)
    private TextView bookdetails_bookBind;
    @ViewInject(R.id.douban_bookdetails_bookPricint)
    private TextView bookdetails_bookPrice;
    @ViewInject(R.id.douban_bookdetails_average_star)
    private StarBar bookdetails_average_star;
    @ViewInject(R.id.douban_bookdetails_average)
    private TextView bookdetails_average;
    @ViewInject(R.id.douban_bookdetails_bookIntroduction)
    private TextView bookdetails_bookIntroduction;
    private Integer mid;

    @OnClick(R.id.douban_bookdetails_back)
    private void bookdetails_back(View view){
        finish();
    }

    private Boolean flag = true;
    @OnClick(R.id.douban_bookdetails_showall)
    private void bookdetails_showall(View view){
        if(flag){
            flag = false;
            bookdetails_bookIntroduction.setEllipsize(null); // 展开
            bookdetails_bookIntroduction.setSingleLine(flag);
        }else{
            flag = true;
            bookdetails_bookIntroduction.setLines(5);
            bookdetails_bookIntroduction.setEllipsize(TextUtils.TruncateAt.END); // 收缩

        }
    }
    SharedPreferences sp;
    @OnClick(R.id.apply_book)
    private void apply_book(View view){

        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        if (sp.getInt("mid",-1) != -1) {
            String timestamp = System.currentTimeMillis()/1000+"";
            Map<String,String> map = new HashMap<>();
            map.put("mid",sp.getInt("mid",-1)+"");
            map.put("timestamp",timestamp);
            map.put("doubanid", dbook.getId().toString());
            String sign = Sign.sign(map,sp.getString("token",""));
            RequestCall call = OkHttpUtils.get().url(HttpURLConfig.URL + "private/douban/apply")
                    .addParams("doubanid", dbook.getId().toString())
                    .addParams("mid", sp.getInt("mid",-1)+"")
                    .addParams("timestamp", timestamp)
                    .addParams("sign", sign)
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

                    } else if (messageBean.getStatus() == 0) {
                        Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }else {
            Toast.makeText(getBaseContext(),"您未登录不可进行此操作",Toast.LENGTH_SHORT).show();
            isLogin();
        }
    }
    private void isLogin(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_dou_ban_book_detail);
    }


    private DoubanBook dbook;
    @Override
    protected void initView() {

        dbook = (DoubanBook)getIntent().getExtras().getSerializable("doubanbooks");
        bookdetails_bookName.setText(dbook.getTitle());

        bookdetails_bookImage.setImageURI(Uri.parse(dbook.getImage().toString()));

        if(dbook.getAuthor().length>0) {
            bookdetails_bookAuthor.setText("作者："+dbook.getAuthor()[0]);
        }else{
            bookdetails_bookAuthor.setText("作者不明");
        }

        bookdetails_average_star.setStarMark(Float.parseFloat(dbook.getRating().getAverage())/2);
        bookdetails_average.setText(dbook.getRating().getAverage());
        bookdetails_numraters.setText("（"+dbook.getRating().getNumRaters()+"人评价）");

    //    bookdetails_bookPricint.setText("￥"+dbook.getPrice());
        bookdetails_bookPress.setText("出版社："+dbook.getPublisher());
        bookdetails_bookPuttime.setText("出版时间："+dbook.getPubdate());
        bookdetails_Isbn.setText("ISBN："+dbook.getIsbn13());
        bookdetails_bookPageCount.setText("页数："+dbook.getPages());
        bookdetails_bookBind.setText("装帧："+dbook.getBinding());
        bookdetails_bookPrice.setText("￥"+dbook.getPrice());
        bookdetails_bookIntroduction.setText(dbook.getSummary());

    }

    @Override
    protected void initData() {

    }
}
