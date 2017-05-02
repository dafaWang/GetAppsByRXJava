package app.par.com.applist_rxandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wang_dafa on 2017/5/2.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {

    private List<AppInfo> mList;
    public AppListAdapter(List<AppInfo> lists){
        this.mList = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定视图
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        填充控件
        AppInfo appInfo = mList.get(position);
        holder.icon.setBackground(appInfo.getAppIcon());
        holder.name.setText(appInfo.getAppDec());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //注意:这里的viewHoder必须预先加载,就像驱动一样,否则找不到这个控件啊
    public class ViewHolder extends RecyclerView.ViewHolder {
        //绑定控件
        @Bind(R.id.item_icon)
        public ImageView icon;
        @Bind(R.id.item_name)
        public TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
