package material.com.base.img;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

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
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }
}
