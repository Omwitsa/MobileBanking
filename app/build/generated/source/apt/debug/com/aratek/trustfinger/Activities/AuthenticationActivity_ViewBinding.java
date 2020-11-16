// Generated code from Butter Knife. Do not modify!
package com.aratek.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
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

    target.Transactions = Utils.findRequiredViewAsType(source, R.id.trans, "field 'Transactions'", CardView.class);
    target.registration = Utils.findRequiredViewAsType(source, R.id.registration, "field 'registration'", CardView.class);
    target.verification = Utils.findRequiredViewAsType(source, R.id.verify, "field 'verification'", CardView.class);
    target.report = Utils.findRequiredViewAsType(source, R.id.report, "field 'report'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AuthenticationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Transactions = null;
    target.registration = null;
    target.verification = null;
    target.report = null;
  }
}
