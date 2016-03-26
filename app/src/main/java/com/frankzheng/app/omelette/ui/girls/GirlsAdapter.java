package com.frankzheng.app.omelette.ui.girls;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Girl;
import com.frankzheng.app.omelette.ui.BaseAdapter;
import com.frankzheng.app.omelette.ui.IViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengxiaoqiang on 16/3/26.
 */
public class GirlsAdapter extends BaseAdapter<Girl> {

    public GirlsAdapter(Context context) {
        super(context);
    }

    @Override
    protected IViewHolder<Girl> createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected int getRowLayoutID() {
        return R.layout.row_girl;
    }

    static class ViewHolder implements IViewHolder<Girl> {
        @Bind(R.id.tv_author)
        TextView tv_author;
        /*
        @Bind(R.id.tv_title)
        TextView tv_title;
        */
        @Bind(R.id.iv_pic)
        SimpleDraweeView iv_pic;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @Override
        public void setData(Girl data) {
            tv_author.setText(TextUtils.isEmpty(data.author) ? "Anonymous" : data.author);
            //tv_title.setText(data.title);
            Uri uri = Uri.parse(data.picURL);
            //Log.i(TAG, uri.toString());
            iv_pic.setImageURI(uri);
        }
    }


}
