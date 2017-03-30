package material.com.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import material.com.news.ui.ViewPagerAdapter;

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
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        try {
            Class clazz = Class.forName(PageConfig.AllNewsFragment);
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

//        tabLayout = (TabLayout) findViewById(R.id.gank_tab);
//        mViewPager =(ViewPager) findViewById(R.id.gank_view_pager);
//        pageTitles = PageConfig.getPageTitles();
//        try{
////            for(String address:PageConfig.fragmentNames){
//            for (int i=0;i<PageConfig.fragmentNames.length;i++){
//                String address = PageConfig.fragmentNames[i];
//                //反射获得Class
//                Class clazz = Class.forName(address);
//                //创建类
//                Fragment tab = (Fragment) clazz.newInstance();
//                Bundle bundle = new Bundle();
//                bundle.putString("sort",PageConfig.getPageTitles().get(i));
//                tab.setArguments(bundle);
//                //添加到viewPagerAdapter的资源
//                pageFagments.add(tab);
//            }
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }catch (IllegalAccessException e){
//            e.printStackTrace();
//        }catch (InstantiationException e){
//            e.printStackTrace();
//        }
//
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabLayout.setupWithViewPager(mViewPager);
//        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),pageFagments,pageTitles);
//
//        mViewPager.setAdapter(mViewPagerAdapter);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


//        //全部预加载
//        mViewPager.setOffscreenPageLimit(pageFagments.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
