package com.frankzheng.app.omelette.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.frankzheng.app.omelette.MainApplication;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;
import com.frankzheng.app.omelette.task.OMError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class RecentPostsModel extends BaseModel<Post> {
    private static final String TAG = "RecentPostsModel";

    private static final String POSTS_LOCAL_CACHE = "PostsLocalCache";
    private static final String POSTS_KEY = "Posts";
    SharedPreferences sp;

    private static RecentPostsModel instance;
    private final SparseArray<Post> posts = new SparseArray<>();
    private SparseBooleanArray tasksStatus = new SparseBooleanArray();

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
    }

    @Override
    public boolean isEmpty() {
        return this.posts.size() == 0;
    }

    public void loadRecentPostsFromLocalCache() {
        String json = sp.getString(POSTS_KEY, null);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
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

    public Observable<Void> loadRecentPosts(final int page) {
        if (!tasksStatus.get(page, false)) {
            tasksStatus.put(page, true);
            Observable<RecentPostsResponse> observable = Network.getInstance().getRecentPosts(page);
            observable.subscribe(new Observer<RecentPostsResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, "onError: " + e.getMessage());
                    tasksStatus.delete(page);
                }

                @Override
                public void onNext(RecentPostsResponse recentPostsResponse) {
                    Log.i(TAG, "onSuccess");
                    tasksStatus.delete(page);

                    if (page == 1) {
                        //cache the posts on first page
                        Gson gson = new Gson();
                        sp.edit().putString(POSTS_KEY, gson.toJson(recentPostsResponse)).apply();
                    }

                    updatePosts(recentPostsResponse);
                }
            });

            return observable.map(new Func1<RecentPostsResponse, Void>() {
                @Override
                public Void call(RecentPostsResponse recentPostsResponse) {
                    return null;
                }
            });
        }

        return Observable.error(new OMError("In the loading"));
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
                dataChanged.onNext(null);
            }
        }
    }

    @Override
    public Post getItemById(String id) {
        return getPostById(Integer.valueOf(id));
    }

    @Override
    public List<Post> getItems() {
        return getPosts();
    }

    @Override
    public void loadItemsFromLocalCache() {
        loadRecentPostsFromLocalCache();
    }

    @Override
    public Observable<Void> loadItems(int page) {
        return loadRecentPosts(page);
    }
}
