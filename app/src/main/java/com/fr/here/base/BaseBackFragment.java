package com.fr.here.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fr.here.R;


/**
 * 带有back图标的基类
 */
public abstract class BaseBackFragment extends BaseFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
                //_mActivity.onBackPressed();
            }
        });
        //initToolbarMenu(toolbar);
    }

    protected void initToolbarNavClose(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
                _mActivity.finish();
            }
        });
        //initToolbarMenu(toolbar);
    }
}
