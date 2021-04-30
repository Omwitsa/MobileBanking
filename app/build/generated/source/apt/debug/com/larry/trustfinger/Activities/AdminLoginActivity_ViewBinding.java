// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdminLoginActivity_ViewBinding implements Unbinder {
  private AdminLoginActivity target;

  @UiThread
  public AdminLoginActivity_ViewBinding(AdminLoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdminLoginActivity_ViewBinding(AdminLoginActivity target, View source) {
    this.target = target;

    target.news = Utils.findRequiredViewAsType(source, R.id.identify, "field 'news'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdminLoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.news = null;
  }
}
