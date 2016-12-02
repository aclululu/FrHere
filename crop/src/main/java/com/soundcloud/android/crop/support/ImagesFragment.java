/*
 * Copyright 2013 Thomas Hoffmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soundcloud.android.crop.support;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


import com.soundcloud.android.crop.R;

import java.util.ArrayList;
import java.util.List;


public class ImagesFragment extends Fragment {

	@Override
	public View onCreateView(final LayoutInflater inflater,
			final ViewGroup container, final Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.gallery, null);

		Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
		toolbar.setTitle("图片");
		toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		Cursor cur = getActivity().getContentResolver()
				.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						new String[] { MediaStore.Images.Media.DATA,
								MediaStore.Images.Media.DISPLAY_NAME,
								MediaStore.Images.Media.DATE_TAKEN,
								MediaStore.Images.Media.SIZE,
								MediaStore.Images.Media._ID},
						MediaStore.Images.Media.BUCKET_ID + " = ?",
						new String[] { String.valueOf(getArguments().getInt(
								"bucket")) },
						MediaStore.Images.Media.DATE_MODIFIED + " DESC");

		final List<GridItem> images = new ArrayList<GridItem>(cur.getCount());

		if (cur != null) {
			if (cur.moveToFirst()) {
				while (!cur.isAfterLast()) {
                    Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + cur.getString(4));
					images.add(new GridItem(cur.getString(1), cur.getString(0),cur.getString(2),cur.getLong(3),uri));
					cur.moveToNext();
				}
			}
			cur.close();
		}

		GridView grid = (GridView) v.findViewById(R.id.grid);
		grid.setAdapter(new GalleryAdapter(getActivity(), images));
		grid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((SelectPictureActivity) getActivity()).imageSelected(
                        images.get(position).path,
                        images.get(position).imageTaken,
                        images.get(position).imageSize,
                        images.get(position).uri);
			}
		});
		return v;
	}

}
