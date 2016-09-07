//
// Decompiled by Procyon v0.5.30
//

package com.mediatek.perfservice;

public interface IPerfServiceManager
{
    public static final int DISPLAY_TYPE_GAME = 0;
    public static final int DISPLAY_TYPE_OTHERS = 1;
    public static final int NOTIFY_USER_TYPE_DISPLAY_TYPE = 2;
    public static final int NOTIFY_USER_TYPE_FRAME_UPDATE = 1;
    public static final int NOTIFY_USER_TYPE_PID = 0;
    public static final int SCN_APP_LAUNCH = 8;
    public static final int SCN_APP_ROTATE = 2;
    public static final int SCN_APP_SWITCH = 1;
    public static final int SCN_APP_TOUCH = 5;
    public static final int SCN_DONT_USE1 = 6;
    public static final int SCN_NONE = 0;
    public static final int SCN_SW_CODEC = 3;
    public static final int SCN_SW_CODEC_BOOST = 4;
    public static final int SCN_SW_FRAME_UPDATE = 7;
    public static final int STATE_DEAD = 3;
    public static final int STATE_DESTROYED = 2;
    public static final int STATE_PAUSED = 0;
    public static final int STATE_RESUMED = 1;
    public static final int STATE_STOPPED = 4;

    void boostDisable(int p0);

    void boostEnable(int p0);

    void boostEnableTimeout(int p0, int p1);

    void boostEnableTimeoutMs(int p0, int p1);

    void dumpAll();

    void notifyAppState(String p0, String p1, int p2);

    void notifyDisplayType(int p0);

    void notifyFrameUpdate(int p0);

    void notifyUserStatus(int p0, int p1);

    void setFavorPid(int p0);

    void systemReady();

    void userDisable(int p0);

    void userDisableAll();

    void userEnable(int p0);

    void userEnableTimeout(int p0, int p1);

    void userEnableTimeoutMs(int p0, int p1);

    int userGetCapability(int p0);

    int userReg(int p0, int p1, int p2, int p3);

    int userRegBigLittle(int p0, int p1, int p2, int p3, int p4, int p5);

    int userRegScn(int p0, int p1);

    void userRegScnConfig(int p0, int p1, int p2, int p3, int p4, int p5);

    void userResetAll();

    void userRestoreAll();

    void userUnreg(int p0);

    void userUnregScn(int p0);
}
