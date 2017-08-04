package material.com.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cangwang on 2017/8/4.
 */

public abstract class BaseMvpActivity<P extends BasePresenter,V extends BaseView> extends BaseActivity implements BaseView{
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(),"onCreate");
        presenter = createPresenter();
        if (presenter!=null){
            presenter.onViewAttched(this,savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter!=null){
            presenter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null)
            presenter.release();
    }

    public abstract P createPresenter();
}
