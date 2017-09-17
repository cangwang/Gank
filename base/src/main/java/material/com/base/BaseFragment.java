package material.com.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
/**
 * Created by zjl on 2017/3/27.
 */

public abstract class BaseFragment extends Fragment{
    private boolean HasLoadedOnce = false;
    public String sort;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Bundle bundle = getArguments();
        sort = bundle.getString("sort");
        if (isVisibleToUser){
            loadData();
        }
    }

    public abstract void loadData();
}
