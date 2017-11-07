package material.com.base.utils;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by cangwang on 2017/11/7.
 */

public class ViewUtils {

    public static Fragment replaceFragment(Activity act, int containerId, FragmentManager manager, Bundle bundle, Class<? extends Fragment> cls, String tag) {
        if (act == null || act.isFinishing()) return null;
        if (Build.VERSION.SDK_INT >= 17 && act.isDestroyed()) return null;
        if (manager == null || cls == null) return null;
        Fragment fragment;
        FragmentTransaction ft = manager.beginTransaction();
        if (bundle != null)
            fragment = Fragment.instantiate(act, cls.getCanonicalName(), bundle);
        else {
            fragment = Fragment.instantiate(act, cls.getCanonicalName());
        }
        if (tag !=null && !tag.equals("")) {
            ft.replace(containerId, fragment, tag);
        } else {
            ft.replace(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
        return fragment;
    }
}
