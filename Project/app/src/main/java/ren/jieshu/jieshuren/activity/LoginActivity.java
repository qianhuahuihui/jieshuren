package ren.jieshu.jieshuren.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MemberBean;
import ren.jieshu.jieshuren.loading.IOSLoadingDialog;


/**
 * Created by laomaotao on 2017/7/25.
 */

public class LoginActivity extends BaseActivity {

    @OnClick(R.id.login_login)
    private void login_login(View view){
        loginToWeiXin();
    }

    private RequestCall call;
    private IOSLoadingDialog iosLoadingDialog;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void loginToWeiXin(){

//        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
        UMShareAPI.get(this).deleteOauth(LoginActivity.this, SHARE_MEDIA.WEIXIN, null);
//
//        UMShareAPI.get(this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
        UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
        iosLoadingDialog = new IOSLoadingDialog();
        iosLoadingDialog.setHintMsg("正在登录");
        iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");

    }

    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(getBaseContext(), "正在获取微信授权", Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, final Map<String, String> data) {
            iosLoadingDialog.dismiss();
           // Log.e("hhhhhhhhh",data.get("iconurl"));
            Log.e("hhhhhhhhh",data.get("openid"));
            Log.e("hhhhhhhhh",data.get("unionid"));
            call = OkHttpUtils.post().url(HttpURLConfig.URL + "api/user/getToken")
                    .addParams("openid", data.get("openid"))
                    .addParams("unionid", data.get("unionid"))
                    .addParams("image", data.get("iconurl"))
                    .addParams("nickname", data.get("name"))
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
                    MemberBean member = gson.fromJson(response, MemberBean.class);
                    if (member.getStatus() == 1) {
                        Toast.makeText(getBaseContext(), "登陆成功", Toast.LENGTH_LONG).show();
                        SharedPreferences preferences = getSharedPreferences("member", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("mid", member.getMember().getMid());
                        editor.putString("token", member.getMember().getToken());
                        editor.commit();
//                        Intent intent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("member",member.getMember());
//                        intent.putExtras(bundle);
//                        setResult(0,intent);
                        finish();
                    } else if (member.getStatus() == 0) {
                          Toast.makeText(getBaseContext(), member.getError(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
            iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            iosLoadingDialog.dismiss();
            Toast.makeText(getBaseContext(), "登录失败：" + t.getMessage(),                                  Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            iosLoadingDialog.dismiss();
            Toast.makeText(getBaseContext(), "登录取消", Toast.LENGTH_LONG).show();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
        Toast.makeText(getBaseContext(),"这里",Toast.LENGTH_SHORT);

    }


}
