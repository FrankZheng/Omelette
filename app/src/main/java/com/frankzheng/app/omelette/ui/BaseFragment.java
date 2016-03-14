package com.frankzheng.app.omelette.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.ui.mvp.IPresenter;
import com.frankzheng.app.omelette.ui.mvp.IView;
import com.frankzheng.app.omelette.ui.view.ObservableListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;


/**
 * Created by zhengxiaoqiang on 16/3/14.
 */
public abstract class BaseFragment<T> extends Fragment implements IView<T> {
    private static final String TAG = BaseFragment.class.getSimpleName();

    protected View loadMoreFooter;
    protected ArrayAdapter<T> itemsAdapter;

    @Bind(R.id.lv_items)
    public ObservableListView lv_items;

    @Bind(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;

    protected IPresenter presenter;


    abstract protected ArrayAdapter<T> createItemsAdapter(Context context);

    abstract protected IPresenter createPresenter();

    abstract protected int getLayoutId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);

        itemsAdapter = createItemsAdapter(getContext());
        lv_items.setAdapter(itemsAdapter);

        loadMoreFooter = inflater.inflate(R.layout.footer_load_more, null);
        lv_items.addFooterView(loadMoreFooter);

        presenter = createPresenter();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadItems();
            }
        });

        lv_items.scrolledToBottom().subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.d(TAG, "scrolled to bottom, load more posts");
                presenter.loadMoreItems();
            }
        });

        return rootView;
    }


    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress");
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(OMError error) {
        if (getContext() != null) {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showItems(List<T> items) {
        itemsAdapter.clear();
        itemsAdapter.addAll(items);
        itemsAdapter.notifyDataSetChanged();
    }
}
