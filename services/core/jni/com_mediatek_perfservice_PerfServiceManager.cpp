/* //device/libs/android_runtime/com_mediatek_perfservice_PerfServiceManager.cpp
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

#define LOG_TAG "PerfServiceManager"

#include "JNIHelp.h"
#include "jni.h"
#include <utils/Log.h>
#include <utils/misc.h>
#include <utils/String8.h>
#include <fcntl.h>


// Functions implemented by libperfservice.so by MTK
extern "C" {
int perfBoostDisable(int p0);
int perfBoostEnable(int p0);
int perfDumpAll();
int perfLevelBoost(int p0);
int perfNotifyAppState(const char * p0, const char * p1, int p2);
int perfSetFavorPid(int p0);
int perfUserGetCapability(int p0);
int perfUserRegScn(int p0);
int perfUserRegScnConfig(int p0, int p1, int p2, int p3, int p4, int p5);
int perfUserScnDisable(int p0);
int perfUserScnDisableAll();
int perfUserScnEnable(int p0);
int perfUserScnReg(int p0, int p1, int p2, int p3);
int perfUserScnRegBigLittle(int p0, int p1, int p2, int p3, int p4, int p5);
int perfUserScnResetAll();
int perfUserScnRestoreAll();
int perfUserScnUnreg(int p0);
int perfUserUnregScn(int p0);
}

namespace android {

static jint com_mediatek_perfservice_PerfServiceManager_boost_disable(JNIEnv*, jobject obj, jint p0)
{
    int result = perfBoostDisable((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_boost_enable(JNIEnv*, jobject obj, jint p0)
{
    int result = perfBoostEnable((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_dump_all(JNIEnv*, jobject obj)
{
    int result = perfDumpAll();
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_level_boost(JNIEnv*, jobject obj, jint p0)
{
    int result = perfLevelBoost((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_notify_app_state(JNIEnv* env, jobject obj, jstring p0, jstring p1, jint p2)
{
    const char *pkgStr = env->GetStringUTFChars(p0, NULL);
    const char *classStr = env->GetStringUTFChars(p1, NULL);
    int id = perfNotifyAppState(pkgStr, classStr, p2);
    env->ReleaseStringUTFChars(p1, classStr);
    env->ReleaseStringUTFChars(p0, pkgStr);
    return id;
}

static jint com_mediatek_perfservice_PerfServiceManager_set_favor_pid(JNIEnv*, jobject obj, jint p0)
{
    int result = perfSetFavorPid((int)p0);
    return (jint)result;;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_get_capability(JNIEnv*, jobject obj, jint p0)
{
    int result = perfUserGetCapability((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_reg_scn(JNIEnv*, jobject obj, jint p0)
{
    int result = perfUserRegScn((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_reg_scn_config(JNIEnv* env, jobject obj, jint p0, jint p1, jint p2, jint p3, jint p4, jint p5)
{
    int result = perfUserRegScnConfig((int)p0, (int)p1, (int)p2, (int)p3, (int)p4, (int)p5);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_disable(JNIEnv*, jobject obj, jint p0)
{
    int result = perfUserScnDisable((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_disable_all(JNIEnv*, jobject obj)
{
    int result = perfUserScnDisableAll();
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_enable(JNIEnv*, jobject obj, jint p0)
{
    int result = perfUserScnEnable((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_reg(JNIEnv*, jobject obj, jint p0, jint p1, jint p2, jint p3)
{
    int result = perfUserScnReg((int)p0, (int)p1, (int)p2, (int)p3);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_reg_big_little(JNIEnv*, jobject obj, jint p0, jint p1, jint p2, jint p3, jint p4, jint p5)
{
    int result = perfUserScnRegBigLittle((int)p0, (int)p1, (int)p2, (int)p3, (int)p4, (int)p5);
    return (jint)result;}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_reset_all(JNIEnv*, jobject obj)
{
    int result = perfUserScnResetAll();
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_restore_all(JNIEnv*, jobject obj)
{
    int result = perfUserScnRestoreAll();
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_scn_unreg(JNIEnv*, jobject obj, jint p0)
{
    int result = perfUserScnUnreg((int)p0);
    return (jint)result;
}

static jint com_mediatek_perfservice_PerfServiceManager_user_unreg_scn(JNIEnv*, jobject obj, jint p0)
{
    int result = perfUserUnregScn((int)p0);
    return (jint)result;
}

static JNINativeMethod sMethods[] = {
     /* name, signature, funcPtr */
    {"nativePerfBoostDisable", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_boost_disable},
    {"nativePerfBoostEnable", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_boost_enable},
    {"nativePerfDumpAll", "()I", (void*)com_mediatek_perfservice_PerfServiceManager_dump_all},
    {"nativePerfLevelBoost", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_level_boost},
    {"nativePerfNotifyAppState", "(Ljava/lang/String;Ljava/lang/String;I)I", (void*)com_mediatek_perfservice_PerfServiceManager_notify_app_state},
    {"nativePerfSetFavorPid", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_set_favor_pid},
    {"nativePerfUserGetCapability", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_get_capability},
    {"nativePerfUserRegScn", "(II)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_reg_scn},
    {"nativePerfUserRegScnConfig", "(IIIIII)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_reg_scn_config},
    {"nativePerfUserScnDisable", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_disable},
    {"nativePerfUserScnDisableAll", "()I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_disable_all},
    {"nativePerfUserScnEnable", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_enable},
    {"nativePerfUserScnReg", "(IIII)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_reg},
    {"nativePerfUserScnRegBigLittle", "(IIIIII)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_reg_big_little},
    {"nativePerfUserScnResetAll", "()I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_reset_all},
    {"nativePerfUserScnRestoreAll", "()I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_restore_all},
    {"nativePerfUserScnUnreg", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_scn_unreg},
    {"nativePerfUserUnregScn", "(I)I", (void*)com_mediatek_perfservice_PerfServiceManager_user_unreg_scn},
};

int register_com_mediatek_perfservice_PerfServiceManager(JNIEnv* env)
{
    int res;
    ALOGE("Registering native methods\n");
    res = jniRegisterNativeMethods(env, "com/mediatek/perfservice/PerfServiceManager",
                                    sMethods, NELEM(sMethods));
    ALOGE("Registering native methods result %d\n", res);
    return res;
}

} /* namespace android */
