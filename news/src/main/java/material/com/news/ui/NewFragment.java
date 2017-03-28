package material.com.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import material.com.base.BaseFragment;
import material.com.base.retrofit.CommonRetrofit;
import material.com.news.R;
import material.com.news.api.NewsService;
import material.com.news.model.NewsEntity;
import material.com.news.model.NewsItem;
import retrofit2.Retrofit;

/**
 * Created by zjl on 2017/3/27.
 */

public class NewFragment extends BaseFragment{
    private View view;
    private RecyclerView mRecyclerView;
    private NewsReclyerAdapter adapter;
    private List<NewsItem> datas = new ArrayList<>();

    private Retrofit retrofit;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        adapter = new NewsReclyerAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);

        NewsService newsService = CommonRetrofit.getInstance().getRetrofit().create(NewsService.class);
        Observable<NewsEntity> observable = newsService.getNews();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<NewsEntity>() {
                    @Override
                    public void accept(@NonNull NewsEntity newsEntity) throws Exception {
                        datas = newsEntity.getResults();
                        adapter.setDatas(datas);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
//
//        adapter.setDatas(datas);

    }




}
