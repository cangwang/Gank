package material.com.server_settings;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

import material.com.base.app.BaseApplication;
import material.com.base.impl.GetActImpl;

/**
 * Created by zjl on 2017/8/11.
 */
@Interceptor(priority = 5)
public class SettingsInterceptor implements IInterceptor{
    private Context context;
    private Postcard postcard;
    private InterceptorCallback callback;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        this.postcard = postcard;
        this.callback = callback;
        if (postcard.getPath().equals("/gank_setting/1")){
//            if (!AndPermission.hasPermission(context,Manifest.permission.WRITE_SETTINGS)) {
            AndPermission.with(context)
                    .requestCode(101)
                    .permission(Manifest.permission.CAMERA)
                    .callback(this)
                    // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                    // 这样避免用户勾选不再提示，导致以后无法申请权限。
                    // 你也可以不设置。
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                            AndPermission.rationaleDialog(((GetActImpl)context).getTopActivity(), rationale)
                                    .show();
                        }
                    })
                    .start();
//            }else {
//                callback.onContinue(postcard);
//            }
        }else {
            callback.onContinue(postcard);
        }

    }

    @PermissionYes(101)
    public void getCameraSuccess(@NonNull List<String> grantedPermissions){
        callback.onContinue(postcard);
    }

    @PermissionNo(101)
    public void getCameraFail(@NonNull List<String> grantedPermissions){
        if (AndPermission.hasAlwaysDeniedPermission(((GetActImpl)context).getTopActivity(), grantedPermissions)) {
            // 第一种：用默认的提示语。
//            AndPermission.defaultSettingDialog(BaseApplication.getTopActivity()).show();
            // 第三种：自定义dialog样式。
            SettingService settingService = AndPermission.defineSettingDialog(((GetActImpl)context).getTopActivity(), 400);

            // 你的dialog点击了确定调用：
            settingService.execute();

        }
        callback.onInterrupt(new RuntimeException("权限被拒"));
    }


    PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            callback.onContinue(postcard);
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(((GetActImpl)context).getTopActivity(), deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(context).show();
            }
            callback.onInterrupt(new RuntimeException("权限被拒"));
        }
    };

}
