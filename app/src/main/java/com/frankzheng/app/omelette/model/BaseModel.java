package com.frankzheng.app.omelette.model;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by zhengxiaoqiang on 16/3/8.
 */
public abstract class BaseModel<T> {
    public PublishSubject<Void> dataChanged = PublishSubject.create();
    //private final Map<String, T> items = new HashMap<>();

    abstract public T getItemById(String id);

    abstract public List<T> getItems();

    abstract public boolean isEmpty();

    abstract public void loadItemsFromLocalCache();

    abstract public Observable<Void> loadItems(int page);


}
