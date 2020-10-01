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

public class BalanceActivity_ViewBinding implements Unbinder {
  private BalanceActivity target;

  @UiThread
  public BalanceActivity_ViewBinding(BalanceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BalanceActivity_ViewBinding(BalanceActivity target, View source) {
    this.target = target;

    target.submit = Utils.findRequiredViewAsType(source, R.id.submit, "field 'submit'", Button.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BalanceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.submit = null;
    target.back = null;
  }
}
