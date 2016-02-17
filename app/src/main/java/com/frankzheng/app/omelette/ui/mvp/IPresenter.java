package com.frankzheng.app.omelette.ui.mvp;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface IPresenter {

    void onResume(IView view);

    void onPause();

    void onDestroy();
}
