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

public class DepositActivity_ViewBinding implements Unbinder {
  private DepositActivity target;

  @UiThread
  public DepositActivity_ViewBinding(DepositActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DepositActivity_ViewBinding(DepositActivity target, View source) {
    this.target = target;

    target.amount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'amount'", EditText.class);
    target.sNo = Utils.findRequiredViewAsType(source, R.id.sNo, "field 'sNo'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.submit, "field 'submit'", Button.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DepositActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.amount = null;
    target.sNo = null;
    target.submit = null;
    target.back = null;
  }
}
