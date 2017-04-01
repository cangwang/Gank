package com.cangwang.submit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cangwang.submit.R;

import material.com.base.BaseActivity;

/**
 * Created by cangwang on 2017/4/1.
 */

public class SubmitActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_activity);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        SubmitFragment sf = new SubmitFragment();
        tr.replace(R.id.submit_activity,sf);
        tr.commitAllowingStateLoss();
    }
}
