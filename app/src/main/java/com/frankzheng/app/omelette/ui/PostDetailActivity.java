package com.frankzheng.app.omelette.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.GetPostResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhengxiaoqiang on 16/2/5.
 */
public class PostDetailActivity extends AppCompatActivity {
    private static final String TAG = "PostDetailActivity";
    private static final String POST_ID_KEY = "PostId";

    @Bind(R.id.wv_post)
    WebView wv_post;

    private Post post;

    public static void start(Context context, Post post) {
        Intent intent = new Intent(context, PostDetailActivity.class);
        intent.putExtra(POST_ID_KEY, post.id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra(POST_ID_KEY, -1);
            post = RecentPostsModel.getInstance().getPostById(id);
            //TODO: show process indicator
            if (TextUtils.isEmpty(post.content)) {
                loadPostContent();
            } else {
                //display content directly
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        renderPostDetail();
                    }
                }, 500);
            }

        }
    }

    private void loadPostContent() {
        Call<GetPostResponse> response = Network.getInstance().getJandanService().getPost(post.id);
        response.enqueue(new Callback<GetPostResponse>() {
            @Override
            public void onResponse(Response<GetPostResponse> response) {
                if (response.isSuccess()) {
                    post.content = response.body().post.content;
                    renderPostDetail();
                } else {
                    Log.e(TAG, String.format("Failed to load post detail, %d", response.code()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, String.format("onFailure, %s", t.getLocalizedMessage()));
            }
        });
    }

    private void renderPostDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>");
        stringBuilder.append("<html dir=\"ltr\" lang=\"zh\">");
        stringBuilder.append("<head>");
        stringBuilder.append("<meta name=\"viewport\" content=\"width=100%; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" />");
        stringBuilder.append("<link rel=\"stylesheet\" href='file:///android_asset/style.css' type=\"text/css\" media=\"screen\" />");
        stringBuilder.append("</head>");
        stringBuilder.append("<body style=\"padding:0px 8px 8px 8px;\">");
        stringBuilder.append("<div id=\"pagewrapper\">");
        stringBuilder.append("<div id=\"mainwrapper\" class=\"clearfix\">");
        stringBuilder.append("<div id=\"maincontent\">");
        stringBuilder.append("<div class=\"post\">");
        stringBuilder.append("<div class=\"posthit\">");
        stringBuilder.append("<div class=\"postinfo\">");
        stringBuilder.append("<h2 class=\"thetitle\">");
        stringBuilder.append("<a>");
        stringBuilder.append(post.title);
        stringBuilder.append("</a>");
        stringBuilder.append("</h2>");
        stringBuilder.append(new StringBuilder(String.valueOf(post.author)).append(" @ ").append(post.tag));
        stringBuilder.append("</div>");
        stringBuilder.append("<div class=\"entry\">");
        stringBuilder.append(post.content);
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("</div>");
        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        //wv_post.loadData(stringBuilder.toString(), "text/html", null);
        wv_post.loadDataWithBaseURL("", stringBuilder.toString(), "text/html", "", null);
    }
}
