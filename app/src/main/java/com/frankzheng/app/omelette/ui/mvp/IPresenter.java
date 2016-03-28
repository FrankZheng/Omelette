package com.frankzheng.app.omelette.ui.mvp;

import android.content.Context;

/**
 * Created by zhengxiaoqiang on 16/2/17.
 */
public interface IPresenter<T> {

    void onResume(IView<T> view);

    void onPause();

    void onDestroy();

    void loadItems();

    void loadMoreItems();

    void showItemDetail(Context context, T item);
}
