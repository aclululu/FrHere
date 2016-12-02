package com.soundcloud.android.crop.support;

import android.net.Uri;

class GridItem {
    final String name;
    final String path;
    final String imageTaken;
    final long imageSize;
    final Uri uri;
    public GridItem(final String n, final String p, final String imageTaken, final long imageSize, final Uri uri) {
        name = n;
        path = p;
        this.imageTaken = imageTaken;
        this.imageSize = imageSize;
        this.uri = uri;
    }
}
