package material.com.top;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import material.com.base.event.SubmitStartEvent;
import material.com.news.ui.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> pageFagments = new ArrayList<Fragment>();
    private List<String> pageTitles = new ArrayList<String>();
    private TabLayout tabLayout;

    private long exitTime;
//    private FragmentTransaction tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tr = getSupportFragmentManager().beginTransaction();
        replaceFm(PageConfig.AllNewsFragment);

        EventBus.getDefault().register(this);

    }

    public void replaceFm(String className){
        try {
            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
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
