package com.frankzheng.app.omelette.ui;

import android.util.Log;

import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;
import com.frankzheng.app.omelette.ui.mvp.IView;
import com.frankzheng.app.omelette.util.ThreadUtil;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhengxiaoqiang on 16/3/14.
 */
public abstract class BasePresenter<T> implements IPresenter<T> {
    private static final String TAG = BasePresenter.class.getSimpleName();

    protected IView<T> view;
    int currentPage = 1;

    protected BaseModel<T, ? extends APIResponse> model;

    public BasePresenter(IView<T> view) {
        this.view = view;
        onViewChanged(view);

        model = createModel();

        model.dataChanged.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (BasePresenter.this.view != null) {
                    onItemsChanged(model.getItems());
                }
            }
        });

        if (RecentPostsModel.getInstance().isEmpty()) {
            //load posts from cache.
            model.loadItemsFromLocalCache();
        } else {
            //has posts already, show them directly.
            onItemsChanged(model.getItems());
        }

        loadItems();
    }

    protected void onItemsChanged(final List<T> items) {
        Log.d(TAG, "items Changed, " + items.size());

        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.showItems(items);
                }
            }
        });
    }


    @Override
    public void onResume(IView<T> view) {
        this.view = view;
    }

    @Override
    public void onPause() {
        //view = null;
        //onViewChanged(view);
    }

    @Override
    public void onDestroy() {
        view = null;
        onViewChanged(null);
    }


    @Override
    public void loadItems() {
        model.loadItems(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadItemsObserver());
    }

    @Override
    public void loadMoreItems() {
        if (currentPage == 1) {
            //first load more
            currentPage = 2;
        }
        model.loadItems(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadMoreItemsObserver());
    }

    protected void onViewChanged(IView<T> view) {
        //do nothing by default
        //child class can cast to their own views
    }

    abstract protected BaseModel<T, ? extends APIResponse> createModel();

    private class LoadItemsObserver implements Observer<Void> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError " + e.getLocalizedMessage());
            if (view != null) {
                final String msg = String.format("Failed to load posts - %s", e.getMessage());
                view.showError(new OMError(msg));
                view.hideProgress();
            }
        }

        @Override
        public void onNext(Void avoid) {
            if (view != null) {
                view.hideProgress();
            }
        }
    }

    private class LoadMoreItemsObserver extends LoadItemsObserver {
        @Override
        public void onNext(Void aVoid) {
            synchronized (this) {
                currentPage++;
            }
        }
    }
}
