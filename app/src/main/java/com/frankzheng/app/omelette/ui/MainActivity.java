package com.frankzheng.app.omelette.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.task.OMError;

import java.util.List;


public class MainActivity extends AppCompatActivity implements RecentPostsView {
    private static final String TAG = "MainActivity";

    PostsAdapter postsAdapter;
    ListView lv_posts;
    View footer_load_more;
    SwipeRefreshLayout swipeRefreshLayout;

    RecentPostsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_posts = (ListView)findViewById(R.id.lv_posts);
        postsAdapter = new PostsAdapter(this);
        lv_posts.setAdapter(postsAdapter);

        footer_load_more = getLayoutInflater().inflate(R.layout.footer_load_more, null);
        lv_posts.addFooterView(footer_load_more);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        presenter = new RecentPostsPresenterImpl(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadRecentPosts();
            }
        });

        lv_posts.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int preLastItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Log.d(TAG, "onScrollStateChanged " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //Log.d(TAG, String.format("first:%d, visible count:%d, total: %d", firstVisibleItem, visibleItemCount, totalItemCount));
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLastItem != lastItem) {
                        preLastItem = lastItem;
                        //load more
                        presenter.loadMorePosts();
                    }
                }
            }
        });

        lv_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = postsAdapter.getItem(position);
                PostDetailActivity.start(MainActivity.this, post);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPosts(List<Post> posts) {
        postsAdapter.clear();
        postsAdapter.addAll(posts);
        postsAdapter.notifyDataSetChanged();
    }
}
