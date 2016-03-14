package com.frankzheng.app.omelette.ui.pictures;

import com.frankzheng.app.omelette.model.PicturesModel;
import com.frankzheng.app.omelette.ui.mvp.IView;

/**
 * Created by zhengxiaoqiang on 16/3/14.
 */
public class PicturesPresenterImpl implements PicturesPresenter {
    PicturesView view;
    PicturesModel model = PicturesModel.getInstance();

    public PicturesPresenterImpl(PicturesView view) {
        this.view = view;
    }

    @Override
    public void onResume(IView view) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadItems() {

    }

    @Override
    public void loadMoreItems() {

    }
}
