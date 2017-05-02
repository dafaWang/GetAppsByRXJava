// Generated code from Butter Knife. Do not modify!
package app.par.com.applist_rxandroid;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends app.par.com.applist_rxandroid.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427414, "field 'mRecycler'");
    target.mRecycler = finder.castView(view, 2131427414, "field 'mRecycler'");
    view = finder.findRequiredView(source, 2131427413, "field 'mSwipe'");
    target.mSwipe = finder.castView(view, 2131427413, "field 'mSwipe'");
  }

  @Override public void unbind(T target) {
    target.mRecycler = null;
    target.mSwipe = null;
  }
}
