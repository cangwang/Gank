package material.com.news

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.Toast

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

import material.com.base.BaseActivity
import material.com.base.event.NewsItemChangeEvent
import material.com.base.img.ImageLoader
import material.com.base.utils.ListDataSave
import material.com.news.ui.AllNewsFragment

/**
 * Created by cangwang on 2017/4/27.
 */

class NewsActivity : BaseActivity() {

    private var exitTime: Long = 0
    private var fm: FragmentManager? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        getDataFromSF()
        fm = supportFragmentManager
        replaceFm()
        EventBus.getDefault().register(this)
    }

    fun replaceFm() {
        val tr = fm!!.beginTransaction()
        val fm = AllNewsFragment()
        tr.replace(R.id.gank_frame, fm)
        tr.commitAllowingStateLoss()
    }

    @Subscribe
    fun onEvent(event: NewsItemChangeEvent) {
        ImageLoader.clearCacheMemory(application)
        replaceFm()
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

    @Throws(PackageManager.NameNotFoundException::class)
    fun getTargetContext(packageName: String): Context {
        return createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY)
    }

    private fun getDataFromSF() {
        try {
            val share = ListDataSave(getTargetContext("material.com.settings"), "gank",
                    ListDataSave.DEBUG)
            val dataSave = ListDataSave(this, "gank",
                    ListDataSave.DEBUG)
            Log.d("NewsActivity", share.getDataList<Any>("setting_data").toString())
            dataSave.setDataList("setting_data", share.getDataList<Any>("setting_data"))
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

    }
}
