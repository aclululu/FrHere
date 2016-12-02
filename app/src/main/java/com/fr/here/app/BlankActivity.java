package com.fr.here.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fr.here.R;
import com.fr.here.ui.login.widget.LoginFragment;
import com.fr.here.ui.welcome.widget.AnimWelcomeFragment;
import com.fr.here.ui.welcome.widget.MyWelcomeActivity;
import com.fr.here.util.PLog;
import com.stephentuso.welcome.WelcomeScreenHelper;
import com.stephentuso.welcome.ui.WelcomeActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 空界面
 * Created by shli on 2016-05-17.
 * update by shli on 2016-07-27.
 */
public class BlankActivity extends SupportActivity {
    WelcomeScreenHelper welcomeScreen;
    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        //return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置自定义动画
//        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        welcomeScreen = new WelcomeScreenHelper(this, MyWelcomeActivity.class);
        //是否显示了welcomeScreen
        Boolean isWelcome = welcomeScreen.show(savedInstanceState);
        //标志位
        String flag = getIntent().getStringExtra(C.ARGS_BLANK);
        //参数
        String args1 =  getIntent().getStringExtra(C.ARGS1);
        if(flag==null){
            if(isWelcome){
                loadRootFragment(R.id.blank_content, LoginFragment.newInstance());
            }else{
                loadRootFragment(R.id.blank_content, AnimWelcomeFragment.newInstance());
            }
        }else{


        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.h_fragment_enter, R.anim.h_fragment_exit);
    }

    /**
     * 在这里接收导向结束的“事件”
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == WelcomeScreenHelper.DEFAULT_WELCOME_SCREEN_REQUEST) {
            String welcomeKey = data.getStringExtra(WelcomeActivity.WELCOME_SCREEN_KEY);

            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), welcomeKey , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), welcomeKey , Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }
}
