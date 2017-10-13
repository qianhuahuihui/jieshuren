package ren.jieshu.jieshuren.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;

import ren.jieshu.jieshuren.loading.IOSLoadingDialog;


public abstract class BaseFragment extends Fragment {



    public static FragmentManager fragmentManager;
    public static FragmentTransaction transaction;
    public static IOSLoadingDialog iosLoadingDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = createView(inflater,container,savedInstanceState);
        ViewUtils.inject(this, view);
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();
        iosLoadingDialog = new IOSLoadingDialog();
        initToolBar();

        init();

        return view;

    }

    public void  initToolBar(){

    }


    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void init();


}
