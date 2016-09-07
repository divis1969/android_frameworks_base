//
// Decompiled by Procyon v0.5.30
//

package com.mediatek.perfservice;

import android.os.Handler;
import android.os.Message;
import com.mediatek.xlog.Xlog;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import dalvik.system.VMRuntime;
import android.os.HandlerThread;
import android.content.Context;

public class PerfServiceManager implements IPerfServiceManager
{
    private static final float HEAP_UTILIZATION_DURING_FRAME_UPDATE = 0.5f;
    private static final int RENDER_BIT = 0x800000;
    private static final String TAG = "PerfServiceManager";
    private static final int TOUCH_BOOST_DURATION_MS = 3000;
    private static final int UI_UPDATE_DURATION_MS = 500;
    private boolean bDuringTouch;
    private boolean bDuringTouchBoost;
    private Context mContext;
    private float mDefaultUtilization;
    private int mDisplayType;
    private PerfServiceThreadHandler mHandler;
    private HandlerThread mHandlerThread;
    private VMRuntime mRuntime;
    List<Integer> mTimeList;

    public PerfServiceManager(Context context) {
        mContext = context;
        (mHandlerThread = new HandlerThread("PerfServiceManager", -2)).start();
        Looper looper = mHandlerThread.getLooper();
        if (looper != null) {
            mHandler = new PerfServiceThreadHandler(looper);
        }
        mTimeList = new ArrayList<Integer>();
        bDuringTouch = false;
        bDuringTouchBoost = false;
        mDisplayType = DISPLAY_TYPE_OTHERS;
        mRuntime = VMRuntime.getRuntime();
        mDefaultUtilization = mRuntime.getTargetHeapUtilization();
        log("Created and started PerfService thread");
    }

    private void log(String s) {
        Xlog.d(TAG, "[PerfService] " + s + " ");
    }

    private void loge(String s) {
        Xlog.e(TAG, "[PerfService] ERR: " + s + " ");
    }

    public static native int nativePerfBoostDisable(int p0);

    public static native int nativePerfBoostEnable(int p0);

    public static native int nativePerfDumpAll();

    public static native int nativePerfLevelBoost(int p0);

    public static native int nativePerfNotifyAppState(String p0, String p1, int p2);

    public static native int nativePerfSetFavorPid(int p0);

    public static native int nativePerfUserGetCapability(int p0);

    public static native int nativePerfUserRegScn(int p0, int p1);

    public static native int nativePerfUserRegScnConfig(int p0, int p1, int p2, int p3, int p4, int p5);

    public static native int nativePerfUserScnDisable(int p0);

    public static native int nativePerfUserScnDisableAll();

    public static native int nativePerfUserScnEnable(int p0);

    public static native int nativePerfUserScnReg(int p0, int p1, int p2, int p3);

    public static native int nativePerfUserScnRegBigLittle(int p0, int p1, int p2, int p3, int p4, int p5);

    public static native int nativePerfUserScnResetAll();

    public static native int nativePerfUserScnRestoreAll();

    public static native int nativePerfUserScnUnreg(int p0);

    public static native int nativePerfUserUnregScn(int p0);

