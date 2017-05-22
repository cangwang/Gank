package material.com.top.app

import android.app.Application
import android.util.Log

import com.alibaba.android.arouter.launcher.ARouter
import com.squareup.leakcanary.LeakCanary

/**
 * Created by cangwang on 2017/4/2.
 */

class GankApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            Log.d(TAG, "pass LeakCanary")
            return
        }
        LeakCanary.install(this)
        Log.d(TAG, "start LeakCanary")
        //        if (BuildConfig.DEBUG){
//        ARouter.openLog()
//        ARouter.openDebug()
//        //        }
//        ARouter.init(this)
    }

    companion object {
        private val TAG = "GankApplication"
    }
}
