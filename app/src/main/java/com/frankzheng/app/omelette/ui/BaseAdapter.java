package com.frankzheng.app.omelette.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


/**
 * Created by zhengxiaoqiang on 16/3/14.
 */
public abstract class BaseAdapter<T> extends ArrayAdapter<T> {

    public BaseAdapter(Context context) {
        super(context, 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        IViewHolder<T> viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(getRowLayoutID(), null);
            viewHolder = createViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (IViewHolder<T>) convertView.getTag();
        }

        if (viewHolder != null) {
            T item = getItem(position);
            viewHolder.setData(item);
        }
        return convertView;
    }

    abstract protected IViewHolder<T> createViewHolder(View view);

    abstract protected int getRowLayoutID();

}
