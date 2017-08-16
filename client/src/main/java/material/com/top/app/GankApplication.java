package material.com.top.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.util.MutableLong;

import com.alibaba.android.arouter.launcher.ARouter;
import com.squareup.leakcanary.LeakCanary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import material.com.base.BaseAppInt;
import material.com.base.app.BaseApplication;
import material.com.gank.BuildConfig;
import material.com.top.PageConfig;

/**
 * Created by cangwang on 2017/4/2.
 */

public class GankApplication extends BaseApplication{
    private static final String TAG = "GankApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        initModulesSpeed();
        Log.d(TAG,"onCreate");
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            Log.d(TAG,"pass LeakCanary");
//            return;
//        }
//        LeakCanary.install(this);
//        Log.d(TAG,"start LeakCanary");
//        if (BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
//        }
        ARouter.init(this);
        initModulesLow();
    }

    public void initModulesSpeed(){
        Observable.fromArray(PageConfig.initModules).subscribeOn(Schedulers.io()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String init) throws Exception {
                try {
                    Class<?> clazz = Class.forName(init);
                    BaseAppInt moudleInit = (BaseAppInt) clazz.newInstance();
                    moudleInit.onInitSpeed(GankApplication.this);
                }catch (ClassNotFoundException e){
                    Log.e(TAG,"error="+e.toString());
                }catch (IllegalAccessException e){
                    Log.e(TAG,"error="+e.toString());
                }catch (InstantiationException e){
                    Log.e(TAG,"error="+e.toString());
                }
            }
        });
    }

    public void initModulesLow(){
        for (String init: PageConfig.initModules){
            try {
                Class<?> clazz = Class.forName(init);
                BaseAppInt moudleInit = (BaseAppInt) clazz.newInstance();
                moudleInit.onInitLow(this);
            }catch (ClassNotFoundException e){
                Log.e(TAG,"error="+e.toString());
            }catch (IllegalAccessException e){
                Log.e(TAG,"error="+e.toString());
            }catch (InstantiationException e){
                Log.e(TAG,"error="+e.toString());
            }
        }
    }

}
