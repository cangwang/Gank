package material.com.settings;

import java.util.List;
import java.util.Vector;

/**
 * Created by cangwang on 2017/4/1.
 */

public class SettingConfig {
    public static List<String> getUnSetDats(){
        List<String> unsetDatas = new Vector<>();
        unsetDatas.add("all");
        unsetDatas.add("Android");
        unsetDatas.add("iOS");
        unsetDatas.add("福利");
        unsetDatas.add("休息视频");
        unsetDatas.add("拓展资源");
        unsetDatas.add("前端");
        unsetDatas.add("瞎推荐");
        unsetDatas.add("App");
        return unsetDatas;
    }
}
