package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.ui.BaseFragment;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;

import butterknife.ButterKnife;


/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class RecentPostsFragment extends BaseFragment<Post> implements RecentPostsView {
    private static final String TAG = RecentPostsFragment.class.getSimpleName();

    private RecentPostsPresenter recentPostsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = itemsAdapter.getItem(position);
                recentPostsPresenter.showPostDetail(getContext(), post);
            }
        });

        return rootView;
    }

    @Override
    protected ArrayAdapter<Post> createItemsAdapter(Context context) {
        return new PostsAdapter(context);
    }

    @Override
    protected IPresenter createPresenter() {
        recentPostsPresenter = new RecentPostsPresenterImpl(this);
        return recentPostsPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recent_posts;
    }

}
