package material.com.web.ui

import material.com.base.BasePresenter
import material.com.base.BaseView

/**
 * Created by zjl on 2017/4/13.
 */

interface WebContact {
    interface Prenster : BasePresenter

    interface View : BaseView<Prenster> {
        fun initUI()
    }
}
