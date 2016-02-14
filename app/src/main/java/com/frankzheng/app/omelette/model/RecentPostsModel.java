package com.frankzheng.app.omelette.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.frankzheng.app.omelette.MainApplication;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;
import com.frankzheng.app.omelette.task.LoadRecentPostsTask;
import com.frankzheng.app.omelette.task.OMError;
import com.frankzheng.app.omelette.task.Task;
import com.frankzheng.app.omelette.task.TaskManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class RecentPostsModel {
    private static final String TAG = "RecentPostsModel";

    private static final String POSTS_LOCAL_CACHE = "PostsLocalCache";
    private static final String POSTS_KEY = "Posts";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public interface Listener {
        void postsChanged();
    }

    private static RecentPostsModel instance;
    private final SparseArray<Post> posts = new SparseArray<>();
    private SparseBooleanArray tasksStatus = new SparseBooleanArray();
    private Listener listener;

    public static RecentPostsModel getInstance() {
        if (instance == null) {
            synchronized (RecentPostsResponse.class) {
                if (instance == null) {
                    instance = new RecentPostsModel();
                }
            }
        }
        return instance;
    }

    private RecentPostsModel() {
        //load posts from local cache
        sp = MainApplication.context.getSharedPreferences(POSTS_LOCAL_CACHE, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    private Task startLoadRecentPostsTask(final int page) {
        if (!tasksStatus.get(page, false)) {
            final LoadRecentPostsTask task = new LoadRecentPostsTask(page);
            task.addTaskListener(new Task.TaskListener<RecentPostsResponse>() {
                @Override
                public void onSuccess(Task task, RecentPostsResponse data) {
                    Log.i(TAG, "onSuccess");
                    tasksStatus.delete(page);

                    if (page == 1) {
                        //cache the posts on first page
                        Gson gson = new Gson();
                        editor.putString(POSTS_KEY, gson.toJson(data));
                        editor.commit();
                    }

                    updatePosts(data);
                }

                @Override
                public void onError(OMError error) {
                    Log.i(TAG, "onError: " + error.getMessage());
                    tasksStatus.delete(page);
                }
            });
            tasksStatus.put(page, true);
            TaskManager.getInstance().execute(task);
            return task;
        }
        return null;
    }

    public boolean isEmpty() {
        return this.posts.size() == 0;
    }


    public void loadRecentPostsFromLocalCache() {
        String json = sp.getString(POSTS_KEY, null);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            //Type type = new TypeToken<ArrayList<Post>>(){}.getType();
            try {
                RecentPostsResponse posts = gson.fromJson(json, RecentPostsResponse.class);
                if (posts != null) {
                    updatePosts(posts);
                }
            } catch(JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

    }

    public Task loadRecentPosts() {
        //load first page
        return startLoadRecentPostsTask(1);
    }

    public Task loadRecentPosts(int page) {
        //load with page number
        return startLoadRecentPostsTask(page);
    }

    public Post getPostById(int id) {
        return this.posts.get(id);
    }

    public List<Post> getPosts() {
        synchronized (this.posts) {
            List<Post> posts = new ArrayList<>(this.posts.size());
            for (int i = 0; i < this.posts.size(); i++) {
                int key = this.posts.keyAt(i);
                Post post = this.posts.get(key);
                posts.add(post);
            }
            return posts;
        }
    }

    public void updatePosts(List<Post> posts) {
        synchronized (this.posts) {
            boolean updated = false;
            for (Post post : posts) {
                if (this.posts.get(post.id) == null) {
                    this.posts.put(post.id, post);
                    updated = true;
                }
            }
            if (updated) {
                if (listener != null) {
                    listener.postsChanged();
                }
            }
        }
    }

    public void updatePosts(RecentPostsResponse recentPosts) {
        synchronized (this.posts) {
            boolean updated = false;
            for (RecentPostsResponse.InnerPost pt : recentPosts.posts) {
                if (this.posts.get(pt.id) == null) {
                    Post post = new Post(pt);
                    this.posts.put(post.id, post);
                    updated = true;
                }
            }
            if (updated) {
                if (listener != null) {
                    listener.postsChanged();
                }
            }
        }
    }





}
