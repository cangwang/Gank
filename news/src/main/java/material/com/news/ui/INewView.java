package material.com.news.ui;

import java.util.List;

import material.com.base.BaseView;
import material.com.news.model.NewsItem;

/**
 * Created by cangwang on 2017/8/4.
 */

public interface INewView extends BaseView{
    void refreshStatus(boolean refresh);
    void setAdapterData(List<NewsItem> datas);
    void addAdapterData(List<NewsItem> datas);
}
