package material.com.news.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import material.com.news.R;
import material.com.news.bean.NewsHolder;
import material.com.news.databinding.NewsItemBinding;

/**
 * Created by zjl on 2017/3/27.
 */

public class NewsReclyerAdapter extends RecyclerView.Adapter<NewsHolder>{

    private List<NewsHolder> datas;
    private Context context;

    public NewsReclyerAdapter(Context context){
        this.context = context;

    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {

    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item,parent,false);
        return new NewsHolder(binding);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

}
