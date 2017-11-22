package material.com.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.alibaba.android.arouter.launcher.ARouter

import java.util.ArrayList
import java.util.Vector

import material.com.base.BaseFragment
import material.com.base.utils.ListDataSave
import material.com.news.BuildConfig
import material.com.news.R
import material.com.news.adapter.ViewPagerAdapter

/**
 * Created by zjl on 2017/3/30.
 */

class AllNewsFragment : BaseFragment() {

    private var mViewPager: ViewPager? = null
    private var mViewPagerAdapter: ViewPagerAdapter? = null
    private val pageFagments = ArrayList<Fragment>()
    private var pageTitles: List<String> = Vector()
    private var tabLayout: TabLayout? = null
    private var addNewBtn: FloatingActionButton? = null

    private var v: View? = null

    private var dataSave: ListDataSave? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater!!.inflate(R.layout.news_all_fragment, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataSave = ListDataSave(context, "gank", if (BuildConfig.BUILD_TYPE == "debug") ListDataSave.DEBUG else ListDataSave.PUBLISH)
        pageTitles = dataSave!!.getDataList<String>("setting_data")
        val toolbar = view!!.findViewById<Toolbar>(R.id.news_toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        tabLayout = view.findViewById(R.id.news_gank_tab)
        mViewPager = view.findViewById(R.id.news_gank_view_pager)
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


        for (item in pageTitles) {
            val tab = NewFragment()
            val bundle = Bundle()
            bundle.putString("sort", item)
            tab.arguments = bundle
            pageFagments.add(tab)
        }

        tabLayout!!.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout!!.setupWithViewPager(mViewPager)
        mViewPagerAdapter = ViewPagerAdapter(childFragmentManager, pageFagments, pageTitles)

        mViewPager!!.adapter = mViewPagerAdapter
        mViewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        addNewBtn = view.findViewById(R.id.news_add_float_btn)
        addNewBtn!!.setOnClickListener {
            //                EventBus.getDefault().post(new SubmitStartEvent());
//            startActivity(Intent("com.cangwang.submit"));
            ARouter.getInstance().build("/gank_submit/1").navigation()
        }
    }

    override fun loadData() {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_news_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        if (id == R.id.action_settings) {
//            startActivity(Intent("material.com.settings"));
            ARouter.getInstance().build("/gank_setting/1").navigation()
        }
        return super.onOptionsItemSelected(item)
    }
}
