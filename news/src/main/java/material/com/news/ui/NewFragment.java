package material.com.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import material.com.base.BaseFragment;
import material.com.news.R;

/**
 * Created by zjl on 2017/3/27.
 */

public class NewFragment extends BaseFragment{
    private View view;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_fragment,container,false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.news_recyclerview);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
