// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginAdminActivity_ViewBinding implements Unbinder {
  private LoginAdminActivity target;

  @UiThread
  public LoginAdminActivity_ViewBinding(LoginAdminActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginAdminActivity_ViewBinding(LoginAdminActivity target, View source) {
    this.target = target;

    target.username = Utils.findRequiredViewAsType(source, R.id.username, "field 'username'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    target.btnLogin = Utils.findRequiredViewAsType(source, R.id.btnLogin, "field 'btnLogin'", Button.class);
    target.btnBack = Utils.findRequiredViewAsType(source, R.id.btnBack, "field 'btnBack'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginAdminActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.username = null;
    target.password = null;
    target.btnLogin = null;
    target.btnBack = null;
  }
}
