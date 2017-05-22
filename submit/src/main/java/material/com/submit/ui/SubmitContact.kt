package material.com.submit.ui

import material.com.base.BasePresenter
import material.com.base.BaseView

/**
 * Created by air on 2017/3/31.
 */

interface SubmitContact {
    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
        fun onResume()
        fun onPause()
        fun onDestroy()
    }
}
