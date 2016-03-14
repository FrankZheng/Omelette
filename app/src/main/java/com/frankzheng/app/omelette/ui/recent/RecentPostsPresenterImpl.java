package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.util.Log;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.ui.BasePresenter;
import com.frankzheng.app.omelette.ui.mvp.IView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/12.
 */
public class RecentPostsPresenterImpl extends BasePresenter<Post> implements RecentPostsPresenter {
    private static final String TAG = RecentPostsPresenterImpl.class.getSimpleName();

    RecentPostsView recentPostsView;

    public RecentPostsPresenterImpl(final RecentPostsView view) {
        super(view);
    }

    @Override
    protected void onViewChanged(IView view) {
        recentPostsView = (RecentPostsView) view;
    }

    @Override
    protected BaseModel<Post, ? extends APIResponse> createModel() {
        return RecentPostsModel.getInstance();
    }

    @Override
    public void showPostDetail(Context context, Post post) {
        PostDetailActivity.start(context, post);
    }

    protected void onItemsChanged(final List<Post> items) {
        Log.d(TAG, "items Changed, " + items.size());
        //sort by own rules
        Collections.sort(items, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return -lhs.date.compareTo(rhs.date);
            }
        });

        super.onItemsChanged(items);
    }

}
