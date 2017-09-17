package material.com.news.holder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import material.com.news.databinding.NewsItemBinding;

/**
 * Created by zjl on 2017/3/27.
 */

public class NewsHolder extends RecyclerView.ViewHolder{
    private ViewDataBinding binding;

    public NewsHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public NewsHolder(NewsItemBinding binding) {
        super(binding.getRoot());
        setBinding(binding);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}
