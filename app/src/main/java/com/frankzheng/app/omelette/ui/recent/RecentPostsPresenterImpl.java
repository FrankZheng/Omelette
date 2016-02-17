package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.util.Log;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;
import com.frankzheng.app.omelette.task.OMError;
import com.frankzheng.app.omelette.task.Task;
import com.frankzheng.app.omelette.ui.PostDetailActivity;
import com.frankzheng.app.omelette.ui.mvp.IView;
import com.frankzheng.app.omelette.util.ThreadUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public class RecentPostsPresenterImpl implements RecentPostsPresenter {
    private static final String TAG = RecentPostsPresenterImpl.class.getSimpleName();
    RecentPostsView view;
    RecentPostsModel model = RecentPostsModel.getInstance();

    int currentPage = 1;

    RecentPostsModel.Listener recentPostsModelListener = new RecentPostsModel.Listener() {
        @Override
        public void postsChanged() {
            if (view != null) {
                onPostsChanged();
            }
        }
    };

    RecentPostsTaskListener recentPostsTaskListener = new RecentPostsTaskListener();
    LoadMorePostsTaskListener loadMorePostsTaskListener = new LoadMorePostsTaskListener();

    public RecentPostsPresenterImpl(RecentPostsView view) {
        this.view = view;

        RecentPostsModel.getInstance().setListener(recentPostsModelListener);

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
        Task task = model.loadRecentPosts(1);
        if (task != null) {
            view.showProgress();
            task.addTaskListener(recentPostsTaskListener);
        } else {
            view.hideProgress();
        }
    }

    @Override
    public void loadMorePosts() {
        if (currentPage == 1) {
            //first load more
            currentPage = 2;
        }
        Task task = model.loadRecentPosts(currentPage);
        if (task != null) {
            task.addTaskListener(loadMorePostsTaskListener);
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

    private class RecentPostsTaskListener extends Task.TaskListener<RecentPostsResponse> {

        @Override
        public void onError(OMError error) {
            super.onError(error);
            if (view != null) {
                final String msg = String.format("Failed to load posts - %s", error.getMessage());
                ThreadUtil.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showError(new OMError(msg));
                    }
                });

            }
        }

        @Override
        public void onComplete(Task task) {
            super.onComplete(task);

            if (view != null) {
                ThreadUtil.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.hideProgress();
                        }
                    }
                });
            }
        }
    }

    private class LoadMorePostsTaskListener extends RecentPostsTaskListener {

        @Override
        public void onSuccess(Task task, RecentPostsResponse data) {
            super.onSuccess(task, data);
            synchronized (this) {
                currentPage++;
            }
        }
    }
}
