package material.com.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by cangwang on 2017/8/15.
 */

public class AppMetaUtil {
    public static int channelNum=0;

    public static Object getMetaData(Context context, String metatName){
        Object obj= null;
        try {
            if (context !=null){
                String pkgName = context.getPackageName();
                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
                obj = appInfo.metaData.get(metatName);
            }
        }catch (Exception e){
            Log.e("AppMetaUtil",e.toString());
        }finally {
            return obj;
        }
    }

    public static int getChannelNum(Context context){
        if (channelNum <= 0){
            Object obj = AppMetaUtil.getMetaData(context,"channel");
            if (obj!=null && obj instanceof Integer){
                return (int)obj;
            }
        }
        return channelNum;
    }
}
