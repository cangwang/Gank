package material.com.news.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import material.com.base.img.ImageLoader
import material.com.news.R
import material.com.news.holder.FooterHolder
import material.com.news.holder.NewsHolder
import material.com.news.model.NewsItem

/**
 * Created by cangwang on 2017/3/27.
 */
class NewsReclyerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var datas: MutableList<NewsItem> = mutableListOf()

    fun setDatas(datas: MutableList<NewsItem>?) {
        if (datas != null)
            this.datas = datas
        notifyDataSetChanged()
    }

    fun addDatas(data: List<NewsItem>?) {
        if (data != null)
            datas.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        datas.clear()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsHolder) {
            val data = datas[position]
            if(data.images !=null && data.images!!.isNotEmpty() && !data.images!![0].isNullOrEmpty())
                ImageLoader.loadImge(holder.img,data.images!![0])
            holder.title.text = data.desc
            holder.sub_title.text = data.who
            holder.itemView.setOnClickListener{
                data.clickToWeb(holder.itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
            NewsHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_footer, parent, false)
            FooterHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position + 1 == itemCount) {
            return TYPE_FOOTER
        } else {
            return TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return if (datas.size == 0) {
            datas.size
        } else {
            datas.size + 1
        }
    }

    companion object {

        private val TYPE_ITEM = 0
        private val TYPE_FOOTER = 1
    }

}
