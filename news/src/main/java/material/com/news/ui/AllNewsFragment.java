package material.com.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import material.com.base.BaseMvpFragment;
import material.com.base.utils.AppMetaUtil;
import material.com.news.R;
import material.com.news.presenter.AllNewsPresenter;

/**
 * Created by zjl on 2017/3/30.
 */

public class AllNewsFragment extends BaseMvpFragment<AllNewsPresenter,IAllNewView> implements IAllNewView{

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private FloatingActionButton addNewBtn;
    private View view;

    @Override
    public AllNewsPresenter createPresenter() {
        return new AllNewsPresenter(this);
    }

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
        presenter.getData();
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.news_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        tabLayout = (TabLayout) view.findViewById(R.id.news_gank_tab);
        mViewPager =(ViewPager) view.findViewById(R.id.news_gank_view_pager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);

        presenter.setAdapter();
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
        if (AppMetaUtil.getChannelNum(getActivity().getApplicationContext())==10087){
            addNewBtn.setVisibility(View.GONE);
        }
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.navigationSubmit();
            }
        });
    }

    @Override
    public void initPageAapter(PagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
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
            presenter.navigationSettings();
        }
        return super.onOptionsItemSelected(item);
    }
}
