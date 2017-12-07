package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import ren.jieshu.jieshuren.entity.BookBean;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.util.Sign;
import ren.jieshu.jieshuren.widget.StarBar;

/**
 * Created by laomaotao on 2017/7/17.
 */

public class BookDetailsActivity extends BaseActivity {
    @ViewInject(R.id.bookdetails_bookImage)
    private SimpleDraweeView bookdetails_bookImage;
    @ViewInject(R.id.bookdetails_bookName)
    private TextView bookdetails_bookName;
    @ViewInject(R.id.bookdetails_title)
    private TextView bookdetails_title;
    @ViewInject(R.id.bookdetails_numraters)
    private TextView bookdetails_numraters;
    @ViewInject(R.id.bookdetails_use)
    private TextView bookdetails_use;
    @ViewInject(R.id.bookdetails_readCount)
    private TextView bookdetails_readCount;
    @ViewInject(R.id.bookdetails_bookAuthor)
    private TextView bookdetails_bookAuthor;
    @ViewInject(R.id.bookdetails_bookPricint)
    private TextView bookdetails_bookPricint;
    @ViewInject(R.id.bookdetails_bookPress)
    private TextView bookdetails_bookPress;
    @ViewInject(R.id.bookdetails_bookPuttime)
    private TextView bookdetails_bookPuttime;
    @ViewInject(R.id.bookdetails_Isbn)
    private TextView bookdetails_Isbn;
    @ViewInject(R.id.bookdetails_bookPageCount)
    private TextView bookdetails_bookPageCount;
    @ViewInject(R.id.bookdetails_bookBind)
    private TextView bookdetails_bookBind;
    @ViewInject(R.id.bookdetails_bookPrice)
    private TextView bookdetails_bookPrice;
    @ViewInject(R.id.bookdetails_hasRead)
    private TextView bookdetails_hasRead;
    @ViewInject(R.id.bookdetails_average_star)
    private StarBar bookdetails_average_star;
    @ViewInject(R.id.bookdetails_average)
    private TextView bookdetails_average;
    @ViewInject(R.id.bookdetails_freeDb)
    private TextView bookdetails_freeDb;
    @ViewInject(R.id.bookdetails_bookIntroduction)
    private TextView bookdetails_bookIntroduction;
    @ViewInject(R.id.bookdetails_publishNum)
    private TextView bookdetails_publishNum;
    @ViewInject(R.id.bookdetails_addBorrow)
    private Button bookdetails_addBorrow;
    private Integer mid;

    @OnClick(R.id.bookdetails_back)
    private void bookdetails_back(View view){
        finish();
    }
    private Boolean flag = true;
    @OnClick(R.id.bookdetails_showall)
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


    private Integer bookID;
    private SharedPreferences sp;
    private RequestCall addReadcall;
    private RequestCall removeReadCall;
    private RequestCall bookcall;
    private RequestCall addBorrowcall;


    @OnClick(R.id.bookdetails_addBorrow)
    private void bookdetails_addBorrow(View view){
        if (sp.getInt("mid",-1) != -1) {
                String timestamp = System.currentTimeMillis()/1000+"";
                Map<String,String> map = new HashMap<>();
                map.put("mid",sp.getInt("mid",-1)+"");
                map.put("timestamp",timestamp);
                map.put("bookid", bookID.toString());
                String sign = Sign.sign(map,sp.getString("token",""));
                addBorrowcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/borrow/book/addBorrow")
                        .addParams("bookid", bookID.toString())
                        .addParams("mid", sp.getInt("mid",-1)+"")
                        .addParams("timestamp", timestamp)
                        .addParams("sign", sign)
                        .build();
                addBorrowcall.execute(addBorrowCallback);
                iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
    }else {
        Toast.makeText(getBaseContext(),"您未登录不可进行此操作",Toast.LENGTH_SHORT).show();
    }
    }

    @ViewInject(R.id.bookdetails_ll_hasRead)
    private LinearLayout bookdetails_ll_hasRead;
    @OnClick(R.id.bookdetails_ll_hasRead)
    private void bookdetails_ll_hasRead(View view){
        if (sp.getInt("mid",-1) != -1) {
                String timestamp = System.currentTimeMillis() / 1000 + "";
                Map<String, String> map = new HashMap<>();
                map.put("mid", sp.getInt("mid", -1) + "");
                map.put("timestamp", timestamp);
                map.put("bookid", bookID.toString());
                String sign = Sign.sign(map, sp.getString("token", ""));
                addReadcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/book/addRead")
                        .addParams("bookid", bookID.toString())
                        .addParams("mid", sp.getInt("mid", -1) + "")
                        .addParams("timestamp", timestamp)
                        .addParams("sign", sign)
                        .build();
                addReadcall.execute(addReadCallback);
                iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }else {
            Toast.makeText(getBaseContext(),"您未登录不可进行此操作",Toast.LENGTH_SHORT).show();
        }
    }
     @OnClick(R.id.bookdetails_bookfriend)
    private void bookdetails_bookfriend(View view){
         Intent intent = new Intent();
         intent.setClass(getBaseContext(),ThisBookFriendActivity.class);
         //intent.putExtra("bookId",bookDetails.getBookid());
         Bundle bundle = new Bundle();
         bundle.putSerializable("bookDetails",bookDetails);
         intent.putExtras(bundle);
         startActivity(intent);
    }

