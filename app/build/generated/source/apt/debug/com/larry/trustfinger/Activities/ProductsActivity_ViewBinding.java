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

public class ProductsActivity_ViewBinding implements Unbinder {
  private ProductsActivity target;

  @UiThread
  public ProductsActivity_ViewBinding(ProductsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProductsActivity_ViewBinding(ProductsActivity target, View source) {
    this.target = target;

    target.sNo = Utils.findRequiredViewAsType(source, R.id.sNo, "field 'sNo'", EditText.class);
    target.submit = Utils.findRequiredViewAsType(source, R.id.apply_advance, "field 'submit'", Button.class);
    target.back = Utils.findRequiredViewAsType(source, R.id.back, "field 'back'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sNo = null;
    target.submit = null;
    target.back = null;
  }
}
