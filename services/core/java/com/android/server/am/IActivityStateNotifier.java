//
// Decompiled by Procyon v0.5.30
//

package com.android.server.am;

import java.util.HashSet;

public interface IActivityStateNotifier
{
    void notifyActivityState(String p0, String p1, ActivityState p2);

    void notifyAppDied(int p0, HashSet<String> p1);

    public enum ActivityState
    {
        Destroyed,
        Paused,
        Resumed,
        Stopped;
    }
}
