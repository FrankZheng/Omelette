package com.frankzheng.app.omelette.bean;

import android.text.TextUtils;

import com.frankzheng.app.omelette.net.response.Comment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhengxiaoqiang on 16/3/26.
 */
public class Girl {
    public String id;
    public String author;
    public String picURL;
    public String title;
    public int positiveVote;
    public int negativeVote;
    public int comments;
    public Date date;

    private static final String DATE_FMT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public Girl(Comment comment) {
        id = comment.comment_ID;
        author = comment.comment_author;
        picURL = comment.pics != null && !comment.pics.isEmpty() ? comment.pics.get(0) : null;
        title = comment.text_content;
        positiveVote = Integer.valueOf(comment.vote_positive);
        negativeVote = Integer.valueOf(comment.vote_negative);

        //TODO: put datetime stuff into utility or abstract to base class
        if (!TextUtils.isEmpty(comment.comment_date)) {
            SimpleDateFormat fmt = new SimpleDateFormat(DATE_FMT_PATTERN, Locale.US);
            try {
                this.date = fmt.parse(comment.comment_date);
            } catch (ParseException e) {
                //e.printStackTrace();
            }
        }

    }
}
