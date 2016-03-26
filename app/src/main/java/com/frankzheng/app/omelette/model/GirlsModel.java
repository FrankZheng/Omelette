package com.frankzheng.app.omelette.model;

import com.frankzheng.app.omelette.bean.Girl;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.Comment;
import com.frankzheng.app.omelette.net.response.GetCommentsResponse;

import rx.Observable;

/**
 * Created by zhengxiaoqiang on 16/3/26.
 */
public class GirlsModel extends BaseModel<Girl, GetCommentsResponse> {
    private static volatile GirlsModel instance = null;

    private GirlsModel() {

    }

    public static GirlsModel getInstance() {
        if (instance == null) {
            synchronized (GirlsModel.class) {
                if (instance == null) {
                    instance = new GirlsModel();
                }
            }
        }
        return instance;
    }

    @Override
    protected String getStoreName() {
        return "Girls";
    }

    @Override
    protected Observable<GetCommentsResponse> fetchData(int page) {
        return Network.getInstance().getGirls(page);
    }

    @Override
    protected void updateItems(GetCommentsResponse response) {
        synchronized (this.items) {
            boolean updated = false;
            for (Comment comment : response.comments) {
                if (this.items.get(comment.comment_ID) == null) {
                    Girl girl = new Girl(comment);
                    this.items.put(comment.comment_ID, girl);
                    updated = true;
                }
            }
            if (updated) {
                dataChanged.onNext(null);
            }
        }
    }


}
