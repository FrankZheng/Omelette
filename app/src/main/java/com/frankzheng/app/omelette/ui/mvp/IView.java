package com.frankzheng.app.omelette.ui.mvp;

import com.frankzheng.app.omelette.error.OMError;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface IView {
    void showProgress();

    void hideProgress();

    void showError(OMError error);
}
