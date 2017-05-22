package material.com.news.handler

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast

/**
 * Created by zjl on 2017/3/29.
 */

class NewsEventHandler(private val mContext: Context) {

    fun clickToWeb(url: String) {
        val uri = Uri.parse(url)
        mContext.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    fun showToast(view: View) {
        Toast.makeText(view.context, "abc", Toast.LENGTH_SHORT).show()
    }
}
