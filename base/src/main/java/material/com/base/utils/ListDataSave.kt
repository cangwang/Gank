package material.com.base.utils

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Vector

/**
 * Created by air on 2017/4/2.
 */

class ListDataSave {
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    constructor(mContext: Context, preferenceName: String) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_WORLD_READABLE)
        editor = preferences!!.edit()
    }

    constructor(mContext: Context, preferenceName: String, type: Int) {
        if (type == DEBUG) {
            preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_MULTI_PROCESS or Context.MODE_WORLD_READABLE)
        } else {
            preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        }
        if (preferences != null)
            editor = preferences!!.edit()
    }

    /**
     * 保存List
     * @param tag
     * *
     * @param datalist
     */
    fun <T> setDataList(tag: String, datalist: List<T>?) {
        if (null == datalist || datalist.size <= 0)
            return

        val gson = Gson()
        //转换成json数据，再保存
        val strJson = gson.toJson(datalist)
        editor!!.clear()
        editor!!.putString(tag, strJson)
        editor!!.commit()

    }

    /**
     * 获取List
     * @param tag
     * *
     * @return
     */
    fun <T> getDataList(tag: String): List<T> {
        var datalist: List<T> = Vector()
        val strJson = preferences!!.getString(tag, null) ?: return datalist
        val gson = Gson()
        datalist = gson.fromJson<List<T>>(strJson, object : TypeToken<List<T>>() {

        }.type)
        return datalist

    }

    companion object {
        val DEBUG = 1
        val DEV = 2
        val PUBLISH = 3
    }
}
