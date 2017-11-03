package material.com.submit.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.alibaba.android.arouter.facade.annotation.Route;

import material.com.submit.R;
import material.com.submit.api.SubmitService;
import material.com.submit.model.SubmitResult;
import material.com.submit.prestenter.SubmitPresenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import material.com.base.BaseFragment;
import material.com.base.retrofit.CommonRetrofit;
import retrofit2.Retrofit;

/**
 * Created by cangwang on 2017/3/30.
 */
@Route(path = "/gank_submit/submit")
public class SubmitFragment extends BaseFragment{

    private Toolbar mToolBar;
    private ActionBar mActionBar;
    private TextInputEditText mUrlTxt;
    private TextInputEditText mDescTxt;
    private AppCompatButton popMenutBtn;
    private AppCompatButton submitBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.submit_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(view);

        mUrlTxt = (TextInputEditText)view.findViewById(R.id.submit_url_txt);
        mDescTxt = (TextInputEditText)view.findViewById(R.id.submit_desc_txt);

        popMenutBtn = (AppCompatButton)view.findViewById(R.id.submit_type_btn);
        popMenutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu(popMenutBtn);
            }
        });
        submitBtn = (AppCompatButton)view.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitGank();
            }
        });
    }

    private void initToolbar(View view){
        mToolBar = (Toolbar) view.findViewById(R.id.submit_toolbar);
        mToolBar.setTitle("提交干货");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mActionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
//        mActionBar.setTitle("提交干货");

    }

    public void showPopMenu(final AppCompatButton btn){
        PopupMenu menu = new PopupMenu(getActivity(),btn);
        menu.getMenuInflater().inflate(R.menu.menu_submit_type, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                btn.setText(item.getTitle());
                return false;
            }
        });
        menu.show();
    }

    public void submitGank(){
        String url = mUrlTxt.getText().toString();
        if (url.isEmpty()){
            Toast.makeText(getActivity(),"连接不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        String desc = mUrlTxt.getText().toString();
        if (desc.isEmpty()){
            Toast.makeText(getActivity(),"请填写描述",Toast.LENGTH_SHORT).show();
            return;
        }
        String type = popMenutBtn.getText().toString();
        if(type.isEmpty() || type.equals("选择提交类型")){
            Toast.makeText(getActivity(),"请先选择提交类型",Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit =  CommonRetrofit.getInstance().getRetrofit();
        SubmitService newsService = retrofit.create(SubmitService.class);
        Observable<SubmitResult> observable = newsService.submitGank(url,desc,"",type,true);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubmitResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SubmitResult newsEntity) {
//                        datas = newsEntity.getResults();

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadData() {

    }
}
