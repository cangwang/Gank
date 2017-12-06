package material.com.base.utils

import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import material.com.base.bean.TaskBean
import java.io.*
import java.util.*

/**
 * Created by cangwang on 2017/11/9.
 */

object AppUtils{

    val UNKNOWN_PROCESS_NAME = "unknown_process_name"
    /**
     * 获取总内存容量
     */
    fun getTotalMemSize():Long{
        var size=0L
        //系统内存文件
        val file = File("/proc/meminfo")
        try {
            val buffer = BufferedReader(InputStreamReader(FileInputStream(file)))
            var memInfo = buffer.readLine()
            val startIndex = memInfo.indexOf(":")
            val endIndex =memInfo.indexOf("k")
            memInfo = memInfo.substring(startIndex+1,endIndex).trim()
            size = memInfo.toLong()
            size *= 1024
            buffer.close()
        }catch (e:IOException){
            e.printStackTrace()
        }
        return size
    }

    /**
     * 获取可用内存
     */
    fun getAviableMemSize(context:Context):Long{
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var mi = ActivityManager.MemoryInfo()
        am.getMemoryInfo(mi)
        return mi.availMem
    }

    /**
     * 获取所有进程信息（5.0以前）
     */
    fun getAllTask(context:Context):List<TaskBean> {
        val activityManager:ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageManager:PackageManager = context.packageManager
        var runList:List<ActivityManager.RunningAppProcessInfo> = activityManager.runningAppProcesses
        val list = ArrayList<TaskBean>()
        try {
            for (info in runList){
                val bean = TaskBean()
                bean.processName = info.processName
                bean.pInfo = packageManager.getPackageInfo(bean.processName,0)
                bean.drawable  =bean.pInfo!!.applicationInfo.loadIcon(packageManager)
                ApplicationInfo.FLAG_SYSTEM
                if(bean.pInfo!!.applicationInfo.flags == ApplicationInfo.FLAG_SYSTEM){
                    bean.isSystem = true
                }else{
                    bean.isUser = true
                }
                list.add(bean)
            }
        }catch (e:PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        return list
    }

    /**
     * 获取所有进程信息（5.0以前）
     */
    fun getTaskInfos(context:Context):List<TaskBean>?{
        val activityManager:ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageManager:PackageManager = context.packageManager
        var runList:List<ActivityManager.RunningAppProcessInfo> = activityManager.runningAppProcesses
        if(runList.isEmpty()) return null
        val list = ArrayList<TaskBean>()
        try {
            for (info in runList){
                val app =packageManager.getPackageInfo(info.processName,0).applicationInfo
                //过滤自己当前应用
                if (app ==null || context.packageName.equals(app.packageName)) continue

                val bean = TaskBean()
                bean.pid = info.pid
                bean.processName = info.processName  //进程名
                bean.pInfo = packageManager.getPackageInfo(bean.processName,0)  //包信息
                bean.appName = app.loadLabel(packageManager).toString() //app名
                bean.drawable  = app.loadIcon(packageManager)    //app图标
                bean.pakcageName = app.packageName  //包名
                //系统应用
                if((app.flags and ApplicationInfo.FLAG_SYSTEM) >0){
                    bean.isSystem = true
                }else{
                    bean.isUser = true
                }

                val memoryInfo = activityManager.getProcessMemoryInfo(IntArray(info.pid))
                val memsize:Double = memoryInfo[0].dalvikPrivateDirty/1024.0
                bean.memSize = memsize
                list.add(bean)

            }
        }catch (e:PackageManager.NameNotFoundException){
            e.printStackTrace()
        }
        return list
    }

    /**
     * 获取所有进程信息（5.0以后）
     */
    fun getTaskInfosL(context:Context):List<TaskBean>?{
        val list = ArrayList<TaskBean>()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val packageManager:PackageManager = context.packageManager
            val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val calendar = Calendar.getInstance()
            val endTime = calendar.timeInMillis
            calendar.add(Calendar.YEAR, -1)
            val startTime = calendar.timeInMillis
            val usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)
            for(usage in usageStatsList){
                try {
                    val bean = TaskBean()
                    val app = packageManager.getPackageInfo(usage.packageName, 0).applicationInfo
                    bean.pakcageName = usage.packageName
                    bean.processName = app.processName
                    bean.pInfo = packageManager.getPackageInfo(bean.processName, 0)
                    bean.appName = app.loadLabel(packageManager).toString() //app名
                    bean.drawable = app.loadIcon(packageManager)    //app图标
                    //系统应用
                    if ((app.flags and ApplicationInfo.FLAG_SYSTEM) > 0) {
                        bean.isSystem = true
                    } else {
                        bean.isUser = true
                    }
                    list.add(bean)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        return list
    }

    /**
     * 读取当前进程id
     */
    fun getCurrentProcessId():Int{
        return android.os.Process.myPid()
    }

    /**
     * 读取当前进程名
     */
    fun getCurrentProcessName():String{
        var processName = UNKNOWN_PROCESS_NAME
        try {
            val file = File("/proc/"+ getCurrentProcessId()+"/cmdline")
            val buffer = BufferedReader(FileReader(file))
            processName = buffer.readLine().trim()
            buffer.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return processName
    }

    fun getCurrentProcessName(pid:Int):String{
        var processName = UNKNOWN_PROCESS_NAME
        try {
            val file = File("/proc/"+ pid+"/cmdline")
            val buffer = BufferedReader(FileReader(file))
            processName = buffer.readLine().trim()
            buffer.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return processName
    }

    fun getProcessName(context:Context,pid:Int):String{
        var processName = getCurrentProcessName(pid)
        if (UNKNOWN_PROCESS_NAME .equals(processName)){
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningApps = am.runningAppProcesses
            if (runningApps!=null){
                for (info in runningApps){
                    if(info.pid == pid) return info.processName
                }
            }
        }
        return processName
    }

    /**
     * 打开指定包名的App
     */
    fun openSpecifiedApp(context:Context,packageName:String){
        val manager = context.packageManager
        val lauchIntentForPackage = manager.getLaunchIntentForPackage(packageName)
        context.startActivity(lauchIntentForPackage)
    }

    /**
     * 当前app处于前台还是后台
     */
    fun isAppForground(context: Context,packageName:String):Boolean{
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var task = am.getRunningTasks(1)
        if(!task.isEmpty()){
            val top = task.get(0).topActivity
            if (top.packageName.equals(context.packageName)){
                return true
            }
        }
        return false
    }

    /**
     * 打开指定包名的App应用信息界面
     */
    fun showAppInfo(context: Context,packageName:String){
        val intent = Intent()
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.parse("package:" + packageName)
        context.startActivity(intent)
    }

}
