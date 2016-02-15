package com.frankzheng.app.omelette.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.frankzheng.app.omelette.ui.girls.GirlsFragment;
import com.frankzheng.app.omelette.ui.jokes.JokesFragment;
import com.frankzheng.app.omelette.ui.pictures.PicturesFragment;
import com.frankzheng.app.omelette.ui.recent.RecentPostsFragment;
import com.frankzheng.app.omelette.ui.videos.VideosFragment;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class OMPagerAdapter extends FragmentPagerAdapter {
    private static final String[] PAGE_TILES = {"新鲜事", "无聊图", "妹子图", "段子", "小电影"};
    private static final Class[] FRAGMENT_CLASSES = {RecentPostsFragment.class, PicturesFragment.class, GirlsFragment.class, JokesFragment.class, VideosFragment.class};
    Context context;

    public OMPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, FRAGMENT_CLASSES[position].getName());
    }

    @Override
    public int getCount() {
        return FRAGMENT_CLASSES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TILES[position];
    }
}
