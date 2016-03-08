package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.util.Log;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;
import com.frankzheng.app.omelette.task.OMError;
import com.frankzheng.app.omelette.ui.PostDetailActivity;
import com.frankzheng.app.omelette.ui.mvp.IView;
import com.frankzheng.app.omelette.util.ThreadUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public class RecentPostsPresenterImpl implements RecentPostsPresenter {
    private static final String TAG = RecentPostsPresenterImpl.class.getSimpleName();
    RecentPostsView view;
    RecentPostsModel model = RecentPostsModel.getInstance();

    int currentPage = 1;

    public RecentPostsPresenterImpl(final RecentPostsView view) {
        this.view = view;

        RecentPostsModel.getInstance().postsChanged.subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (view != null) {
                    onPostsChanged();
                }
            }
        });

        if (RecentPostsModel.getInstance().isEmpty()) {
            //load posts from cache.
            model.loadRecentPostsFromLocalCache();
        } else {
            //has posts already, show them directly.
            onPostsChanged();
        }

        loadRecentPosts();
    }


    @Override
    public void loadRecentPosts() {
        Observable<RecentPostsResponse> observable = model.loadRecentPosts(1);
        if (observable != null) {
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RecentPostsObserver());
        }
    }

    @Override
    public void loadMorePosts() {
        if (currentPage == 1) {
            //first load more
            currentPage = 2;
        }

        Observable<RecentPostsResponse> observable = model.loadRecentPosts(currentPage);
        if (observable != null) {
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LoadMorePostsObserver());
        }
    }

    @Override
    public void onResume(IView view) {
        this.view = (RecentPostsView) view;
        //TODO: need do things when resume
    }

    @Override
    public void onPause() {
        //view = null;
        //TODO: need do things when paused
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void showPostDetail(Context context, Post post) {
        PostDetailActivity.start(context, post);
    }

    private void onPostsChanged() {
        final List<Post> posts = model.getPosts();
        Log.d(TAG, "posts Changed, " + posts.size());
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return -lhs.date.compareTo(rhs.date);
            }
        });

        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.showPosts(posts);
                }
            }
        });
    }

    private class RecentPostsObserver implements Observer<RecentPostsResponse> {

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
        public void onNext(RecentPostsResponse recentPostsResponse) {
            if (view != null) {
                view.hideProgress();
            }
        }
    }

    private class LoadMorePostsObserver extends RecentPostsObserver {
        @Override
        public void onNext(RecentPostsResponse recentPostsResponse) {
            synchronized (this) {
                currentPage++;
            }
        }
    }

}
