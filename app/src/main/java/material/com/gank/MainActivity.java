package material.com.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> pageFagments = new ArrayList<Fragment>();
    private List<String> pageTitles = new ArrayList<String>();
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.gank_tab);
        mViewPager =(ViewPager) findViewById(R.id.gank_view_pager);
        pageTitles = PageConfig.getPageTitles(this);
        try{
            for(String address:PageConfig.fragmentNames){
                //反射获得Class
                Class clazz = Class.forName(address);
                //创建类
                Fragment tab = (Fragment) clazz.newInstance();
                //添加到viewPagerAdapter的资源
                pageFagments.add(tab);
            }
        }catch (ClassNotFoundException e){

        }catch (IllegalAccessException e){

        }catch (InstantiationException e){

        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),pageFagments,pageTitles);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        //全部预加载
//        mViewPager.setOffscreenPageLimit(pageFagments.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
