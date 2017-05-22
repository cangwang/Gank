package material.com.web

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher

import com.alibaba.android.arouter.facade.annotation.Route

import material.com.base.BaseActivity
import material.com.base.share.ShareUtils
import material.com.base.ui.circleprogress.CircleProgressBar
import material.com.web.ui.WebContact


/**
 * 网页显示
 * Created by zjl on 2017/3/29.
 */
@Route(path = "/gank_web/1")
class WebActivity : BaseActivity(), WebContact.View {

    private var web: WebView? = null
    private var mToolBar: Toolbar? = null
    private var mActionBar: ActionBar? = null
    private var mTextSwitcher: TextSwitcher? = null
    private var mProgress: CircleProgressBar? = null

    private var baseIntent: Intent? = null

    private val prenster: WebContact.Prenster? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_activity)
        initUI()
    }

    override fun setPresenter(presenter: WebContact.Prenster) {

    }

    override fun initUI() {
        baseIntent = intent
        initToolbar()
        initWeb()
    }

    private fun initToolbar() {
        mToolBar = findViewById(R.id.web_toolbar) as Toolbar
        setSupportActionBar(mToolBar)

        mToolBar!!.setNavigationOnClickListener {
            if (web!!.canGoBack()) {
                web!!.goBack()
            } else {
                finish()
            }
        }
        mActionBar = supportActionBar
        mActionBar!!.setHomeButtonEnabled(true)
        mActionBar!!.setDisplayHomeAsUpEnabled(true)
        //        mActionBar.setTitle(intent.getStringExtra("title"));

        mTextSwitcher = findViewById(R.id.web_text_switcher) as TextSwitcher
        mTextSwitcher!!.setFactory {
            val context = this@WebActivity
            val textView = TextView(context)
            textView.setTextAppearance(context, R.style.WebTitle)
            textView.setSingleLine(true)
            textView.ellipsize = TextUtils.TruncateAt.MARQUEE
            textView.setOnClickListener { v -> v.isSelected = !v.isSelected }
            textView
        }
        mTextSwitcher!!.setInAnimation(this, android.R.anim.fade_in)
        mTextSwitcher!!.setOutAnimation(this, android.R.anim.fade_out)
        val title = baseIntent!!.getStringExtra("title")
        mTextSwitcher!!.setText(title)
        mTextSwitcher!!.isSelected = true
    }

    fun initWeb() {
        mProgress = findViewById(R.id.web_progress) as CircleProgressBar
        mProgress!!.setColorSchemeResources(R.color.common_pink_5, R.color.common_light_green_2, R.color.commen_blue_3)

        web = findViewById(R.id.web) as WebView
        web!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        val webSettings = web!!.settings

        webSettings.javaScriptEnabled = true
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true  //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小

        webSettings.displayZoomControls = false //隐藏原生的缩放控件

        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN //支持内容重新布局
        webSettings.supportMultipleWindows()  //多窗口
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings.allowFileAccess = true  //设置可以访问文件
        webSettings.setNeedInitialFocus(true) //当webview调用requestFocus时为webview设置节点
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true  //支持自动加载图片

        webSettings.defaultTextEncodingName = "UTF-8"

        web!!.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                mProgress!!.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                mProgress!!.visibility = View.GONE
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                //                super.onReceivedSslError(view, handler, error);
                handler.proceed() //接受证书
            }
        })

        web!!.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        })
        val url = baseIntent!!.getStringExtra("url")
        web!!.loadUrl(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_webview_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_share) {
            ShareUtils.shareText(this, "【" + intent.getStringExtra("title") + "】" + intent.getStringExtra("url"))
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (web != null)
            web!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (web != null)
            web!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web != null)
            web!!.destroy()
    }
}
