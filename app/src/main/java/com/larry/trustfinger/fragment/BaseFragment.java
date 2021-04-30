package com.larry.trustfinger.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

//import com.aratek.trustfinger.Activities.
        //FingeprintActivity;
import com.larry.trustfinger.utils.MyApplication;
import com.aratek.trustfinger.sdk.DeviceModel;
import com.aratek.trustfinger.sdk.LedIndex;
import com.aratek.trustfinger.sdk.LedStatus;
import com.aratek.trustfinger.sdk.TrustFingerDevice;
import com.aratek.trustfinger.sdk.TrustFingerException;

/**
 * Created by hecl on 2018/9/19.
 */

public abstract class BaseFragment extends Fragment {
    protected TrustFingerDevice mTrustFingerDevice;
    protected MyApplication mApp;

    protected boolean viewCreated = false;

    private boolean isLedBlinking = false;

    public abstract void setDatas(TrustFingerDevice device);

//    public void handleMsg(String msg, int color) {
//        ((FingeprintActivity) getActivity()).handleMsg(msg, color);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (MyApplication) getActivity().getApplication();
    }

    protected void ledOnRed() {
        if (mTrustFingerDevice == null) {
            return;
        }

        if (mTrustFingerDevice.getDeviceModel() == DeviceModel.A600) {
            try {
                if (mTrustFingerDevice.getLedStatus(LedIndex.GREEN) != LedStatus.CLOSE) {
                    mTrustFingerDevice.setLedStatus(LedIndex.GREEN, LedStatus.CLOSE);
                }
                if (mTrustFingerDevice.getLedStatus(LedIndex.RED) != LedStatus.OPEN) {
                    mTrustFingerDevice.setLedStatus(LedIndex.RED, LedStatus.OPEN);
                }
            } catch (TrustFingerException e) {
                e.printStackTrace();
            }
        }
    }
    protected void ledOnGreen() {
        if (mTrustFingerDevice == null) {
            return;
        }

        if (mTrustFingerDevice.getDeviceModel() == DeviceModel.A600) {
            try {
                if (mTrustFingerDevice.getLedStatus(LedIndex.RED) != LedStatus.CLOSE) {
                    mTrustFingerDevice.setLedStatus(LedIndex.RED, LedStatus.CLOSE);
                }
                if (mTrustFingerDevice.getLedStatus(LedIndex.GREEN) != LedStatus.OPEN) {
                    mTrustFingerDevice.setLedStatus(LedIndex.GREEN, LedStatus.OPEN);
                }
            } catch (TrustFingerException e) {
                e.printStackTrace();
            }
        }
    }
    protected void ledOff() {
        if (mTrustFingerDevice == null) {
            return;
        }

        if (mTrustFingerDevice.getDeviceModel() == DeviceModel.A600) {
            try {
                if (mTrustFingerDevice.getLedStatus(LedIndex.GREEN) != LedStatus.CLOSE) {
                    mTrustFingerDevice.setLedStatus(LedIndex.GREEN, LedStatus.CLOSE);
                }
                if (mTrustFingerDevice.getLedStatus(LedIndex.RED) != LedStatus.CLOSE) {
                    mTrustFingerDevice.setLedStatus(LedIndex.RED, LedStatus.CLOSE);
                }
            } catch (TrustFingerException e) {
                e.printStackTrace();
            }
        }
    }

    protected void ledBlink(final LedIndex ledIndex, final int count) {
        if (mTrustFingerDevice == null) {
            return;
        }
        if (mTrustFingerDevice.getDeviceModel() == DeviceModel.A600) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        isLedBlinking = true;
                        if (mTrustFingerDevice.getLedStatus(LedIndex.GREEN) == LedStatus.OPEN) {
                            mTrustFingerDevice.setLedStatus(LedIndex.GREEN, LedStatus.CLOSE);
                        }
                        if (mTrustFingerDevice.getLedStatus(LedIndex.RED) == LedStatus.OPEN) {
                            mTrustFingerDevice.setLedStatus(LedIndex.RED, LedStatus.CLOSE);
                        }
                        for (int i = 0; i < count; i++) {
                            SystemClock.sleep(300);
                            mTrustFingerDevice.setLedStatus(ledIndex, LedStatus.OPEN);
                            SystemClock.sleep(300);
                            mTrustFingerDevice.setLedStatus(ledIndex, LedStatus.CLOSE);
                        }
                        isLedBlinking = false;
                    } catch (TrustFingerException e) {
                        e.printStackTrace();
                        isLedBlinking = false;
                    }
                }
            }.start();
        }
    }

    protected <T> void saveParameterToPreferences(String key , T value){
        if (value instanceof String || value == null){
            mApp.getSp().edit().putString(key , (String) value).apply();
        }else{
            mApp.getSp().edit().putBoolean(key , (Boolean) value).apply();
        }
    }

    protected <T> Object getParameterFromPreferences(String key , T defaultValue){
        if (defaultValue instanceof String || defaultValue == null){
            return mApp.getSp().getString(key , (String) defaultValue);
        }else{
            return mApp.getSp().getBoolean(key , (Boolean) defaultValue);
        }
    }
}
