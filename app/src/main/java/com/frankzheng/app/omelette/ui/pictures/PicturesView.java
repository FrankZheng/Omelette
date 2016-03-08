package com.frankzheng.app.omelette.ui.pictures;

import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.ui.mvp.IView;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface PicturesView extends IView {
    void showPictures(List<Picture> pictures);

}
