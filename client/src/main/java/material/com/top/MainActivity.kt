package material.com.top


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.os.Bundle
import android.widget.Toast

import com.alibaba.android.arouter.facade.annotation.Route

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import java.util.ArrayList

import material.com.base.BaseActivity
import material.com.base.event.ChangeAdiviceEvent
import material.com.base.event.NewsItemChangeEvent
import material.com.base.event.SubmitStartEvent
import material.com.base.img.ImageLoader
import material.com.gank.ui.SplashView

/**
 * 首页
 */
@Route(path = "/gank_main/1")
class MainActivity : BaseActivity() {
    private val pageFagments = ArrayList<Fragment>()
    private val pageTitles = ArrayList<String>()

    private var exitTime: Long = 0
    private var fm: FragmentManager? = null


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        replaceFm(PageConfig.AllNewsFragment)
        EventBus.getDefault().register(this)
    }

    fun replaceFm(className: String) {
        try {
            val tr = fm!!.beginTransaction()
            val clazz = Class.forName(className)
            val fm = clazz.newInstance() as Fragment
            tr.replace(R.id.gank_frame, fm)
            tr.commitAllowingStateLoss()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }

    }


    @Subscribe
    fun onEvent(event: SubmitStartEvent) {
        replaceFm(PageConfig.SubmitFragment)
    }

    @Subscribe
    fun onEvent(event: ChangeAdiviceEvent) {
        val url = event.url
        if (url != null && !url.isEmpty())
            SplashView.updateSplashData(this, url, url)
    }

    @Subscribe
    fun onEvent(event: NewsItemChangeEvent) {
        ImageLoader.clearCacheMemory(application)
        replaceFm(PageConfig.AllNewsFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(applicationContext, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            System.exit(0)
        }
    }
}
