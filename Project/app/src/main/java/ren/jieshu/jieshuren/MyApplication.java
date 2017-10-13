package ren.jieshu.jieshuren;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.netease.scan.QrScan;
import com.netease.scan.QrScanConfiguration;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import ren.jieshu.jieshuren.wxapi.Constants;

/**
 * Created by laomaotao on 2017/6/30.
 */

public class MyApplication extends Application {

    public static IWXAPI api;

    {
        PlatformConfig.setWeixin("wx6d30b35505897a90","a1c721b16f49aed4a43cadb8ec932a3e");

    }
    public static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        Fresco.initialize(this);
        context = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        api = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信
        api.registerApp(Constants.APP_ID);

        //saoyisao
        //        // 默认配置
//        QrScanConfiguration configuration = QrScanConfiguration.createDefault(this);

        // 自定义配置
        QrScanConfiguration configuration = new QrScanConfiguration.Builder(this)
                .setTitleHeight(53)
                .setTitleText("来扫一扫")
                .setTitleTextSize(18)
                .setTitleTextColor(R.color.white)
                .setTipText("将二维码放入框内扫描~")
                .setTipTextSize(14)
                .setTipMarginTop(40)
                .setTipTextColor(R.color.white)
                .setSlideIcon(R.drawable.capture_add_scanning)
                .setAngleColor(R.color.white)
                .setMaskColor(R.color.black_80)
                .setScanFrameRectRate((float) 0.8)
                .build();
        QrScan.getInstance().init(configuration);
    }


}



