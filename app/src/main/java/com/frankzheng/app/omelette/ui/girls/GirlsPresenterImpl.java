package com.frankzheng.app.omelette.ui.girls;

import android.content.Context;

import com.frankzheng.app.omelette.bean.Girl;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.GirlsModel;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.ui.BasePresenter;

/**
 * Created by zhengxiaoqiang on 16/3/26.
 */
public class GirlsPresenterImpl extends BasePresenter<Girl> implements GirlsPresenter {

    public GirlsPresenterImpl(GirlsView view) {
        super(view);
    }

    @Override
    protected BaseModel<Girl, ? extends APIResponse> createModel() {
        return GirlsModel.getInstance();
    }

    @Override
    public void showItemDetail(Context context, Girl girl) {
        GirlsDetailActivity.start(context, girl);
    }
}
