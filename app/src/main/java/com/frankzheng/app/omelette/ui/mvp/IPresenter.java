package com.frankzheng.app.omelette.ui.mvp;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface IPresenter<T> {

    void onResume(IView<T> view);

    void onPause();

    void onDestroy();

    void loadItems();

    void loadMoreItems();
}
