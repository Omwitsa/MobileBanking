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

public class TransActionEnquiryActivity_ViewBinding implements Unbinder {
  private TransActionEnquiryActivity target;

  @UiThread
  public TransActionEnquiryActivity_ViewBinding(TransActionEnquiryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TransActionEnquiryActivity_ViewBinding(TransActionEnquiryActivity target, View source) {
    this.target = target;

    target.Enquiry = Utils.findRequiredViewAsType(source, R.id.submit091, "field 'Enquiry'", Button.class);
    target.AdminButton = Utils.findRequiredViewAsType(source, R.id.buttonnnadmin, "field 'AdminButton'", Button.class);
    target.buttonBack = Utils.findRequiredViewAsType(source, R.id.buttoonBack, "field 'buttonBack'", Button.class);
    target.IdNumber = Utils.findRequiredViewAsType(source, R.id.number10099, "field 'IdNumber'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransActionEnquiryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Enquiry = null;
    target.AdminButton = null;
    target.buttonBack = null;
    target.IdNumber = null;
  }
}
