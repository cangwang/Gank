package material.com.news.api

import io.reactivex.Observable
import material.com.news.model.NewsEntity
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Service统一接口
 * Created by zjl on 2017/3/28.
 */

interface NewsService {
    @GET("data/{sort}/10/{page}")
    fun getNews(@Path("sort") sort: String, @Path("page") page: Int): Observable<NewsEntity>
}
