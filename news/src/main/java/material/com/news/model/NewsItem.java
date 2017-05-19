package material.com.news.model;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;

import material.com.base.event.ChangeAdiviceEvent;

/**
 * Created by cangwang on 2017/3/28.
 */

public class NewsItem {
    public String _id;
    public String createdAt;
    public String desc;
    public String[] images;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
    public int hasImage;

    public NewsItem(String _id, String createdAt, String desc, String[] images
            , String publishedAt, String source, String type, String url
            , boolean used, String who) {
        this._id = _id;
        this.createdAt = createdAt;
        this.desc = desc;
        this.images = images;
        this.publishedAt = publishedAt;
        this.source = source;
        this.type = type;
        this.url = url;
        this.used = used;
        this.who = who;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getHasImage() {
        hasImage = (images!=null && !images[0].equals(""))?View.VISIBLE:View.GONE;
        return hasImage;
    }

    public void clickToWeb(View view){
//        Intent intent = new Intent("material.com.web.Web");
//        intent.putExtra("url",url);
//        intent.putExtra("title",desc);
//        view.getContext().startActivity(intent);
        ARouter.getInstance().build("/gank_web/1")
                .withString("url",url)
                .withString("title",desc)
                .navigation();
        if (type.equals("福利")){     //更换
            ChangeAdiviceEvent event =new ChangeAdiviceEvent(url);
            EventBus.getDefault().post(event);
        }
    }

//    public int isHasImage(){
//        return (images!=null && !images[0].equals(""))?View.VISIBLE:View.GONE;
//    }

}
