package material.com.base;

import android.support.v4.app.Fragment;

/**
 * Created by zjl on 2017/3/27.
 */

public abstract class BaseFragment extends Fragment{
    private boolean HasLoadedOnce = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            loadData();
//            HasLoadedOnce=true;
        }
    }

    public abstract void loadData();
}
