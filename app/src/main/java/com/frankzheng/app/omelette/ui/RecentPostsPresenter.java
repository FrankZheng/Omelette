package com.frankzheng.app.omelette.ui;

import android.content.Context;

import com.frankzheng.app.omelette.bean.Post;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public interface RecentPostsPresenter {
    void loadRecentPosts();

    void loadMorePosts();

    void onStart(RecentPostsView view);

    void onResume(RecentPostsView view);

    void onPause();

    void onDestroy();

    void showPostDetail(Context context, Post post);

}
