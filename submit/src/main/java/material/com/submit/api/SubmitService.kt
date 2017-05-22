package material.com.submit.api

import material.com.submit.model.SubmitResult
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.POST

/**
 * Created by cangwang on 2017/3/30.
 */

interface SubmitService {
    @POST("add2gank")
    fun submitGank(@Field("url") url: String, @Field("desc") desc: String, @Field("who") who: String, @Field("type") type: String, @Field("debug") debug: Boolean): Observable<SubmitResult>
}
