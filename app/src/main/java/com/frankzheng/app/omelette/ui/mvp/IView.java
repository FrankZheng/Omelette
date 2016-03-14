package com.frankzheng.app.omelette.ui.mvp;

import com.frankzheng.app.omelette.error.OMError;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface IView<T> {
    void showProgress();

    void hideProgress();

    void showError(OMError error);

    void showItems(List<T> items);

}
