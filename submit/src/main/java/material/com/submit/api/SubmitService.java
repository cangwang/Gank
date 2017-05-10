package material.com.submit.api;

import material.com.submit.model.SubmitResult;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by cangwang on 2017/3/30.
 */

public interface SubmitService {
    @POST("add2gank")
    Observable<SubmitResult> submitGank(@Field("url") String url
            ,@Field("desc") String desc
            ,@Field("who")String who
            ,@Field("type") String type
            ,@Field("debug")boolean debug);
}
