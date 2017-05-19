package material.com.news.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import material.com.base.BaseFragment;
import material.com.base.retrofit.CommonRetrofit;
import material.com.news.R;
import material.com.news.adapter.NewsReclyerAdapter;
import material.com.news.api.NewsService;
import material.com.news.model.NewsEntity;
import material.com.news.model.NewsItem;
import retrofit2.Retrofit;

/**
 * Created by zjl on 2017/3/27.
 */

public class NewFragment extends BaseFragment{
    private static final String TAG ="NewFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshlayout;
    private NewsReclyerAdapter adapter;
    private List<NewsItem> datas = new ArrayList<>();

    private Retrofit retrofit;
    private int page=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.news_swipe_refresh);
        mSwipeRefreshlayout.setColorSchemeResources(R.color.common_pink_5, R.color.common_light_green_2, R.color.commen_blue_3);
        mSwipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }
        });
        mRecyclerView = (RecyclerView)view.findViewById(R.id.news_recyclerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        adapter = new NewsReclyerAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition()+1 == adapter.getItemCount()){
//                    page++;
//                    loadData();
//                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){  //当屏幕停止滚动，加载图片
                    if (layoutManager.findLastVisibleItemPosition()+1 == adapter.getItemCount()){
                        page++;
                        loadData();
                    }
                    Glide.with(getContext()).resumeRequests();
                }else if (newState == RecyclerView.SCROLL_STATE_DRAGGING){  //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    Glide.with(getContext()).pauseRequests();
                }else if (newState == RecyclerView.SCROLL_STATE_SETTLING){  //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    Glide.with(getContext()).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeRefreshlayout.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() ==0);
            }
        });
    }

    @Override
    public void loadData() {
        Log.d(TAG,"sort = "+sort);
        retrofit =  CommonRetrofit.getInstance().getRetrofit();
        NewsService newsService = retrofit.create(NewsService.class);
        Observable<NewsEntity> observable = newsService.getNews(sort,page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (mSwipeRefreshlayout !=null && !mSwipeRefreshlayout.isRefreshing())
                            mSwipeRefreshlayout.setRefreshing(true);
                    }

                    @Override
                    public void onNext(NewsEntity newsEntity) {
                        datas = newsEntity.getResults();
                        if (page == 1) {
                            adapter.setDatas(datas);
                        }else {
                            adapter.addDatas(datas);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mSwipeRefreshlayout !=null && mSwipeRefreshlayout.isRefreshing())
                            mSwipeRefreshlayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        if (mSwipeRefreshlayout !=null && mSwipeRefreshlayout.isRefreshing())
                            mSwipeRefreshlayout.setRefreshing(false);
                    }
                });

    }

    @Override
    public void onDestroyView() {
        adapter.clearData();
        datas = null;
        super.onDestroyView();
    }
}
