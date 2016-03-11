package com.frankzheng.app.omelette.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by zhengxiaoqiang on 16/3/11.
 */
public class ObservableListView extends ListView {

    private static final String TAG = ObservableListView.class.getSimpleName();

    public ObservableListView(Context context) {
        super(context);
    }

    public ObservableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ObservableListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        throw new IllegalArgumentException("This won't work!");
    }

    /*
    observe the scrolled to bottom event.
    should be called on main thread
     */
    public Observable<Boolean> scrolledToBottom() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {

                OnScrollListener listener = new OnScrollListener() {
                    private int preLastItem = 0;

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        //Log.d(TAG, "onScrollStateChanged " + scrollState);
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        //Log.d(TAG, String.format("first:%d, visible count:%d, total: %d, footers: %d", firstVisibleItem, visibleItemCount, totalItemCount, lv_posts.getFooterViewsCount()));
                        if (totalItemCount - getFooterViewsCount() - getHeaderViewsCount() == 0) {
                            //list is empty
                            return;
                        }
                        final int lastItem = firstVisibleItem + visibleItemCount;
                        if (lastItem == totalItemCount) {
                            if (preLastItem != lastItem) {
                                preLastItem = lastItem;
                                //load more
                                Log.i(TAG, "scroll to bottom");
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext(true);
                                }
                            }
                        }
                    }
                };

                ObservableListView.super.setOnScrollListener(listener);
                subscriber.add(new MainThreadSubscription() {
                    @Override
                    protected void onUnsubscribe() {
                        ObservableListView.super.setOnScrollListener(null);
                    }
                });
            }
        });
    }

}
