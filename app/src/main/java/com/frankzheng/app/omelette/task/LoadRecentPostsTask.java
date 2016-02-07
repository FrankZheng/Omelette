package com.frankzheng.app.omelette.task;

import android.util.Log;

import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.JandanService;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class LoadRecentPostsTask extends Task<RecentPostsResponse> implements Runnable {
    private static final String TAG = "LoadRecentPostsTask";
    private JandanService service = Network.getInstance().getJandanService();
    private final int page;

    public LoadRecentPostsTask(int page) {
        if (page <= 0) {
            throw new IllegalArgumentException("Page should larger than or equal to  1");
        }
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void run() {
        Call<RecentPostsResponse> call = service.getRecentPosts(page);
        try {
            retrofit2.Response<RecentPostsResponse> response = call.execute();
            Log.i(TAG, String.format("status code: %d", response.code()));
            if (response.code() == 200) {
                RecentPostsResponse posts = response.body();
                Log.i(TAG, String.format("%s, %d， %d", posts.status, posts.count, posts.posts.size()));
                RecentPostsModel.getInstance().updatePosts(posts);
                onSuccess(posts);
            }
        } catch (IOException e) {
            e.printStackTrace();
            onError(-1);
        }
    }
}
