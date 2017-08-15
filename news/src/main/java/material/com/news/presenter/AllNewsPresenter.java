package material.com.news.presenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import material.com.base.BaseFragment;
import material.com.base.BasePresenter;
import material.com.base.utils.AppMetaUtil;
import material.com.base.utils.ListDataSave;
import material.com.flow.FlowFragment;
import material.com.news.BuildConfig;
import material.com.news.adapter.ViewPagerAdapter;
import material.com.news.ui.IAllNewView;
import material.com.news.ui.NewFragment;

/**
 * Created by cangwang on 2017/8/4.
 */

public class AllNewsPresenter extends BasePresenter<IAllNewView>{
    private BaseFragment bf;

    private List<String> pageTitles = new Vector<>();
    private List<Fragment> pageFagments = new ArrayList<Fragment>();

    private ListDataSave dataSave;
    int channel = 0;

    public AllNewsPresenter(BaseFragment bf){
        this.bf = bf;
        channel=AppMetaUtil.getChannelNum(bf.getActivity().getApplicationContext());
    }

    public void getData(){
        dataSave = new ListDataSave(bf.getContext(),"gank", BuildConfig.BUILD_TYPE.equals("debug")? ListDataSave.DEBUG:ListDataSave.PUBLISH);
        pageTitles = dataSave.getDataList("setting_data");
    }

    public void setAdapter(){
        for(String item :pageTitles){
            Fragment tab = new NewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("sort",item);
            tab.setArguments(bundle);
            pageFagments.add(tab);
        }
        pageTitles.add("Flow");
        pageFagments.add(new FlowFragment());

        getView().initPageAapter(new ViewPagerAdapter(bf.getChildFragmentManager(),pageFagments,pageTitles));
    }

    public void navigationSubmit(){
//                EventBus.getDefault().post(new SubmitStartEvent());
//                startActivity(new Intent("com.cangwang.submit"));
        ARouter.getInstance().build("/gank_submit/1").navigation();
    }

    public void navigationSettings(){
        //            startActivity(new Intent("material.com.settings"));

        String path = "/gank_setting";
        if (channel == 10086) {
            path +="/1";
        }else if (channel == 10087){
            path +="_server/1";
        }
        ARouter.getInstance().build(path).navigation();
    }

    @Override
    public void release() {
        bf=null;
        super.release();
    }
}
