package com.frankzheng.app.omelette.net;

import android.util.Log;

import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.net.response.GetCommentsResponse;
import com.frankzheng.app.omelette.net.response.GetPostResponse;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class Network {
    private static final String TAG = Network.class.getSimpleName();

    private static Network sInstance = new Network();

    private Retrofit retrofit;
    private JandanService jandanService;

    public static Network getInstance() {
        return sInstance;
    }

    public void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(JandanService.HOST_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

        jandanService = retrofit.create(JandanService.class);
    }

    public JandanService getJandanService() {
        return jandanService;
    }

    private <T extends APIResponse> Observable<T> checkStatus(Observable<T> observable) {
        return observable.flatMap(new Func1<T, Observable<T>>() {
            @Override
            public Observable<T> call(T response) {
                if ("ok".equals(response.status)) {
                    return Observable.just(response);
                } else {
                    return Observable.error(OMError.createWithStatus(response.status));
                }
            }
        });
    }


    public Observable<RecentPostsResponse> getRecentPosts(final int page) {
        Log.i(TAG, "getRecentPosts, " + page);
        return checkStatus(jandanService.getRecentPosts(page));
    }

    public Observable<GetPostResponse> getPost(final String id) {
        return checkStatus(jandanService.getPost(id));
    }

    public Observable<GetCommentsResponse> getPictures(final int page) {
        Log.i(TAG, "getPictures, " + page);
        return checkStatus(jandanService.getPictures(page));
    }

    public Observable<GetCommentsResponse> getGirls(final int page) {
        Log.i(TAG, "getGirls, " + page);
        return checkStatus(jandanService.getGirls(page));
    }



}
