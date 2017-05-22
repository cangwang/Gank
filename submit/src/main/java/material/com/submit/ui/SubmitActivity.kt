package material.com.submit.ui

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import com.alibaba.android.arouter.facade.annotation.Route

import material.com.submit.R

import material.com.base.BaseActivity

/**
 * 提交页
 * Created by cangwang on 2017/4/1.
 */
@Route(path = "/gank_submit/1")
class SubmitActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.submit_activity)
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        val sf = SubmitFragment()
        tr.replace(R.id.submit_activity, sf)
        tr.commitAllowingStateLoss()
    }
}
