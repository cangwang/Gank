package material.com.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import material.com.base.*;
import material.com.base.event.NewsItemChangeEvent;
import material.com.base.ui.flow.FlowLayout;
import material.com.base.utils.BaseUtils;
import material.com.base.utils.ListDataSave;

/**
 * 设置
 * Created by cangwang on 2017/4/1.
 */
@Route(path = "/gank_setting/1")
public class SettingActivity extends BaseActivity{

    private FlowLayout setLayout;
    private List<String> allData = new Vector<>();
    private List<String> setData = new Vector<>();

    private Toolbar mToolBar;
    private ActionBar mActionBar;
    private ListDataSave dataSave;

    private boolean isChange = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initToolbar();
        dataSave = new ListDataSave(this,"gank", BuildConfig.BUILD_TYPE.equals("debug")? ListDataSave.DEBUG:ListDataSave.PUBLISH);
        setData = dataSave.getDataList("setting_data");
        setLayout = (FlowLayout)findViewById(R.id.settings_set_flow);
        allData = SettingConfig.getUnSetDats();
        for (final String item:allData){
           addUnsetItem(setLayout, item);
        }
    }


    private void initToolbar(){
        mToolBar = (Toolbar)findViewById(R.id.settings_toolbar);
        mToolBar.setTitle("设置页面");
        setSupportActionBar(mToolBar);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void addUnsetItem(FlowLayout layout, final String item){
        int ranHeight = BaseUtils.dip2px(this, 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(BaseUtils.dip2px(this, 10), 0,BaseUtils.dip2px(this, 10), 0);
        TextView tv = new TextView(this);
        tv.setPadding(BaseUtils.dip2px(this, 15), 0, BaseUtils.dip2px(this, 15), 0);

        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(1);
        if (!setData.contains(item)) {
            tv.setTag(R.id.tag_set, "unset");
            tv.setTextColor(Color.parseColor("#FF3030"));
            tv.setBackgroundResource(R.drawable.bg_tag);
        }else {
            tv.setTag(R.id.tag_set, "set");
            tv.setTextColor(getResources().getColor(R.color.commen_blue_8));
            tv.setBackgroundResource(R.drawable.bg_select);
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt=((TextView) v).getText().toString();
                if (v.getTag(R.id.tag_set).equals("unset")) {
                    v.setTag(R.id.tag_set, "set");
                    int index = allData.indexOf(txt);
                    if (index >= setData.size()) {
                        setData.add(txt);
                    }else {
                        setData.add(index,txt);
                    }
                    ((TextView) v).setTextColor(getResources().getColor(R.color.commen_blue_8));
                    v.setBackgroundResource(R.drawable.bg_select);
                } else {
                    v.setTag(R.id.tag_set, "unset");
                    setData.remove(txt);
                    ((TextView) v).setTextColor(Color.parseColor("#FF3030"));
                    v.setBackgroundResource(R.drawable.bg_tag);
                }
                dataSave.setDataList("setting_data",setData);
                isChange = true;
            }
        });
        layout.addView(tv, lp);
    }

    @Override
    protected void onDestroy() {
        if (isChange){
            EventBus.getDefault().post(new NewsItemChangeEvent());
        }
        dataSave = null;
        super.onDestroy();
    }
}
