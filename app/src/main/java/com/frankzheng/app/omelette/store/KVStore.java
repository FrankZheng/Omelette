package com.frankzheng.app.omelette.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.frankzheng.app.omelette.MainApplication;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by zhengxiaoqiang on 16/3/11.
 */
public class KVStore {

    SharedPreferences sp;
    Gson gson;

    protected KVStore() {

    }

    public KVStore(String name) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name is empty or null");
        }
        sp = MainApplication.context.getSharedPreferences(name, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    /*
    use json to store object
     */
    public void put(String key, Object object, boolean commit) {
        String json = gson.toJson(object);
        put(key, json, commit);
    }

    public <T> T get(String key, Class clazz) {
        try {
            String json = get(key, "");
            if (!TextUtils.isEmpty(json)) {
                return (T) gson.fromJson(json, clazz);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(String key, String value) {
        put(key, value, false);
    }

    public String get(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void put(String key, String value, boolean commit) {
        if (commit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }
}
