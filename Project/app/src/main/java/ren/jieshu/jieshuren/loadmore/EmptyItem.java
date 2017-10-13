package ren.jieshu.jieshuren.loadmore;

import android.view.View;
import android.view.ViewGroup;

/**
 * 没数据时候的默认View
 *
 * @author zzz40500 on 16/1/31.
 */
public abstract class EmptyItem {

    public CharSequence mEmptyText;
    public int mEmptyIconRes = -1;

    public abstract View onCreateView(ViewGroup parent);

    public abstract void onBindData(View view);
}
