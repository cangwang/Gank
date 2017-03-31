package com.cangwang.submit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cangwang.submit.R;
import com.cangwang.submit.prestenter.SubmitPresenter;

import material.com.base.BaseFragment;

/**
 * Created by cangwang on 2017/3/30.
 */

public class SubmitFragment extends BaseFragment implements SubmitContact.View{

    SubmitContact.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.submit_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SubmitPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void setPresenter(SubmitContact.Presenter presenter) {

    }
}
