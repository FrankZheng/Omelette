package com.frankzheng.app.omelette.ui.pictures;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.frankzheng.app.omelette.bean.Picture;

/**
 * Created by zhengxiaoqiang on 16/3/14.
 */
public class PicturesAdapter extends ArrayAdapter<Picture> {

    public PicturesAdapter(Context context) {
        super(context, 0);
    }
}
