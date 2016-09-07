//
// Decompiled by Procyon v0.5.30
//

package com.mediatek.perfservice;

import android.os.RemoteException;
import com.mediatek.xlog.Xlog;
import android.os.IBinder;
import android.os.ServiceManager;
import android.content.Context;

public class PerfServiceWrapper implements IPerfServiceWrapper
{
    private static final int RENDER_THREAD_UPDATE_DURATION = 400;
    private static final String TAG = "PerfServiceWrapper";
    private int inited;
    private Context mContext;
    private long mPreviousTime;
    private IPerfService sService;
    private int setTid;

    public PerfServiceWrapper(Context context) {
        sService = null;
        inited = 0;
        setTid = 0;
        mPreviousTime = 0L;
        mContext = context;
        init();
    }

    private void init() {
        if (inited == 0) {
            IBinder checkService = ServiceManager.checkService("mtk-perfservice");
            if (checkService != null) {
                sService = IPerfService.Stub.asInterface(checkService);
                if (sService != null) {
                    inited = 1;
                }
                else {
                    log("ERR: getService() sService is still null..");
                }
            }
        }
    }

    private void log(String s) {
        Xlog.d("PerfServiceWrapper", "[PerfServiceWrapper] " + s + " ");
    }

    private void loge(String s) {
        Xlog.e("PerfServiceWrapper", "[PerfServiceWrapper] ERR: " + s + " ");
    }

    public static native int nativeGetPid();

    public static native int nativeGetTid();

    @Override
    public void boostDisable(int n) {
        try {
            init();
            if (sService != null) {
                sService.boostDisable(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in boostDisable:" + ex);
        }
    }

    @Override
    public void boostEnable(int n) {
        try {
            init();
            if (sService != null) {
                sService.boostEnable(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in boostEnable:" + ex);
        }
    }

    @Override
    public void boostEnableTimeout(int n, int n2) {
        try {
            init();
            if (sService != null) {
                sService.boostEnableTimeout(n, n2);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in boostEnableTimeout:" + ex);
        }
    }

    @Override
    public void boostEnableTimeoutMs(int n, int n2) {
        try {
            init();
            if (sService != null) {
                sService.boostEnableTimeoutMs(n, n2);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in boostEnableTimeoutMs:" + ex);
        }
    }

    @Override
    public void dumpAll() {
        try {
            init();
            if (sService != null) {
                sService.dumpAll();
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in dumpAll:" + ex);
        }
    }

    @Override
    public void notifyAppState(String s, String s2, int n) {
        try {
            init();
            if (sService != null) {
                sService.notifyAppState(s, s2, n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in notifyAppState:" + ex);
        }
    }

    @Override
    public void notifyDisplayType(int n) {
        try {
            init();
            if (sService != null) {
                sService.notifyDisplayType(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in notifyDisplayType:" + ex);
        }
    }

    @Override
    public void notifyFrameUpdate(int nativeGetTid) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            init();
            if (setTid == 0) {
                nativeGetTid = nativeGetTid();
                setTid = 1;
            }
            if (mPreviousTime == 0L || currentTimeMillis - mPreviousTime > 400L) {
                if (sService != null) {
                    sService.notifyFrameUpdate(nativeGetTid);
                }
                mPreviousTime = currentTimeMillis;
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in notifyFrameUpdate:" + ex);
        }
    }

    @Override
    public void notifyUserStatus(int n, int n2) {
        try {
            init();
            if (sService != null) {
                sService.notifyUserStatus(n, n2);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in notifyUserStatus:" + ex);
        }
    }

    @Override
    public void setFavorPid(int favorPid) {
        try {
            init();
            if (sService != null) {
                sService.setFavorPid(favorPid);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in setFavorPid:" + ex);
        }
    }

    @Override
    public void userDisable(int n) {
        try {
            init();
            if (sService != null) {
                sService.userDisable(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userDisable:" + ex);
        }
    }

    @Override
    public void userDisableAll() {
        try {
            init();
            if (sService != null) {
                sService.userDisableAll();
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userDisableAll:" + ex);
        }
    }

    @Override
    public void userEnable(int n) {
        try {
            init();
            if (sService != null) {
                sService.userEnable(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userEnable:" + ex);
        }
    }

    @Override
    public void userEnableTimeout(int n, int n2) {
        try {
            init();
            if (sService != null) {
                sService.userEnableTimeout(n, n2);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userEnableTimeout:" + ex);
        }
    }

    @Override
    public void userEnableTimeoutMs(int n, int n2) {
        try {
            init();
            if (sService != null) {
                sService.userEnableTimeoutMs(n, n2);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userEnableTimeoutMs:" + ex);
        }
    }

    @Override
    public int userGetCapability(int n) {
        int n2 = -1;
        try {
            init();
            int userGetCapability = n2;
            if (sService != null) {
                userGetCapability = sService.userGetCapability(n);
            }
            return userGetCapability;
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userGetCapability:" + ex);
            return n2;
        }
    }

    @Override
    public int userReg(int n, int n2) {
        int n3 = -1;
        try {
            init();
            int nativeGetPid = nativeGetPid();
            int nativeGetTid = nativeGetTid();
            int userReg = n3;
            if (sService != null) {
                userReg = sService.userReg(n, n2, nativeGetPid, nativeGetTid);
            }
            return userReg;
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userReg:" + ex);
            return n3;
        }
    }

    @Override
    public int userRegBigLittle(int n, int n2, int n3, int n4) {
        int n5 = -1;
        try {
            init();
            int nativeGetPid = nativeGetPid();
            int nativeGetTid = nativeGetTid();
            int userRegBigLittle = n5;
            if (sService != null) {
                userRegBigLittle = sService.userRegBigLittle(n, n2, n3, n4, nativeGetPid, nativeGetTid);
            }
            return userRegBigLittle;
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userRegBigLittle:" + ex);
            return n5;
        }
    }

    @Override
    public int userRegScn() {
        int n = -1;
        try {
            init();
            int nativeGetPid = nativeGetPid();
            int nativeGetTid = nativeGetTid();
            int userRegScn = n;
            if (sService != null) {
                userRegScn = sService.userRegScn(nativeGetPid, nativeGetTid);
            }
            return userRegScn;
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userRegScn:" + ex);
            return n;
        }
    }

    @Override
    public void userRegScnConfig(int n, int n2, int n3, int n4, int n5, int n6) {
        try {
            init();
            if (sService != null) {
                sService.userRegScnConfig(n, n2, n3, n4, n5, n6);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userRegScnConfig:" + ex);
        }
    }

    @Override
    public void userResetAll() {
        try {
            init();
            if (sService != null) {
                sService.userResetAll();
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userResetAll:" + ex);
        }
    }

    @Override
    public void userRestoreAll() {
        try {
            init();
            if (sService != null) {
                sService.userRestoreAll();
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userRestoreAll:" + ex);
        }
    }

    @Override
    public void userUnreg(int n) {
        try {
            init();
            if (sService != null) {
                sService.userUnreg(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userUnreg:" + ex);
        }
    }

    @Override
    public void userUnregScn(int n) {
        try {
            init();
            if (sService != null) {
                sService.userUnregScn(n);
            }
        }
        catch (RemoteException ex) {
            loge("ERR: RemoteException in userUnregScn:" + ex);
        }
    }
}
