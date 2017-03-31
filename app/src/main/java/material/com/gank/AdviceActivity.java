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

import material.com.gank.ui.SplashView;

/**
 * Created by air on 2017/3/30.
 */

public class AdviceActivity extends AppCompatActivity{
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")));
                finish();
            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {
                startActivity(new Intent("material.com.top.MAIN"));
//                Toast.makeText(AdviceActivity.this,"欢迎到Gank",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
//        SplashView.updateSplashData(AdviceActivity.this, "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg", "http://jkyeo.com");
    }
}
