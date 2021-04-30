// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Reports;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AdvancesreportActivity_ViewBinding implements Unbinder {
  private AdvancesreportActivity target;

  @UiThread
  public AdvancesreportActivity_ViewBinding(AdvancesreportActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdvancesreportActivity_ViewBinding(AdvancesreportActivity target, View source) {
    this.target = target;

    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdvancesreportActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
  }
}
