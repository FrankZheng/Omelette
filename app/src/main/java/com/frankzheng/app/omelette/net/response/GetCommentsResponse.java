package com.frankzheng.app.omelette.net.response;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class GetCommentsResponse extends APIResponse {
    public int current_page;
    public int total_comments;
    public int page_count;
    public int count;
    public List<Comment> comments;
}
