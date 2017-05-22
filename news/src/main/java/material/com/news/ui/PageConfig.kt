package material.com.news.ui

import java.util.ArrayList

/**
 * Created by zjl on 16/7/6.
 */
object PageConfig {
    private val pageTitles = ArrayList<String>()

    fun getPageTitles(): List<String> {
        pageTitles.clear()
        pageTitles.add("all")
        pageTitles.add("Android")
        pageTitles.add("iOS")
        pageTitles.add("福利")
        return pageTitles
    }

    private val NewFragment = "material.com.news.ui.NewFragment"

    var fragmentNames = arrayOf(NewFragment, NewFragment, NewFragment, NewFragment)
}
