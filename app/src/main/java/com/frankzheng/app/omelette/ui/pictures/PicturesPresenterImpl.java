package com.frankzheng.app.omelette.ui.pictures;

import android.content.Context;

import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.PicturesModel;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.ui.BasePresenter;
import com.frankzheng.app.omelette.ui.mvp.IView;


/**
 * Created by zhengxiaoqiang on 16/3/14.
 */
public class PicturesPresenterImpl extends BasePresenter<Picture> implements PicturesPresenter {
    PicturesView picturesView;


    public PicturesPresenterImpl(PicturesView view) {
        super(view);
    }


    @Override
    protected BaseModel<Picture, ? extends APIResponse> createModel() {
        return PicturesModel.getInstance();
    }

    @Override
    protected void onViewChanged(IView<Picture> view) {
        super.onViewChanged(view);
        picturesView = (PicturesView) view;
    }

    @Override
    public void showPictureDetail(Context context, Picture picture) {
        PictureDetailActivity.start(context, picture);
    }
}
