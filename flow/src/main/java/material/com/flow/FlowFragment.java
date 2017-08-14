package material.com.flow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.List;

/**
 * Created by cangwang on 2017/8/14.
 */

public class FlowFragment extends Fragment {
    private View rootView;
    private DanmuView mDanmuView;
    private List<View> mViewList;
    private String[] mStrItems = {
            "搜狗","百度",
            "腾讯","360",
            "阿里巴巴","搜狐",
            "网易","新浪",
            "搜狗-上网从搜狗开始","百度一下,你就知道",
            "必应搜索-有求必应","好搜-用好搜，特顺手",
            "Android-谷歌","IOS-苹果",
            "Windows-微软","Linux"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.flow_layout,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDanmuView();
        initListener();
    }

    private void initListener() {
        rootView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDanmuView.isWorking()) {
                    mDanmuView.hide();
                    ((Button) view).setText("开启弹幕");
                } else {
                    mDanmuView.start();
                    ((Button) view).setText("关闭弹幕");
                }
            }
        });
    }

    private void initDanmuView() {
        mDanmuView = (DanmuView)rootView.findViewById(R.id.danmu);
//        mDanmuView.setDirection(XCDanmuView.XCDirection.FORM_LEFT_TO_RIGHT);
        mDanmuView.initDanmuItemViews(mStrItems);
    }
}
