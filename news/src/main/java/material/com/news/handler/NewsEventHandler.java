package material.com.news.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zjl on 2017/3/29.
 */

public class NewsEventHandler {
    private Context mContext;
    public NewsEventHandler(Context context){
        mContext = context;
    }

    public void clickToWeb(String url){
        Uri uri = Uri.parse(url);
        mContext.startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void showToast(View view){
        Toast.makeText(view.getContext(),"abc",Toast.LENGTH_SHORT).show();
    }
}
