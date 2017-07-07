package material.com.home.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Cangwang on 2017/6/14.
 */

public interface HomService {
    @GET("data/{sort}/10/{page}")
    Observable<NewsEntity> getNews(@Path("sort") String sort, @Path("page") int page);
}
