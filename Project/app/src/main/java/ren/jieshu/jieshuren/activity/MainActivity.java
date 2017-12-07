package ren.jieshu.jieshuren.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import ren.jieshu.jieshuren.R;
import ren.jieshu.jieshuren.base.BaseActivity;
import ren.jieshu.jieshuren.fragment.BookbuddyFragment;
import ren.jieshu.jieshuren.fragment.BooklistFragment;
import ren.jieshu.jieshuren.fragment.HomeFragment;
import ren.jieshu.jieshuren.fragment.MessageFragment;
import ren.jieshu.jieshuren.fragment.MineFragment;

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener{
    /**
     * FragmentTabhost
     */
    private FragmentTabHost mTabHost;

    /**
     * 布局填充器
     *
     */
    private LayoutInflater mLayoutInflater;

    /**
     * Fragment数组界面
     *
     */
    private Class mFragmentArray[] = { HomeFragment.class, BookbuddyFragment.class,
            MessageFragment.class, BooklistFragment.class, MineFragment.class };
    /**
     * 存放图片数组
     *
     */
    private int mImageArray[] = { R.drawable.selector_icon_home,
            R.drawable.selector_icon_bookbuddy, R.drawable.selector_icon_message,
            R.drawable.selector_icon_booklist, R.drawable.selector_icon_mine };

    /**
     * 选修卡文字
     *
     */
    private String mTextArray[] = { "主页", "书友", "信息", "书单", "我的" };
    /**
     *
     *
     */

    @Override
    protected void setContentView() {
        //        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_main);
    }

    /**
     * 初始化组件
     */
    protected void initView() {


        mLayoutInflater = LayoutInflater.from(this);

        // 找到TabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.main_fragmenttabhost);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_framelayout_background);
        // 得到fragment的个数
        int count = mFragmentArray.length;
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
//            // 设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i)
//                    .setBackgroundResource(R.drawable.selector_tab_background);
        }
    }

    @Override
    protected void initData() {
      //  Bugly.init(this,"b031f7bfcc",true);
        Bugly.init(this,"c703debc48",true); //借书人
    }


    /**
     *
     * 给每个Tab按钮设置图标和文字
     */
    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
        imageView.setImageResource(mImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.txt_indicator);
        textView.setText(mTextArray[index]);

        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTabChanged(String tabId) {
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public static final String TAG_EXIT = "exit";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//设置新的intent
        if (intent != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().getString("PAYTYPE") != null) {
                    mTabHost.setCurrentTabByTag("我的");
                }
            }
                boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
                if (isExit) {
                    this.finish();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}




