package com.frankzheng.app.omelette.task;

import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.JandanService;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import retrofit2.Call;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class LoadRecentPostsTask extends Task<RecentPostsResponse> {
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

    @Override
    protected RecentPostsResponse doInBackground() throws Throwable {
        Call<RecentPostsResponse> call = service.getRecentPosts(page);

        retrofit2.Response<RecentPostsResponse> response = call.execute();
        if (response.isSuccess()) {
            RecentPostsResponse posts = response.body();
            if ("ok".equals(posts.status)) {
                //success
                RecentPostsModel.getInstance().updatePosts(posts);
                return posts;
            } else {
                //status not ok.
                throw OMError.createWithStatus(posts.status);
            }
        } else {
            throw new OMError(response.code(), response.message());
        }
    }
}
