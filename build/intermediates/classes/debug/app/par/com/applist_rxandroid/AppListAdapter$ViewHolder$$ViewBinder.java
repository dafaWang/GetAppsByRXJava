// Generated code from Butter Knife. Do not modify!
package app.par.com.applist_rxandroid;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AppListAdapter$ViewHolder$$ViewBinder<T extends app.par.com.applist_rxandroid.AppListAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427415, "field 'icon'");
    target.icon = finder.castView(view, 2131427415, "field 'icon'");
    view = finder.findRequiredView(source, 2131427416, "field 'name'");
    target.name = finder.castView(view, 2131427416, "field 'name'");
  }

  @Override public void unbind(T target) {
    target.icon = null;
    target.name = null;
  }
}
