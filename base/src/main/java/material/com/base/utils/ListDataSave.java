package material.com.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Vector;

/**
 * Created by air on 2017/4/2.
 */

public class ListDataSave {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final int DEBUG = 1;
    public static final int DEV = 2;
    public static final int PUBLISH = 3;

    public ListDataSave(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_WORLD_READABLE);
        editor = preferences.edit();
    }

    public ListDataSave(Context mContext, String preferenceName,int type) {
        if (type == DEBUG) {
            preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_MULTI_PROCESS | Context.MODE_WORLD_READABLE);
        }else {
            preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        }
        if(preferences !=null)
            editor = preferences.edit();
    }

    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public <T> List<T> getDataList(String tag) {
        List<T> datalist = new Vector<>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }
}
