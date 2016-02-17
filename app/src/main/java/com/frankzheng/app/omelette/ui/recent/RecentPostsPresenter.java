package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public interface RecentPostsPresenter extends IPresenter {
    void loadRecentPosts();
    void loadMorePosts();
    void showPostDetail(Context context, Post post);

}
