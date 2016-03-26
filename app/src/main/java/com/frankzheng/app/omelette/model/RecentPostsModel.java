package com.frankzheng.app.omelette.model;


import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import rx.Observable;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class RecentPostsModel extends BaseModel<Post, RecentPostsResponse> {
    private static final String TAG = "RecentPostsModel";

    private static volatile RecentPostsModel instance;

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
    protected Observable<RecentPostsResponse> fetchData(int page) {
        return Network.getInstance().getRecentPosts(page);
    }

    @Override
    protected void updateItems(RecentPostsResponse recentPosts) {
        synchronized (this.items) {
            boolean updated = false;
            for (RecentPostsResponse.InnerPost pt : recentPosts.posts) {
                String id = String.valueOf(pt.id);
                if (this.items.get(id) == null) {
                    Post post = new Post(pt);
                    this.items.put(id, post);
                    updated = true;
                }
            }
            if (updated) {
                dataChanged.onNext(null);
            }
        }
    }

}
