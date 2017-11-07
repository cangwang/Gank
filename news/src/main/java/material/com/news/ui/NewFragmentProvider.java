package material.com.news.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;

import material.com.base.utils.ViewUtils;

/**
 * Created by zjl on 2017/11/7.
 */
@Route(path = "/gank_news/new_fragment_provider")
public class NewFragmentProvider implements IProvider{

    @Override
    public void init(Context context) {
    }

    public Fragment newInstance(Activity context, @IdRes int containerId, FragmentManager manager, Bundle bundle,String tag){
        return ViewUtils.replaceFragment(context,containerId,manager,bundle,NewFragment.class,tag);
    }
}
