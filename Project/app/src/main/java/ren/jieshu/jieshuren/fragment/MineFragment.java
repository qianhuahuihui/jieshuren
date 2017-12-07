package ren.jieshu.jieshuren.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.BorrowbookActivity;
import ren.jieshu.jieshuren.activity.LoginActivity;
import ren.jieshu.jieshuren.activity.MyBookshelfActivity;
import ren.jieshu.jieshuren.activity.MyWalletActivity;
import ren.jieshu.jieshuren.activity.ReadBookActivity;
import ren.jieshu.jieshuren.activity.RealSettingActivity;
import ren.jieshu.jieshuren.activity.ReturnBookActionActivity;
import ren.jieshu.jieshuren.activity.ReturnbookActivity;
import ren.jieshu.jieshuren.activity.WearPaymentListActivity;
import ren.jieshu.jieshuren.base.BaseFragment;
import ren.jieshu.jieshuren.entity.MemberBean;
import ren.jieshu.jieshuren.util.Sign;
import ren.jieshu.jieshuren.util.UpdateManager;

/**
 * Created by laomaotao on 2017/6/29.
 */

public class MineFragment extends BaseFragment {
    @ViewInject(R.id.minefragment_head)
    private SimpleDraweeView minefragment_head;
    @ViewInject(R.id.minefragment_name)
    private TextView minefragment_name;
//    @ViewInject(R.id.minefragment_id)
//    private TextView minefragment_id;
    @ViewInject(R.id.minefragment_money)
    private TextView minefragment_deposit;
    @ViewInject(R.id.minefragment_deposit)
    private TextView minefragment_money;
    @ViewInject(R.id.minefragment_login)
    private LinearLayout minefragment_login;
    @ViewInject(R.id.minefragment_rl)
    private RelativeLayout minefragment_rl;
    private Integer mid;
    private SharedPreferences preferences;


    @OnClick(R.id.minefragment_rl_setting)
    public void minefragment_rl_setting(View arg0){
        if (mid == -1){
            isLogin();
        }else {
                Intent intent = new Intent();
                intent.setClass(getContext(),RealSettingActivity.class);
                startActivity(intent);
        }
    }
 @OnClick(R.id.minefragment_rl_wearpayment)
    public void minefragment_rl_wearpayment(View arg0){
        if (mid == -1){
            isLogin();
        }else {
                Intent intent = new Intent();
                intent.setClass(getContext(),WearPaymentListActivity.class);
                startActivity(intent);
        }
    }


    @OnClick(R.id.minefragment_libraryorder_click)
    public void minefragment_libraryorder(View arg0){
        if (mid == -1){
            isLogin();
        }else {
            Intent intent = new Intent();
            intent.setClass(getContext(),BorrowbookActivity.class);
            startActivity(intent);
        }
    }
    @OnClick(R.id.minefragment_returnbook)
    public void minefragment_returnbook(View arg0){
        if (mid == -1){
            isLogin();
        }else {
            Intent intent = new Intent();
            intent.setClass(getContext(),ReturnbookActivity.class);
            startActivity(intent);
        }
    }
    @OnClick(R.id.minefragment_mywallet_click)
    public void minefragment_mywallet_click(View arg0){
        if (mid == -1){
            isLogin();
        }else {
            Intent intent = new Intent();
            intent.setClass(getContext(),MyWalletActivity.class);
            startActivity(intent);
        }
    }
    @OnClick(R.id.minefragment_rl_read)
    public void minefragment_rl_read(View arg0){
        if (mid == -1){
            isLogin();
        }else {
            Intent intent = new Intent();
            intent.setClass(getContext(),ReadBookActivity.class);
            startActivity(intent);

        }
    }

