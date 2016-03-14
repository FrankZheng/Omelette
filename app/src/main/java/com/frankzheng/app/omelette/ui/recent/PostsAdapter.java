package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frankzheng.app.omelette.BuildConfig;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.ui.BaseAdapter;
import com.frankzheng.app.omelette.ui.IViewHolder;

/**
 * Created by zhengxiaoqiang on 16/2/5.
 */
public class PostsAdapter extends BaseAdapter<Post> {

    public PostsAdapter(Context context) {
        super(context);
    }

    @Override
    protected IViewHolder<Post> createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected int getRowLayoutID() {
        return R.layout.row_post;
    }

    static class ViewHolder implements IViewHolder<Post> {
        private TextView tv_title;
        private TextView tv_source;
        private SimpleDraweeView iv_thumb;

        public ViewHolder(View view) {
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_source = (TextView)view.findViewById(R.id.tv_source);
            iv_thumb = (SimpleDraweeView)view.findViewById(R.id.iv_thumb);
        }

        @Override
        public void setData(Post post) {
            tv_title.setText(post.title);
            if (BuildConfig.DEBUG) {
                tv_source.setText(String.format("%s @ %s (%s)", post.author, post.tag, post.getFormattedDate()));
            } else {
                tv_source.setText(String.format("%s @ %s", post.author, post.tag));
            }
            //tv_source.setText(String.format("%s @ %s", post.author, post.tag));
            Uri uri = Uri.parse(post.thumbURL);
            iv_thumb.setImageURI(uri);
        }
    }
}
