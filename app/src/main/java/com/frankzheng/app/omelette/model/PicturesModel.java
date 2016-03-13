package com.frankzheng.app.omelette.model;

import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.net.response.GetCommentsResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by zhengxiaoqiang on 16/3/10.
 */
public class PicturesModel extends BaseModel<Picture, GetCommentsResponse> {
    private static final String TAG = PicturesModel.class.getSimpleName();

    private static PicturesModel instance = null;

    public static PicturesModel getInstance() {
        if (instance == null) {
            synchronized (PicturesModel.class) {
                if (instance == null) {
                    instance = new PicturesModel();
                }
            }
        }
        return instance;
    }

    protected String getStoreName() {
        return "Pictures";
    }

    @Override
    protected Observable<GetCommentsResponse> fetchData(int page) {
        return null;
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
    protected void updateItems(GetCommentsResponse response) {

    }


}
