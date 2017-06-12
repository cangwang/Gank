package material.com.news.model

import android.content.Intent
import android.net.Uri
import android.view.View

import com.alibaba.android.arouter.launcher.ARouter

import org.greenrobot.eventbus.EventBus

import material.com.base.event.ChangeAdiviceEvent

/**
 * Created by cangwang on 2017/3/28.
 */

class NewsItem(var _id: String, var createdAt: String, var desc: String, var images: Array<String>?, var publishedAt: String, var source: String, var type: String, var url: String, var isUsed: Boolean, var who: String) {
//    var hasImage: Int? = View.VISIBLE

    fun getHasImage(): Int {
        if (images != null && images!![0] != "")
            return View.VISIBLE
        else
            return View.GONE
    }

    fun clickToWeb(view: View) {
//        var intent = Intent("material.com.web.Web");
//        intent.putExtra("url",url);
//        intent.putExtra("title",desc);
//        view.getContext().startActivity(intent);
        ARouter.getInstance().build("/gank_web/1")
                .withString("url", url)
                .withString("title", desc)
                .navigation()
        if (type == "福利") {     //更换
            val event = ChangeAdiviceEvent(url)
            EventBus.getDefault().post(event)
        }
    }

    //    public int isHasImage(){
    //        return (images!=null && !images[0].equals(""))?View.VISIBLE:View.GONE;
    //    }

}
