package material.com.news.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
    private static final String TAG ="NewFragment";
    private View view;
    private RecyclerView mRecyclerView;
    private NewsReclyerAdapter adapter;
    private List<NewsItem> datas = new ArrayList<>();

    private Retrofit retrofit;

    private ProgressDialog pd;

    private String sort;

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

        Bundle bundle = getArguments();
        sort = bundle.getString("sort");
        Log.d(TAG,"sort = "+sort);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        adapter = new NewsReclyerAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);

        pd = new ProgressDialog(getActivity());
        loadData();
    }

    @Override
    public void loadData() {
        retrofit =  CommonRetrofit.getInstance().getRetrofit();
        NewsService newsService = retrofit.create(NewsService.class);
        Observable<NewsEntity> observable = newsService.getNews(sort,1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (pd !=null)
                            pd.show();
                    }

                    @Override
                    public void onNext(NewsEntity newsEntity) {
                        datas = newsEntity.getResults();
                        adapter.setDatas(datas);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                    }
                });
    }
}
