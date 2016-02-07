package com.frankzheng.app.omelette.net;

import com.frankzheng.app.omelette.net.response.GetPostResponse;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public interface JandanService {
    @GET("?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1")
    Call<RecentPostsResponse> getRecentPosts(@Query("page") int page);

    @GET("http://i.jandan.net/?oxwlxojflwblxbsapi=get_post&include=content")
    Call<GetPostResponse> getPost(@Query("id") int id);
}
