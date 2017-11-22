package material.com.base.img

import android.app.Application
import android.content.Context
//import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Looper
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.stream.StreamModelLoader

import java.io.IOException
import java.io.InputStream

import material.com.base.R
import material.com.base.utils.NetworkUitls

/**
 * Created by zjl on 2017/3/29.
 */

object ImageLoader {

//    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, bitmap: Bitmap) {
        view.setImageBitmap(bitmap)
    }

//    @BindingAdapter("android:src")
    fun setSrc(view: ImageView, @DrawableRes resId: Int) {
        view.setImageResource(resId)
    }

//    @BindingAdapter("android:src")
    fun setSrc(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(imageView)
    }

//    @BindingAdapter("imageurl")
    fun loadImge(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(imageView)
    }

//    @BindingAdapter(value = *arrayOf("imageurl", "holderDrawable", "errorDrawable"), requireAll = false)
//    @JvmStatic fun loadImge(imageView: ImageView, url: String?, holderDrawable: Drawable, errorDrawable: Drawable) {
//        if (NetworkUitls.isWifiConnected(imageView.context)) {
//            loadNormal(imageView, url, holderDrawable, errorDrawable)
//        } else {
//            loadCache(imageView, url, holderDrawable, errorDrawable)
//        }
//        //        if (url==null||url.equals("")){
//        //            imageView.setVisibility(View.GONE);
//        //            return;
//        //        }
//        //        Glide.get(imageView.getContext())
//        //                .setMemoryCategory(MemoryCategory.NORMAL);
//        //        Glide.with(imageView.getContext())
//        //                .load(url)
//        //                .thumbnail(0.5f)
//        //                .placeholder(holderDrawable)
//        //                .error(errorDrawable)
//        //                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//        //                .crossFade()
//        //                .into(imageView)
//        //                .onLoadStarted(holderDrawable);
//    }

    private fun loadNormal(imageView: ImageView, url: String?, holderDrawable: Drawable, errorDrawable: Drawable) {
        Glide.with(imageView.context)
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .animate(R.anim.fade_in)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView)
    }

    private fun loadCache(imageView: ImageView, url: String?, holderDrawable: Drawable, errorDrawable: Drawable) {
        Glide.with(imageView.context)
                .using(StreamModelLoader<String> { model, i, i1 ->
                    object : DataFetcher<InputStream> {
                        @Throws(Exception::class)
                        override fun loadData(priority: Priority): InputStream {
                            throw IOException()
                        }

                        override fun cleanup() {

                        }

                        override fun getId(): String {
                            return model
                        }

                        override fun cancel() {

                        }
                    }
                })
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .animate(R.anim.fade_in)
                .into(imageView)
    }


    // 清除Glide内存缓存
    fun clearCacheMemory(application: Application): Boolean {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(application).clearMemory()
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}
