package app.par.com.applist_rxandroid;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.main_recycler)
    RecyclerView mRecycler;
    @Bind(R.id.main_srl)
    SwipeRefreshLayout mSwipe;

    private List<AppInfo> mAppList;
    private AppListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        //初始化变量
        mAppList = new ArrayList<>();
        mAdapter = new AppListAdapter(mAppList);
        //绑定适配器
        mRecycler.setAdapter(mAdapter);
        //设置监听器
        mSwipe.setOnRefreshListener(this);

        mSwipe.post(new Runnable() {
            @Override
            public void run() {
                //启动时加载一遍数据
                mSwipe.setRefreshing(true);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        if(null != mAppList){
            //如果内容存在-->清空,刷新
            mAppList.clear();
            mAdapter.notifyDataSetChanged();
        }
        //加载应用信息
        loadApps();


    }

    private void loadApps() {
        //获取包管理器
        final PackageManager packageManager = getPackageManager();
        //创建一个针对获取手机应用的监听对象
        Observable.create(new Observable.OnSubscribe<ApplicationInfo>() {
            //获取到事件响应时执行的操作
            @Override
            public void call(Subscriber<? super ApplicationInfo> subscriber) {
                //获取到除了未安装的应用的手机内应用列表
                List<ApplicationInfo> installedAppList = getInstalledAppList(packageManager);
                for (ApplicationInfo applicationInfo:installedAppList
                     ) {
                    //遍历应用列表,执行 订阅者 的onNext() 操作
                    subscriber.onNext(applicationInfo);
                }
                //在所有onNext()方法执行完之后执行onComplete()方法
                subscriber.onCompleted();
            }
        }).filter(new Func1<ApplicationInfo, Boolean>() {
            //filter操作符起到过滤作用,这里使用它来过滤掉系统应用
            @Override
            public Boolean call(ApplicationInfo applicationInfo) {
                //ApplicationInfo.FLAG_SYSTEM = 1
                //如果当前找到的应用为系统应用,那么flag = 1
                //所以 & 1 还是等于1        --> 返回false(不满足查询条件)
                //否则 & 1 等于0或者负数    --> 返回true(满足查询条件)
                return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0;
            }
        }).map(new Func1<ApplicationInfo, AppInfo>() {
            //.map() --> RXJava中最常用的变换  事件对象的直接变换
            //在这里将ApplicationInfo 中的信息摘取我们用到的,生成自定义的AppInfo对象
            @Override
            public AppInfo call(ApplicationInfo applicationInfo) {
                AppInfo appinfo = new AppInfo();
                appinfo.setAppIcon(applicationInfo.loadIcon(packageManager));
                appinfo.setAppDec(applicationInfo.loadLabel(packageManager).toString());
                return appinfo;
            }
        }).subscribeOn(Schedulers.io())//事件发生在io线程中
            .observeOn(AndroidSchedulers.mainThread())//在主线程中处理回调
            .subscribe(new Subscriber<AppInfo>() {//当订阅者在收到被订阅者的事件发生之后的具体响应
                @Override
                public void onCompleted() {
                    //在处理事件响应完成之后
                    mAdapter.notifyDataSetChanged();
                    mSwipe.setRefreshing(false);
                }

                @Override
                public void onError(Throwable e) {
                    //处理事件时遇到错误时
                    mSwipe.setRefreshing(false);
                }

                @Override
                public void onNext(AppInfo appInfo) {
                    //处理事件时
                    mAppList.add(appInfo);
                }
            });

    }

    private List<ApplicationInfo> getInstalledAppList(PackageManager packageManager) {
        //获取已经安装的手机应用列表
        //flag 为过滤掉未安装的应用
        return packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
