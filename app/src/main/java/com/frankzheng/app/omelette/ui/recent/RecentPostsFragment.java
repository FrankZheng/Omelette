package com.frankzheng.app.omelette.ui.recent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.ui.view.ObservableListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class RecentPostsFragment extends Fragment implements RecentPostsView {
    private static final String TAG = "RecentPostsFragment";

    @Bind(R.id.lv_posts)
    ObservableListView lv_posts;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    View footer_load_more;
    PostsAdapter postsAdapter;
    RecentPostsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_posts, container, false);
        ButterKnife.bind(this, rootView);

        postsAdapter = new PostsAdapter(getContext());
        lv_posts.setAdapter(postsAdapter);

        footer_load_more = inflater.inflate(R.layout.footer_load_more, null);
        lv_posts.addFooterView(footer_load_more);

        presenter = new RecentPostsPresenterImpl(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadRecentPosts();
            }
        });

        lv_posts.scrolledToBottom().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.d(TAG, "scrolled to bottom, load more posts");
                presenter.loadMorePosts();
            }
        });

        lv_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = postsAdapter.getItem(position);
                presenter.showPostDetail(getContext(), post);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress");
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(OMError error) {
        if (getContext() != null) {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPosts(List<Post> posts) {
        postsAdapter.clear();
        postsAdapter.addAll(posts);
        postsAdapter.notifyDataSetChanged();
    }
}
