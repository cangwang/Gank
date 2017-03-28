package material.com.news.api;

import io.reactivex.Observable;
import material.com.news.model.NewsEntity;
import retrofit2.http.GET;

/**
 * Service统一接口
 * Created by zjl on 2017/3/28.
 */

public interface NewsService {
    @GET("data/Android/10/1")
    Observable<NewsEntity> getNews();
}
