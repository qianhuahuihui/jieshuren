package ren.jieshu.jieshuren.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseFragment;

/**
 * Created by laomaotao on 2017/6/29.
 */

public class BookbuddyFragment extends BaseFragment {
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bookbuddy,container,false);
    }

    @Override
    public void init() {


    }
}
