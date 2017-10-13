package ren.jieshu.jieshuren.activity;

import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;

/**
 * Created by laomaotao on 2017/9/20.
 */

public class TransferActivity extends BaseActivity {
    @ViewInject(R.id.transfer_integral_available)
    private TextView transfer_integral_available;

    @OnClick(R.id.transfer_back)
    private void transfer_back(View view){
        finish();
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_transfer);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        String integral_available = getIntent().getStringExtra("integral_available");
        transfer_integral_available.setText(integral_available);
    }
}
