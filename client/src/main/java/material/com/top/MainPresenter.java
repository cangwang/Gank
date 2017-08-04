package material.com.top;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import material.com.base.BaseActivity;
import material.com.base.BasePresenter;
import material.com.base.event.ChangeAdiviceEvent;
import material.com.base.event.NewsItemChangeEvent;
import material.com.base.event.SubmitStartEvent;
import material.com.base.img.ImageLoader;
import material.com.gank.ui.SplashView;

/**
 * Created by cangwang on 2017/8/4.
 */

public class MainPresenter extends BasePresenter<IMainView>{
    private BaseActivity context;
    private long exitTime;

    public MainPresenter(BaseActivity context){
        this.context = context;
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEvent(SubmitStartEvent event){
        replaceFm(context.getSupportFragmentManager(),PageConfig.SubmitFragment);
    }

    @Subscribe
    public void onEvent(ChangeAdiviceEvent event){
        String url =event.url;
        if(url!=null && !url.isEmpty())
            SplashView.updateSplashData(context,url, url);
    }

    @Subscribe
    public void onEvent(NewsItemChangeEvent event){
        ImageLoader.clearCacheMemory(context.getApplication());
        replaceFm(context.getSupportFragmentManager(),PageConfig.AllNewsFragment);
    }

    public void replaceFm(FragmentManager fm,String className){
        try {
            FragmentTransaction tr = fm.beginTransaction();
            Class clazz = Class.forName(className);
            Fragment fg = (Fragment) clazz.newInstance();
            tr.replace(R.id.gank_frame,fg);
            tr.commitAllowingStateLoss();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        super.release();
        EventBus.getDefault().unregister(this);
    }

    public void backPress(){
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(context.getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            context.finish();
            System.exit(0);
        }
    }
}
