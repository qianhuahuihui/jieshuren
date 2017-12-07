package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import ren.jieshu.jieshuren.entity.BlanceBean;
import ren.jieshu.jieshuren.entity.ComfirmResult;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.util.Sign;

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
    @ViewInject(R.id.bit_count_name)
    private EditText bit_count_name;
    @ViewInject(R.id.integral_need_output)
    private EditText integral_need_output;
    private RequestCall call;
    private SharedPreferences sp;
    private String sign;
    private String timestamp;
    private String bitAddress;
    private String integral;
    @OnClick(R.id.integral_output_bt)
    private void confirm(View view){
        sp = this.getSharedPreferences("member", Context.MODE_PRIVATE);
        if(getInput()){
            timestamp = System.currentTimeMillis() / 1000 + "";
            Map<String, String> map = new HashMap<>();
            map.put("mid", sp.getInt("mid", -1) + "");
            map.put("timestamp", timestamp);
            map.put("bitAddress", bitAddress);
            map.put("integral", integral);
            sign = Sign.sign(map, sp.getString("token", ""));
            map.put("sign", sign);
            call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/integral/output")
                    .params(map)
                    .build();
            call.execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(TransferActivity.this, "网络错误！", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    Gson gson = new Gson();
                    ComfirmResult result = gson.fromJson(response, ComfirmResult.class);
                    if (result.getStatus() == 1) {
                        Toast.makeText(TransferActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (result.getStatus() == 0) {
                        Toast.makeText(TransferActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            Toast.makeText(this, inputMsg, Toast.LENGTH_LONG).show();
        }

    }

    private String integral_available;
    private String inputMsg;
    protected boolean getInput(){
        bitAddress = bit_count_name.getText().toString().trim();
        integral  = integral_need_output.getText().toString().trim();
        if(!"".equals(bitAddress)&&!"".equals(integral)){
            if(Float.parseFloat(integral)>Float.parseFloat(integral_available)){
                inputMsg = "您还没有足够的积分，加油！";
                return false;
            }else if(Float.parseFloat(integral)<100f){
                inputMsg = "至少提取100积分哦！";
                return false;
            }else{
                return true;
            }

        }else {

            inputMsg = "比特股地址和转出积分必填哦！";
            return false;
        }
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

        integral_available = getIntent().getStringExtra("integral_available");
        transfer_integral_available.setText(integral_available);
    }
}
