// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TransactionActivity_ViewBinding implements Unbinder {
  private TransactionActivity target;

  @UiThread
  public TransactionActivity_ViewBinding(TransactionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TransactionActivity_ViewBinding(TransactionActivity target, View source) {
    this.target = target;

    target.mtry = Utils.findRequiredViewAsType(source, R.id.retry, "field 'mtry'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransactionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mtry = null;
  }
}
