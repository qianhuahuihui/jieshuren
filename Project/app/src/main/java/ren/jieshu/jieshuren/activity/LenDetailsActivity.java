package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.DecimalFormat;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.BookBean;
import ren.jieshu.jieshuren.widget.StarBar;

/**
 * Created by laomaotao on 2017/9/24.
 */

public class LenDetailsActivity extends BaseActivity {
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
    @OnClick(R.id.bookdetails_addBorrow)
    private void bookdetails_addBorrow(View view){
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),PayLenActivity.class);
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        intent.putExtra("price",decimalFormat.format(bookDetails.getBookPrice()));
        intent.putExtra("private_isbn",bookDetails.getIsbn());
        intent.putExtra("PAYTYPE","0");
        startActivity(intent);
    }

    @ViewInject(R.id.bookdetails_ll_hasRead)
    private BookBean bookDetails;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_lendetails);

    }
    protected void initView() {

    }
    @Override
    protected void initData() {
        bookDetails = (BookBean) getIntent().getExtras().getSerializable("book");
        bookdetails_bookImage.setImageURI(Uri.parse(bookDetails.getBookImage()));
        bookdetails_bookName.setText(bookDetails.getBookName());
        bookdetails_title.setText(bookDetails.getBookName());
        bookdetails_use.setText("已借出："+bookDetails.getUse());
//        bookdetails_average_star.setStarMark(Float.parseFloat(bookDetails.getAverage())/2);
//        bookdetails_average.setText(bookDetails.getAverage());
        bookdetails_numraters.setText("（"+bookDetails.getNumraters()+"人评价）");
        bookdetails_readCount.setText("借阅人次："+bookDetails.getReadCount());
        bookdetails_bookAuthor.setText("作者："+bookDetails.getBookAuthor());
        bookdetails_bookPricint.setText("￥"+bookDetails.getBookPricing());
        bookdetails_bookPress.setText("出版社："+bookDetails.getBookPress());
        bookdetails_bookPuttime.setText("出版时间："+bookDetails.getBookPuttime());
        bookdetails_publishNum.setText("版次："+bookDetails.getPublishNum());
        bookdetails_Isbn.setText("ISBN："+bookDetails.getIsbn());
        Log.e("isbn",bookDetails.getIsbn());
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
    }
}
