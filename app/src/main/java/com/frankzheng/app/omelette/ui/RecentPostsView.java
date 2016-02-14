package com.frankzheng.app.omelette.ui;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.task.OMError;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public interface RecentPostsView {
    void showProgress();

    void hideProgress();

    void showPosts(List<Post> posts);

    void showError(OMError error);
}
