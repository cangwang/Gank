package material.com.news.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import material.com.news.BR;
import material.com.news.R;
import material.com.news.holder.FooterHolder;
import material.com.news.holder.NewsHolder;
import material.com.news.databinding.NewsItemBinding;
import material.com.news.model.NewsItem;

/**
 * Created by cangwang on 2017/3/27.
 */
public class NewsReclyerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<NewsItem> datas = new ArrayList<>();
    private Context context;

    private static final int TYPE_ITEM =0;
    private static final int TYPE_FOOTER =1;

    public NewsReclyerAdapter(Context context){
        this.context = context;
    }

    public void setDatas(List<NewsItem> datas) {
        if (datas!=null)
            this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<NewsItem> data){
        if (data!=null)
            datas.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        datas=null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHolder) {
            final NewsItem data = datas.get(position);
            ((NewsHolder)holder).getBinding().setVariable(BR.news, data);
            ((NewsHolder)holder).getBinding().executePendingBindings();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            NewsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.news_item, parent, false);
            return new NewsHolder(binding);
        }else{
            View converView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer,parent,false);
            return new FooterHolder(converView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1 ==getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (datas.size()==0){
            return datas.size();
        }else {
            return datas.size() + 1;
        }
    }

}
