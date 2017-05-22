package material.com.submit.prestenter

import material.com.submit.ui.SubmitContact

/**
 * Created by cangwang on 2017/3/30.
 */

class SubmitPresenter(private val mSubmitView: SubmitContact.View) : SubmitContact.Presenter {

    init {
        mSubmitView.setPresenter(this)
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onDestroy() {

    }

    override fun start() {

    }
}
