package material.com.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import material.com.base.BaseActivity;
import material.com.base.ui.flow.FlowLayout;
import material.com.base.utils.BaseUtils;

/**
 * Created by cangwang on 2017/4/1.
 */

public class SettingActivity extends BaseActivity{

    private FlowLayout unsetLayout;
    private FlowLayout setLayout;
    private List<String> unsetData = new Vector<>();
    private List<String> setData = new Vector<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        unsetLayout = (FlowLayout) findViewById(R.id.settings_unset_flow);
        setLayout = (FlowLayout)findViewById(R.id.settings_set_flow);
        unsetData = SettingConfig.getUnSetDats();
        for (final String item:unsetData){
           addUnsetItem(unsetLayout, item);
        }
    }



    private void addUnsetItem(FlowLayout layout, final String item){
        int ranHeight = BaseUtils.dip2px(this, 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(BaseUtils.dip2px(this, 10), 0,BaseUtils.dip2px(this, 10), 0);
        TextView tv = new TextView(this);
        tv.setPadding(BaseUtils.dip2px(this, 15), 0, BaseUtils.dip2px(this, 15), 0);
        tv.setTextColor(Color.parseColor("#FF3030"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(1);
        tv.setBackgroundResource(R.drawable.bg_tag);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSetItem(setLayout,item);
                removeItem(unsetLayout,unsetData.indexOf(item));
            }
        });
        layout.addView(tv, lp);
    }

    private void addSetItem(FlowLayout layout, final String item){
        int ranHeight = BaseUtils.dip2px(this, 30);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(BaseUtils.dip2px(this, 10), 0,BaseUtils.dip2px(this, 10), 0);
        TextView tv = new TextView(this);
        tv.setPadding(BaseUtils.dip2px(this, 15), 0, BaseUtils.dip2px(this, 15), 0);
        tv.setTextColor(Color.parseColor("#FF3030"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setLines(1);
        tv.setBackgroundResource(R.drawable.bg_tag);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUnsetItem(unsetLayout,item);
                removeItem(setLayout,setData.indexOf(item));
            }
        });
        layout.addView(tv, lp);
    }

    private void removeItem(FlowLayout layout,int position){
        layout.removeViewAt(position);
    }
}
