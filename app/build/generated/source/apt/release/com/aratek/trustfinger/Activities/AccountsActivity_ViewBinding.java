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

public class AccountsActivity_ViewBinding implements Unbinder {
  private AccountsActivity target;

  @UiThread
  public AccountsActivity_ViewBinding(AccountsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccountsActivity_ViewBinding(AccountsActivity target, View source) {
    this.target = target;

    target.submit = Utils.findRequiredViewAsType(source, R.id.get_account, "field 'submit'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccountsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.submit = null;
  }
}
