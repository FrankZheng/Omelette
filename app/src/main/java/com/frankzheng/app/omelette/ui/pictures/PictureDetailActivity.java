package com.frankzheng.app.omelette.ui.pictures;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.model.PicturesModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhengxiaoqiang on 16/3/26.
 */
public class PictureDetailActivity extends AppCompatActivity {
    private static final String TAG = PictureDetailActivity.class.getSimpleName();
    private static final String PIC_ID = "PictureID";

    private Picture picture;

    @Bind(R.id.iv_pic)
    SimpleDraweeView iv_pic;


    public static void start(Context context, Picture picture) {
        Intent intent = new Intent(context, PictureDetailActivity.class);
        intent.putExtra(PIC_ID, picture.id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            String picID = getIntent().getStringExtra(PIC_ID);
            picture = PicturesModel.getInstance().getItemById(picID);
            if (picture == null) {
                Toast.makeText(this, "Failed to get picture from model", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Uri uri = Uri.parse(picture.picURL);
                iv_pic.setImageURI(uri);
            }
        });
    }


}
