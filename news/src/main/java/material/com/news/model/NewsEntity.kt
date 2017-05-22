package material.com.news.model

/**
 * Created by zjl on 2017/3/28.
 */

class NewsEntity {
    var isError: Boolean = false
    var results: List<NewsItem>? = null

    override fun toString(): String {
        return "NewsEntity{" +
                "error=" + isError +
                ", results=" + results +
                '}'
    }
}
