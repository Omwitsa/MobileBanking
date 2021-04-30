// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
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

    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
    target.NumbeId = Utils.findRequiredViewAsType(source, R.id.Idnuh, "field 'NumbeId'", EditText.class);
    target.spinner = Utils.findRequiredViewAsType(source, R.id.accountno, "field 'spinner'", Spinner.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.get_account, "field 'submit'", Button.class);
    target.FetchAccount = Utils.findRequiredViewAsType(source, R.id.FetchAccount, "field 'FetchAccount'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AccountsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
    target.NumbeId = null;
    target.spinner = null;
    target.submit = null;
    target.FetchAccount = null;
  }
}
