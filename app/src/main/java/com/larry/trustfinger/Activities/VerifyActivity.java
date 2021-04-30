

package com.larry.trustfinger.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.larry.trustfinger.R;
import com.larry.trustfinger.adapter.MyPagerAdapter;
import com.larry.trustfinger.fragment.DeviceInfoFragment;
import com.larry.trustfinger.fragment.EnrollFragment;
import com.larry.trustfinger.interfaces.LedCallback;
import com.aratek.trustfinger.sdk.DeviceListener;
import com.aratek.trustfinger.sdk.DeviceModel;
import com.aratek.trustfinger.sdk.DeviceOpenListener;
import com.aratek.trustfinger.sdk.LedIndex;
import com.aratek.trustfinger.sdk.LedStatus;
import com.aratek.trustfinger.sdk.TrustFinger;
import com.aratek.trustfinger.sdk.TrustFingerDevice;
import com.aratek.trustfinger.sdk.TrustFingerException;
import com.larry.trustfinger.utils.Config;
import com.larry.trustfinger.utils.MediaPlayerHelper;
import com.larry.trustfinger.utils.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.syvanstone.PosManager;

public class VerifyActivity extends FragmentActivity implements DeviceOpenListener, LedCallback {
    private static final String TAG = "VerifyActivity";
    private static final String ACTION_USB_PERMISSION = "com.aratek.trustfinger.USB_PERMISSION";
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
    //private CaptureFragment mCaptureFragment;
    //private VerifyFragment mVerifyFragment;
    private EnrollFragment mEnrollFragment;
//   private TransactionFragment mVerifyFragment;
//    private IdentifyFragment mIdentifyFragment;
    private DeviceInfoFragment mDeviceInfoFragment;
    private String[] titles;
    private Handler handler = new Handler();
    private int mDeviceId = 0;
    private boolean isDeviceOpened = false;
    private MyApplication mApp;
    PosManager mPosManager;
    @BindView(R.id.backss) Button back;
    //@BindView(R.id.login) Button login;
    //@BindView(R.id.refresh) Button identification;
//    @BindView(R.id.verification) Button verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        ButterKnife.bind(this);
        findViews();
        initTrustFinger();
        mApp = (MyApplication) getApplication();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
        else {


            run();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                mPosManager = PosManager.create();

                int  ret =  mPosManager.fingerSwitchOn();
                Log.e("TrustFinger", "ret: " + ret);

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        //Refresh();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FingerPrintsupdateActivity.class);
                startActivity(intent);

            }
        });
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), FingeprintActivity.class);
//                startActivity(intent);
//            }
//        });

    }



    private void Refresh() {
        System.exit(0);
        Intent intent = new Intent(getApplicationContext(), RefreshVerifyActivity.class);
        startActivity(intent);
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

    public  void initTrustFinger() {
        try {
            mTrustFinger = TrustFinger.getInstance(this.getApplicationContext());
            //            mTextView_version.setText("v" + mTrustFinger.getSdkVersion());
            //mTrustFinger.initialize();
            mTrustFinger.initialize();



            /*Start*/

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


                    isDeviceOpened = true;


                    mButton_openClose.setTextColor(Color.parseColor("#1D9F9A"));
                    mButton_openClose.setText(getString(R.string.btn_open_device));



                    //setFragmentDatas(null);


                    mSpinner_deviceType.setEnabled(true);
                    mSpinner_usbDevice.setEnabled(true);
                    mCheckBox_enableLed.setEnabled(true);
                    handleMsg("Device detached!", Color.RED);
                }
            });
            /*end */



            /*if (mTrustFinger.getDeviceCount() <= 0) {
                showAlertDialog(false, "No fingerprint device detected!");
            }*/
        }
        catch (TrustFingerException e) {
            handleMsg("TrustFinger getInstance Exception: " + e.getType().toString() + "", Color.RED);
            System.exit(0);
            Intent intent = new Intent(getApplicationContext(), RefreshVerifyActivity.class);
            startActivity(intent);
            if (e.getType().toString().equals("DEVICE_NOT_FOUND")) {
                showAlertDialog(true, "No fingerprint device detected!");

            }
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
//        if (mCaptureFragment != null)
//            mCaptureFragment.setDatas(mTrustFingerDevice);
//        if (mEnrollFragment != null)
//            mEnrollFragment.setDatas(mTrustFingerDevice);

//        if (mIdentifyFragment != null)
//            mIdentifyFragment.setDatas(mTrustFingerDevice);
        if (mEnrollFragment != null)
            mEnrollFragment.setDatas(mTrustFingerDevice);
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

    /* start Checked today */

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(VerifyActivity.this).setMessage("This app need to open the permissions").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", getPackageName(), null));
                                startActivityForResult(intent, 100);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(VerifyActivity.this, "Permissions denied!", Toast
                                        .LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        else {
            run();
        }
    }
    /* end Checked today */

    private void run() {
        initDatas();
        File file = new File(Config.COMMON_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initDatas() {
        ArrayAdapter arrayAdapter_dev_type = ArrayAdapter.createFromResource(this, R.array
                .device_type, R.layout.spinner_list_item);
        arrayAdapter_dev_type.setDropDownViewResource(R.layout.spinner_list_item);
        mSpinner_deviceType.setAdapter(arrayAdapter_dev_type);
        mSpinner_deviceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemChange(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapter_usbDevice = new ArrayAdapter<String>(VerifyActivity.this, R.layout
                .spinner_list_item, sp_usbDevice_datas);
        adapter_usbDevice.setDropDownViewResource(R.layout.spinner_list_item);
        mSpinner_usbDevice.setAdapter(adapter_usbDevice);
        mSpinner_usbDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String tag = parent.getItemAtPosition(position).toString();
                if (getString(R.string.sp_usb_device_auto).equals(tag)) {
                    mDeviceId = 0;
                    return;
                }
                String[] strs = tag.split("-");
                if (strs != null && strs.length > 0) {
                    mDeviceId = Integer.parseInt(strs[0]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDeviceId = -1;
            }
        });
        mCheckBox_enableLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener
                () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mApp.setLedEnable(true);
                }
                else {
                    mApp.setLedEnable(false);
                }
                if (isDeviceOpened) {
                    if (mTrustFingerDevice != null) {
                        if (mTrustFingerDevice.getDeviceModel() == 600) {
                            if (!isChecked) {
                                if (mTrustFingerDevice.getLedStatus(LedIndex.RED) != LedStatus
                                        .CLOSE) {
                                    mTrustFingerDevice.setLedStatus(LedIndex.RED, LedStatus.CLOSE);
                                }
                                if (mTrustFingerDevice.getLedStatus(LedIndex.GREEN) != LedStatus
                                        .CLOSE) {
                                    mTrustFingerDevice.setLedStatus(LedIndex.GREEN, LedStatus
                                            .CLOSE);
                                }
                            }
                        }
                    }
                }
            }
        });

        isDeviceOpened = false;
        mButton_openClose.setText(getString(R.string.btn_open_device));
        mButton_openClose.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton_openClose.setEnabled(false);
                if (!isDeviceOpened) {
                    if (mDeviceId == -1) {

                        mButton_openClose.setEnabled(true);
                        return;
                    }
                    //                    if (sp_usbDevice_datas.size() == 1) {
                    //                        handleMsg("Device not attached", Color.RED);
                    //                        mButton_openClose.setEnabled(true);
                    //                        return;
                    //                    }
                    String tag = mSpinner_usbDevice.getSelectedItem().toString();
                    if (getString(R.string.sp_usb_device_auto).equals(tag)) {
                        mDeviceId = 0;
                    }
                    else {
                        String[] strs = tag.split("-");
                        if (strs != null && strs.length > 0) {
                            mDeviceId = Integer.parseInt(strs[0]);
                        }
                    }
                    try {
                        mTrustFinger.openDevice(mDeviceId, VerifyActivity.this);
                    }
                    catch (TrustFingerException e) {
                        mButton_openClose.setEnabled(true);
                        handleMsg("Device open exception:" + e.getType().toString() + "", Color
                                .RED);
                        e.printStackTrace();
                    }
                }
                else {
                    int position = mViewPager.getCurrentItem();
//                    if (position == 0) {
//                        mCaptureFragment.forceStop();
//                        mCaptureFragment.resetUI();
//                    }
//                    if (position == 1) {
//                        mEnrollFragment.forceStop();
//                        mEnrollFragment.resetUI();
//                    }
                    if (position == 0) {
                        mEnrollFragment.forceStop();
                        mEnrollFragment.resetUI();
                   }
//                    if (position == 3) {
//                        mIdentifyFragment.forceStop();
//                        mIdentifyFragment.resetUI();
//                    }
                    try {
                        if (mTrustFingerDevice.getDeviceModel() == DeviceModel.A600) {
                            if (mTrustFingerDevice.getLedStatus(LedIndex.RED) != LedStatus.CLOSE) {
                                mTrustFingerDevice.setLedStatus(LedIndex.RED, LedStatus.CLOSE);
                            }
                            if (mTrustFingerDevice.getLedStatus(LedIndex.GREEN) != LedStatus
                                    .CLOSE) {
                                mTrustFingerDevice.setLedStatus(LedIndex.GREEN, LedStatus.CLOSE);
                            }
                        }

                        mButton_openClose.setTextColor(Color.parseColor("#1D9F9A"));
                        mTrustFingerDevice.close();

                        mTrustFingerDevice = null;


                        isDeviceOpened = false;



                        mButton_openClose.setText(getString(R.string.btn_open_device));
                        mButton_openClose.setEnabled(true);
                        mSpinner_deviceType.setEnabled(true);
                        mSpinner_usbDevice.setEnabled(true);


                        mCheckBox_enableLed.setEnabled(false);



                        setFragmentDatas(null);
                        handleMsg("Device closed!", Color.RED);
                    }
                    catch (TrustFingerException e) {
                        mButton_openClose.setTextColor(Color.parseColor("#1D9F9A"));
                        mButton_openClose.setEnabled(true);
                        handleMsg("Device close exception : " + e.getType().toString() + "",
                                Color.RED);
                        e.printStackTrace();
                    }
                }
            }
        });
