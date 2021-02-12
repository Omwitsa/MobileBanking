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

public class RegisterPhone_ViewBinding implements Unbinder {
  private RegisterPhone target;

  @UiThread
  public RegisterPhone_ViewBinding(RegisterPhone target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterPhone_ViewBinding(RegisterPhone target, View source) {
    this.target = target;

    target.Transactions = Utils.findRequiredViewAsType(source, R.id.transs, "field 'Transactions'", CardView.class);
    target.registration = Utils.findRequiredViewAsType(source, R.id.registrations, "field 'registration'", CardView.class);
    target.verification = Utils.findRequiredViewAsType(source, R.id.verifys, "field 'verification'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterPhone target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Transactions = null;
    target.registration = null;
    target.verification = null;
  }
}
