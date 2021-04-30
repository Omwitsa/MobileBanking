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

public class IdentificationActivity_ViewBinding implements Unbinder {
  private IdentificationActivity target;

  @UiThread
  public IdentificationActivity_ViewBinding(IdentificationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IdentificationActivity_ViewBinding(IdentificationActivity target, View source) {
    this.target = target;

    target.news = Utils.findRequiredViewAsType(source, R.id.identify, "field 'news'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IdentificationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.news = null;
  }
}
