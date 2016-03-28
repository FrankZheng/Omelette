package com.frankzheng.app.omelette.ui.girls;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Girl;
import com.frankzheng.app.omelette.ui.BaseFragment;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class GirlsFragment extends BaseFragment<Girl> implements GirlsView {

    GirlsPresenter girlsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Girl girl = itemsAdapter.getItem(position);
                girlsPresenter.showGirlDetail(getContext(), girl);
            }
        });
        return rootView;
    }

    @Override
    protected ArrayAdapter<Girl> createItemsAdapter(Context context) {
        return new GirlsAdapter(context);
    }

    @Override
    protected IPresenter createPresenter() {
        girlsPresenter = new GirlsPresenterImpl(this);
        return girlsPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girls;
    }


}
