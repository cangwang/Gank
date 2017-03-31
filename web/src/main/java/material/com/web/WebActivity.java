package material.com.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import material.com.base.ui.CircleProgressBar;
import material.com.base.ui.MaterialProgressDrawable;

/**
 * Created by zjl on 2017/3/29.
 */

public class WebActivity extends AppCompatActivity{

    private WebView web;
    private Toolbar mToolBar;
    private ActionBar mActionBar;
    private CircleProgressBar mProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        initUI();
    }

    private void initUI(){
        Intent intent = getIntent();
        mToolBar = (Toolbar) findViewById(R.id.web_toolbar);
        setSupportActionBar(mToolBar);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(web.canGoBack()){
                    web.goBack();
                }else {
                    finish();
                }
            }
        });
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(intent.getStringExtra("title"));

        mProgress = (CircleProgressBar) findViewById(R.id.web_progress);
        mProgress.setColorSchemeResources(R.color.common_pink_5, R.color.common_light_green_2, R.color.commen_blue_3);

        web = (WebView)findViewById(R.id.web);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = web.getSettings();

        webSettings.setJavaScriptEnabled(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)

        webSettings.setDefaultTextEncodingName("UTF-8");

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgress.setVisibility(View.GONE);
            }
        });

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        web.loadUrl(intent.getStringExtra("url"));
    }
}
