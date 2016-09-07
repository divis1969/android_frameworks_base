/* //device/libs/android_runtime/com_mediatek_perfservice_PerfServiceWrapper.cpp
**
** Copyright 2006, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*/

#define LOG_TAG "PerfServiceWrapper"

#include "JNIHelp.h"
#include "jni.h"
#include <utils/Log.h>
#include <utils/misc.h>
#include <utils/String8.h>
#include <fcntl.h>


namespace android {

static jint com_mediatek_perfservice_PerfServiceWrapper_get_pid(JNIEnv*, jobject obj)
{
    return (jint)getpid();
}

static jint com_mediatek_perfservice_PerfServiceWrapper_get_tid(JNIEnv*, jobject obj)
{
    return (jint)gettid();
}

static JNINativeMethod sMethods[] = {
     /* name, signature, funcPtr */
    {"nativeGetPid", "()I", (void*)com_mediatek_perfservice_PerfServiceWrapper_get_pid},
    {"nativeGetTid", "()I", (void*)com_mediatek_perfservice_PerfServiceWrapper_get_tid},
};

int register_com_mediatek_perfservice_PerfServiceWrapper(JNIEnv* env)
{
    int res;
    ALOGE("Registering native methods\n");
    res = jniRegisterNativeMethods(env, "com/mediatek/perfservice/PerfServiceWrapper",
                                    sMethods, NELEM(sMethods));
    ALOGE("Registering native methods result %d\n", res);
    return res;
}

} /* namespace android */
