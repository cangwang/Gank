package material.com.news.ui;

import android.support.v4.view.PagerAdapter;

import material.com.base.BaseView;
import material.com.news.adapter.ViewPagerAdapter;

/**
 * Created by cangwang on 2017/8/4.
 */

public interface IAllNewView extends BaseView{
    void initPageAapter(PagerAdapter adapter);
}
