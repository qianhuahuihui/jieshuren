package ren.jieshu.jieshuren.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import ren.jieshu.jieshuren.Adapter.SexAdapter;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.MemberBean;
import ren.jieshu.jieshuren.entity.MessageBean;
import ren.jieshu.jieshuren.util.IsPhone;
import ren.jieshu.jieshuren.util.Name;
import ren.jieshu.jieshuren.util.Sign;
import ren.jieshu.jieshuren.util.UtilImags;
import ren.jieshu.jieshuren.view.ZQRoundOvalImageView;

/**
 * Created by laomaotao on 2017/8/22.
 */

public class SettingActivity extends BaseActivity {
    String URL = "url";
    private PopupWindow pop;
    private LinearLayout ll_popup;
    private SexAdapter sexAdapter;
    private File file;
    private String lat;
    private String lng;
    private String sex;
    private RequestCall readCall,call,fixcall;

    @OnClick(R.id.setting_back)
    private void setting_back(View view){
//        Intent intent = new Intent();
//        intent.setClass(getBaseContext(),MainActivity.class);
//        intent.putExtra("PAYTYPE","10");
//        startActivity(intent);
        finish();
    }
    @ViewInject(R.id.setting_headimage)
    private SimpleDraweeView setting_headimage;
    @ViewInject(R.id.setting_name)
    private EditText setting_name;
    @ViewInject(R.id.setting_phone)
    private EditText setting_phone;
    @ViewInject(R.id.setting_realname)
    private EditText setting_realname;
//    @ViewInject(R.id.setting_adress)
//    private TextView setting_adress;
    @ViewInject(R.id.setting_sex)
    private Spinner setting_sex;
    @OnClick(R.id.setting_save)
    private void setting_save(View view){
        if (IsPhone.isPhone(setting_phone.getText().toString().trim())) {
            if (setting_name.getText().toString().trim().equals("")) {
                Toast.makeText(getBaseContext(), "请填写会员昵称", Toast.LENGTH_SHORT).show();
            } else if (setting_realname.getText().toString().trim().equals("")) {
                Toast.makeText(getBaseContext(), "请填写真实姓名", Toast.LENGTH_SHORT).show();
            } else if (sex.equals("")) {
                Toast.makeText(getBaseContext(), "请选择性别", Toast.LENGTH_SHORT).show();
            } else if (setting_phone.getText().toString().trim().equals("")) {
                Toast.makeText(getBaseContext(), "请填写联系电话", Toast.LENGTH_SHORT).show();
            }
            //else if (file == null) {
               // Toast.makeText(getBaseContext(), "请选择头像上传", Toast.LENGTH_SHORT).show();
               //}
            else {
                SharedPreferences sp = getBaseContext().getSharedPreferences("member", Context.MODE_PRIVATE);

                String timestamp = System.currentTimeMillis() / 1000 + "";
                Map<String, String> map = new HashMap<>();
                map.put("mid", sp.getInt("mid", -1) + "");
                map.put("timestamp", timestamp);
//            map.put("lat", lat);
//            map.put("lng", lng);
                map.put("name", setting_name.getText().toString().trim());
                map.put("realname", setting_realname.getText().toString().trim());
                map.put("sex", sex);
                map.put("mobile", setting_phone.getText().toString().trim());
//            map.put("address", setting_adress.getText().toString().trim());
                String sign = Sign.sign(map, sp.getString("token", ""));
                map.put("sign", sign);
                readCall = OkHttpUtils.post().url(HttpURLConfig.URL + "private/user/userSet")
                    //    .addFile("headimg", Name.getRandomFileName(), file)
                        .params(map)
                        .build();
                readCall.execute(new StringCallback() {
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
                            Intent intent = new Intent();
                            intent.setClass(getBaseContext(), MainActivity.class);
                            intent.putExtra("PAYTYPE", "10");
                            startActivity(intent);
                            finish();
                        } else if (messageBean.getStatus() == 0) {
                            Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
            }
        }else {
            Toast.makeText(getBaseContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
        }
    }
//    @OnClick(R.id.setting_rl_adress)
//    private void setting_rl_adress(View view){
//
//        Intent intent = new Intent();
//        intent.setClass(getBaseContext(),MapActivity.class);
//        startActivityForResult(intent,10);
//    }
    @OnClick(R.id.setting_headimage)
    private void setting_headimage(View view){
        showPopupWindow();
        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                SettingActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(setting_headimage, Gravity.BOTTOM, 0, 0);
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        final List<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        list.add("保密");
        sexAdapter = new SexAdapter(getBaseContext(),list);
        setting_sex.setAdapter(sexAdapter);
        setting_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sex = list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void initData() {
        SharedPreferences sp = getSharedPreferences("member", Context.MODE_PRIVATE);
        Integer mid = sp.getInt("mid", -1);
        if (mid != -1) {
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
                }

                @Override
                public void onResponse(String response, int id) {
                    Gson gson = new Gson();
                    MemberBean member = gson.fromJson(response, MemberBean.class);
                    if (member.getStatus() == 1) {
                        setting_headimage.setImageURI(Uri.parse(member.getMember().getHeadimgurl()));
                        setting_name.setText(member.getMember().getName());
                        setting_realname.setText(member.getMember().getRealname());
                        setting_phone.setText(member.getMember().getMobile());
                    } else if (member.getStatus() == 0) {
                        Toast.makeText(getBaseContext(), member.getError(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
           // iosLoadingDialog.show(getFragmentManager(), "iosLoadingDialog");
        }
    }

    public void getImage(String url) {
        OkHttpUtils.get().url(url).tag(this).build().connTimeOut(20000)
                .readTimeOut(20000).writeTimeOut(20000)
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id) {
                        setting_headimage.setImageBitmap(bitmap);
                    }
                });
    }
    /****
     * 头像提示框
     */
    public void showPopupWindow() {
        pop = new PopupWindow(SettingActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
                null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 1);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent picture = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, 2);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bmp = null;
        if (requestCode == 1 && resultCode == Activity.RESULT_OK
                && null != data) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss",
                    Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            // 获取相机返回的数据，并转换为图片格式
            bmp = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            String filename = null;
            try {
                filename = UtilImags.SHOWFILEURL(SettingActivity.this) + "/" + name;
            } catch (IOException e) {
            }
            try {
                fout = new FileOutputStream(filename);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            } catch (FileNotFoundException e) {
                showToastShort("上传失败");
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    showToastShort("上传失败");
                }
            }
           // setting_headimage.setImageBitmap(bmp);
            file = new File(filename);

//            staffFileupload(new File(filename));
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage,
                        filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                bmp = BitmapFactory.decodeFile(picturePath);
                // 获取图片并显示
                //setting_headimage.setImageBitmap(bmp);
                saveBitmapFile(UtilImags.compressScale(bmp), UtilImags.SHOWFILEURL(SettingActivity.this) + "/stscname.jpg");
                file = new File(UtilImags.SHOWFILEURL(SettingActivity.this) + "/stscname.jpg");
//                staffFileupload(new File(UtilImags.SHOWFILEURL(SettingActivity.this) + "/stscname.jpg"));
            } catch (Exception e) {
                showToastShort("上传失败");
            }
        }

        if(bmp!=null){
           // Log.e("psn","================start");
            fixHeadImg(bmp);
        }


//        if (requestCode == 10 && resultCode == Activity.RESULT_OK
//                && null != data) {
//                String address = data.getStringExtra("Address");
//                lat = data.getStringExtra("lat");
//                lng = data.getStringExtra("lng");
//            setting_adress.setText(address);
//        }
    }

