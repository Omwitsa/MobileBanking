// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FingerPrintsupdateActivity_ViewBinding implements Unbinder {
  private FingerPrintsupdateActivity target;

  @UiThread
  public FingerPrintsupdateActivity_ViewBinding(FingerPrintsupdateActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FingerPrintsupdateActivity_ViewBinding(FingerPrintsupdateActivity target, View source) {
    this.target = target;

    target.Exist = Utils.findRequiredViewAsType(source, R.id.main11, "field 'Exist'", Button.class);
    target.news = Utils.findRequiredViewAsType(source, R.id.main12, "field 'news'", Button.class);
    target.admin = Utils.findRequiredViewAsType(source, R.id.main13, "field 'admin'", Button.class);
    target.Back = Utils.findRequiredViewAsType(source, R.id.main14, "field 'Back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FingerPrintsupdateActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Exist = null;
    target.news = null;
    target.admin = null;
    target.Back = null;
  }
}
