package com.fr.here.widget.imageload;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fr.here.R;
import com.fr.here.base.BaseBackFragment;
import com.fr.here.util.ImgHelper;
import com.fr.here.util.ShareUtil;
import com.fr.here.widget.G;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片显示专用
 * Created by shli on 2016-08-12.
 */
public class ImageFragment extends BaseBackFragment {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.image)
    ImageView image;

    private String url;
    private Bitmap bitmap;
    private PhotoViewAttacher attacher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            url = args.getString(G.IMAGE_URL);
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_pic;
    }

    @Override
    public void initView() {
        toolbar.setTitle("图片");
        initToolbarNav(toolbar);
        toolbar.inflateMenu(R.menu.menu_image);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_share:
                        ShareUtil.shareImage(_mActivity, ImgHelper.saveImage(_mActivity, url, bitmap, image, "share"));
                        break;
                    case R.id.action_save:
                        ImgHelper.saveImage(_mActivity, url, bitmap, image, "save");
                        break;
                    case R.id.action_click_me:
                        Snackbar.make(image, "萝莉赛高.. ヾ (o ° ω ° O ) ノ", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        attacher = new PhotoViewAttacher(image);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        image.setImageBitmap(resource);
                        attacher.update();
                        bitmap = resource;
                    }
                });



    }

    @Override
    public void initPresenter() {

    }

    public static ImageFragment newInstance(String url){
        Bundle args = new Bundle();
        args.putString(G.IMAGE_URL, url);
        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(args);
        return imageFragment;
    }

}
