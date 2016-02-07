package com.frankzheng.app.omelette.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.task.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int page = 1;

    PostsAdapter postsAdapter;
    ListView lv_posts;
    SwipeRefreshLayout swipeRefreshLayout;

    boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_posts = (ListView)findViewById(R.id.lv_posts);
        postsAdapter = new PostsAdapter(this);
        lv_posts.setAdapter(postsAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPosts();
            }
        });

        lv_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = postsAdapter.getItem(position);
                PostDetailActivity.start(MainActivity.this, post);
            }
        });

        RecentPostsModel.getInstance().setListener(new RecentPostsModel.Listener() {
            @Override
            public void postsChanged() {
                showPosts();
            }
        });

        if (RecentPostsModel.getInstance().isEmpty()) {
            //load posts from cache.
            RecentPostsModel.getInstance().loadRecentPostsFromLocalCache();
        } else {
            //has posts already, show them directly.
            showPosts();
        }

        //load posts
        swipeRefreshLayout.setRefreshing(true);
        loadPosts();
    }

    private void loadPosts() {
        Task task = RecentPostsModel.getInstance().loadRecentPosts();
        if (task != null) {
            task.addTaskListener(new Task.TaskListener() {
                @Override
                public void onComplete(Task task) {
                    super.onComplete(task);
                    if (!paused) {
                        lv_posts.post(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });

                    }
                }
            });
        }
    }

    private void showPosts() {
        if (paused) {
            Log.d(TAG, "activity paused, won't show posts");
            return;
        }
        final List<Post> posts = RecentPostsModel.getInstance().getPosts();
        Log.i(TAG, "posts Changed, " + posts.size());
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return -lhs.date.compareTo(rhs.date);
            }
        });

        lv_posts.post(new Runnable() {
            @Override
            public void run() {
                //refresh data
                postsAdapter.clear();
                postsAdapter.addAll(posts);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