    @OnClick(R.id.minefragment_rl_bookshelf)
    public void minefragment_rl_bookshelf(View arg0){
        if (mid == -1){
            isLogin();
        }else {
//            Intent intent = new Intent();
//            intent.setClass(getContext(),MyBookshelfActivity.class);
//            startActivity(intent);
            Toast.makeText(getContext(),"正在开发中，敬请期待......",Toast.LENGTH_LONG).show();
        }
    }
   /* @OnClick(R.id.minefragment_returnbookaction_click)
    public void minefragment_returnbookaction_click(View arg0){
        if (mid == -1){
            isLogin();
        }else {
            Intent intent = new Intent();
            intent.setClass(getContext(), ReturnBookActionActivity.class);
            startActivity(intent);
        }
    }*/
    @OnClick(R.id.minefragment_login)
    public void minefragment_login(View arg0){

        new AlertDialog.Builder(getActivity())
                .setTitle("警告！").setIcon(R.drawable.jieshurenlogo)
                .setMessage("老用户请务必先进入借书人公众号借书页面，再过来登录。否者数据将不能同步！！！")
                .setPositiveButton("我要登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        startActivityForResult(intent, 0);
                    }
                })
                .setNegativeButton("我要同步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
//        Intent intent=new Intent(getActivity(),LoginActivity.class);
//        startActivityForResult(intent, 0);
    }
    private RequestCall call;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,container,false);

    }

    @Override
    public void onResume() {
        super.onResume();
     //   Log.e("psn","返回会执行吗");
        addData();

    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            //onResume
//            Log.e("psn","setUserVisibleHint返回会执行吗");
//            addData();
//        } else {
//            //相当于Fragment的onPause
//        }
//    }
    @Override
    public void init() {
    }

    private void addData(){
        SharedPreferences sp = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
        mid = sp.getInt("mid", -1);
        Log.e("psn","this time mid is "+mid);
        if (mid != -1){
            minefragment_rl.setVisibility(View.VISIBLE);
            minefragment_login.setVisibility(View.GONE);

            String timestamp = System.currentTimeMillis() / 1000 + "";
            Map<String, String> map = new HashMap<>();
            map.put("mid", sp.getInt("mid", -1) + "");
            map.put("timestamp", timestamp);
            String sign = Sign.sign(map, sp.getString("token", ""));
            call = OkHttpUtils.post().url(HttpURLConfig.URL + "private/user/get")
                    .addParams("mid", sp.getInt("mid", -1) + "")
                    .addParams("timestamp", timestamp)
                    .addParams("sign", sign)
                    .build();
            call.execute(new StringCallback() {


                @Override
                public void onError(Call call, Exception e, int id) {
                    iosLoadingDialog.dismiss();
                }

                @Override
                public void onResponse(String response, int id) {
                    iosLoadingDialog.dismiss();
                    Gson gson = new Gson();
                    MemberBean member = gson.fromJson(response, MemberBean.class);
                    if (member.getStatus() == 1) {
                        minefragment_head.setImageURI(Uri.parse(member.getMember().getHeadimgurl()));
                        minefragment_name.setText("昵称：" + member.getMember().getName());
                       // minefragment_id.setText("ID:" + member.getMember().getMid());
                        preferences = getActivity().getSharedPreferences("member", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        mid = member.getMember().getMid();
                        editor.putInt("mid", member.getMember().getMid());
                        editor.putString("token", member.getMember().getToken());
                        editor.commit();
                        if (!member.getMember().toString().equals("")) {
                            minefragment_money.setText(member.getMember().getFrozenMoney().toString());
                            minefragment_deposit.setText(member.getMember().getMoney().toString());
                        }
                    } else if (member.getStatus() == 0) {
                        Toast.makeText(getContext(), member.getError(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
            iosLoadingDialog.show(getActivity().getFragmentManager(), "iosLoadingDialog");
        }
    }
    private void isLogin(){
            Intent intent=new Intent(getActivity(),LoginActivity.class);
            startActivityForResult(intent, 0);
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (resultCode){
//            case 0:
//                if (data != null) {
//                    Bundle bundle = data.getExtras();
//                    MemberBean member = (MemberBean) bundle.getSerializable("member");
//
//                    minefragment_rl.setVisibility(View.VISIBLE);
//                    minefragment_login.setVisibility(View.GONE);
//                    minefragment_head.setImageURI(Uri.parse(member.getHeadimgurl()));
//                    minefragment_name.setText("昵称："+member.getName());
//                    minefragment_id.setText("ID:"+member.getMid());
//                    minefragment_money.setText(member.getFrozenMoney().toString());
//                    minefragment_deposit.setText(member.getMoney().toString());
//
//                }
//                break;
//        }
//    }
}
