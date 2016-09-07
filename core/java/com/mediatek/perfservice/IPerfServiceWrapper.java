//
// Decompiled by Procyon v0.5.30
//

package com.mediatek.perfservice;

public interface IPerfServiceWrapper
{
    public static final int CMD_GET_CPU_FREQ_BIG_LEVEL_COUNT = 2;
    public static final int CMD_GET_CPU_FREQ_LEVEL_COUNT = 0;
    public static final int CMD_GET_CPU_FREQ_LITTLE_LEVEL_COUNT = 1;
    public static final int CMD_GET_GPU_FREQ_LEVEL_COUNT = 3;
    public static final int CMD_GET_MEM_FREQ_LEVEL_COUNT = 4;
    public static final int CMD_SET_CPU_CORE_BIG_LITTLE_MAX = 3;
    public static final int CMD_SET_CPU_CORE_BIG_LITTLE_MIN = 2;
    public static final int CMD_SET_CPU_CORE_MAX = 1;
    public static final int CMD_SET_CPU_CORE_MIN = 0;
    public static final int CMD_SET_CPU_FREQ_BIG_LITTLE_MAX = 7;
    public static final int CMD_SET_CPU_FREQ_BIG_LITTLE_MIN = 6;
    public static final int CMD_SET_CPU_FREQ_MAX = 5;
    public static final int CMD_SET_CPU_FREQ_MIN = 4;
    public static final int CMD_SET_GPU_FREQ_MAX = 9;
    public static final int CMD_SET_GPU_FREQ_MIN = 8;
    public static final int CMD_SET_MEM_FREQ_MAX = 11;
    public static final int CMD_SET_MEM_FREQ_MIN = 10;
    public static final int CMD_SET_SCREEN_OFF_STATE = 12;
    public static final int DISPLAY_TYPE_GAME = 0;
    public static final int DISPLAY_TYPE_OTHERS = 1;
    public static final int NOTIFY_USER_TYPE_DISPLAY_TYPE = 2;
    public static final int NOTIFY_USER_TYPE_FRAME_UPDATE = 1;
    public static final int NOTIFY_USER_TYPE_PID = 0;
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

    void boostDisable(final int p0);

    void boostEnable(final int p0);

    void boostEnableTimeout(final int p0, final int p1);

    void boostEnableTimeoutMs(final int p0, final int p1);

    void dumpAll();

    void notifyAppState(final String p0, final String p1, final int p2);

    void notifyDisplayType(final int p0);

    void notifyFrameUpdate(final int p0);

    void notifyUserStatus(final int p0, final int p1);

    void setFavorPid(final int p0);

    void userDisable(final int p0);

    void userDisableAll();

    void userEnable(final int p0);

    void userEnableTimeout(final int p0, final int p1);

    void userEnableTimeoutMs(final int p0, final int p1);

    int userGetCapability(final int p0);

    int userReg(final int p0, final int p1);

    int userRegBigLittle(final int p0, final int p1, final int p2, final int p3);

    int userRegScn();

    void userRegScnConfig(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);

    void userResetAll();

    void userRestoreAll();

    void userUnreg(final int p0);

    void userUnregScn(final int p0);
}
