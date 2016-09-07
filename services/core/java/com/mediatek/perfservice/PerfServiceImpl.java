//
// Decompiled by Procyon v0.5.30
//

package com.mediatek.perfservice;

import android.content.Intent;
import com.mediatek.xlog.Xlog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Context;

public class PerfServiceImpl extends IPerfService.Stub
{
    private static final String TAG = "PerfService";
    Context mContext;
    private IPerfServiceManager mPerfServiceMgr;

    public PerfServiceImpl(Context context, IPerfServiceManager perfServiceMgr) {
        mPerfServiceMgr = perfServiceMgr;
        mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        mContext.registerReceiver((BroadcastReceiver)new PerfServiceBroadcastReceiver(), intentFilter);
    }

    private void log(String s) {
        Xlog.d(TAG, "[PerfService] " + s + " ");
    }

    private void loge(String s) {
        Xlog.e(TAG, "[PerfService] ERR: " + s + " ");
    }

    public void boostDisable(int n) {
        mPerfServiceMgr.boostDisable(n);
    }

    public void boostEnable(int n) {
        mPerfServiceMgr.boostEnable(n);
    }

    public void boostEnableTimeout(int n, int n2) {
        mPerfServiceMgr.boostEnableTimeout(n, n2);
    }

    public void boostEnableTimeoutMs(int n, int n2) {
        mPerfServiceMgr.boostEnableTimeoutMs(n, n2);
    }

    public void dumpAll() {
        mPerfServiceMgr.dumpAll();
    }

    public void notifyAppState(String s, String s2, int n) {
        mPerfServiceMgr.notifyAppState(s, s2, n);
    }

    public void notifyDisplayType(int n) {
        mPerfServiceMgr.notifyDisplayType(n);
    }

    public void notifyFrameUpdate(int n) {
        mPerfServiceMgr.notifyFrameUpdate(n);
    }

    public void notifyUserStatus(int n, int n2) {
        mPerfServiceMgr.notifyUserStatus(n, n2);
    }

    public void setFavorPid(int favorPid) {
        mPerfServiceMgr.setFavorPid(favorPid);
    }

    public void userDisable(int n) {
        mPerfServiceMgr.userDisable(n);
    }

    public void userDisableAll() {
        mPerfServiceMgr.userDisableAll();
    }

    public void userEnable(int n) {
        mPerfServiceMgr.userEnable(n);
    }

    public void userEnableTimeout(int n, int n2) {
        mPerfServiceMgr.userEnableTimeout(n, n2);
    }

    public void userEnableTimeoutMs(int n, int n2) {
        mPerfServiceMgr.userEnableTimeoutMs(n, n2);
    }

    public int userGetCapability(int n) {
        return mPerfServiceMgr.userGetCapability(n);
    }

    public int userReg(int n, int n2, int n3, int n4) {
        return mPerfServiceMgr.userReg(n, n2, n3, n4);
    }

    public int userRegBigLittle(int n, int n2, int n3, int n4, int n5, int n6) {
        return mPerfServiceMgr.userRegBigLittle(n, n2, n3, n4, n5, n6);
    }

    public int userRegScn(int n, int n2) {
        log("userRegScn");
        return mPerfServiceMgr.userRegScn(n, n2);
    }

    public void userRegScnConfig(int n, int n2, int n3, int n4, int n5, int n6) {
        mPerfServiceMgr.userRegScnConfig(n, n2, n3, n4, n5, n6);
    }

    public void userResetAll() {
        mPerfServiceMgr.userResetAll();
    }

    public void userRestoreAll() {
        mPerfServiceMgr.userRestoreAll();
    }

    public void userUnreg(int n) {
        mPerfServiceMgr.userUnreg(n);
    }

    public void userUnregScn(int n) {
        mPerfServiceMgr.userUnregScn(n);
    }

    class PerfServiceBroadcastReceiver extends BroadcastReceiver
    {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                log("Intent.ACTION_SCREEN_OFF");
                mPerfServiceMgr.userDisableAll();
            }
            else if ("android.intent.action.SCREEN_ON".equals(action)) {
                log("Intent.ACTION_SCREEN_ON");
                mPerfServiceMgr.userRestoreAll();
            }
            else {
                log("Unexpected intent " + intent);
            }
        }
    }
}
