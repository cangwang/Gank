package material.com.gank;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import material.com.base.BaseActivity;
import material.com.gank.ui.SplashView;

/**
 * 广告页
 * Created by cangwang on 2017/3/30.
 */

public class AdviceActivity extends BaseActivity{
    private int adviceReqCode = 100;
    private boolean hasClick = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SplashView.showSplashView(this, 3, null, R.drawable.dream, new SplashView.OnSplashViewActionListener() {
            @Override
            public void onSplashImageClick(String actionUrl) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")));
                Intent topIntent = new Intent(new Intent("material.com.top.MAIN"));
                Intent webIntent = new Intent("material.com.web.Web");
                webIntent.putExtra("url","http://www.baidu.com");
                Intent[] its = {topIntent,webIntent};
                startActivities(its);
                hasClick = true;
//                finish();
            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {
                if(!hasClick) {
//                    startActivity(new Intent("material.com.top.MAIN"));
                    ARouter.getInstance().build("/gank_main/1").navigation();
                }
                finish();
            }
        });
//        SplashView.updateSplashData(AdviceActivity.this, "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg", "http://jkyeo.com");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == adviceReqCode)
//    }
}
