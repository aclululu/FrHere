package com.fr.here.ui.welcome.widget;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fr.here.R;
import com.fr.here.app.BaseApplication;
import com.fr.here.base.BaseFragment;
import com.fr.here.base.BaseResponse;
import com.fr.here.net.FrHereApi;
import com.fr.here.net.RetrofitSingleton;
import com.fr.here.ui.login.widget.LoginFragment;
import com.fr.here.ui.welcome.vo.WelcomePic;
import com.fr.here.util.NetUtils;
import com.fr.here.util.PLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 进入app动画界面   可读取服务器文件  但是服务器图片稍微慢一点就会影响动画效果
 * Created by shli on 2016-07-26.
 */
public class AnimWelcomeFragment extends BaseFragment {

    @Bind(R.id.welcome_image)
    ImageView welcomeImage;
    @Bind(R.id.info)
    TextView info;

    Subscription subscription;
    AnimatorSet set;

    @Override
    public int getLayoutRes() {

        return R.layout.fragment_welcome_layout;

    }

    @Override
    public void initView() {
        init();
    }

    @Override
    public void initPresenter() {

    }

    /**
     * 装载图片
     * 无网络环境       显示默认
     * 读取失败时       显示默认
     */
    private void init() {
        if(!NetUtils.isNetworkConnected(BaseApplication.getInstance())){
            welcomeImage.setImageResource(R.drawable.wall_picture);
            animateImage();
            return;
        }
        final RequestListener listener = new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                //image.setImageResource(R.drawable.wall_picture);
                animateImage();
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                animateImage();
                return false;
            }
        };

        /**
         * 这里不使用项目封装好的网络请求，因为项目中的请求实例响应时间为15秒。明显不适合做欢迎功能。
         * 将响应时间缩短为3秒  防止服务器或者网络出问题的时候 出现长时间的白屏时间
         */
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(2, TimeUnit.SECONDS);
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        //创建请求实例
         subscription = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(FrHereApi.PING_URL)
                .build()
                .create(FrHereApi.class).getWelcomePic()
                /**
                 *注释掉项目中的网络请求实例
                 */
        //RetrofitSingleton.apiService.getWelcomePic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BaseResponse<WelcomePic>, String[]>() {
                    @Override
                    public String[] call(BaseResponse<WelcomePic> welcomePicBaseResponse) {
                        return new String[]{welcomePicBaseResponse.model.info==null?"":welcomePicBaseResponse.model.info,FrHereApi.API_URL + welcomePicBaseResponse.model.picurl};
                    }
                })
                .subscribe(new Observer<String[]>() {
                    @Override
                    public void onCompleted() {
                        //Log.e("666","onCompleted");
                    }
                    @Override
                    public void onError(Throwable e) {
                        PLog.e("welcome", "onError");
                        welcomeImage.setImageResource(R.drawable.wall_picture);
                        animateImage();
                    }
                    @Override
                    public void onNext(String[] url) {
                        info.setText(url[0]);
                        Glide.with(BaseApplication.getInstance())
                                .load(url[1])
                                .fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.wall_picture)
                                .listener(listener)
                                .into(welcomeImage);
                    }
                });
    }

    /**
     * 动画
     */
    private void animateImage() {
        set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(welcomeImage, "scaleX", 1.5f, 1f)
                , ObjectAnimator.ofFloat(welcomeImage, "scaleY", 1.5f, 1f)
                , ObjectAnimator.ofFloat(welcomeImage, "alpha", 0.5f, 1f, 1));

        //image.startAnimation(scaleAnimation);
        set.setDuration(2000).start();
        //缩放动画监听
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                startWithPop( LoginFragment.newInstance());
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public static AnimWelcomeFragment newInstance() {
        Bundle args = new Bundle();
        AnimWelcomeFragment welcomeFragment = new AnimWelcomeFragment();
        welcomeFragment.setArguments(args);
        return welcomeFragment;
    }

    /**
     * 释放资源
     * 注意直接调用set.cancel会执行listener的end方法。所以需要先remove掉listener
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(subscription!=null&&!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        if(set!=null){
            set.removeAllListeners();
            set.cancel();
        }
    }
}
