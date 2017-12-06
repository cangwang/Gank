package material.com.news.adapter

import android.content.Context
//import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import material.com.base.img.ImageLoader

import java.util.ArrayList

//import material.com.news.BR
import material.com.news.R
import material.com.news.holder.FooterHolder
import material.com.news.holder.NewsHolder
//import material.com.news.databinding.NewsItemBinding
import material.com.news.model.NewsItem

/**
 * Created by cangwang on 2017/3/27.
 */
class NewsReclyerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var datas: MutableList<NewsItem>? = mutableListOf()

    fun setDatas(datas: MutableList<NewsItem>?) {
        if (datas != null)
            this.datas = datas
        notifyDataSetChanged()
    }

    fun addDatas(data: List<NewsItem>?) {
        if (data != null)
            datas!!.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        datas = null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (holder is NewsHolder) {
//            val data = datas!![position]
//            holder.binding!!.setVariable(BR.news, data)
//            holder.binding!!.executePendingBindings()
//        }
        if (holder is NewsHolder){
            val n = datas!!.get(position)
            holder.imageView.visibility = n.getHasImage()
            if (n.getHasImage() == View.VISIBLE)
                ImageLoader.loadImge(holder.imageView,n.url)
            holder.title.setText(n.desc)
            holder.content.setText(n.who)
            holder.itemView.setOnClickListener { n.clickToWeb(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM) {
//            val binding = DataBindingUtil.inflate<NewsItemBinding>(LayoutInflater.from(context), R.layout.news_item, parent, false)
            val binding = LayoutInflater.from(context).inflate(R.layout.news_item_normal,parent,false)
            return NewsHolder(binding)
        } else {
//            val converView = LayoutInflater.from(parent.context).inflate(R.layout.news_footer, parent, false)
            val converView = LayoutInflater.from(context).inflate(R.layout.news_footer,parent,false)
            return FooterHolder(converView)
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
        if (datas!!.size == 0) {
            return datas!!.size
        } else {
            return datas!!.size + 1
        }
    }

    companion object {

        private val TYPE_ITEM = 0
        private val TYPE_FOOTER = 1
    }

}
