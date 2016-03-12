package com.frankzheng.app.omelette.model;

import com.frankzheng.app.omelette.bean.Picture;

import java.util.List;

import rx.Observable;

/**
 * Created by zhengxiaoqiang on 16/3/10.
 */
public class PicturesModel extends BaseModel<Picture> {

    @Override
    protected String getStoreName() {
        return "Pictures";
    }

    @Override
    public Picture getItemById(String id) {
        return null;
    }

    @Override
    public List<Picture> getItems() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void loadItemsFromLocalCache() {

    }

    @Override
    public Observable<Void> loadItems(int page) {
        return null;
    }
}
