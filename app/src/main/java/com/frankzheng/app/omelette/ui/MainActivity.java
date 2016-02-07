package com.frankzheng.app.omelette.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.task.Task;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private int page = 1;

    PostsAdapter postsAdapter;
    ListView lv_posts;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        lv_posts = (ListView)findViewById(R.id.lv_posts);
        postsAdapter = new PostsAdapter(this);
        lv_posts.setAdapter(postsAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Task task = RecentPostsModel.getInstance().loadRecentPosts();
                if (task != null) {
                    task.addTaskListener(new Task.TaskListener() {
                        @Override
                        public void onSuccess(Task task, Object data) {
                            swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });

                        }

                        @Override
                        public void onError(int code) {
                            swipeRefreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    });
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


        RecentPostsModel.getInstance().setListener(new RecentPostsModel.Listener() {
            @Override
            public void postsChanged() {
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
        });

        /*
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = RecentPostsModel.getInstance().loadRecentPosts(page++);
                if (task != null) {
                    task.addTaskListener(new Task.TaskListener<RecentPostsResponse>() {
                        @Override
                        public void onSuccess(Task task, RecentPostsResponse data) {
                            Log.i(TAG, "onSuccess");
                        }

                        @Override
                        public void onError(int code) {
                            Log.i(TAG, "onError: " + code);
                        }
                    });
                }
            }
        });
        */

        /*
        findViewById(R.id.btn_fresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = RecentPostsModel.getInstance().loadRecentPosts(1);
                if (task != null) {
                    task.addTaskListener(new Task.TaskListener<RecentPostsResponse>() {
                        @Override
                        public void onSuccess(Task task, RecentPostsResponse data) {
                            Log.i(TAG, "onSuccess");
                        }

                        @Override
                        public void onError(int code) {
                            Log.i(TAG, "onError: " + code);
                        }
                    });
                }
            }
        });
        */

        RecentPostsModel.getInstance().loadRecentPostsFromLocalCache();



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
