//
// Decompiled by Procyon v0.5.30
//

package com.android.server;

import java.util.Iterator;
import java.util.HashSet;
import com.android.server.am.IActivityStateNotifier;

import com.mediatek.perfservice.PerfServiceWrapper;

public final class PerfMgrStateNotifier implements IActivityStateNotifier
{
    static final String TAG = "PerfMgrStateNotifier";

    private PerfServiceWrapper mPerfService = new PerfServiceWrapper(null);

    public PerfMgrStateNotifier() {
        super();
    }

    @Override
    public void notifyActivityState(String s, String s2, ActivityState activityState) {
        int n = 0;
        switch (activityState) {
            default: {
                return;
            }
            case Paused: {
                n = PerfServiceWrapper.STATE_PAUSED;
                break;
            }
            case Resumed: {
                n = PerfServiceWrapper.STATE_RESUMED;
                break;
            }
            case Destroyed: {
                n = PerfServiceWrapper.STATE_DESTROYED;
                break;
            }
            case Stopped: {
                n = PerfServiceWrapper.STATE_STOPPED;
                break;
            }
        }
        mPerfService.notifyAppState(s, s2, n);
    }

    @Override
    public void notifyAppDied(int pid, HashSet<String> set) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            mPerfService.notifyAppState(iterator.next(), null, PerfServiceWrapper.STATE_DEAD);
        }
    }
}
