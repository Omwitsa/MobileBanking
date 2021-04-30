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

public class PosUsersActivity_ViewBinding implements Unbinder {
  private PosUsersActivity target;

  @UiThread
  public PosUsersActivity_ViewBinding(PosUsersActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PosUsersActivity_ViewBinding(PosUsersActivity target, View source) {
    this.target = target;

    target.fname = Utils.findRequiredViewAsType(source, R.id.names, "field 'fname'", EditText.class);
    target.lname = Utils.findRequiredViewAsType(source, R.id.names2, "field 'lname'", EditText.class);
    target.idnos = Utils.findRequiredViewAsType(source, R.id.idno, "field 'idnos'", EditText.class);
    target.phones = Utils.findRequiredViewAsType(source, R.id.phone, "field 'phones'", EditText.class);
    target.admin = Utils.findRequiredViewAsType(source, R.id.admin, "field 'admin'", Spinner.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.submits, "field 'submit'", Button.class);
    target.Return = Utils.findRequiredViewAsType(source, R.id.subs, "field 'Return'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PosUsersActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fname = null;
    target.lname = null;
    target.idnos = null;
    target.phones = null;
    target.admin = null;
    target.submit = null;
    target.Return = null;
  }
}
