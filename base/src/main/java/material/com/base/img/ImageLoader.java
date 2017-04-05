package material.com.base.img;

import android.app.Application;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import material.com.base.R;

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
        if (url==null||url.equals("")){
            imageView.setVisibility(View.GONE);
            return;
        }
        Glide.get(imageView.getContext())
                .setMemoryCategory(MemoryCategory.NORMAL);
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.5f)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .into(imageView)
                .onLoadStarted(holderDrawable);
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
