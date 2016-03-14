package com.frankzheng.app.omelette.ui.pictures;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.ui.BaseFragment;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class PicturesFragment extends BaseFragment<Picture> implements PicturesView {
    private static final String TAG = PicturesFragment.class.getSimpleName();

    PicturesPresenter picturesPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    protected ArrayAdapter<Picture> createItemsAdapter(Context context) {
        return new PicturesAdapter(context);
    }

    @Override
    protected IPresenter createPresenter() {
        picturesPresenter = new PicturesPresenterImpl(this);
        return picturesPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pictures;
    }


}
