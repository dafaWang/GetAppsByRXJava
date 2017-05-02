package app.par.com.applist_rxandroid;

import android.graphics.drawable.Drawable;

/**
 * Created by wang_dafa on 2017/5/2.
 */
public class AppInfo {
    private Drawable appIcon;
    private String appDec;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppDec() {
        return appDec;
    }

    public void setAppDec(String appDec) {
        this.appDec = appDec;
    }
}
