//
// Reworked decompiled by Procyon v0.5.30
//

package com.android.server;

import android.net.Uri;
import android.database.ContentObserver;
import android.os.IBinder.DeathRecipient;
import android.os.Binder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import android.os.RemoteException;
import android.provider.MzSettings;
import android.provider.Settings;
import android.provider.Settings.System;
import android.view.IGestureCallback;
import android.view.IGestureManager;
//import android.os.BuildExt;
import android.util.Log;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Vibrator;
import android.hardware.SensorManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
import java.util.ArrayList;

import android.os.Handler;
import android.os.IBinder;
import android.content.IntentFilter;
import android.content.Context;

import com.android.internal.R;

public class GestureManagerService extends IGestureManager.Stub
{
    private static final int CONTORL_DISABLE_ALL = 0;
    private static final int CONTORL_ENABLE_TAP = 0x1;
    private static final int CONTORL_ENABLE_SLIDE_LEFT = 0x2;
    private static final int CONTORL_ENABLE_SLIDE_RIGHT = 0x4;
    private static final int CONTORL_ENABLE_SLIDE_UP = 0x8;
    private static final int CONTORL_ENABLE_SLIDE_DOWN = 0x10;
    private static final int CONTORL_ENABLE_DRAW_C = 0x20;
    private static final int CONTORL_ENABLE_DRAW_E = 0x40;
    private static final int CONTORL_ENABLE_DRAW_M = 0x80;
    private static final int CONTORL_ENABLE_DRAW_O = 0x100;
    private static final int CONTORL_ENABLE_DRAW_S = 0x200;
    private static final int CONTORL_ENABLE_DRAW_V = 0x400;
    private static final int CONTORL_ENABLE_DRAW_W = 0x800;
    private static final int CONTORL_ENABLE_DRAW_Z = 0x1000;

    private static final boolean DEBUG = true;
    private static final String TAG = "GestureManager";
    private static final int VIBRATE_TIME_LONG = 50;
    private static final int VIBRATE_TIME_SHORT = 25;
    private Context mContext;
    private int mEasyModeEnable = 0;
    private IntentFilter mFilter;
    private int mGestureAllDisable = 0;
    private String mGestureControlPath = null;
    private int[] mGestureData = new int[] { 160, 176, 177, 178, 179, 193, 192, 195, 196, 197, 199, 194, 202 };
    private String mGestureDataPath = null;
    private int mGestureGlobalSwitch = 0;
    GestureObserver mGestureObserver;
    Handler mHandler = new Handler();;
    ArrayList<GestureListener> mListeners = new ArrayList<GestureListener>();;
    private Sensor mProximitySensor;
    private SensorEventListener mProximitySensorListener;
    private BroadcastReceiver mReceiver;
    private SensorManager mSensorManager;
    private Vibrator mVibrator;

