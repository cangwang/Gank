package material.com.news.api;

import android.app.Application;

import material.com.base.BaseAppInt;

/**
 * Created by cangwang on 2017/7/25.
 */

public class NewsInit implements BaseAppInt{

    @Override
    public boolean onInitLow(Application application) {
        /**你的操作**/
        return false;
    }

    @Override
    public boolean onInitSpeed(Application application) {
        /**你的操作**/
        return false;
    }
}
