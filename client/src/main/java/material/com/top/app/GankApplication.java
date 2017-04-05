package material.com.top.app;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by air on 2017/4/2.
 */

public class GankApplication extends Application{
    private static final String TAG = "GankApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            Log.d(TAG,"pass LeakCanary");
            return;
        }
        LeakCanary.install(this);
        Log.d(TAG,"start LeakCanary");
    }
}
