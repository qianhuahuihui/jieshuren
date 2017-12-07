package ren.jieshu.jieshuren.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.entity.HttpURLConfig;
import ren.jieshu.jieshuren.entity.UpgradeInfo;

/**
 * Author: shinianPan on 2017/11/12.
 * email : snow_psn@163.com
 */

public class UpdateManager {
    private Context mContext;
    private static final int DOWNLOAD = 0;//下载
    private static final int DOWNLOAD_FINISH = 1;//下载完成
    private String mSavePath;//保存路径
    private int progress;//进度值
    private ProgressBar mProgress;//进度条
    private Dialog mDownloadDialog;//更新窗口
    private boolean cancelUpdate = false;//取消更新

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    private Handler handler = new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case DOWNLOAD:
                  mProgress.setProgress(progress);
                  break;
              case DOWNLOAD_FINISH:
                  installApk();
                  break;
              default:
                  break;
          }
      }
    };

    /**
     * 检查软件更新
     */
    public void checkUpdate(){
        if(isUpdate()){
            showNoticeDialog();
        }else{
            Toast.makeText(mContext, "没有新版", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 判断是否有更新
     * @return
     */
    UpgradeInfo upgradeInfo;
    int serviceCode = 1;
    private boolean isUpdate() {
        int versionCode = getVersionCode(mContext);


        RequestCall call =  OkHttpUtils.get().url(HttpURLConfig.URL + "public/checkapk/")
                .build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //Toast.makeText(mContext, "网络出现问题，请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                UpgradeInfo Info = gson.fromJson(response,UpgradeInfo.class);
                upgradeInfo = Info.getData();
                if (upgradeInfo.getStatus()==1){
                    serviceCode = upgradeInfo.getData().getVersionCode();
                }
            }
        });


        if(serviceCode >versionCode)
        {
            return true;
        }
        return false;
    }

    /**
     * 获取本地版本号
     * @param context
     * @return
     */
    private int getVersionCode(Context context) {
        int versionCode = 0;
        try{
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            PackageManager manager = context.getPackageManager();
            versionCode = manager.getPackageInfo(context.getPackageName(),0).versionCode;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("检测更新");
        builder.setMessage("检测到新版本，立即更新！");
        // 更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 显示下载对话框
                showDownloadDialog();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }

    private void downloadApk() {
        new downloadApkThread().start();
    }


    private class downloadApkThread extends Thread{

        public void run(){
            try{
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL(HttpURLConfig.URL+upgradeInfo.getApkUrl());
                    // 创建连接
                   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    //获取文件大小
                    int length = conn.getContentLength();
                    //创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File((mSavePath));

                    if(!file.exists()){
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "软件名字");

                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count=0;
                    byte buf[] = new byte[1024];

                    //写入文件中
                    do{
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        handler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            handler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf,0,numread);
                    }while (!cancelUpdate);
                    fos.close();
                    is.close();

                }
            }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    private void installApk() {
        File apkfile = new File(mSavePath, "软件名字");
        if (!apkfile.exists()) {
            return;
        }
        //校验MD5

        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}
