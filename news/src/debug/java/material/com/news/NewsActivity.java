package material.com.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import material.com.base.BaseActivity;
import material.com.base.event.NewsItemChangeEvent;
import material.com.base.img.ImageLoader;
import material.com.base.utils.ListDataSave;
import material.com.news.R;
import material.com.news.ui.AllNewsFragment;

/**
 * Created by cangwang on 2017/4/27.
 */

public class NewsActivity extends BaseActivity{

    private long exitTime;
    private FragmentManager fm;


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getDataFromSF();
        fm = getSupportFragmentManager();
        replaceFm();
        EventBus.getDefault().register(this);
    }

    public void replaceFm(){
        FragmentTransaction tr = fm.beginTransaction();
        AllNewsFragment fm = new AllNewsFragment();
        tr.replace(R.id.gank_frame,fm);
        tr.commitAllowingStateLoss();
    }

    @Subscribe
    public void onEvent(NewsItemChangeEvent event){
        ImageLoader.clearCacheMemory(getApplication());
        replaceFm();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    public Context getTargetContext(final String packageName) throws PackageManager.NameNotFoundException{
        return createPackageContext(packageName,Context.CONTEXT_IGNORE_SECURITY);
    }

    private void getDataFromSF(){
        try{
//            SharedPreferences share = getTargetContext().getSharedPreferences("gank",Context.MODE_MULTI_PROCESS|Context.MODE_WORLD_READABLE);
            ListDataSave share = new ListDataSave(getTargetContext("material.com.settings"),"gank",0);
            ListDataSave dataSave =  new ListDataSave(this,"gank");
            dataSave.setDataList("setting_data",share.getDataList("setting_data"));
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
    }


}
