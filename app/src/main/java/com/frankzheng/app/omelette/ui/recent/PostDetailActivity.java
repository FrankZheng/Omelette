package com.frankzheng.app.omelette.ui.recent;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Post;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.model.RecentPostsModel;
import com.frankzheng.app.omelette.net.Network;
import com.frankzheng.app.omelette.net.response.APIResponse;
import com.frankzheng.app.omelette.net.response.GetPostResponse;
import com.frankzheng.app.omelette.ui.ItemDetailActivity;

import butterknife.Bind;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhengxiaoqiang on 16/2/5.
 */
public class PostDetailActivity extends ItemDetailActivity<Post> {
    private static final String TAG = "PostDetailActivity";

    @Bind(R.id.wv_post)
    WebView wv_post;

    private Post post;

    public static void start(Context context, Post post) {
        ItemDetailActivity.start(context, post, PostDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: show process indicator
        if (isInitOk()) {
            post = item;
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

    @Override
    protected BaseModel<Post, ? extends APIResponse> getModel() {
        return RecentPostsModel.getInstance();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_post_detail;
    }

    private void loadPostContent() {
        Network.getInstance().getPost(post.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetPostResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Failed to load post, " + e.getLocalizedMessage());
                        Toast.makeText(PostDetailActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GetPostResponse response) {
                        post.content = response.post.content;
                        renderPostDetail();
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
