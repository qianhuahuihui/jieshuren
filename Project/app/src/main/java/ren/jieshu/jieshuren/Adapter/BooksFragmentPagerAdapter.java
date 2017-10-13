package ren.jieshu.jieshuren.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ren.jieshu.jieshuren.entity.TypesBean;
import ren.jieshu.jieshuren.fragment.NewbookFragment;

/**
 * Created by laomaotao on 2017/8/13.
 */

public class BooksFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<TypesBean> typesList;
    public BooksFragmentPagerAdapter(FragmentManager fm, List<TypesBean> typesList) {
        super(fm);
        this.typesList  =typesList;
    }

    @Override
    public Fragment getItem(int position) {

        return NewbookFragment.newInstance(typesList.get(position).getId().toString());
    }

    @Override
    public int getCount() {
        return typesList.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return typesList.get(position).getTypeName();
    }
}