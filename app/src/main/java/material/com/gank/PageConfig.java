package material.com.gank;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjl on 16/7/6.
 */
public class PageConfig {
    public static List<String> pageTitles = new ArrayList<String>();

    public static List<String> getPageTitles(Context context) {
        pageTitles.clear();
        pageTitles.add("New");
        pageTitles.add("New2");
        return pageTitles;
    }

    private static final String NewFragment = "material.com.news.ui.NewFragment";

    private static final String FragmentB = "com.cangwang.b.FragmentB";

    public static String[] fragmentNames = {
            NewFragment,
            NewFragment
    };
}