    public void saveBitmapFile(Bitmap bitmap, String path) {
        File file = new File(path);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改头像
     */
    public void fixHeadImg(final Bitmap bmp){

        SharedPreferences sp = getBaseContext().getSharedPreferences("member", Context.MODE_PRIVATE);
        Log.e("psn","================start"+sp.getInt("mid", -1) + "======"+file.getName());
        String timestamp = System.currentTimeMillis() / 1000 + "";
        Map<String, String> map = new HashMap<>();
        map.put("mid", sp.getInt("mid", -1) + "");
        map.put("timestamp", timestamp);
        String sign = Sign.sign(map, sp.getString("token", ""));
        map.put("sign", sign);
        fixcall = OkHttpUtils.post().url(HttpURLConfig.URL + "private/user/fixHeadImg")
                .addParams("mid", sp.getInt("mid", -1) + "")
                .addParams("timestamp", timestamp)
                .addParams("sign", sign)
                .addFile("headimg", Name.getRandomFileName(), file)
                .build();
        fixcall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(getBaseContext(), "网络出现问题，请重试", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                MessageBean messageBean = gson.fromJson(response, MessageBean.class);
                if (messageBean.getStatus() == 1) {

                    setting_headimage.setImageBitmap(bmp);
                    Toast.makeText(getBaseContext(), messageBean.getMsg(), Toast.LENGTH_SHORT).show();
                } else if (messageBean.getStatus() == 0) {
                    Toast.makeText(getBaseContext(), messageBean.getError(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /***
     * 修改头像
     *
     * @return
     */
    public static final RequestParams MYUPDATEIMG(File file) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("c", "profile");
        params.addBodyParameter("a", "setavatar");
        params.addBodyParameter("uid", "");
        params.addBodyParameter("username", "");
        if (file != null) {
            params.addBodyParameter("filedata", file);
        }
        return params;
    }

    private void showToastShort(String string) {
        Toast.makeText(SettingActivity.this, string, Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(),MainActivity.class);
            intent.putExtra("PAYTYPE","10");
            startActivity(intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
