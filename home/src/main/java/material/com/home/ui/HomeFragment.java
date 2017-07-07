package material.com.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import material.com.base.BaseFragment;
import material.com.home.R;
import material.com.home.adapter.ItemDecoration;
import material.com.home.adapter.TimeLineAdapter;

/**
 * Created by CangWang on 2017/6/14.
 */

public class HomeFragment extends BaseFragment{

    private View view;
    private RecyclerView homeView;
    private TimeLineAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeView = (RecyclerView) homeView.findViewById(R.id.home_recyclerview);
        homeView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        homeView.addItemDecoration(new ItemDecoration(getContext(),50));
        adapter = new TimeLineAdapter(getContext());
        homeView.setAdapter(adapter);
    }

    @Override
    public void loadData() {

    }
}
