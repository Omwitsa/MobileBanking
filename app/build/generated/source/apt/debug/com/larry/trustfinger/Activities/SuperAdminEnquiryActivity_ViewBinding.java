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

public class SuperAdminEnquiryActivity_ViewBinding implements Unbinder {
  private SuperAdminEnquiryActivity target;

  @UiThread
  public SuperAdminEnquiryActivity_ViewBinding(SuperAdminEnquiryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SuperAdminEnquiryActivity_ViewBinding(SuperAdminEnquiryActivity target, View source) {
    this.target = target;

    target.AdminButton = Utils.findRequiredViewAsType(source, R.id.buttonAdmin, "field 'AdminButton'", Button.class);
    target.ButtonAdmin = Utils.findRequiredViewAsType(source, R.id.BtnAdmin, "field 'ButtonAdmin'", Button.class);
    target.ButtonBackn = Utils.findRequiredViewAsType(source, R.id.nexxtBack, "field 'ButtonBackn'", Button.class);
    target.idnos = Utils.findRequiredViewAsType(source, R.id.edt_admin, "field 'idnos'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SuperAdminEnquiryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.AdminButton = null;
    target.ButtonAdmin = null;
    target.ButtonBackn = null;
    target.idnos = null;
  }
}
