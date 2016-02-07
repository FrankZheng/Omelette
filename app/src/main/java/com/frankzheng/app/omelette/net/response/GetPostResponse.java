package com.frankzheng.app.omelette.net.response;

/**
 * Created by zhengxiaoqiang on 16/2/6.
 */
public class GetPostResponse {
    public static class InnerPost {
        public int id;
        public String content;
    }
    public String status;
    public InnerPost post;
    public String previous_url;

}
