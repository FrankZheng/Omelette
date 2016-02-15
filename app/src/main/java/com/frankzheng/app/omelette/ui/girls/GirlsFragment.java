package com.frankzheng.app.omelette.ui.girls;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frankzheng.app.omelette.R;

/**
 * Created by zhengxiaoqiang on 16/2/15.
 */
public class GirlsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girls, container, false);
        return view;
    }


}
