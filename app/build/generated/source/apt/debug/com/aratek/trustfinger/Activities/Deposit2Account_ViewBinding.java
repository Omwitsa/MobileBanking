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

public class Deposit2Account_ViewBinding implements Unbinder {
  private Deposit2Account target;

  @UiThread
  public Deposit2Account_ViewBinding(Deposit2Account target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Deposit2Account_ViewBinding(Deposit2Account target, View source) {
    this.target = target;

    target.amount = Utils.findRequiredViewAsType(source, R.id.cash, "field 'amount'", EditText.class);
    target.Acc = Utils.findRequiredViewAsType(source, R.id.accno, "field 'Acc'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.save, "field 'submit'", Button.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.pack, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Deposit2Account target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.amount = null;
    target.Acc = null;
    target.submit = null;
    target.back = null;
  }
}
