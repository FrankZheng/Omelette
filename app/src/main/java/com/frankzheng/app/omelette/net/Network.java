package com.frankzheng.app.omelette.net;

import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.net.response.GetPostResponse;
import com.frankzheng.app.omelette.net.response.RecentPostsResponse;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhengxiaoqiang on 16/2/2.
 */
public class Network {
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

    private <T extends APIResponse> Observable<T> checkStatus(T response) {
        if ("ok".equals(response.status)) {
            return Observable.just(response);
        } else {
            return Observable.error(OMError.createWithStatus(response.status));
        }
    }

    private <T extends APIResponse> Observable<T> checkStatusAndSubscribeOnNewThread(Observable<T> observable) {
        return observable.flatMap(new Func1<T, Observable<T>>() {
            @Override
            public Observable<T> call(T response) {
                return checkStatus(response);
            }
        }).subscribeOn(Schedulers.newThread());
    }


    public Observable<RecentPostsResponse> getRecentPosts(final int page) {
        return checkStatusAndSubscribeOnNewThread(jandanService.getRecentPosts(page));
    }

    public Observable<GetPostResponse> getPost(final int id) {
        return checkStatusAndSubscribeOnNewThread(jandanService.getPost(id));
    }

}
