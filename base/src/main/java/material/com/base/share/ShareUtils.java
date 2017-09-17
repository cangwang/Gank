package material.com.base.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by cangwang on 2017/4/1.
 */

public class ShareUtils {
    public static void shareText(Context ctx,String text){
        Intent sendIntent= new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,text);
        sendIntent.setType("text/plain");
        ctx.startActivity(Intent.createChooser(sendIntent,"分享至"));
    }

    public static void shareImage(Context ctx,Uri uri){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
        sendIntent.setType("image/jpeg");
        ctx.startActivity(Intent.createChooser(sendIntent,"分享至"));
    }
}
