package ren.jieshu.jieshuren.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.activity.TransferActivity;
import ren.jieshu.jieshuren.base.BaseFragment;


/**
 * Created by laomaotao on 2017/7/21.
 */

public class QuantumIntegralFragment extends BaseFragment {

    @ViewInject(R.id.quantumintegral_integral_available)
    private TextView quantumintegral_integral_available;
    @ViewInject(R.id.quantumintegral_deposit)
    private TextView quantumintegral_deposit;
    @OnClick(R.id.quantumintegral_switchto)
    private void quantumintegral_switchto(View view){
//        Intent intent = new Intent();
//        intent.setClass(getContext(),TransferActivity.class);
//        intent.putExtra("integral_available",quantumintegral_integral_available.getText());
//        startActivity(intent);
    }
    @OnClick(R.id.quantumintegral_out)
    private void quantumintegral_out(View view){
        Intent intent = new Intent();
        intent.setClass(getContext(),TransferActivity.class);
        intent.putExtra("integral_available",quantumintegral_deposit.getText());
        startActivity(intent);
    }
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quantumintegral,container,false);
    }

    @Override
    public void init() {

    }
}
