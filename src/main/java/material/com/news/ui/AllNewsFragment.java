package material.com.news.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import material.com.base.BaseFragment;
import material.com.base.utils.ListDataSave;
import material.com.news.BuildConfig;
import material.com.news.R;
import material.com.news.adapter.ViewPagerAdapter;

/**
 * Created by zjl on 2017/3/30.
 */

public class AllNewsFragment extends BaseFragment{

    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> pageFagments = new ArrayList<Fragment>();
    private List<String> pageTitles = new Vector<>();
    private TabLayout tabLayout;
    private FloatingActionButton addNewBtn;

    private View view;

    private ListDataSave dataSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.news_all_fragment,container,false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataSave = new ListDataSave(getContext(),"gank", BuildConfig.BUILD_TYPE.equals("debug")? ListDataSave.DEBUG:ListDataSave.PUBLISH);
        pageTitles = dataSave.getDataList("setting_data");
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.news_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        tabLayout = (TabLayout) view.findViewById(R.id.news_gank_tab);
        mViewPager =(ViewPager) view.findViewById(R.id.news_gank_view_pager);
//        pageTitles = PageConfig.getPageTitles();
//        try{
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
//        }catch (java.lang.InstantiationException e){
//            e.printStackTrace();
//        }


        for(String item :pageTitles){
            Fragment tab = new NewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("sort",item);
            tab.setArguments(bundle);
            pageFagments.add(tab);
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),pageFagments,pageTitles);

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
        addNewBtn = (FloatingActionButton)view.findViewById(R.id.news_add_float_btn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                EventBus.getDefault().post(new SubmitStartEvent());
//                startActivity(new Intent("com.cangwang.submit"));
                ARouter.getInstance().build("/gank_submit/1").navigation();
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_toolbar,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
//            startActivity(new Intent("material.com.settings"));
            ARouter.getInstance().build("/gank_setting/1").navigation();
        }
        return super.onOptionsItemSelected(item);
    }
}
