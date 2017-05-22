package material.com.base.share

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by cangwang on 2017/4/1.
 */

object ShareUtils {
    fun shareText(ctx: Context, text: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        ctx.startActivity(Intent.createChooser(sendIntent, "分享至"))
    }

    fun shareImage(ctx: Context, uri: Uri) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
        sendIntent.type = "image/jpeg"
        ctx.startActivity(Intent.createChooser(sendIntent, "分享至"))
    }
}
