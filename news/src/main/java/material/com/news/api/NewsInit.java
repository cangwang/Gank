package material.com.news.api;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

import material.com.base.BaseAppInt;

/**
 * Created by cangwang on 2017/7/25.
 */

public class NewsInit implements IProvider{

    @Override
    public void init(Context context) {

    }

    public boolean onInitLow(Application application) {
        /**你的操作**/
        return false;
    }

    public boolean onInitSpeed(Application application) {
        /**你的操作**/
        return false;
    }
}
