package material.com.base.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by cangwang on 2017/5/19.
 */

object NetworkUitls {
    /**
     * 判断wifi是否连接状态
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`

     * @param context 上下文
     * *
     * @return `true`: 连接<br></br>`false`: 未连接
     */
    fun isWifiConnected(context: Context): Boolean {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm != null && cm.activeNetworkInfo != null
                && cm.activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }
}
