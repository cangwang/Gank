package material.com.news.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import material.com.news.R


/**
 * Created by zjl on 2017/3/27.
 */

class NewsHolder : RecyclerView.ViewHolder {
//    var binding: ViewDataBinding? = null
//
//    constructor(itemView: View) : super(itemView) {
//        binding = DataBindingUtil.bind<ViewDataBinding>(itemView)
//    }
//
//    constructor(bind: NewsItemBinding) : super(bind.root) {
//        binding = bind
//    }

    var imageView:ImageView
    var  title:TextView
    var  content:TextView

    constructor(itemView: View) : super(itemView) {
        imageView = itemView.findViewById(R.id.news_recyle_img)
        title = itemView.findViewById(R.id.news_recyle_title)
        content = itemView.findViewById(R.id.news_recyle_sub_title)
    }
}
