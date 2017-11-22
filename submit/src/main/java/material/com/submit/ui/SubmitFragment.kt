package material.com.submit.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewSwitcher

import material.com.submit.R
import material.com.submit.api.SubmitService
import material.com.submit.model.SubmitResult
import material.com.submit.prestenter.SubmitPresenter

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import material.com.base.BaseFragment
import material.com.base.retrofit.CommonRetrofit
import retrofit2.Retrofit

/**
 * Created by cangwang on 2017/3/30.
 */

class SubmitFragment : BaseFragment(), SubmitContact.View {

    private var presenter: SubmitContact.Presenter?=null
    private var mToolBar: Toolbar? = null
    private var mActionBar: ActionBar? = null
    private var mUrlTxt: TextInputEditText? = null
    private var mDescTxt: TextInputEditText? = null
    private var popMenutBtn: AppCompatButton? = null
    private var submitBtn: AppCompatButton? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.submit_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view!!)

        mUrlTxt = view.findViewById(R.id.submit_url_txt)
        mDescTxt = view.findViewById(R.id.submit_desc_txt)

        popMenutBtn = view.findViewById(R.id.submit_type_btn)
        popMenutBtn!!.setOnClickListener { showPopMenu(popMenutBtn!!) }
        submitBtn = view.findViewById(R.id.submit_btn)
        submitBtn!!.setOnClickListener { submitGank() }

        presenter = SubmitPresenter(this)
    }

    private fun initToolbar(view: View) {
        mToolBar = view.findViewById(R.id.submit_toolbar)
        mToolBar!!.title = "提交干货"
        (activity as AppCompatActivity).setSupportActionBar(mToolBar)

        mToolBar!!.setNavigationOnClickListener { activity.finish() }
        mActionBar = (activity as AppCompatActivity).supportActionBar
        mActionBar!!.setHomeButtonEnabled(true)
        mActionBar!!.setDisplayHomeAsUpEnabled(true)
        //        mActionBar.setTitle("提交干货");

    }

    fun showPopMenu(btn: AppCompatButton) {
        val menu = PopupMenu(activity, btn)
        menu.menuInflater.inflate(R.menu.menu_submit_type, menu.menu)
        menu.setOnMenuItemClickListener { item ->
            btn.text = item.title
            false
        }
        menu.show()
    }

    fun submitGank() {
        val url = mUrlTxt!!.text.toString()
        if (url.isEmpty()) {
            Toast.makeText(activity, "连接不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val desc = mUrlTxt!!.text.toString()
        if (desc.isEmpty()) {
            Toast.makeText(activity, "请填写描述", Toast.LENGTH_SHORT).show()
            return
        }
        val type = popMenutBtn!!.text.toString()
        if (type.isEmpty() || type == "选择提交类型") {
            Toast.makeText(activity, "请先选择提交类型", Toast.LENGTH_SHORT).show()
            return
        }
        val retrofit = CommonRetrofit.get().retrofit
        val newsService = retrofit!!.create(SubmitService::class.java)
        val observable = newsService.submitGank(url, desc, "", type, true)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<SubmitResult> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(newsEntity: SubmitResult) {
                        //                        datas = newsEntity.getResults();

                    }

                    override fun onError(e: Throwable) {}

                    override fun onComplete() {

                    }
                })
    }

    override fun onResume() {
        super.onResume()
        presenter!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }

    override fun loadData() {

    }

    override fun setPresenter(presenter: SubmitContact.Presenter) {

    }
}
