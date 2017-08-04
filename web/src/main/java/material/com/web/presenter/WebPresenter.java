package material.com.web.presenter;

import android.content.Intent;

import material.com.base.BaseActivity;
import material.com.base.BasePresenter;
import material.com.base.share.ShareUtils;
import material.com.web.ui.IWebView;

/**
 * 网页适配
 * Created by zjl on 2017/4/13.
 */

public class WebPresenter extends BasePresenter<IWebView>{
    private BaseActivity context;

    public WebPresenter(BaseActivity context){
        this.context =context;
    }

    public void shareText(Intent intent){
        ShareUtils.shareText(context,"【"+intent.getStringExtra("title")+"】"+intent.getStringExtra("url"));
    }

    @Override
    public void release() {
        context=null;
        super.release();
    }
}
