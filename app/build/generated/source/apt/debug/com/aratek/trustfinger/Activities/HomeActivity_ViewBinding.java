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

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target, View source) {
    this.target = target;

    target.deposit = Utils.findRequiredViewAsType(source, R.id.deposit, "field 'deposit'", CardView.class);
    target.withdraw = Utils.findRequiredViewAsType(source, R.id.withdraw, "field 'withdraw'", CardView.class);
    target.memberRegister = Utils.findRequiredViewAsType(source, R.id.member_register, "field 'memberRegister'", CardView.class);
    target.balanceEnquiry = Utils.findRequiredViewAsType(source, R.id.balance_enquiry, "field 'balanceEnquiry'", CardView.class);
    target.report = Utils.findRequiredViewAsType(source, R.id.report, "field 'report'", CardView.class);
    target.advance = Utils.findRequiredViewAsType(source, R.id.advance, "field 'advance'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.deposit = null;
    target.withdraw = null;
    target.memberRegister = null;
    target.balanceEnquiry = null;
    target.report = null;
    target.advance = null;
  }
}
