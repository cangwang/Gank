package material.com.gank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

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

    public AdvicePresenter(BaseActivity activity){
        this.context = activity;
    }

    public void startWeb(){
        Intent topIntent = new Intent(new Intent("material.com.top.MAIN"));
        Intent webIntent = new Intent("material.com.web.Web");
        webIntent.putExtra("url","http://www.baidu.com");
        Intent[] its = {topIntent,webIntent};
        context.startActivities(its);
        hasClick = true;
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
