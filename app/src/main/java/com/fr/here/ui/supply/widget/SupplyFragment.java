package com.fr.here.ui.supply.widget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fr.here.R;
import com.fr.here.app.C;
import com.fr.here.base.BaseMainFragment;
import com.fr.here.base.BaseResponse;
import com.fr.here.net.RetrofitSingleton;
import com.fr.here.ui.supply.adapter.PhotoAdapter;
import com.fr.here.ui.supply.adapter.RecyclerItemClickListener;
import com.fr.here.util.FileInfoUtils;
import com.fr.here.util.PLog;
import com.gc.materialdesign.views.ButtonRectangle;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 用于测试文件上传功能
 * Created by shli on 2016-08-25.
 */
public class SupplyFragment extends BaseMainFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.update_1)
    ButtonRectangle update1;
    @Bind(R.id.update_2)
    ButtonRectangle update2;
    @Bind(R.id.update_3)
    ButtonRectangle update3;
    @Bind(R.id.update_4)
    ButtonRectangle update4;
    @Bind(R.id.iv_1)
    ImageView iv1;
    @Bind(R.id.update_5)
    ButtonRectangle update5;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;

    ArrayList<String> selectedPhotos = new ArrayList<>();


    public static SupplyFragment newInstance() {
        return new SupplyFragment();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_supply_content;
    }

    @Override
    public void initView() {
        mToolbar.setTitle(getResources().getString(R.string.supply));
        initToolbarNav(mToolbar, true);

        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update1();
            }
        });

        update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update2();
            }
        });
        update3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        update4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(_mActivity,SupplyFragment.this);
            }
        });

        update5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setGridColumnCount(3)
                        .start(_mActivity,SupplyFragment.this);
            }
        });

        photoAdapter = new PhotoAdapter(_mActivity, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(_mActivity, new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .setCurrentItem(position)
                        .start(_mActivity,SupplyFragment.this);
            }
        }));


    }

    private void update1() {
        //找到项目日志的位置
        File file = new File(PLog.PATH, PLog.PLOG_FILE_NAME);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("txt"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

        String descriptionString = "This is a description";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);


        RetrofitSingleton.apiService
                .upload1(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseResponse<String>>() {
                    @Override
                    public void call(BaseResponse<String> s) {
                        PLog.e(s.model);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        PLog.e("发生错误");
                    }
                });

    }


    private void update2() {
        //找到项目日志的位置
        File file = new File(PLog.PATH, PLog.PLOG_FILE_NAME);
        File file1 = new File(C.APPIMAGE, "11.png");
        RequestBody pictureNameBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), "This is a description");

        RequestBody requestFile = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), file);
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), file1);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("txt\"; filename=\"" + file.getName() + "", requestFile);
        params.put("png\"; filename=\"" + file1.getName() + "", requestFile1);

        RetrofitSingleton.apiService
                .upload2(pictureNameBody,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseResponse<String>>() {
                    @Override
                    public void call(BaseResponse<String> s) {
                        PLog.e(s.model);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        PLog.e(throwable.getMessage());
                    }
                });

    }
    @Override
    public void initPresenter() {

    }


     public final static int FILE_SELECT_CODE = 151;
    private void showFileChooser() {
        Intent intent;
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(_mActivity, "Please install a File Manager.",  Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)  {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    PLog.e(FileInfoUtils.getPath(_mActivity,uri));
                    //找到项目日志的位置
                    File file = new File(FileInfoUtils.getPath(_mActivity,uri));
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

                    String descriptionString = "This is a description";
                    RequestBody description =
                            RequestBody.create(
                                    MediaType.parse("multipart/form-data"), descriptionString);
                    RetrofitSingleton.apiService
                            .upload1(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<BaseResponse<String>>() {
                                @Override
                                public void call(BaseResponse<String> s) {
                                    PLog.e(s.model);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    PLog.e("发生错误");
                                }
                            });
                }
                break;
            case Crop.REQUEST_PICK:
                if(resultCode == RESULT_OK){
                    beginCrop(data.getData());  //开始剪切
                }
                break;
            case Crop.REQUEST_CROP:
                handleCrop(resultCode, data);   //剪切成功，显示
                break;

            case PhotoPicker.REQUEST_CODE:
            case PhotoPreview.REQUEST_CODE:
                List<String> photos = null;
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }
                selectedPhotos.clear();

                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                photoAdapter.notifyDataSetChanged();
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(_mActivity.getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).asSquare().start(_mActivity,this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String imagePath = uri.toString();
            PLog.e(imagePath);
            Glide.with(getContext())
                    .load(imagePath)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv1);
        } else if (resultCode == Crop.RESULT_ERROR) {
            showToast("剪切发生错误");
        }
    }

}
