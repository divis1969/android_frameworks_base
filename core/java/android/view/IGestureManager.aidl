package android.view;

import android.view.IGestureCallback;

/**
 * Meizu interface
 *
 * @hide
 */
interface IGestureManager
{
    void pauseAllGesture();

    void registeCallback(IGestureCallback p0, in String p1);

    void removeCallback(IGestureCallback p0);

    void resumeAllGesture();

    // This method differs from original Meizu counterpart: we return true if
    // this is a correct wakeup gesture
    boolean triggerGesture();
}
