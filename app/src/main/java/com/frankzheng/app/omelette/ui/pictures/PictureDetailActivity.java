package com.frankzheng.app.omelette.ui.pictures;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.PicturesModel;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.ui.ItemDetailActivity;

import butterknife.Bind;


/**
 * Created by zhengxiaoqiang on 16/3/26.
 */
public class PictureDetailActivity extends ItemDetailActivity<Picture> {
    private static final String TAG = PictureDetailActivity.class.getSimpleName();

    @Bind(R.id.iv_pic)
    SimpleDraweeView iv_pic;


    public static void start(Context context, Picture picture) {
        ItemDetailActivity.start(context, picture, PictureDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isInitOk()) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    Uri uri = Uri.parse(item.picURL);
                    iv_pic.setImageURI(uri);
                }
            });
        }
    }

    @Override
    protected BaseModel<Picture, ? extends APIResponse> getModel() {
        return PicturesModel.getInstance();
    }

    protected int getLayoutResID() {
        return R.layout.activity_picture_detail;
    }


}
