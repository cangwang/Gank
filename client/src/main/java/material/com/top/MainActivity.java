package material.com.top;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import com.alibaba.android.arouter.facade.annotation.Route;
import material.com.base.BaseMvpActivity;

/**
 * 首页
 */
@Route(path = "/gank_main/1")
public class MainActivity extends BaseMvpActivity<MainPresenter,IMainView> implements IMainView {
    private FragmentManager fm;

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.showNews();
    }

    @Override
    public void onBackPressed() {
        presenter.backPress();
    }
}
