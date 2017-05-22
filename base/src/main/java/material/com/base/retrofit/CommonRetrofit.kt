package material.com.base.retrofit

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by zjl on 2017/3/28.
 */

class CommonRetrofit {

    var retrofit: Retrofit? = null
        private set
    private var bu = BASE_URL

    private constructor() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)

        retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(bu)
                .build()
    }

    private constructor(baseUrl: String?) {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)

        if (baseUrl != null) {
            bu = baseUrl
        }

        retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(bu)
                .build()
    }

    companion object {
        @Volatile private var Instance: CommonRetrofit? = null
        private val BASE_URL = "http://gank.io/api/"

         fun get():CommonRetrofit {
                if (Instance == null)
                    synchronized(CommonRetrofit::class.java) {
                        if (Instance == null)
                            Instance = CommonRetrofit()
                    }
                return Instance!!
            }
    }

}