    public GestureManagerService(Context context) {
        mProximitySensorListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int n) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
            }
        };
        mContext = context;
        mSensorManager = (SensorManager)mContext.getSystemService("sensor");
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY, false);
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d(TAG, "onReceive(), ACTION_SCREEN_ON");
                    mSensorManager.unregisterListener(mProximitySensorListener);
                }
                else if (action.equals(Intent.ACTION_SCREEN_OFF) && mGestureAllDisable != 0 && mGestureGlobalSwitch == 1) {
                    Log.d(TAG, "onReceive(), ACTION_SCREEN_OFF");
                    mSensorManager.registerListener(mProximitySensorListener, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        };
        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_SCREEN_ON);
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(mReceiver, mFilter);
        mVibrator = (Vibrator)mContext.getSystemService("vibrator");
        //if (!BuildExt.IS_MX2 && !BuildExt.IS_MX3) {
            mGestureDataPath = mContext.getResources().getString(R.string.gesture_data_path_string);
            mGestureControlPath = mContext.getResources().getString(R.string.gesture_control_path_string);
        //}
    }

    private void disableAllGesture() {
    }

    private void enableAllGesture() {
    }

    private GestureListener findByCallbackLocked(IGestureCallback gestureCallback) {
        for (int i = 0; i < this.mListeners.size(); ++i) {
            GestureListener gestureListener = mListeners.get(i);
            if (gestureCallback == gestureListener.mCallback) {
                return gestureListener;
            }
        }
        return null;
    }

    private void initGestureControl() {
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DOUBLE_CLICK, 0) != 0) {
            mGestureAllDisable = CONTORL_ENABLE_TAP;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_LEFT_RIGHT, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_SLIDE_LEFT;
            mGestureAllDisable |= CONTORL_ENABLE_SLIDE_RIGHT;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_UP, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_SLIDE_UP;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_DOWN, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_SLIDE_DOWN;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_C, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_C;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_E, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_E;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_M, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_M;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_O, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_O;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_S, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_S;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_V, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_V;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_W, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_W;
        }
        if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_Z, 0) == 1) {
            mGestureAllDisable |= CONTORL_ENABLE_DRAW_Z;
        }
        Log.d(TAG, "initGestureControl , mGestureAllDisable = " + mGestureAllDisable);
        mGestureGlobalSwitch = Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SWITCH, 0);
        Log.d(TAG, "initGestureControl , mGestureGlobalSwitch = " + mGestureGlobalSwitch);
        mEasyModeEnable = Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_EASY_MODE, 0);
        Log.d(TAG, "initGestureControl , mEasyModeEnable = " + mEasyModeEnable);
        if (mGestureAllDisable == 0 || mGestureGlobalSwitch == 0 || mEasyModeEnable == 1) {
            writeToControl(mGestureControlPath, 0);
        }
        else {
            writeToControl(mGestureControlPath, 1);
        }
    }

    private void notifyListeners(int n) {
        for (GestureListener gestureListener : mListeners) {
            if (gestureListener != null) {
                try {
                    gestureListener.mCallback.onGestureTrigger(n);
                    Log.d(TAG, "notifyListeners " + gestureListener.mTag + ", type is " + n);
                }
                catch (RemoteException ex) {
                    Log.e(TAG, "Fails to notify listener ", ex);
                }
            }
        }
    }

    private int readFrom(String p0) {
        int result = -1;
        byte[] read  = new byte[8];
        try (FileInputStream fis = new FileInputStream(p0)){
            int n = fis.read(read);
            if (n > 0) {
                String strValue = new String(read, "UTF-8").substring(0, n-1);
                Log.d(TAG, "readFrom: read: " + strValue);
                result = Integer.parseInt(strValue, 10);
            }
            fis.close();
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "readFrom: Cannot convert value", e);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "readFrom: file not found:", e);
        } catch (IOException e) {
            Log.e(TAG, "readFrom: cannot read from file " + p0, e);
        }
        return result;
    }

    private String toString(byte[] array, int n) {
        byte[] array2 = new byte[n];
        for (int i = 0; i < n; ++i) {
            array2[i] = (byte)(array[i] + 48);
        }
        array = null;
        try {
            array = (byte[])(Object)new String(array2, "UTF-8");
            return (String)(Object)array;
        }
        catch (UnsupportedEncodingException ex) {
            Log.e(TAG, "Failed to convert ", ex);
            return (String)(Object)array;
        }
    }

    private void writeToControl(String p0, int p1) {
        if (p0 == null || p0.length() == 0) {
            throw new NullPointerException();
        }
        Log.d(TAG, "writeToControl , deviceNode: " + p0 + " enable:" + p1);
        try (FileOutputStream fos = new FileOutputStream(new File(p0))) {
            fos.write(p1 + '0');
        } catch (IOException e) {
            Log.d(TAG, "writeToControl , cannot write to" + p0);
        }
    }

    public void pauseAllGesture() throws RemoteException {
    }

    public void registeCallback(IGestureCallback gestureCallback, String s) throws RemoteException {
        synchronized (this) {
            if (findByCallbackLocked(gestureCallback) != null) {
                Log.e("GestureManager", "Object tried to add another listeners", new Exception("Called by tag"));
            }
            GestureListener gestureListener = new GestureListener(gestureCallback, s);
            gestureCallback.asBinder().linkToDeath(new BinderDeath(s, Binder.getCallingUid(), gestureListener), 0);
            mListeners.add(gestureListener);
            Log.d("GestureManager", "Object tried to add listeners: " + s + ", mListeners.size = " + mListeners.size());
        }
    }

    public void removeCallback(IGestureCallback gestureCallback) throws RemoteException {
        synchronized (this) {
            GestureListener byCallbackLocked = findByCallbackLocked(gestureCallback);
            boolean removed = false;
            if (byCallbackLocked != null) {
                removed = mListeners.remove(byCallbackLocked);
                Log.d("GestureManager", "remove tried to add listeners: " + byCallbackLocked.mTag + ", mListeners.size = " + mListeners.size());
            }
            if (!removed) {
                Log.e("GestureManager", "can't find the listener. ", new Exception("Called by"));
            }
        }
    }

    public void resumeAllGesture() throws RemoteException {
    }

    public void systemReady() {
        initGestureControl();
        mGestureObserver = new GestureObserver(this.mHandler);
        ContentResolver cr = mContext.getContentResolver();
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_LEFT_RIGHT), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_UP), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_DOWN), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_C), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_E), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_M), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_O), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_S), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_V), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_W), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_Z), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DOUBLE_CLICK), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SWITCH), false, (ContentObserver)mGestureObserver);
        cr.registerContentObserver(Settings.System.getUriFor(MzSettings.System.MZ_EASY_MODE), false, (ContentObserver)mGestureObserver);
        Log.d("GestureManager", "system startWatching");
    }

    public boolean triggerGesture() throws RemoteException {
        int gesture = 0;
        boolean result = false;
        int n = -1;
        if (mGestureDataPath != null) {
            gesture = readFrom(mGestureDataPath);
        }
        if (gesture != 0) {
            int idx = -1;
            for (int i = 0; i < mGestureData.length; i++) {
                if (gesture == mGestureData[i]) {
                    idx = i;
                    break;
                }
            }
            if (idx == -1 || (mGestureAllDisable & (1 << idx)) == 0x0) {
                Log.e("GestureManager", "This gesture (type = " + gesture + ") is off!");
            }
            else {
                result = true;
                //if (BuildExt.IS_M1) {
                //    mVibrator.vibrate(VIBRATE_TIME_LONG);
                //}
                //else {
                    mVibrator.vibrate(VIBRATE_TIME_SHORT);
                //}
                final int aGesture = gesture;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyListeners(aGesture);
                    }
                });
            }
        }
        return result;
    }

    private final class BinderDeath implements IBinder.DeathRecipient
    {
        private GestureListener mListener;
        private String mTag;
        private int mUid;

        BinderDeath(String tag, int uid, GestureListener listener) {
            mTag = tag;
            mUid = uid;
            mListener = listener;
        }

        public void binderDied() {
            synchronized (this) {
                Log.w("GestureManager", "Death received from " + mTag + " for uid " + mUid);
                mListeners.remove(mListener);
            }
        }
    }

    private final class GestureListener
    {
        IGestureCallback mCallback;
        String mTag;

        GestureListener(IGestureCallback callback, String tag) {
            mTag = tag;
            mCallback = callback;
        }
    }

    class GestureObserver extends ContentObserver
    {
        public GestureObserver(Handler handler) {
            super(handler);
        }

        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_UP).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_UP, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_SLIDE_UP;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_SLIDE_UP;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_LEFT_RIGHT).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_LEFT_RIGHT, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_SLIDE_LEFT;
                    mGestureAllDisable |= CONTORL_ENABLE_SLIDE_RIGHT;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_SLIDE_LEFT;
                    mGestureAllDisable &= ~CONTORL_ENABLE_SLIDE_RIGHT;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DOUBLE_CLICK).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DOUBLE_CLICK, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_TAP;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_TAP;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_DOWN).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SLIDE_DOWN, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_SLIDE_DOWN;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_SLIDE_DOWN;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_C).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_C, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_C;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_C;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_E).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_E, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_E;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_E;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_M).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_M, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_M;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_M;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_O).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_O, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_O;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_O;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_S).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_S, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_S;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_S;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_V).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_V, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_V;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_V;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_W).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_W, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_W;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_W;
                }
            }
            else if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_DRAW_Z).equals(uri)) {
                if (Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_DRAW_Z, 0) == 1) {
                    mGestureAllDisable |= CONTORL_ENABLE_DRAW_Z;
                }
                else {
                    mGestureAllDisable &= ~CONTORL_ENABLE_DRAW_Z;
                }
            }
            Log.d(TAG, "onChange , mGestureAllDisable = " + mGestureAllDisable);
            if (Settings.System.getUriFor(MzSettings.System.MZ_QUICK_WAKEUP_SWITCH).equals(uri)) {
                mGestureGlobalSwitch = Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_QUICK_WAKEUP_SWITCH, 0);
            }
            Log.d(TAG, "onChange , mGestureGlobalSwitch = " + mGestureGlobalSwitch);
            if (Settings.System.getUriFor(MzSettings.System.MZ_EASY_MODE).equals(uri)) {
                mEasyModeEnable = Settings.System.getInt(mContext.getContentResolver(), MzSettings.System.MZ_EASY_MODE, 0);
            }
            Log.d(TAG, "onChange , mEasyModeEnable = " + mEasyModeEnable);
            if (mGestureAllDisable == 0 || mGestureGlobalSwitch == 0 || mEasyModeEnable == 1) {
                writeToControl(mGestureControlPath, 0);
            }
            else {
                writeToControl(mGestureControlPath, 1);
            }
        }
    }
}
