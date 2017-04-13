package material.com.web.ui;

import material.com.base.BasePresenter;
import material.com.base.BaseView;

/**
 * Created by zjl on 2017/4/13.
 */

public interface WebContact {
    interface Prenster extends BasePresenter{

    }

    interface View extends BaseView<Prenster>{
        void initUI();
    }
}
