package material.com.submit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import material.com.base.BaseFragment;
import material.com.base.retrofit.CommonRetrofit;
import material.com.submit.R;
import material.com.submit.R2;
import material.com.submit.api.SubmitService;
import material.com.submit.model.SubmitResult;
import retrofit2.Retrofit;

/**
 * Created by cangwang on 2017/3/30.
 */

public class SubmitButterFragment extends BaseFragment{

    @BindView(R2.id.submit_toolbar)
    public Toolbar mToolBar;

    public ActionBar mActionBar;

    @BindView(R2.id.submit_url_txt)
    public TextInputEditText mUrlTxt;

    @BindView(R2.id.submit_desc_txt)
    public TextInputEditText mDescTxt;

    @BindView(R2.id.submit_type_btn)
    public AppCompatButton popMenutBtn;

    @BindView(R2.id.submit_btn)
    public AppCompatButton submitBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.submit_fragment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(view);

//        popMenutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopMenu(popMenutBtn);
//            }
//        });
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitGank();
//            }
//        });
    }

    private void initToolbar(View view){
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

    @OnClick(R2.id.submit_type_btn)
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

    @OnClick(R2.id.submit_btn)
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
