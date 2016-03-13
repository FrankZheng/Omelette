package com.frankzheng.app.omelette.model;

import android.util.Log;
import android.util.SparseBooleanArray;

import com.frankzheng.app.omelette.error.OMError;
import com.frankzheng.app.omelette.store.KVStore;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by zhengxiaoqiang on 16/3/8.
 */
public abstract class BaseModel<T1, T2> {
    private static final String TAG = BaseModel.class.getSimpleName();

    public PublishSubject<Void> dataChanged = PublishSubject.create();
    protected final Map<String, T1> items = new HashMap<>();
    protected SparseBooleanArray tasksStatus = new SparseBooleanArray();
    protected KVStore store;

    protected BaseModel() {
        store = new KVStore(getStoreName());
    }

    abstract protected String getStoreName();

    protected String getStoreKey() {
        return "items";
    }

    abstract protected Observable<T2> fetchData(int page);

    public T1 getItemById(String id) {
        return items.get(id);
    }

    public List<T1> getItems() {
        synchronized (this.items) {
            return new ArrayList<>(items.values());
        }
    }

    public boolean isEmpty() {
        return this.items.size() == 0;
    }

    public void loadItemsFromLocalCache() {
        Type superClass = this.getClass().getGenericSuperclass();
        Type clazz = ((ParameterizedType) superClass).getActualTypeArguments()[1];
        T2 response = store.get(getStoreKey(), clazz);
        if (response != null) {
            updateItems(response);
        }
    }

    abstract protected void updateItems(T2 response);

    public Observable<Void> loadItems(final int page) {
        if (!tasksStatus.get(page, false)) {
            tasksStatus.put(page, true);
            Observable<T2> observable = fetchData(page);
            observable.subscribe(new Observer<T2>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.i(TAG, "onError: " + e.getMessage());
                    tasksStatus.delete(page);
                }

                @Override
                public void onNext(T2 response) {
                    Log.i(TAG, "onSuccess");
                    tasksStatus.delete(page);

                    if (page == 1) {
                        store.put(getStoreKey(), response, false);
                    }

                    updateItems(response);
                }
            });

            return observable.map(new Func1<T2, Void>() {
                @Override
                public Void call(T2 response) {
                    return null;
                }
            });
        }

        return Observable.error(new OMError("In the loading"));
    }



}
