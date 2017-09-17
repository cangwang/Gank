package material.com.base.img;

import android.app.Application;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.IOException;
import java.io.InputStream;

import material.com.base.R;
import material.com.base.utils.NetworkUitls;

/**
 * Created by zjl on 2017/3/29.
 */

public class ImageLoader {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap){
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view,@DrawableRes int resId){
        view.setImageResource(resId);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView imageView,String url){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter({"imageurl"})
    public static void loadImge(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(imageView);
    }

    @BindingAdapter(value = {"imageurl","holderDrawable","errorDrawable"},requireAll = false)
    public static void loadImge(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable){
//        if (url==null||url.equals("")){
//            imageView.setVisibility(View.GONE);
//            return;
//        }
//        Glide.get(imageView.getContext())
//                .setMemoryCategory(MemoryCategory.NORMAL);
//        Glide.with(imageView.getContext())
//                .load(url)
//                .thumbnail(0.5f)
//                .placeholder(holderDrawable)
//                .error(errorDrawable)
//                .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                .crossFade()
//                .into(imageView)
//                .onLoadStarted(holderDrawable);

            if (NetworkUitls.isWifiConnected(imageView.getContext())) {
                loadNormal(imageView, url,holderDrawable,errorDrawable);
            } else {
                loadCache(imageView, url,holderDrawable,errorDrawable);
            }
    }

    private static void loadNormal(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .animate(R.anim.fade_in)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private static void loadCache(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .using(new StreamModelLoader<String>() {
                    @Override
                    public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                        return new DataFetcher<InputStream>() {
                            @Override
                            public InputStream loadData(Priority priority) throws Exception {
                                throw new IOException();
                            }

                            @Override
                            public void cleanup() {

                            }

                            @Override
                            public String getId() {
                                return model;
                            }

                            @Override
                            public void cancel() {

                            }
                        };
                    }
                })
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .animate(R.anim.fade_in)
                .into(imageView);
    }



    // 清除Glide内存缓存
    public static boolean clearCacheMemory(Application application) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(application).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
