package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frankzheng.app.omelette.BuildConfig;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;

/**
 * Created by zhengxiaoqiang on 16/2/5.
 */
public class PostsAdapter extends ArrayAdapter<Post> {


    public PostsAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_post, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(viewHolder != null) {
            Post post = getItem(position);
            viewHolder.setData(post);
        }
        return convertView;
    }

    static class ViewHolder {
        private TextView tv_title;
        private TextView tv_source;
        private SimpleDraweeView iv_thumb;

        public ViewHolder(View view) {
            tv_title = (TextView)view.findViewById(R.id.tv_title);
            tv_source = (TextView)view.findViewById(R.id.tv_source);
            iv_thumb = (SimpleDraweeView)view.findViewById(R.id.iv_thumb);
        }

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
