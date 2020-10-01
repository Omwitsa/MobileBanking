// Generated code from Butter Knife. Do not modify!
package com.aratek.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.aratek.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuthenticationActivity_ViewBinding implements Unbinder {
  private AuthenticationActivity target;

  @UiThread
  public AuthenticationActivity_ViewBinding(AuthenticationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AuthenticationActivity_ViewBinding(AuthenticationActivity target, View source) {
    this.target = target;

    target.btnLogin = Utils.findRequiredViewAsType(source, R.id.btnLogin, "field 'btnLogin'", Button.class);
    target.btnSignup = Utils.findRequiredViewAsType(source, R.id.btnSignup, "field 'btnSignup'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AuthenticationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnLogin = null;
    target.btnSignup = null;
  }
}
