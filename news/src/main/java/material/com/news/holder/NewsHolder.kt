package material.com.news.holder

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

import material.com.news.databinding.NewsItemBinding

/**
 * Created by zjl on 2017/3/27.
 */

class NewsHolder : RecyclerView.ViewHolder {
    var binding: ViewDataBinding? = null

    constructor(itemView: View) : super(itemView) {
        binding = DataBindingUtil.bind<ViewDataBinding>(itemView)
    }

    constructor(bind: NewsItemBinding) : super(bind.root) {
        binding = bind
    }
}
