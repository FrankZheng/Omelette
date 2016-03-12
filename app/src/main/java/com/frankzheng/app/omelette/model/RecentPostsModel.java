package com.frankzheng.app.omelette.model;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

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
        super();
    }

    @Override
    protected String getStoreName() {
        return "RecentPosts";
    }

    @Override
    public boolean isEmpty() {
        return this.posts.size() == 0;
    }

    public void loadRecentPostsFromLocalCache() {
        RecentPostsResponse posts = store.get(getStoreKey(), RecentPostsResponse.class);
        if (posts != null) {
            updatePosts(posts);
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
                        store.put(getStoreKey(), recentPostsResponse, false);
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

    private void updatePosts(RecentPostsResponse recentPosts) {
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
