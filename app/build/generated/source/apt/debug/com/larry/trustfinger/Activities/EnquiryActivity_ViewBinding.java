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

public class EnquiryActivity_ViewBinding implements Unbinder {
  private EnquiryActivity target;

  @UiThread
  public EnquiryActivity_ViewBinding(EnquiryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EnquiryActivity_ViewBinding(EnquiryActivity target, View source) {
    this.target = target;

    target.Enquiry = Utils.findRequiredViewAsType(source, R.id.submit, "field 'Enquiry'", Button.class);
    target.Next = Utils.findRequiredViewAsType(source, R.id.next, "field 'Next'", Button.class);
    target.nextBackkk = Utils.findRequiredViewAsType(source, R.id.nextBack, "field 'nextBackkk'", Button.class);
    target.IdNumber = Utils.findRequiredViewAsType(source, R.id.number, "field 'IdNumber'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EnquiryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Enquiry = null;
    target.Next = null;
    target.nextBackkk = null;
    target.IdNumber = null;
  }
}
