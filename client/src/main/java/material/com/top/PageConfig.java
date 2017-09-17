package material.com.top;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjl on 16/7/6.
 */
public class PageConfig {
    private static List<String> pageTitles = new ArrayList<String>();

    public static List<String> getPageTitles() {
        pageTitles.clear();
        pageTitles.add("首页");
        pageTitles.add("提交干货");
        return pageTitles;
    }

    private static final String NewFragment = "material.com.news.ui.NewFragment";
    public static final String AllNewsFragment = "material.com.news.ui.AllNewsFragment";
    public static final String SubmitFragment = "com.cangwang.submit.ui.SubmitFragment";

    public static String[] fragmentNames = {
            AllNewsFragment,
            SubmitFragment,
            NewFragment,
            NewFragment
    };
}
