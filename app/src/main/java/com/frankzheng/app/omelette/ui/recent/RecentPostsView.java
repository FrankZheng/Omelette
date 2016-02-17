package com.frankzheng.app.omelette.ui.recent;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.ui.mvp.IView;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public interface RecentPostsView extends IView {
    void showPosts(List<Post> posts);
}
