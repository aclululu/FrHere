package com.fr.here.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fr.here.R;
import com.fr.here.base.BaseMainFragment;
import com.fr.here.ui.login.G;
import com.fr.here.ui.login.widget.LoginFragment;
import com.fr.here.ui.news.widget.NewsPTFragment;
import com.fr.here.ui.news.widget.NewsPicFragment;
import com.fr.here.ui.supply.widget.SupplyFragment;
import com.fr.here.ui.video.widget.MovieFragment;
import com.fr.here.util.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends SupportActivity implements NavigationView.OnNavigationItemSelectedListener,
        LoginFragment.OnLoginSuccessListener , BaseMainFragment.OnFragmentOpenDrawerListener{
    BaseApplication ac;
    @Bind(R.id.fl_container)
    FrameLayout flContainer;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    TextView mTvName;   // NavigationView上的名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化View注入
        ButterKnife.bind(this);
        ac = BaseApplication.getInstance();
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, NewsPTFragment.newInstance());
        }
        initView();
    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        navView.setCheckedItem(R.id.nav_news);
        LinearLayout llNavHeader = (LinearLayout) navView.getHeaderView(0);
        mTvName = (TextView) llNavHeader.findViewById(R.id.tv_name);
        llNavHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

                drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        goLogin();
                    }
                }, 250);
            }
        });

        mTvName.setText(SharedPreferencesUtil.loadString(G.USER_NAME));
    }

    private void goLogin() {
        start(LoginFragment.newInstance());
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        //return new DefaultHorizontalAnimator();
        // 设置自定义动画
//        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    @Override
    public void onBackPressedSupport() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment topFragment = getTopFragment();

            // 主页的Fragment
            if (topFragment instanceof BaseMainFragment) {
                navView.setCheckedItem(R.id.nav_news);
            }

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                exitApp();
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.h_fragment_enter, R.anim.h_fragment_exit);
    }


    /**
     * 打开抽屉
     */
    @Override
    public void onOpenDrawer() {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }


    /**
     * 退出应用
     */
    private void exitApp() {
        new MaterialDialog.Builder(this)
                .iconRes(R.mipmap.ic_launcher)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(C.IS_EXIT_TITLE)
                .content(C.IS_EXIT_INFO)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancle)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        finish();
                        BaseApplication.getInstance().exitApp();
                    }
                })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hierarchy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_hierarchy) {
            showFragmentStackHierarchyView();
            logFragmentStackHierarchy("Fragmentation");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    /**
     * 通过回调将姓名设置回来
     * @param account
     */
    @Override
    public void onLoginSuccess(String account) {
        mTvName.setText(account);
    }

    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        drawerLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                final SupportFragment topFragment = getTopFragment();

                switch (id) {
                    case R.id.nav_news:
                        NewsPTFragment newsPTFragment = findFragment(NewsPTFragment.class);
                        start(newsPTFragment, SupportFragment.SINGLETASK);
                        break;
                    case R.id.nav_news_pic:
                        NewsPicFragment newsPicFragment = findFragment(NewsPicFragment.class);
                        if (newsPicFragment == null) {
                            popTo(NewsPTFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(NewsPicFragment.newInstance());
                                }
                            });
                        } else {
                            // 如果已经在栈内,则以SingleTask模式start
                            start(newsPicFragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.nav_video:
                        MovieFragment movieFragment = findFragment(MovieFragment.class);
                        if (movieFragment == null) {
                            popTo(NewsPTFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(MovieFragment.newInstance());
                                }
                            });
                        } else {
                            start(movieFragment, SupportFragment.SINGLETASK);
                        }
                        break;
                    case R.id.nav_supply:
                        SupplyFragment supplyFragment = findFragment(SupplyFragment.class);
                        if (supplyFragment == null) {
                            popTo(NewsPTFragment.class, false, new Runnable() {
                                @Override
                                public void run() {
                                    start(SupplyFragment.newInstance());
                                }
                            });
                        } else {
                            start(supplyFragment, SupportFragment.SINGLETASK);
                        }
                        break;
                }
            }
        }, 250);

        return true;
    }

}
