package material.com.submit.prestenter;

import material.com.submit.ui.SubmitContact;

/**
 * Created by cangwang on 2017/3/30.
 */

public class SubmitPresenter implements SubmitContact.Presenter{

    private SubmitContact.View mSubmitView;

    public SubmitPresenter(SubmitContact.View view){
        mSubmitView = view;
        mSubmitView.setPresenter(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void start() {

    }
}
