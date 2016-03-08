package com.frankzheng.app.omelette.net.response;

import java.util.ArrayList;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class RecentPostsResponse extends APIResponse {

    public static class InnerPost {
        public int id;
        public String url;
        public String title;
        public String date;

        public ArrayList<Tag> tags;
        public Author author;
        public long comment_count;
        public CustomFields custom_fields;
    }

    public static class Tag {
        public int id;
        public String slug;
        public String title;
        public String description;
        public long post_count;
    }

    public static class Author {
        public int id;
        public String slug;
        public String name;
        public String first_name;
        public String last_name;
        public String nickname;
        public String url;
        public String description;
    }

    public class CustomFields {
        public ArrayList<String> thumb_c;
    }

    public int count;
    public int count_total;
    public int pages;
    public ArrayList<InnerPost> posts;



}
