// Generated code from Butter Knife. Do not modify!
package com.larry.trustfinger.Activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.larry.trustfinger.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterPhone_ViewBinding implements Unbinder {
  private RegisterPhone target;

  @UiThread
  public RegisterPhone_ViewBinding(RegisterPhone target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterPhone_ViewBinding(RegisterPhone target, View source) {
    this.target = target;

    target.Transactions = Utils.findRequiredViewAsType(source, R.id.transs, "field 'Transactions'", CardView.class);
    target.registration = Utils.findRequiredViewAsType(source, R.id.registrations, "field 'registration'", CardView.class);
    target.verification = Utils.findRequiredViewAsType(source, R.id.verifys, "field 'verification'", CardView.class);
    target.Fingerprints = Utils.findRequiredViewAsType(source, R.id.updates, "field 'Fingerprints'", CardView.class);
    target.mage1 = Utils.findRequiredViewAsType(source, R.id.image001, "field 'mage1'", ImageView.class);
    target.mage2 = Utils.findRequiredViewAsType(source, R.id.image002, "field 'mage2'", ImageView.class);
    target.mage3 = Utils.findRequiredViewAsType(source, R.id.image003, "field 'mage3'", ImageView.class);
    target.mage4 = Utils.findRequiredViewAsType(source, R.id.image004, "field 'mage4'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterPhone target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.Transactions = null;
    target.registration = null;
    target.verification = null;
    target.Fingerprints = null;
    target.mage1 = null;
    target.mage2 = null;
    target.mage3 = null;
    target.mage4 = null;
  }
}
