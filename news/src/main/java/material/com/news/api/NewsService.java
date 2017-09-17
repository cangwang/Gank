package material.com.news.api;

import io.reactivex.Observable;
import material.com.news.model.NewsEntity;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Service统一接口
 * Created by zjl on 2017/3/28.
 */

public interface NewsService {
    @GET("data/{sort}/10/{page}")
    Observable<NewsEntity> getNews(@Path("sort") String sort,@Path("page") int page);
}
