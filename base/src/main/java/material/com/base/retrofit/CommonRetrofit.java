package material.com.base.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zjl on 2017/3/28.
 */

public class CommonRetrofit {
    private volatile static CommonRetrofit Instance;
    private static final String BASE_URL = "http://gank.io/api/";

    private Retrofit retrofit;
    private String bu = BASE_URL;

    public static CommonRetrofit getInstance(){
        if (Instance == null)
            synchronized (CommonRetrofit.class){
                if (Instance == null)
                    Instance = new CommonRetrofit();
            }
        return Instance;
    }

    private CommonRetrofit(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(bu)
                .build();
    }
    private CommonRetrofit(String baseUrl){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        if (baseUrl !=null){
            bu = baseUrl;
        }

        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(bu)
                .build();
    }

    public Retrofit getRetrofit(){
        return  retrofit;
    }

}
