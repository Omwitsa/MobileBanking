// Generated code from Butter Knife. Do not modify!
package com.aratek.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.aratek.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.username = Utils.findRequiredViewAsType(source, R.id.username, "field 'username'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    target.btnLogin = Utils.findRequiredViewAsType(source, R.id.btnLogin, "field 'btnLogin'", Button.class);
    target.btnSignup = Utils.findRequiredViewAsType(source, R.id.btnSignup, "field 'btnSignup'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.username = null;
    target.password = null;
    target.btnLogin = null;
    target.btnSignup = null;
  }
}
