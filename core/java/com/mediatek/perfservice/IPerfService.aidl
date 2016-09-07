//
// Decompiled by Procyon v0.5.30
//

package com.mediatek.perfservice;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

interface IPerfService
{
    void boostEnable(int p0);

    void boostDisable(int p0);

    void boostEnableTimeout(int p0, int p1);

    void boostEnableTimeoutMs(int p0, int p1);

    void notifyAppState(String p0, String p1, int p2);

    int userReg(int p0, int p1, int p2, int p3);

    int userRegBigLittle(int p0, int p1, int p2, int p3, int p4, int p5);

    void userUnreg(int p0);

    int userGetCapability(int p0);

    int userRegScn(int p0, int p1);

    void userRegScnConfig(int p0, int p1, int p2, int p3, int p4, int p5);

    void userUnregScn(int p0);

    void userEnable(int p0);

    void userEnableTimeout(int p0, int p1);

    void userEnableTimeoutMs(int p0, int p1);

    void userDisable(int p0);

    void userResetAll();

    void userDisableAll();

    void userRestoreAll();

    void dumpAll();

    void setFavorPid(int p0);

    void notifyFrameUpdate(int p0);

    void notifyDisplayType(int p0);

    void notifyUserStatus(int p0, int p1);

}