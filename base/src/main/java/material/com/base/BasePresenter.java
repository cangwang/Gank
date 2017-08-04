package material.com.base;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by canwang on 2017/3/31.
 */

public class BasePresenter<V extends BaseView> {
    protected  V view;

    public BasePresenter(){}
    public V getView(){
        return view;
    }

    public void onViewAttched(V view, Bundle saveInstanceState){
        this.view= view;
    }

    public void onViewDetached(){
        view= null;
    }

    public void release(){

    }
    public void onSaveInstanceState(Bundle outState){

    }
}
