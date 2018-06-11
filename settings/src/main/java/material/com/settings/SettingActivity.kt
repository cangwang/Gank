package material.com.settings

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import com.alibaba.android.arouter.facade.annotation.Route

import org.greenrobot.eventbus.EventBus

import java.util.LinkedList
import java.util.Vector

import material.com.base.*
import material.com.base.event.NewsItemChangeEvent
import material.com.base.ui.flow.FlowLayout
import material.com.base.utils.BaseUtils
import material.com.base.utils.ListDataSave

/**
 * 设置
 * Created by cangwang on 2017/4/1.
 */
@Route(path = "/gank_setting/1")
class SettingActivity : BaseActivity() {

    private var setLayout: FlowLayout? = null
    private var allData: List<String> = Vector()
    private var setData: MutableList<String> = Vector()

    private var mToolBar: Toolbar? = null
    private var mActionBar: ActionBar? = null
    private var dataSave: ListDataSave? = null

    private var isChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        initToolbar()
        dataSave = ListDataSave(this, "gank", if (BuildConfig.BUILD_TYPE == "debug") ListDataSave.DEBUG else ListDataSave.PUBLISH)
        setData = dataSave!!.getDataList<String>("setting_data").toMutableList()
        setLayout = findViewById(R.id.settings_set_flow)
        allData = SettingConfig.unSetDats
        for (item in allData) {
            addUnsetItem(setLayout!!, item)
        }
    }


    private fun initToolbar() {
        mToolBar = findViewById(R.id.settings_toolbar)
        mToolBar!!.title = "设置页面"
        setSupportActionBar(mToolBar)

        mToolBar!!.setNavigationOnClickListener { finish() }
        mActionBar = supportActionBar
        mActionBar!!.setHomeButtonEnabled(true)
        mActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun addUnsetItem(layout: FlowLayout, item: String) {
        val ranHeight = BaseUtils.dip2px(this, 30f)
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight)
        lp.setMargins(BaseUtils.dip2px(this, 10f), 0, BaseUtils.dip2px(this, 10f), 0)
        val tv = TextView(this)
        tv.setPadding(BaseUtils.dip2px(this, 15f), 0, BaseUtils.dip2px(this, 15f), 0)

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        tv.text = item
        tv.gravity = Gravity.CENTER_VERTICAL
        tv.setLines(1)
        if (!setData.contains(item)) {
            tv.setTag(R.id.tag_set, "unset")
            tv.setTextColor(Color.parseColor("#FF3030"))
            tv.setBackgroundResource(R.drawable.bg_tag)
        } else {
            tv.setTag(R.id.tag_set, "set")
            tv.setTextColor(resources.getColor(R.color.commen_blue_8))
            tv.setBackgroundResource(R.drawable.bg_select)
        }
        tv.setOnClickListener { v ->
            val txt = (v as TextView).text.toString()
            if (v.getTag(R.id.tag_set) == "unset") {
                v.setTag(R.id.tag_set, "set")
                val index = allData.indexOf(txt)
                if (index >= setData.size) {
                    setData.add(txt)
                } else {
                    setData.add(index, txt)
                }
                v.setTextColor(resources.getColor(R.color.commen_blue_8))
                v.setBackgroundResource(R.drawable.bg_select)
            } else {
                v.setTag(R.id.tag_set, "unset")
                setData.remove(txt)
                v.setTextColor(Color.parseColor("#FF3030"))
                v.setBackgroundResource(R.drawable.bg_tag)
            }
            dataSave!!.setDataList("setting_data", setData)
            isChange = true
        }
        layout.addView(tv, lp)
    }

    override fun onDestroy() {
        if (isChange) {
            EventBus.getDefault().post(NewsItemChangeEvent())
        }
        dataSave = null
        super.onDestroy()
    }
}
