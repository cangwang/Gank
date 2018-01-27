package material.com.base.bean

import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable

/**
 * Created by cangwang on 2017/11/9.
 */
class TaskBean(){
    lateinit var processName:String
    lateinit var pakcageName:String
    lateinit var appName:String
    lateinit var pInfo: PackageInfo
    lateinit var drawable: Drawable
    var pid = 0
    var memSize:Double = 0.0
    var isSystem:Boolean= false
    var isUser:Boolean = false
}