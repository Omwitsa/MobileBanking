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

public class OperatoerFingerprintActivity_ViewBinding implements Unbinder {
  private OperatoerFingerprintActivity target;

  @UiThread
  public OperatoerFingerprintActivity_ViewBinding(OperatoerFingerprintActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OperatoerFingerprintActivity_ViewBinding(OperatoerFingerprintActivity target,
      View source) {
    this.target = target;

    target.back = Utils.findRequiredViewAsType(source, R.id.backss, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OperatoerFingerprintActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.back = null;
  }
}
