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

public class AdvanceActivity_ViewBinding implements Unbinder {
  private AdvanceActivity target;

  @UiThread
  public AdvanceActivity_ViewBinding(AdvanceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdvanceActivity_ViewBinding(AdvanceActivity target, View source) {
    this.target = target;

    target.amount = Utils.findRequiredViewAsType(source, R.id.amount, "field 'amount'", EditText.class);
    target.sNo = Utils.findRequiredViewAsType(source, R.id.sNo, "field 'sNo'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.submit, "field 'submit'", Button.class);
    target.Productname = Utils.findRequiredViewAsType(source, R.id.Product, "field 'Productname'", EditText.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdvanceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.amount = null;
    target.sNo = null;
    target.submit = null;
    target.Productname = null;
    target.back = null;
  }
}
