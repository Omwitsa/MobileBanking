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

public class Reports_ViewBinding implements Unbinder {
  private Reports target;

  @UiThread
  public Reports_ViewBinding(Reports target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Reports_ViewBinding(Reports target, View source) {
    this.target = target;

    target.Deposit = Utils.findRequiredViewAsType(source, R.id.deposit, "field 'Deposit'", CardView.class);
    target.Withdraw = Utils.findRequiredViewAsType(source, R.id.with, "field 'Withdraw'", CardView.class);
    target.Advance = Utils.findRequiredViewAsType(source, R.id.adv, "field 'Advance'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Reports target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Deposit = null;
    target.Withdraw = null;
    target.Advance = null;
  }
}
