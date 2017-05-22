package material.com.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by zjl on 2017/3/27.
 */

abstract class BaseFragment : Fragment() {
    private val HasLoadedOnce = false
    var sort: String?=null

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        val bundle = arguments
        sort = bundle.getString("sort")
        if (isVisibleToUser) {
            loadData()
        }
    }

    abstract fun loadData()
}
