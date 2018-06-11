package material.com.news.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import material.com.news.R


/**
 * Created by zjl on 2017/3/27.
 */

class NewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public var img:ImageView
    public var title:TextView
    public var sub_title:TextView

    init {
        img =itemView.findViewById(R.id.news_recyle_img)
        title = itemView.findViewById(R.id.news_recyle_title)
        sub_title = itemView.findViewById(R.id.news_recyle_sub_title)
    }
}
