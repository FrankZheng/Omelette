package com.frankzheng.app.omelette.ui.pictures;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frankzheng.app.omelette.R;
import com.frankzheng.app.omelette.bean.Picture;
import com.frankzheng.app.omelette.task.OMError;

import java.util.List;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class PicturesFragment extends Fragment implements PicturesView {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictures, container, false);
        return view;
    }

    @Override
    public void showPictures(List<Picture> pictures) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(OMError error) {

    }
}
