package material.com.news.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import material.com.news.BR;
import material.com.news.R;
import material.com.news.holder.NewsHolder;
import material.com.news.databinding.NewsItemBinding;
import material.com.news.model.NewsItem;

/**
 * Created by zjl on 2017/3/27.
 */
public class NewsReclyerAdapter extends RecyclerView.Adapter<NewsHolder>{

    private List<NewsItem> datas = new ArrayList<>();
    private Context context;

    public NewsReclyerAdapter(Context context){
        this.context = context;
    }

    public void setDatas(List<NewsItem> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final NewsItem data = datas.get(position);
        holder.getBinding().setVariable(BR.news,data);
        holder.getBinding().executePendingBindings();
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