    public void boostDisable(int arg1) {
        if (SCN_APP_TOUCH == arg1) {
            bDuringTouch = false;
            mHandler.startCheckTouchBoostTimerMs(TOUCH_BOOST_DURATION_MS);
        }
        mHandler.stopCheckTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_BOOST_DISABLE;
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    public void boostEnable(int arg1) {
        if (SCN_APP_TOUCH == arg1) {
            if (mDisplayType != DISPLAY_TYPE_GAME) {
                bDuringTouch = true;
                bDuringTouchBoost = true;
                mHandler.stopCheckTouchBoostTimer();
            } else {
                nativePerfBoostDisable(arg1);
                return;
            }
        }
        mHandler.stopCheckTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_BOOST_ENABLE;
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    public void boostEnableTimeout(int arg1, int arg2) {
        mHandler.stopCheckTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_BOOST_ENABLE_TIMEOUT;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.sendToTarget();
    }

    public void boostEnableTimeoutMs(int arg1, int arg2) {
        mHandler.stopCheckTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_BOOST_ENABLE_TIMEOUT_MS;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.sendToTarget();
    }

    public void dumpAll() {
        nativePerfDumpAll();
    }

    public void notifyAppState(String s, String s2, int n) {
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_NOTIFY_APP_STATE;
        msg.obj = new PerfServiceAppState(s, s2, n);
        msg.sendToTarget();
    }

    public void notifyDisplayType(int displayType) {
        log("notifyDisplayType:" + displayType);
        mDisplayType = displayType;
    }

    public void notifyFrameUpdate(int n) {
        log("notifyFrameUpdate - bDuringTouchBoost:" + bDuringTouchBoost);
        if (n != 0) {
            nativePerfSetFavorPid(0x800000 | n);
        }
        if (bDuringTouchBoost) {
            mHandler.stopCheckTimer(IPerfServiceManager.SCN_SW_FRAME_UPDATE);
            Message msg = mHandler.obtainMessage();
            msg.what = PerfServiceThreadHandler.MESSAGE_BOOST_ENABLE_TIMEOUT_MS;
            msg.arg1 = IPerfServiceManager.SCN_SW_FRAME_UPDATE;
            msg.arg2 = UI_UPDATE_DURATION_MS;
            msg.sendToTarget();
        }
    }

    public void notifyUserStatus(int n, int n2) {
        log("notifyUserStatus - type:" + n + " status:" + n2);
    }

    public void setFavorPid(int n) {
        nativePerfSetFavorPid(n);
    }

    public void systemReady() {
        log("systemReady, register ACTION_BOOT_COMPLETED");
    }

    public void userDisable(int arg1) {
        mHandler.stopCheckUserTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_DISABLE;
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    public void userDisableAll() {
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_DISABLE_ALL;
        msg.sendToTarget();
    }

    public void userEnable(int arg1) {
        mHandler.stopCheckUserTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_ENABLE;
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    public void userEnableTimeout(int arg1, int arg2) {
        mHandler.stopCheckUserTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_ENABLE_TIMEOUT;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.sendToTarget();
    }

    public void userEnableTimeoutMs(int arg1, int arg2) {
        mHandler.stopCheckUserTimer(arg1);
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_ENABLE_TIMEOUT_MS;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.sendToTarget();
    }

    public int userGetCapability(int n) {
        return nativePerfUserGetCapability(n);
    }

    public int userReg(int n, int n2, int n3, int n4) {
        return nativePerfUserScnReg(n, n2, n3, n4);
    }

    public int userRegBigLittle(int n, int n2, int n3, int n4, int n5, int n6) {
        return nativePerfUserScnRegBigLittle(n, n2, n3, n4, n5, n6);
    }

    public int userRegScn(int n, int n2) {
        return nativePerfUserRegScn(n, n2);
    }

    public void userRegScnConfig(int n, int n2, int n3, int n4, int n5, int n6) {
        nativePerfUserRegScnConfig(n, n2, n3, n4, n5, n6);
    }

    public void userResetAll() {
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_RESET_ALL;
        msg.sendToTarget();
    }

    public void userRestoreAll() {
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_RESTORE_ALL;
        msg.sendToTarget();
    }

    public void userUnreg(int n) {
        nativePerfUserScnUnreg(n);
    }

    public void userUnregScn(int arg1) {
        Message msg = mHandler.obtainMessage();
        msg.what = PerfServiceThreadHandler.MESSAGE_USER_UNREG_SCN;
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    public class PerfServiceAppState
    {
        private String mClassName;
        private String mPackName;
        private int mState;

        PerfServiceAppState(String packName, String className, int state) {
            mPackName = packName;
            mClassName = className;
            mState = state;
        }
    }

    private class PerfServiceThreadHandler extends Handler
    {
        private static final int MESSAGE_BOOST_DISABLE = 1;
        private static final int MESSAGE_BOOST_ENABLE = 0;
        private static final int MESSAGE_BOOST_ENABLE_TIMEOUT = 2;
        private static final int MESSAGE_BOOST_ENABLE_TIMEOUT_MS = 3;
        private static final int MESSAGE_DUMP_ALL = 34;
        private static final int MESSAGE_NOTIFY_APP_STATE = 4;
        private static final int MESSAGE_NOTIFY_FRAME_UPDATE = 36;
        private static final int MESSAGE_SET_FAVOR_PID = 35;
        private static final int MESSAGE_SW_FRAME_UPDATE_TIMEOUT = 37;
        private static final int MESSAGE_TIMER_DONT_USE1 = 10;
        private static final int MESSAGE_TIMER_SCN_APP_LAUNCH = 13;
        private static final int MESSAGE_TIMER_SCN_APP_ROTATE = 6;
        private static final int MESSAGE_TIMER_SCN_APP_SWITCH = 5;
        private static final int MESSAGE_TIMER_SCN_APP_TOUCH = 9;
        private static final int MESSAGE_TIMER_SCN_SW_CODEC = 7;
        private static final int MESSAGE_TIMER_SCN_SW_CODEC_BOOST = 8;
        private static final int MESSAGE_TIMER_SCN_SW_FRAME_UPDATE = 11;
        private static final int MESSAGE_TIMER_SCN_USER_BASE = 64;
        private static final int MESSAGE_TIMER_TOUCH_BOOST_DURATION = 12;
        private static final int MESSAGE_TOUCH_BOOST_DURATION = 38;
        private static final int MESSAGE_USER_DISABLE = 30;
        private static final int MESSAGE_USER_DISABLE_ALL = 32;
        private static final int MESSAGE_USER_ENABLE = 27;
        private static final int MESSAGE_USER_ENABLE_TIMEOUT = 28;
        private static final int MESSAGE_USER_ENABLE_TIMEOUT_MS = 29;
        private static final int MESSAGE_USER_GET_CAPABILITY = 23;
        private static final int MESSAGE_USER_REG = 20;
        private static final int MESSAGE_USER_REG_BIG_LITTLE = 21;
        private static final int MESSAGE_USER_REG_SCN = 24;
        private static final int MESSAGE_USER_REG_SCN_CONFIG = 25;
        private static final int MESSAGE_USER_RESET_ALL = 31;
        private static final int MESSAGE_USER_RESTORE_ALL = 33;
        private static final int MESSAGE_USER_UNREG = 22;
        private static final int MESSAGE_USER_UNREG_SCN = 26;

        public PerfServiceThreadHandler(Looper looper) {
            super(looper);
        }

        private int getScenarioTimer(int n) {
            switch (n) {
                default: {
                    n = -1;
                    break;
                }
                case SCN_APP_SWITCH: {
                    n = MESSAGE_TIMER_SCN_APP_SWITCH;
                    break;
                }
                case SCN_APP_ROTATE: {
                    n = MESSAGE_TIMER_SCN_APP_ROTATE;
                    break;
                }
                case SCN_SW_CODEC: {
                    n = MESSAGE_TIMER_SCN_SW_CODEC;
                    break;
                }
                case SCN_SW_CODEC_BOOST: {
                    n = MESSAGE_TIMER_SCN_SW_CODEC_BOOST;
                    break;
                }
                case SCN_APP_TOUCH: {
                    n = MESSAGE_TIMER_SCN_APP_TOUCH;
                    break;
                }
                case SCN_SW_FRAME_UPDATE: {
                    n = MESSAGE_TIMER_SCN_SW_FRAME_UPDATE;
                    break;
                }
                case SCN_APP_LAUNCH: {
                    n = MESSAGE_TIMER_SCN_APP_LAUNCH;
                    break;
                }
            }
            return n;
        }

        private void removeAllUserTimerList() {
            for (int i = mTimeList.size() - 1; i >= 0; --i) {
                mTimeList.remove(i);
            }
        }

        private void startCheckTimer(int scenarioTimer, int n) {
            Message msg = obtainMessage();
            scenarioTimer = getScenarioTimer(scenarioTimer);
            msg.what = scenarioTimer;
            if (scenarioTimer != -1) {
                sendMessageDelayed(msg, (long)(n * 1000));
            }
        }

        private void startCheckTimerMs(int scenarioTimer, int n) {
            Message msg = obtainMessage();
            scenarioTimer = getScenarioTimer(scenarioTimer);
            msg.what = scenarioTimer;
            if (scenarioTimer != -1) {
                sendMessageDelayed(msg, (long)n);
            }
        }

        private void startCheckTouchBoostTimerMs(int n) {
            Message msg = obtainMessage();
            msg.what = MESSAGE_TIMER_TOUCH_BOOST_DURATION;
            sendMessageDelayed(msg, (long)n);
        }

        private void startCheckUserTimer(int arg1, int n) {
            Message msg = obtainMessage();
            msg.what = arg1 + 64;
            msg.arg1 = arg1;
            sendMessageDelayed(msg, (long)(n * 1000));
            if (!mTimeList.contains(arg1)) {
                mTimeList.add(arg1);
            }
        }

        private void startCheckUserTimerMs(int arg1, int n) {
            Message msg = obtainMessage();
            msg.what = arg1 + 64;
            msg.arg1 = arg1;
            sendMessageDelayed(msg, (long)n);
            if (!mTimeList.contains(arg1)) {
                mTimeList.add(arg1);
            }
        }

        private void stopAllUserTimer() {
            for (int i = 0; i < mTimeList.size(); ++i) {
                removeMessages(mTimeList.get(i) + 64);
            }
        }

        private void stopCheckTimer(int scenarioTimer) {
            scenarioTimer = getScenarioTimer(scenarioTimer);
            if (scenarioTimer != -1) {
                removeMessages(scenarioTimer);
            }
        }

        private void stopCheckTouchBoostTimer() {
            removeMessages(12);
        }

        private void stopCheckUserTimer(int n) {
            removeMessages(n + 64);
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case MESSAGE_BOOST_ENABLE: {
                        nativePerfBoostEnable(message.arg1);
                        break;
                    }
                    case MESSAGE_BOOST_DISABLE: {
                        nativePerfBoostDisable(message.arg1);
                        return;
                    }
                    case MESSAGE_BOOST_ENABLE_TIMEOUT: {
                        nativePerfBoostEnable(message.arg1);
                        startCheckTimer(message.arg1, message.arg2);
                        return;
                    }
                    case MESSAGE_BOOST_ENABLE_TIMEOUT_MS: {
                        if (message.arg1 == SCN_SW_FRAME_UPDATE) {
                            if (!bDuringTouchBoost) {
                                return;
                            }
                            mRuntime.setTargetHeapUtilization(0.5f);
                        }
                        nativePerfBoostEnable(message.arg1);
                        startCheckTimerMs(message.arg1, message.arg2);
                        return;
                    }
                    case MESSAGE_NOTIFY_APP_STATE: {
                        PerfServiceAppState perfServiceAppState = (PerfServiceAppState)message.obj;
                        log("MESSAGE_NOTIFY_APP_STATE");
                        nativePerfNotifyAppState(perfServiceAppState.mPackName, perfServiceAppState.mClassName, perfServiceAppState.mState);
                        message.obj = null;
                        return;
                    }
                    case MESSAGE_TIMER_SCN_APP_SWITCH: {
                        log("MESSAGE_TIMER_SCN_APP_SWITCH");
                        nativePerfBoostDisable(1);
                        return;
                    }
                    case MESSAGE_TIMER_SCN_APP_ROTATE: {
                        log("MESSAGE_TIMER_SCN_APP_ROTATE");
                        nativePerfBoostDisable(2);
                        return;
                    }
                    case MESSAGE_TIMER_SCN_SW_CODEC: {
                        log("MESSAGE_TIMER_SCN_SW_CODEC");
                        nativePerfBoostDisable(3);
                        return;
                    }
                    case MESSAGE_TIMER_SCN_SW_CODEC_BOOST: {
                        log("MESSAGE_TIMER_SCN_SW_CODEC_BOOST");
                        nativePerfBoostDisable(4);
                        return;
                    }
                    case MESSAGE_TIMER_SCN_APP_TOUCH: {
                        log("MESSAGE_TIMER_SCN_APP_TOUCH");
                        nativePerfBoostDisable(5);
                        return;
                    }
                    case MESSAGE_TIMER_SCN_SW_FRAME_UPDATE: {
                        log("MESSAGE_TIMER_SCN_SW_FRAME_UPDATE");
                        if (!bDuringTouch) {
                            bDuringTouchBoost = false;
                        }
                        mRuntime.setTargetHeapUtilization(mDefaultUtilization);
                        log("set utilization:" + mRuntime.getTargetHeapUtilization());
                        nativePerfBoostDisable(SCN_SW_FRAME_UPDATE);
                        stopCheckTouchBoostTimer();
                        return;
                    }
                    case MESSAGE_TIMER_TOUCH_BOOST_DURATION: {
                        log("MESSAGE_TIMER_TOUCH_BOOST_DURATION");
                        bDuringTouchBoost = false;
                        nativePerfBoostDisable(SCN_SW_FRAME_UPDATE);
                        return;
                    }
                    case MESSAGE_USER_ENABLE: {
                        log("MESSAGE_USER_ENABLE: " + message.arg1);
                        nativePerfUserScnEnable(message.arg1);
                        return;
                    }
                    case MESSAGE_USER_DISABLE: {
                        log("MESSAGE_USER_DISABLE: " + message.arg1);
                        nativePerfUserScnDisable(message.arg1);
                        return;
                    }
                    case MESSAGE_USER_ENABLE_TIMEOUT: {
                        log("MESSAGE_USER_ENABLE_TIMEOUT: " + message.arg1 + ", " + message.arg2);
                        nativePerfUserScnEnable(message.arg1);
                        startCheckUserTimer(message.arg1, message.arg2);
                        return;
                    }
                    case MESSAGE_USER_ENABLE_TIMEOUT_MS: {
                        log("MESSAGE_USER_ENABLE_TIMEOUT_MS: " + message.arg1 + ", " + message.arg2);
                        nativePerfUserScnEnable(message.arg1);
                        startCheckUserTimerMs(message.arg1, message.arg2);
                        return;
                    }
                    case MESSAGE_USER_UNREG_SCN: {
                        log("MESSAGE_USER_UNREG_SCN: " + message.arg1);
                        nativePerfUserUnregScn(message.arg1);
                        return;
                    }
                    case MESSAGE_USER_RESET_ALL: {
                        log("MESSAGE_USER_RESET_ALL");
                        stopAllUserTimer();
                        removeAllUserTimerList();
                        nativePerfUserScnResetAll();
                        return;
                    }
                    case MESSAGE_USER_DISABLE_ALL: {
                        log("MESSAGE_USER_DISABLE_ALL");
                        nativePerfUserScnDisableAll();
                        return;
                    }
                    case MESSAGE_USER_RESTORE_ALL: {
                        log("MESSAGE_USER_RESTORE_ALL");
                        nativePerfUserScnRestoreAll();
                        return;
                    }
                    case MESSAGE_TIMER_SCN_APP_LAUNCH: {
                        log("MESSAGE_TIMER_SCN_APP_LAUNCH");
                        nativePerfBoostDisable(8);
                    }
                    default: {
                        log("MESSAGE_TIMER_SCN_USER_BASE:" + message.what);
                        if (message.what >= 64) {
                            nativePerfUserScnDisable(message.arg1);
                            break;
                        }
                        break;
                    }
                }
                return;
            }
            catch (NullPointerException ex) {
                loge("Exception in PerfServiceThreadHandler.handleMessage: " + ex);
                return;
            }
        }
    }
}
