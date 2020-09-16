package com.example.mobilebanking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.aratek.trustfinger.sdk.DeviceListener;
import com.aratek.trustfinger.sdk.TrustFinger;
import com.aratek.trustfinger.sdk.TrustFingerDevice;
import com.aratek.trustfinger.sdk.TrustFingerException;
import com.example.mobilebanking.Fingerprint.fragment.CaptureFragment;
import com.example.mobilebanking.Fingerprint.fragment.DeviceInfoFragment;
import com.example.mobilebanking.Fingerprint.fragment.EnrollFragment;
import com.example.mobilebanking.Fingerprint.fragment.IdentifyFragment;
import com.example.mobilebanking.Fingerprint.fragment.VerifyFragment;
import com.example.mobilebanking.Fingerprint.utils.MediaPlayerHelper;
import com.example.mobilebanking.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FingurePrintSampleActivity extends AppCompatActivity {
    private Spinner mSpinner_deviceType;
    private Spinner mSpinner_usbDevice;
    private Button mButton_openClose;
    private CheckBox mCheckBox_enableLed;
    private CheckBox mCheckBox_antiSpoofing;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTextView_msg;
    private TextView mTextView_version;

    private TrustFinger mTrustFinger;
    protected TrustFingerDevice mTrustFingerDevice;
    private ArrayAdapter<String> adapter_usbDevice;
    private List<String> sp_usbDevice_datas_A400 = new ArrayList<String>();
    private List<String> sp_usbDevice_datas_A600 = new ArrayList<String>();
    private List<String> sp_usbDevice_datas_all = new ArrayList<String>();
    private List<String> sp_usbDevice_datas = new ArrayList<String>();
    private List<Fragment> fragmnts = new ArrayList<Fragment>();
    private CaptureFragment mCaptureFragment;
    private EnrollFragment mEnrollFragment;
    private VerifyFragment mVerifyFragment;
    private IdentifyFragment mIdentifyFragment;
    private DeviceInfoFragment mDeviceInfoFragment;
    private String[] titles;
    private Handler handler = new Handler();
    private int mDeviceId = 0;
    private boolean isDeviceOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingure_print_sample);
        findViews();
        initTrustFinger();
    }

    private void findViews() {
        mSpinner_deviceType = (Spinner) findViewById(R.id.sp_device_type);
        mSpinner_usbDevice = (Spinner) findViewById(R.id.sp_usb_device);
        mButton_openClose = (Button) findViewById(R.id.btn_open_device);
        mCheckBox_enableLed = (CheckBox) findViewById(R.id.chk_enabled_led);
        mCheckBox_antiSpoofing = (CheckBox) findViewById(R.id.chk_anti_spoofing);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTextView_msg = (TextView) findViewById(R.id.tv_msg);
        mTextView_version = (TextView) findViewById(R.id.tv_version);
    }

    private void initTrustFinger() {
        try {
            mTrustFinger = TrustFinger.getInstance(this.getApplicationContext());
            //            mTextView_version.setText("v" + mTrustFinger.getSdkVersion());
            mTrustFinger.initialize();
            mTrustFinger.setDeviceListener(new DeviceListener() {
                @Override
                public void deviceAttached(List<String> deviceList) {
                    sp_usbDevice_datas_all.clear();
                    for (int i = 0; i < deviceList.size(); i++) {
                        sp_usbDevice_datas_all.add(i + "-" + deviceList.get(i));
                    }
                    sp_usbDevice_datas.clear();
                    sp_usbDevice_datas.add(getString(R.string.sp_usb_device_auto));//show default
                    // value
                    sp_usbDevice_datas.addAll(sp_usbDevice_datas_all);
                    mSpinner_deviceType.setSelection(0);
                    mSpinner_usbDevice.setSelection(0);
                    adapter_usbDevice.notifyDataSetChanged();
                    handleMsg("Device atached!", Color.BLACK);
                }

                @Override
                public void deviceDetached(List<String> deviceList) {
                    sp_usbDevice_datas_all.clear();
                    for (int i = 0; i < deviceList.size(); i++) {
                        sp_usbDevice_datas_all.add(i + "-" + deviceList.get(i));
                    }
                    sp_usbDevice_datas.clear();
                    sp_usbDevice_datas.add(getString(R.string.sp_usb_device_auto));//show default
                    // value
                    sp_usbDevice_datas.addAll(sp_usbDevice_datas_all);
                    adapter_usbDevice.notifyDataSetChanged();
                    mSpinner_deviceType.setSelection(0);
                    mSpinner_usbDevice.setSelection(0);
                    isDeviceOpened = false;
                    mButton_openClose.setTextColor(Color.parseColor("#1D9F9A"));
                    mButton_openClose.setText(getString(R.string.btn_open_device));
                    setFragmentDatas(null);
                    mSpinner_deviceType.setEnabled(true);
                    mSpinner_usbDevice.setEnabled(true);
                    //                mCheckBox_enableLed.setEnabled(true);
                    handleMsg("Device detached!", Color.RED);
                }
            });
            /*if (mTrustFinger.getDeviceCount() <= 0) {
                showAlertDialog(false, "No fingerprint device detected!");
            }*/
        }
        catch (TrustFingerException e) {
            handleMsg("TrustFinger getInstance Exception: " + e.getType().toString() + "", Color
                    .RED);
            /*if (e.getType().toString().equals("DEVICE_NOT_FOUND")) {
                showAlertDialog(true, "No fingerprint device detected!");
            }*/
            e.printStackTrace();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            showAlertDialog(true, "The system does not support simultaneous access to two devices" +
                    ".");
        }
    }

    public void handleMsg(final String msg, final int color) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mTextView_msg.setText(msg);
                mTextView_msg.setTextColor(color);
            }
        });
    }

    private void setFragmentDatas(TrustFingerDevice mTrustFingerDevice) {
        if (mCaptureFragment != null)
            mCaptureFragment.setDatas(mTrustFingerDevice);
        if (mEnrollFragment != null)
            mEnrollFragment.setDatas(mTrustFingerDevice);
        if (mVerifyFragment != null)
            mVerifyFragment.setDatas(mTrustFingerDevice);
        if (mIdentifyFragment != null)
            mIdentifyFragment.setDatas(mTrustFingerDevice);
        if (mDeviceInfoFragment != null)
            mDeviceInfoFragment.setDatas(mTrustFingerDevice);
    }

    private void showAlertDialog(final boolean isError, String msg) throws TrustFingerException {
        MediaPlayerHelper.payMedia(this, R.raw.no_fingerprint_device_detected);
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Error")//设置对话框的标题
                .setMessage(msg)//设置对话框的内容
                //设置对话框的按钮
                .setNeutralButton("Redetect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (isError) {
                            initTrustFinger();
                        }
                        else {
                            showAlertDialog(false, "No fingerprint device detected!");
                        }
                    }
                })
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                }).create().show();
    }

}