package material.com.top;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import material.com.base.BaseActivity;
import material.com.base.event.ChangeAdiviceEvent;
import material.com.base.event.NewsItemChangeEvent;
import material.com.base.event.SubmitStartEvent;
import material.com.base.img.ImageLoader;
import material.com.gank.ui.SplashView;

/**
 * 首页
 */
@Route(path = "/gank_main/1")
public class MainActivity extends BaseActivity {
    private List<Fragment> pageFagments = new ArrayList<Fragment>();
    private List<String> pageTitles = new ArrayList<String>();

    private long exitTime;
    private FragmentManager fm;


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        replaceFm(PageConfig.AllNewsFragment);
        EventBus.getDefault().register(this);
    }

    public void replaceFm(String className){
        try {
            FragmentTransaction tr = fm.beginTransaction();
            Class clazz = Class.forName(className);
            Fragment fm = (Fragment) clazz.newInstance();
            tr.replace(R.id.gank_frame,fm);
            tr.commitAllowingStateLoss();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }
    }


    @Subscribe
    public void onEvent(SubmitStartEvent event){
        replaceFm(PageConfig.SubmitFragment);
    }

    @Subscribe
    public void onEvent(ChangeAdiviceEvent event){
        String url =event.url;
        if(url!=null && !url.isEmpty())
            SplashView.updateSplashData(this,url, url);
    }

    @Subscribe
    public void onEvent(NewsItemChangeEvent event){
        ImageLoader.clearCacheMemory(getApplication());
        replaceFm(PageConfig.AllNewsFragment);
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
}
