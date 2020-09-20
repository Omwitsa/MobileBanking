package cn.com.syvanstone;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

public class PosManager {

    private static PosManager instance = null;
    private  long mNativeContext; // accessed by native methods
    private int mListenerContext; // accessed by native methods
    private EventHandler mEventHandler;
    private static final String TAG = "PosManager";
    private static final int NATIVE_SERVER_DIED =4 ;
    private class EventHandler extends Handler {
    private PosManager mPosManager;

    public EventHandler(PosManager pm, Looper looper) {
        super(looper);
        mPosManager = pm;
    }

    @Override
    public void handleMessage(Message msg) {
        if (mPosManager.mNativeContext == 0) {
            return;
        }
        switch (msg.what) {
            case NATIVE_SERVER_DIED:
                instance = null;
                mPosManager = PosManager.create();
                Log.d("samfan","服务端挂掉，重新连接");
                break;
            default:
                return;
        }
    }
}

    private static void postEventFromNative(Object posmanager_ref,
                                            int what, int arg1, int arg2, Object obj) {
        PosManager pm = (PosManager) ((WeakReference) posmanager_ref).get();

        if (pm == null) {
            return;
        }
        if (pm.mNativeContext == 0) {
                //Log.w(TAG, "posmanager went away with unhandled events");
                return;
        }

        if (pm.mEventHandler != null) {
            Message m = pm.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            pm.mEventHandler.sendMessage(m);
        }

    }

    private PosManager() {
         Looper looper;
        if ((looper = Looper.myLooper()) != null) {
            mEventHandler = new EventHandler(this, looper);
        } else if ((looper = Looper.getMainLooper()) != null) {
            mEventHandler = new EventHandler(this, looper);
        } else {
            mEventHandler = null;
        }
        /* Native setup requires a weak reference to our object.
         * It's easier to create it here than in C++.
         */
        native_setup(new WeakReference<PosManager>(this));
    }

    /**
     * 返回pos设备管理器的实例。通过该实例可以进一步操作各个pos设备模块
     *
     * @return PosManager实例
     */
    public static PosManager create() {
        if (instance == null) {
            instance = new PosManager();
        }
        return instance;
    }

    /**
     * 释放PosManager资源，执行该函数后，所有API将不可用。除非重新调用{@link #create()};
     */
    public void release() {
        _release();
    }

    ///////////////////////////////////////////////////////////////

    static {
        System.loadLibrary("sycloudpos");
        native_init();
    }

    private static native void native_init();
    private native void native_setup(Object mediaplayer_this);
    private native void native_finalize();
    public native int fingerSwitchOn();
    public native int _release();
    public native int fingerSwitchOff();
}
