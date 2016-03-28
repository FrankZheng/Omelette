package com.frankzheng.app.omelette.ui.girls;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Girl;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.GirlsModel;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.ui.ItemDetailActivity;

import butterknife.Bind;


/**
 * Created by zhengxiaoqiang on 16/3/28.
 */
public class GirlsDetailActivity extends ItemDetailActivity<Girl> {

    @Bind(R.id.iv_pic)
    SimpleDraweeView iv_pic;

    public static void start(Context context, Girl girl) {
        ItemDetailActivity.start(context, girl, GirlsDetailActivity.class);
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
    protected BaseModel<Girl, ? extends APIResponse> getModel() {
        return GirlsModel.getInstance();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_girl_detail;
    }
}
