package material.com.news.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import material.com.base.BaseFragment;
import material.com.base.BasePresenter;
import material.com.base.retrofit.CommonRetrofit;
import material.com.news.api.NewsService;
import material.com.news.model.NewsEntity;
import material.com.news.model.NewsItem;
import material.com.news.ui.INewView;
import retrofit2.Retrofit;

/**
 * Created by cangwang on 2017/8/4.
 */

public class NewPresenter extends BasePresenter<INewView>{
    private static final String TAG ="NewPresenter";
    private BaseFragment bf;

    private Retrofit retrofit;
    private List<NewsItem> datas = new ArrayList<>();

    public NewPresenter(BaseFragment bf){
        this.bf = bf;
    }

    public void loadData(String sort,final int page) {
        Log.d(TAG,"sort = "+sort);
        retrofit =  CommonRetrofit.getInstance().getRetrofit();
        NewsService newsService = retrofit.create(NewsService.class);
        Observable<NewsEntity> observable = newsService.getNews(sort,page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                       getView().refreshStatus(true);
                    }

                    @Override
                    public void onNext(NewsEntity newsEntity) {
                        Log.e(this.getClass().getSimpleName(),newsEntity.toString());
                        datas = newsEntity.getResults();
                        if (page == 1) {
                            getView().setAdapterData(datas);
                        }else {
                            getView().addAdapterData(datas);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(),e.toString());
                        getView().refreshStatus(false);
                    }

                    @Override
                    public void onComplete() {
                        getView().refreshStatus(false);
                    }
                });

    }

    @Override
    public void release() {
        datas=null;
        bf=null;
        super.release();
    }
}
