package com.linzh.android.newfriendvoice.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.linzh.android.newfriendvoice.R;
import com.linzh.android.newfriendvoice.ui.ActivityCollector;
import com.linzh.android.newfriendvoice.ui.base.BaseActivity;
import com.linzh.android.newfriendvoice.ui.login.LoginActivity;
import com.linzh.android.newfriendvoice.ui.manual.ManualActivity;
import com.linzh.android.newfriendvoice.ui.setting.SettingActivity;
import com.linzh.android.newfriendvoice.ui.writer.WriterActivity;
import com.linzh.android.newfriendvoice.utils.VoiceUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.main_navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.main_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.main_view_pager)
    ViewPager mViewPager;

    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout.Tab mTabOne;
    private TabLayout.Tab mTabTwo;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);

        setUnbinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);

        setUp();

        // 初始化语音设置
        VoiceUtils.initVoiceSetting(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        unlockDrawer();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();//不要调用Super类方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public void lockDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    @Override
    public void unlockDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void setUp() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();//获取actionbar实例
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);//导航按钮图标
        }

        mNavigationView.setCheckedItem(R.id.nav_manual);
        mNavigationView.setNavigationItemSelectedListener(this);

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout.setupWithViewPager(mViewPager);

        // 指定Tab位置
        mTabOne = mTabLayout.getTabAt(0);
        mTabTwo = mTabLayout.getTabAt(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_manual://启动使用说明界面
                openManualActivity();
                break;
            case R.id.nav_write_board:
                openWriterActivity();
                break;
            case R.id.nav_debug_mode:
                openLoginActivity();
                break;
            case R.id.nav_color_style:
                showTempAlert();
                break;
            case R.id.nav_setting:
                openSettingActivity();
                break;
            case R.id.nav_exit://询问是否真要退出App
                showExitDialog();
                break;
            default:
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void openLoginActivity() {
        Intent intent = LoginActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openWriterActivity() {
        Intent intent = WriterActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openManualActivity() {
        Intent intent = ManualActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void openSettingActivity() {
        Intent intent = SettingActivity.getStartIntent(this);
        startActivity(intent);
    }

    @Override
    public void showTempAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("当前功能未完成")
                .setCancelable(false)
                .setPositiveButton("确定", null);
        builder.show();
    }

    @Override
    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("是否确定退出应用程序？")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing to do now
                    }
                });
                builder.show();
    }
}
