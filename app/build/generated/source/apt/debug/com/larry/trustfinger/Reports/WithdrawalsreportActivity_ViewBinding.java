// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Reports;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WithdrawalsreportActivity_ViewBinding implements Unbinder {
  private WithdrawalsreportActivity target;

  @UiThread
  public WithdrawalsreportActivity_ViewBinding(WithdrawalsreportActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WithdrawalsreportActivity_ViewBinding(WithdrawalsreportActivity target, View source) {
    this.target = target;

    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WithdrawalsreportActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
  }
}
