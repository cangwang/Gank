package material.com.submit.ui;

import material.com.base.BasePresenter;
import material.com.base.BaseView;

/**
 * Created by air on 2017/3/31.
 */

public interface SubmitContact {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void onResume();
        void onPause();
        void onDestroy();
    }
}
