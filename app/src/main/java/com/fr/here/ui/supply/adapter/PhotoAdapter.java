package com.fr.here.ui.supply.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import me.iwf.photopicker.R;
import java.io.File;
import java.util.ArrayList;
/**
 *
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

  private ArrayList<String> photoPaths = new ArrayList<String>();
  private LayoutInflater inflater;

  private Context mContext;


  public PhotoAdapter(Context mContext, ArrayList<String> photoPaths) {
    this.photoPaths = photoPaths;
    this.mContext = mContext;
    inflater = LayoutInflater.from(mContext);

  }


  @Override public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = inflater.inflate(R.layout.__picker_item_photo, parent, false);
    return new PhotoViewHolder(itemView);
  }


  @Override
  public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

    Uri uri = Uri.fromFile(new File(photoPaths.get(position)));

    Glide.with(mContext)
        .load(uri)
        .centerCrop()
        .thumbnail(0.1f)
        .placeholder(R.drawable.__picker_ic_photo_black_48dp)
        .error(R.drawable.__picker_ic_broken_image_black_48dp)
        .into(holder.ivPhoto);
  }


  @Override public int getItemCount() {
    return photoPaths.size();
  }


  public static class PhotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private View vSelected;
    public PhotoViewHolder(View itemView) {
      super(itemView);
      ivPhoto   = (ImageView) itemView.findViewById(R.id.iv_photo);
      vSelected = itemView.findViewById(R.id.v_selected);
      vSelected.setVisibility(View.GONE);
    }
  }

}
