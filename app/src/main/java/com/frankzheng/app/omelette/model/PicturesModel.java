package com.frankzheng.app.omelette.model;

import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.Comment;
import com.frankzheng.app.omelette.net.response.GetCommentsResponse;

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
        return Network.getInstance().getPictures(page);
    }


    @Override
    protected void updateItems(GetCommentsResponse response) {
        synchronized (this.items) {
            boolean updated = false;
            for (Comment comment : response.comments) {
                if (this.items.get(comment.comment_ID) == null) {
                    Picture picture = new Picture(comment);
                    this.items.put(comment.comment_ID, picture);
                    updated = true;
                }
            }
            if (updated) {
                dataChanged.onNext(null);
            }
        }
    }


}
