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

    @GET("?oxwlxojflwblxbsapi=get_post&include=content")
    Call<GetPostResponse> getPost(@Query("id") int id);

    @GET("?oxwlxojflwblxbsapi=jandan.get_pic_comments")
    Call<GetPostResponse> getPictures(@Query("page") int page);

    @GET("?oxwlxojflwblxbsapi=jandan.get_ooxx_comments")
    Call<GetPostResponse> getHotGirls(@Query("page") int page);

    @GET("?oxwlxojflwblxbsapi=jandan.get_duan_comments")
    Call<GetPostResponse> getJokes(@Query("page") int page);

    @GET("?oxwlxojflwblxbsapi=jandan.get_video_comments")
    Call<GetPostResponse> getVideos(@Query("page") int page);





}
