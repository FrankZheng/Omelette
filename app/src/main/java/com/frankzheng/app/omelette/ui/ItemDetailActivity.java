package com.frankzheng.app.omelette.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.frankzheng.app.omelette.bean.Item;
import com.frankzheng.app.omelette.model.BaseModel;
import com.frankzheng.app.omelette.net.response.APIResponse;

import butterknife.ButterKnife;

/**
 * Created by zhengxiaoqiang on 16/3/28.
 */
public abstract class ItemDetailActivity<T extends Item> extends AppCompatActivity {
    private static final String TAG = ItemDetailActivity.class.getSimpleName();
    private static final String ITEM_ID_KEY = "ItemID";

    protected T item;
    protected boolean initOk;

    protected static <T extends Item> void start(Context context, T item, Class<? extends ItemDetailActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(ITEM_ID_KEY, item.getId());
        context.startActivity(intent);
    }

    protected boolean init() {
        if (getIntent() != null) {
            String itemId = getIntent().getStringExtra(ITEM_ID_KEY);
            item = getModel().getItemById(itemId);
            if (item == null) {
                Toast.makeText(this, "Failed to get item from model", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            setContentView(getLayoutResID());

            ButterKnife.bind(this);

            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOk = init();
    }

    protected boolean isInitOk() {
        return initOk;
    }

    abstract protected BaseModel<T, ? extends APIResponse> getModel();

    abstract protected int getLayoutResID();


}
