package material.com.gank

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast

import com.alibaba.android.arouter.launcher.ARouter

import java.util.ArrayList

import material.com.base.BaseActivity
import material.com.base.utils.ListDataSave
import material.com.gank.ui.SplashView

/**
 * 广告页
 * Created by cangwang on 2017/3/30.
 */

class AdviceActivity : BaseActivity() {
    private val adviceReqCode = 100
    private var hasClick = false
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        SplashView.showSplashView(this, 3, null, R.drawable.dream, object : SplashView.OnSplashViewActionListener {
            override fun onSplashImageClick(actionUrl: String) {
                //                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")));
                val topIntent = Intent(Intent("material.com.top.MAIN"))
                val webIntent = Intent("material.com.web.Web")
                webIntent.putExtra("url", "http://www.baidu.com")
                val its = arrayOf(topIntent, webIntent)
                startActivities(its)
                hasClick = true
                //                finish();
            }

            override fun onSplashViewDismiss(initiativeDismiss: Boolean) {
                if (!hasClick) {
                    startActivity(Intent("material.com.top.MAIN"));
//                    ARouter.getInstance().build("/gank_main/1").navigation()
                }
                finish()
            }
        })
        dataInit()
        //        SplashView.updateSplashData(AdviceActivity.this, "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg", "http://jkyeo.com");
    }

    private fun dataInit() {
        val sp = getSharedPreferences("gank", Context.MODE_PRIVATE)
        val firstEnter = sp.getBoolean("first_enter", false)
        if (!firstEnter) {
            val dataSave = ListDataSave(this, "gank", if (BuildConfig.BUILD_TYPE == "debug") ListDataSave.DEBUG else ListDataSave.PUBLISH)
            val setData = ArrayList<String>()
            setData.add("all")
            setData.add("Android")
            setData.add("iOS")
            setData.add("福利")
            dataSave.setDataList("setting_data", setData)
            val editor = sp.edit()
            editor.putBoolean("first_enter", true)
            editor.commit()
        }
    }
}