    private StringCallback addReadCallback = new StringCallback() {
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
                bookdetails_hasRead.setText("已读");
                bookdetails_hasRead.setTextColor(getResources().getColor(R.color.button_color_no));
                bookdetails_ll_hasRead.setClickable(false);
                isHasRead = 1;
            } else if (messageBean.getStatus() == 0){
                Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private StringCallback addBorrowCallback = new StringCallback() {
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
                Toast.makeText(getBaseContext(), "已加入书单", Toast.LENGTH_SHORT).show();
                bookdetails_addBorrow.setText("已加入书单");
                bookdetails_addBorrow.setBackgroundResource(R.color.button_color_no);
                bookdetails_addBorrow.setClickable(false);
                isWantToRead = 1;
            } else if (messageBean.getStatus() == 0) {
                Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private BookBean book;
    private BookBean bookDetails;
    private Integer isHasRead;
    private Integer isWantToRead;
    private StringCallback bookCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            iosLoadingDialog.dismiss();
            Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            iosLoadingDialog.dismiss();
            Gson gson = new Gson();
            book = gson.fromJson(response, BookBean.class);
            if (book.getStatus() == 1){
                bookDetails = book.getBook();
                bookdetails_bookImage.setImageURI(Uri.parse(bookDetails.getBookImage()));
                bookdetails_bookName.setText(bookDetails.getBookName());
                bookdetails_title.setText(bookDetails.getBookName());
                bookdetails_use.setText("已借出："+bookDetails.getUse());
                bookdetails_average_star.setStarMark(Float.parseFloat(bookDetails.getAverage())/2);
                bookdetails_average.setText(bookDetails.getAverage());
                bookdetails_numraters.setText("（"+bookDetails.getNumraters()+"人评价）");
                bookdetails_readCount.setText("借阅人次："+bookDetails.getReadCount());
                bookdetails_bookAuthor.setText("作者："+bookDetails.getBookAuthor());
                bookdetails_bookPricint.setText("￥"+bookDetails.getBookPricing());
                bookdetails_bookPress.setText("出版社："+bookDetails.getBookPress());
                bookdetails_bookPuttime.setText("出版时间："+bookDetails.getBookPuttime());
                bookdetails_publishNum.setText("版次："+bookDetails.getPublishNum());
                bookdetails_Isbn.setText("ISBN："+bookDetails.getIsbn());
                bookdetails_bookPageCount.setText("页数："+bookDetails.getBookPageCount());
                bookdetails_bookBind.setText("装帧："+bookDetails.getBookBind());
                bookdetails_bookPrice.setText("￥"+bookDetails.getBookPrice());
                bookdetails_bookIntroduction.setText(bookDetails.getBookIntroduction());
                if (bookDetails.getFreeDb()!= null) {
                    if (bookDetails.getFreeDb() == 0) {
                        bookdetails_freeDb.setText("2-5天发货");
                    } else {
                        bookdetails_freeDb.setText("24小时发货");
                    }
                }
                isHasRead = bookDetails.getHasRead();
                if (isHasRead > 0){
                    bookdetails_hasRead.setText("已读");
                    bookdetails_hasRead.setTextColor(getResources().getColor(R.color.button_color_no));
                    bookdetails_ll_hasRead.setClickable(false);
                }
                isWantToRead = bookDetails.getWantTORead();
                if (isWantToRead > 0){
                    bookdetails_addBorrow.setText("已加入书单");
                    bookdetails_addBorrow.setBackgroundResource(R.color.button_color_no);
                    bookdetails_addBorrow.setClickable(false);
                }
            }else if (book.getStatus() == 0){
                Toast.makeText(getBaseContext(),book.getError(),Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_bookdetails);

    }
    protected void initView() {
        isHasRead = 0;
        sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        bookID = getIntent().getIntExtra("bookID",0);
        mid = sp.getInt("mid", -1);
        if (mid != -1){
            String timestamp = System.currentTimeMillis() / 1000 + "";
            Map<String, String> map = new HashMap<String, String>();
            map.put("mid", sp.getInt("mid", -1) + "");
            map.put("timestamp", timestamp);
            map.put("bookid", bookID.toString());
            String sign = Sign.sign(map, sp.getString("token", ""));
            map.put("sign",sign);
            bookcall = OkHttpUtils.get().url(HttpURLConfig.URL + "private/borrow/book/get")
                    .params(map)
                    .build();
            bookcall.execute(bookCallback);
        }else {
            bookcall = OkHttpUtils.get().url(HttpURLConfig.URL + "api/book/get")
                    .addParams("bookid", bookID.toString())
                    .build();
            bookcall.execute(bookCallback);
        }

        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }
    @Override
    protected void initData() {

    }
}
