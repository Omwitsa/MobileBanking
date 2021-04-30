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

public class AgentMemberActivity_ViewBinding implements Unbinder {
  private AgentMemberActivity target;

  @UiThread
  public AgentMemberActivity_ViewBinding(AgentMemberActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AgentMemberActivity_ViewBinding(AgentMemberActivity target, View source) {
    this.target = target;

    target.surname = Utils.findRequiredViewAsType(source, R.id.surname, "field 'surname'", EditText.class);
    target.othername = Utils.findRequiredViewAsType(source, R.id.othername, "field 'othername'", EditText.class);
    target.iddno = Utils.findRequiredViewAsType(source, R.id.idnno, "field 'iddno'", EditText.class);
    target.mobile = Utils.findRequiredViewAsType(source, R.id.mobile, "field 'mobile'", EditText.class);
    target.gender = Utils.findRequiredViewAsType(source, R.id.gender, "field 'gender'", Spinner.class);
    target.dbb = Utils.findRequiredViewAsType(source, R.id.dob, "field 'dbb'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.submit, "field 'submit'", Button.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AgentMemberActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.surname = null;
    target.othername = null;
    target.iddno = null;
    target.mobile = null;
    target.gender = null;
    target.dbb = null;
    target.submit = null;
    target.back = null;
  }
}
