package com.frankzheng.app.omelette.ui.pictures;

import android.content.Context;

import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface PicturesPresenter extends IPresenter<Picture> {
    void showPictureDetail(Context context, Picture picture);

}
