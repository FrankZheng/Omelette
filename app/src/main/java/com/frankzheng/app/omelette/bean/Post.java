package com.frankzheng.app.omelette.bean;

import android.text.TextUtils;

import com.frankzheng.app.omelette.net.response.RecentPostsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class Post {
    public int id;
    public String title;
    public String author;
    public String tag;
    public String thumbURL;
    public String content;
    public Date date;

    private static final String DATE_FMT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public Post(RecentPostsResponse.InnerPost post) {
        this.id = post.id;
        this.title = post.title;
        this.author = post.author.name;
        this.tag = (post.tags != null && post.tags.size() > 0) ? post.tags.get(0).title : null;
        this.thumbURL = (post.custom_fields != null && post.custom_fields.thumb_c != null && post.custom_fields.thumb_c.size() > 0)
                ? post.custom_fields.thumb_c.get(0) : null;

        if (!TextUtils.isEmpty(post.date)) {
            SimpleDateFormat fmt = new SimpleDateFormat(DATE_FMT_PATTERN);
            try {
                this.date = fmt.parse(post.date);
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public String getFormattedDate() {
        if(date != null) {
            SimpleDateFormat fmt = new SimpleDateFormat(DATE_FMT_PATTERN);
            return fmt.format(date);
        }
        return "";
    }

}
