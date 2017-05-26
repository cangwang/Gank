package material.com.top

import java.util.ArrayList

/**
 * Created by cangwang on 16/7/6.
 */
object PageConfig {
    private val pageTitles = ArrayList<String>()

    fun getPageTitles(): List<String> {
        pageTitles.clear()
        pageTitles.add("首页")
        pageTitles.add("提交干货")
        return pageTitles
    }

    private val NewFragment = "material.com.news.ui.NewFragment"
    val AllNewsFragment = "material.com.news.ui.AllNewsFragment"
    val SubmitFragment = "com.cangwang.submit.ui.SubmitFragment"

    var fragmentNames = arrayOf(AllNewsFragment, SubmitFragment, NewFragment, NewFragment)
}
