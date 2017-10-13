package ren.jieshu.jieshuren.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ren.jieshu.jieshuren.fragment.BooksFragment;

/**
 * Created by laomaotao on 2017/8/15.
 */

public class BooklistPagerAdapter extends FragmentStatePagerAdapter {

    public BooklistPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private List<String> typesList;
    public BooklistPagerAdapter(FragmentManager fm,List<String> typesList) {
        super(fm);
        this.typesList  =typesList;
    }

    @Override
    public Fragment getItem(int position) {
        return BooksFragment.newInstance(position);
    }

    @Override
    public int getItemPosition(Object object) {
        if (object.getClass().getName().equals(BooksFragment.class.getName())) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return typesList.size();
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return typesList.get(position);
    }

}