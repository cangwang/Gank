package material.com.gank;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import material.com.base.BaseActivity;
import material.com.base.BaseMvpActivity;
import material.com.base.BaseMvpFragment;
import material.com.base.utils.ListDataSave;
import material.com.gank.ui.IAdivceView;
import material.com.gank.ui.SplashView;

/**
 * 广告页
 * Created by cangwang on 2017/3/30.
 */

public class AdviceActivity extends BaseMvpActivity<AdvicePresenter,IAdivceView> implements IAdivceView{
    private boolean hasClick = false;

    @Override
    public AdvicePresenter createPresenter() {
        return new AdvicePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SplashView.showSplashView(this, 3, null, R.drawable.dream, new SplashView.OnSplashViewActionListener() {
            @Override
            public void onSplashImageClick(String actionUrl) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")));
//                finish();
                presenter.startWeb();
                checkPermission();
            }

            @Override
            public void onSplashViewDismiss(boolean initiativeDismiss) {
                presenter.statMain();
                finish();
            }
        });
        presenter.dataInit();
//        SplashView.updateSplashData(AdviceActivity.this, "http://ww2.sinaimg.cn/large/72f96cbagw1f5mxjtl6htj20g00sg0vn.jpg", "http://jkyeo.com");
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                Log.i(this.getClass().getSimpleName(),"shouldShowRequestPermissionRationale = "+ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA));
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){

                }else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},101);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //requestCode是在requestPermissions最后的参数，grantResult是同时申请多个权限的数组结果
        if (requestCode == 101 &&grantResults[0] == PackageManager.PERMISSION_GRANTED){

        }
    }
}