//        mCaptureFragment = new CaptureFragment();
//        mCaptureFragment.setLedCallback(this);
        mEnrollFragment = new EnrollFragment();
        mEnrollFragment.setLedCallback(this);
       // mVerifyFragment = new VerifyFragment();
        //mVerifyFragment.setLedCallback(this);
//        mIdentifyFragment = new IdentifyFragment();
//        mIdentifyFragment.setLedCallback(this);
        mDeviceInfoFragment = new DeviceInfoFragment();
//        fragmnts.add(mCaptureFragment);
        fragmnts.add(mEnrollFragment);
//        fragmnts.add(mIdentifyFragment);
      //fragmnts.add(mVerifyFragment);
       fragmnts.add(mDeviceInfoFragment);
        titles = getResources().getStringArray(R.array.tabs_name);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmnts, titles));
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager
                            .HIDE_NOT_ALWAYS);
                }


                handleMsg("", Color.BLACK);
                if (position != 0) {
                    mEnrollFragment.forceStop();
                    mEnrollFragment.resetUI();
                }


                if (position == 0) {
                    if (isDeviceOpened) {
                        handleMsg("Fill your info and select a finger for enrollment", Color
                                .RED);
                    }
                    else {
                        handleMsg(getString(R.string.msg_click_open_device_button), Color.BLACK);
                    }

                }
                else if (position == 1) {
                    if (isDeviceOpened) {
                        handleMsg("Identify your fingerprint", Color.RED);
                    }
                    else {
                        handleMsg(getString(R.string.msg_click_open_device_button), Color.BLACK);
                    }
                    //mVerifyFragment.loadEnrolledUsers();
                }

                else if (position == 2) {
                    if (isDeviceOpened) {
                        handleMsg("Verify your fingerprint to complete transaction", Color
                                .RED);
                    }
                    else {
                        handleMsg(getString(R.string.msg_click_open_device_button), Color.BLACK);
                    }
                }
                else if (position == 4) {
                    if (isDeviceOpened) {
                        handleMsg("Device opened", Color.BLACK);
                    }
                    else {
                        handleMsg(getString(R.string.msg_click_open_device_button), Color.BLACK);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void onItemChange(int position) {
        List<String> deviceList = mTrustFinger.getDeviceList();
        sp_usbDevice_datas_all.clear();
        for (int i = 0; i < deviceList.size(); i++) {
            sp_usbDevice_datas_all.add(i + "-" +
                    deviceList.get(i));
        }
        sp_usbDevice_datas.clear();
        sp_usbDevice_datas.add(getString(R.string.sp_usb_device_auto));//show default
        // value
        sp_usbDevice_datas.addAll(sp_usbDevice_datas_all);
        String deviceType = mSpinner_deviceType.getItemAtPosition
                (position).toString();
        sp_usbDevice_datas.clear();
        sp_usbDevice_datas.add(getString(R.string.sp_usb_device_auto));//set default value
        if (getString(R.string.sp_device_type_auto).equals(deviceType)) {
            sp_usbDevice_datas.addAll(sp_usbDevice_datas_all);
            mSpinner_usbDevice.setSelection(0);
        }
        else if (getString(R.string.sp_device_type_a400).equals(deviceType)) {
            sp_usbDevice_datas_A400.clear();
            for (int i = 0; i < sp_usbDevice_datas_all.size(); i++) {
                if (sp_usbDevice_datas_all.get(i).contains(getString(R.string
                        .sp_device_type_a400))) {
                    sp_usbDevice_datas_A400.add(sp_usbDevice_datas_all.get(i));
                }
            }
            sp_usbDevice_datas.addAll(sp_usbDevice_datas_A400);
            if (sp_usbDevice_datas.size() > 1) {
                mSpinner_usbDevice.setSelection(1);
            }
        }
        else if (getString(R.string.sp_device_type_a600).equals(mSpinner_deviceType
                .getItemAtPosition
                        (position).toString())) {
            sp_usbDevice_datas_A600.clear();
            for (int i = 0; i < sp_usbDevice_datas_all.size(); i++) {
                if (sp_usbDevice_datas_all.get(i).contains(getString(R.string
                        .sp_device_type_a600))) {
                    sp_usbDevice_datas_A600.add(sp_usbDevice_datas_all.get(i));
                }
            }
            sp_usbDevice_datas.addAll(sp_usbDevice_datas_A600);
            if (sp_usbDevice_datas.size() > 1) {
                mSpinner_usbDevice.setSelection(1);
            }
        }
        adapter_usbDevice.notifyDataSetChanged();
    }

    @Override
    public void openSuccess(TrustFingerDevice trustFingerDevice) {
        mButton_openClose.setTextColor(Color.RED);
        handleMsg("Device open success", Color.BLACK);
        mTrustFingerDevice = trustFingerDevice;
        int model = mTrustFingerDevice.getDeviceModel();
        if (model == 400 ) {
            setDeviceTypeSelection(1);
            mCheckBox_enableLed.setChecked(false);
            mCheckBox_enableLed.setEnabled(false);
        }
        else if (model == 600) {
            int firmwareVersion = Integer.valueOf(trustFingerDevice.getDeviceDescription()
                    .getFwVersion());
            if (firmwareVersion < 4200) {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Error")//设置对话框的标题
                        .setMessage("The current firmware version is " + firmwareVersion + ",this " +
                                "software only supports version 4200 or above!")//设置对话框的内容
                        //设置对话框的按钮
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).create().show();
                return;
            }
            mCheckBox_enableLed.setChecked(true);
            mCheckBox_enableLed.setEnabled(true);
            setDeviceTypeSelection(2);
        }
        else {
            //setDeviceTypeSelection(0);
            setDeviceTypeSelection(1);
        }

        if (sp_usbDevice_datas.size() > 1) {
            mSpinner_usbDevice.setSelection(mDeviceId + 1);
        }
        adapter_usbDevice.notifyDataSetChanged();
        isDeviceOpened = true;
        mButton_openClose.setEnabled(true);
        mButton_openClose.setText(getString(R.string.btn_close_device));
        mSpinner_deviceType.setEnabled(false);
        mSpinner_usbDevice.setEnabled(false);
        //                                mCheckBox_enableLed.setEnabled
        // (false);
        setFragmentDatas(mTrustFingerDevice);
    }

    private void setDeviceTypeSelection(int position) {
        int currentPosition = mSpinner_deviceType.getSelectedItemPosition();
        mSpinner_deviceType.setSelection(position);
        if (position != currentPosition) {
            onItemChange(position);
        }
    }


    @Override
    public void openFail(String s) {
        handleMsg("Device open fail", Color.RED);
        isDeviceOpened = false;
        mButton_openClose.setEnabled(true);
    }

    @Override
    public void setLedEnable(final boolean isEnable) {
        handler.post(new Runnable() {
            @Override
            public void run() {
            if (mTrustFingerDevice != null){
                if (mTrustFingerDevice.getDeviceModel() == 600)
                    mCheckBox_enableLed.setEnabled(isEnable);
            }
            }
        });
    }




    @Override
    protected void onStop() {
        super.onStop();
    }



    private long exitTime = 0;

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(this, "Press twice to refresh the app", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//
//                int  ret =  mPosManager.fingerSwitchOff();
//
//                Log.e("TrustFinger", "ret: " + ret
//                );
//                try {
//                    Thread.sleep(1500);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                System.exit(0);
//            }
//            return false;
//
//
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }
@Override
public void onBackPressed() {
    return;
}
}

