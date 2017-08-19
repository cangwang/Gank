package material.com.gank;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import material.com.base.BaseActivity;
import material.com.base.BasePresenter;
import material.com.base.utils.ListDataSave;
import material.com.gank.ui.IAdivceView;

/**
 * Created by cangwang on 2017/8/4.
 */

public class AdvicePresenter extends BasePresenter<IAdivceView>{

    private boolean hasClick=false;
    private BaseActivity context;
    private Handler handler = new Handler();

    public AdvicePresenter(BaseActivity activity){
        this.context = activity;
    }

    public void startWeb(){
        hasClick=true;
        ARouter.getInstance().build("/gank_main/1").navigation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build("/gank_web/1").withString("url","https://github.com/cangwang/Gank").navigation();
            }
        },100);

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                hasClick = true;
//                ARouter.getInstance().build("/gank_main/1").navigation();
//            }
//        }).delay(200, TimeUnit.MILLISECONDS)
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                ARouter.getInstance().build("/gank_web/1").withString("url","https://github.com/cangwang/Gank").navigation();
//                context.finish();
//            }
//        });
    }

    public void statMain(){
        if(!hasClick) {
//                    startActivity(new Intent("material.com.top.MAIN"));
            ARouter.getInstance().build("/gank_main/1").navigation();
        }
    }

    public void dataInit(){
        SharedPreferences sp = context.getSharedPreferences("gank", Context.MODE_PRIVATE);
        boolean firstEnter = sp.getBoolean("first_enter",false);
        if (!firstEnter) {
            ListDataSave dataSave = new ListDataSave(context, "gank", BuildConfig.BUILD_TYPE.equals("debug") ? ListDataSave.DEBUG : ListDataSave.PUBLISH);
            List<String> setData = new ArrayList<>();
            setData.add("all");
            setData.add("Android");
            setData.add("iOS");
            setData.add("福利");
            dataSave.setDataList("setting_data", setData);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first_enter",true);
            editor.commit();
        }
    }

    @Override
    public void release() {
        context=null;
        super.release();
    }
}
