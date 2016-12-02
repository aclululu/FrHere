package com.fr.here.ui.login.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fr.here.R;
import com.fr.here.app.C;
import com.fr.here.app.MainActivity;
import com.fr.here.base.BaseBackFragment;
import com.fr.here.base.BaseFragment;
import com.fr.here.ui.login.G;
import com.fr.here.util.MyStringUtils;
import com.fr.here.util.SharedPreferencesUtil;
import com.fr.here.widget.stereo.CustomEdittext;
import com.fr.here.widget.stereo.CustomTextView;
import com.fr.here.widget.stereo.RippleView;
import com.fr.here.widget.stereo.StereoView;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.CheckBox;
import com.gc.materialdesign.views.CheckBox.OnCheckListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * loign
 * Created by shli on 2016-07-27.
 */
public class LoginFragment extends BaseBackFragment implements View.OnClickListener,OnCheckListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.et_user)
    CustomEdittext etUser;
    @Bind(R.id.et_password)
    CustomEdittext etPassword;
    @Bind(R.id.tv_love)
    CustomTextView tvLove;

    @Bind(R.id.stereoView1)
    StereoView stereoView1;

    @Bind(R.id.remeber_pwd)
    CheckBox remeberPwd;
    @Bind(R.id.remeber_pwd_text)
    TextView remeberPwdText;
    @Bind(R.id.auto_login)
    CheckBox autoLogin;
    @Bind(R.id.auto_login_text)
    TextView autoLoginText;

    @Bind(R.id.login_button)
    ButtonRectangle loginButton;

    private OnLoginSuccessListener mOnLoginSuccessListener;

    public interface  OnLoginSuccessListener {
        void onLoginSuccess(String account);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginSuccessListener) {
            mOnLoginSuccessListener = (OnLoginSuccessListener) context;
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        toolbar.setTitle(C.APP_NAME);
        stereoView1.setAngle(60).setResistance(1f).setItem(1);
        autoLogin.setOncheckListener(this);
        remeberPwd.setOncheckListener(this);
        autoLoginText.setOnClickListener(this);
        remeberPwdText.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void initPresenter() {

    }




    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment chooseFragment = new LoginFragment();
        chooseFragment.setArguments(args);
        return chooseFragment;
    }

    /**
     * checkBox 控制逻辑
     * @param checkBox
     * @param b
     */
    @Override
    public void onCheck(CheckBox checkBox, boolean b) {
        if(checkBox.getId()==R.id.auto_login&&checkBox.isCheck()){
            remeberPwd.setChecked(true);
        }
        if(checkBox.getId()==R.id.remeber_pwd&&!checkBox.isCheck()){
            autoLogin.setChecked(false);
        }
    }

    /**
     * 各种点击事件的简单控制逻辑
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.remeber_pwd_text:
                remeberPwd.setChecked(!remeberPwd.isCheck());
                //onCheck(remeberPwd, remeberPwd.isCheck());
                break;
            case R.id.auto_login_text:
                autoLogin.setChecked(!autoLogin.isCheck());
               // onCheck(autoLogin, autoLogin.isCheck());
                break;
            case R.id.login_button:
                loginButtonclick();
                break;
            default:
                break;
        }
    }

    /**
     * 按钮控制逻辑
     *<br><font color="red">注：由于3D输入框的api局限性 这里就之将登录信息写入sharedpreference文件里面而已</font> </br>
     * TODO 完善3D输入
     */
    private void loginButtonclick() {
        SharedPreferencesUtil.saveString(G.USER_NAME, MyStringUtils.isEmpty(String.valueOf(etUser.getText())) ?
                G.USER_NAME_DEFAULT : String.valueOf(etUser.getText()));
        if(mOnLoginSuccessListener!=null){
            // 登录成功
            mOnLoginSuccessListener.onLoginSuccess(SharedPreferencesUtil.loadString(G.USER_NAME));
            pop();
        }else{
            //直接跳转至主页面了
            ToMain();
        }
    }

    /**
     * 跳转至主界面  关闭Blank界面
     */
    private void ToMain(){
        Intent intent = new Intent(_mActivity, MainActivity.class);
       /* ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(_mActivity);
        ActivityCompat.startActivity(_mActivity,intent,options.toBundle());*/
        startActivity(intent);
        //_mActivity.overridePendingTransition(R.anim.h_fragment_enter,R.anim.h_fragment_exit);
        popTo(LoginFragment.class, true);
        _mActivity.finish();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginSuccessListener = null;
    }
}
