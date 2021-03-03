// Generated code from Butter Knife. Do not modify!
package com.aratek.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.aratek.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChoiceActivity_ViewBinding implements Unbinder {
  private ChoiceActivity target;

  @UiThread
  public ChoiceActivity_ViewBinding(ChoiceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChoiceActivity_ViewBinding(ChoiceActivity target, View source) {
    this.target = target;

    target.First = Utils.findRequiredViewAsType(source, R.id.main1, "field 'First'", Button.class);
    target.Second = Utils.findRequiredViewAsType(source, R.id.main2, "field 'Second'", Button.class);
    target.Back = Utils.findRequiredViewAsType(source, R.id.main3, "field 'Back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChoiceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.First = null;
    target.Second = null;
    target.Back = null;
  }
}
