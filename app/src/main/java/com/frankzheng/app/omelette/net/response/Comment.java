package com.frankzheng.app.omelette.net.response;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class Comment {
    public static class InnerUser {
        public String id;
        public String name;
        public String link;
    }

    public static class InnerVideo {
        public String id;
        public String title;
        public String description;
        public String thumbnail;
        public String thumbnail_v2;
        public String duration;
        public String comment_count;
        public String favorite_count;
        public String up_count;
        public String down_count;
        public String published;
        public String copyright_type;
        public String public_type;
        public String state;
        public String streamtypes;
        public String category;
        public int view_count;
        public int paid;
        public String link;
        public String player;
        //public List<String> operation_limit;
        public InnerUser user;
        public String video_source;
    }

    public String comment_ID;
    public String comment_post_ID;
    public String comment_author;
    public String comment_author_email;
    public String comment_author_url;
    public String comment_author_IP;
    public String comment_date;
    public String comment_date_gmt;
    public String comment_content;
    public String comment_karma;
    public String comment_approved;
    public String comment_agent;
    public String comment_type;
    public String comment_parent;
    public String user_id;
    public String comment_subscribe;
    public String comment_reply_ID;
    public String vote_positive;
    public String vote_negative;
    public String vote_ip_pool;
    public String text_content;
    public List<InnerVideo> videos;
    public List<String> pics;
}
