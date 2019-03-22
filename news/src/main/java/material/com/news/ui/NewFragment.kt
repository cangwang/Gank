package material.com.news.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import material.com.base.BaseFragment
import material.com.base.retrofit.CommonRetrofit
import material.com.news.R
import material.com.news.adapter.NewsReclyerAdapter
import material.com.news.api.NewsService
import material.com.news.model.NewsEntity
import material.com.news.model.NewsItem
import retrofit2.Retrofit

/**
 * Created by zjl on 2017/3/27.
 */

class NewFragment : BaseFragment() {
    private var v: View? = null
    private var mRecyclerView: RecyclerView? = null
    private var mSwipeRefreshlayout: SwipeRefreshLayout? = null
    private var adapter: NewsReclyerAdapter? = null
    private var datas: MutableList<NewsItem>? = ArrayList()

    private var retrofit: Retrofit? = null
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater!!.inflate(R.layout.news_fragment, container, false)
        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSwipeRefreshlayout = view.findViewById(R.id.news_swipe_refresh)
        mSwipeRefreshlayout!!.setColorSchemeResources(R.color.common_pink_5, R.color.common_light_green_2, R.color.commen_blue_3)
        mSwipeRefreshlayout!!.setOnRefreshListener {
            page = 1
            loadData()
        }
        mRecyclerView = view.findViewById(R.id.news_recyclerview)
        val layoutManager = LinearLayoutManager(activity)
        mRecyclerView!!.layoutManager = layoutManager
        mRecyclerView!!.setHasFixedSize(true)
        adapter = NewsReclyerAdapter(activity!!)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.itemAnimator = DefaultItemAnimator()
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition()+1 == adapter.getItemCount()){
                //                    page++;
                //                    loadData();
                //                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {  //当屏幕停止滚动，加载图片
                    if (layoutManager.findLastVisibleItemPosition() + 1 == adapter!!.itemCount) {
                        page++
                        loadData()
                    }
                    Glide.with(context).resumeRequests()
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {  //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    Glide.with(context).pauseRequests()
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {  //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    Glide.with(context).pauseRequests()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mSwipeRefreshlayout!!.isEnabled = layoutManager.findFirstCompletelyVisibleItemPosition() == 0
            }
        })
    }

    override fun loadData() {
        Log.d(TAG, "sort = " + sort)
        retrofit = CommonRetrofit.get().retrofit
        val newsService = retrofit!!.create(NewsService::class.java)
        val observable = newsService.getNews(sort!!, page)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<NewsEntity> {
                    override fun onSubscribe(d: Disposable) {
                        if (mSwipeRefreshlayout != null && !mSwipeRefreshlayout!!.isRefreshing)
                            mSwipeRefreshlayout!!.isRefreshing = true
                    }

                    override fun onNext(newsEntity: NewsEntity) {
                        datas = newsEntity.results!!.toMutableList()
                        if (page == 1) {
                            adapter!!.setDatas(datas)
                        } else {
                            adapter!!.addDatas(datas)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mSwipeRefreshlayout != null && mSwipeRefreshlayout!!.isRefreshing)
                            mSwipeRefreshlayout!!.isRefreshing = false
                    }

                    override fun onComplete() {
                        if (mSwipeRefreshlayout != null && mSwipeRefreshlayout!!.isRefreshing)
                            mSwipeRefreshlayout!!.isRefreshing = false
                    }
                })

    }

    override fun onDestroyView() {
        adapter!!.clearData()
        datas = null
        super.onDestroyView()
    }

    companion object {
        private val TAG = "NewFragment"
    }
}
