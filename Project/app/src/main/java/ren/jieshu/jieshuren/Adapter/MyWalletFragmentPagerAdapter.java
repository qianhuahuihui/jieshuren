package ren.jieshu.jieshuren.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ren.jieshu.jieshuren.fragment.QuantumIntegralFragment;
import ren.jieshu.jieshuren.fragment.RMBFragment;

/**
 * Created by laomaotao on 2017/7/21.
 */

public class MyWalletFragmentPagerAdapter extends FragmentPagerAdapter {

        private String[] mTitles = new String[]{"人民币", "量子积分"};

        public MyWalletFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new RMBFragment();
            } else if (position == 1){
                return new QuantumIntegralFragment();
            }
            return new RMBFragment();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
