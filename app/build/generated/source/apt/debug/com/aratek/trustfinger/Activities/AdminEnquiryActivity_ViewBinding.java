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

public class AdminEnquiryActivity_ViewBinding implements Unbinder {
  private AdminEnquiryActivity target;

  @UiThread
  public AdminEnquiryActivity_ViewBinding(AdminEnquiryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdminEnquiryActivity_ViewBinding(AdminEnquiryActivity target, View source) {
    this.target = target;

    target.Enquiry = Utils.findRequiredViewAsType(source, R.id.submit1, "field 'Enquiry'", Button.class);
    target.AdminButton = Utils.findRequiredViewAsType(source, R.id.buttonadmin, "field 'AdminButton'", Button.class);
    target.buttonBack = Utils.findRequiredViewAsType(source, R.id.buttonBack, "field 'buttonBack'", Button.class);
    target.IdNumber = Utils.findRequiredViewAsType(source, R.id.number1, "field 'IdNumber'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdminEnquiryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Enquiry = null;
    target.AdminButton = null;
    target.buttonBack = null;
    target.IdNumber = null;
  }
}
