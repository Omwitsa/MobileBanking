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

public class AgentMemberEnquiryActivity_ViewBinding implements Unbinder {
  private AgentMemberEnquiryActivity target;

  @UiThread
  public AgentMemberEnquiryActivity_ViewBinding(AgentMemberEnquiryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AgentMemberEnquiryActivity_ViewBinding(AgentMemberEnquiryActivity target, View source) {
    this.target = target;

    target.Enquiry = Utils.findRequiredViewAsType(source, R.id.submit11, "field 'Enquiry'", Button.class);
    target.Agent = Utils.findRequiredViewAsType(source, R.id.buttonagent, "field 'Agent'", Button.class);
    target.buttonBackk = Utils.findRequiredViewAsType(source, R.id.buttonBack1, "field 'buttonBackk'", Button.class);
    target.IdNumber = Utils.findRequiredViewAsType(source, R.id.number11, "field 'IdNumber'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AgentMemberEnquiryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Enquiry = null;
    target.Agent = null;
    target.buttonBackk = null;
    target.IdNumber = null;
  }
}
