package ren.jieshu.jieshuren.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.netease.scan.util.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import ren.jieshu.jieshuren.activity.BorrowbookActivity;
import ren.jieshu.jieshuren.activity.MyWalletActivity;

import static ren.jieshu.jieshuren.MyApplication.api;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = WXPayEntryActivity.class.getSimpleName();
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

		api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e(TAG, "Lukeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			int code = resp.errCode;
			switch (code) {
				case 0:
					ToastUtil.showShortToast(getBaseContext(),"支付成功");
//					Intent intent = new Intent();
//					intent.setClass(getBaseContext(), MainActivity.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					Bundle bundle = new Bundle();
//					bundle.putString("pay","pay");
//					intent.putExtras(bundle);
//					startActivity(intent);
					SharedPreferences sp = getSharedPreferences("member", Context.MODE_PRIVATE);
					String PAYTYPE = sp.getString("PAYTYPE","");
					if (PAYTYPE.equals("0")){
						Intent intent = new Intent();
						intent.setClass(getBaseContext(), BorrowbookActivity.class);
						startActivity(intent);
					}else if (PAYTYPE.equals("1")){
						Intent intent = new Intent();
						intent.setClass(getBaseContext(), MyWalletActivity.class);
						startActivity(intent);
					}
					finish();
					break;
				case -1:
					ToastUtil.showShortToast(getBaseContext(),"支付失败");
					finish();
					break;
				case -2:
					ToastUtil.showShortToast(getBaseContext(),"支付取消");
					finish();
					break;
				default:
					ToastUtil.showShortToast(getBaseContext(),"支付失败");
					setResult(RESULT_OK);
					finish();
					break;
			}
		}

	}
}