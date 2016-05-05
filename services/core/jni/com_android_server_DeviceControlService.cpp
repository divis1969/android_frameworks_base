/* //device/libs/android_runtime/android_server_DeviceControlService.cpp
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

#define LOG_TAG "DeviceControl"

#include "JNIHelp.h"
#include "jni.h"
#include <utils/Log.h>
#include <utils/misc.h>
#include <utils/String8.h>
#include <fcntl.h>


// Functions implemented by libdefcfgparam.so by Meizu
extern "C" {

int read_dev_params(int p0, int * buf);
int write_dev_params();

}

namespace android {

static jint android_server_DeviceControlService_adjust_gravity_sensor(JNIEnv*, jobject obj, jintArray p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_calibrate_acceleration_sensor(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_calibrate_gp2ap(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_calibrate_gravity_sensor(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_enable_touch_adjust(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_read_acceleration_sensor_value(JNIEnv*, jobject obj, jintArray p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_read_auto_cabc(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jstring android_server_DeviceControlService_read_calibration_data(JNIEnv*, jobject obj, jstring p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return NULL;
}

static jint android_server_DeviceControlService_read_cfgparam(JNIEnv* env, jobject obj, jint p0, jintArray p1)
{
    int cfgParams[7] = {0};
    int res = read_dev_params(p0, cfgParams);
    ALOGD("read_dev_params returns %d\n", res);
    if (res >= 0) {
        env->SetIntArrayRegion(p1, 0, 7, cfgParams);
    }
    return res;
}

static jint android_server_DeviceControlService_read_cpu_value(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_read_earpod_adjust_state(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_read_gp2ap(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_read_gravity_value(JNIEnv*, jobject obj, jintArray p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_reset_calibration(JNIEnv*, jobject obj)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_save_cpu_value(JNIEnv*, jobject obj, jint p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_set_auto_cabc(JNIEnv*, jobject obj, jint p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_set_hdmi_cable_status(JNIEnv*, jobject obj, jint p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_set_key_wakeup_type(JNIEnv*, jobject obj, jint p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_switch_usb_fast_charger(JNIEnv*, jobject obj, jint p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_update_led_brightness(JNIEnv*, jobject obj, jint p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_write_acceleration_sensor_value(JNIEnv*, jobject obj, jintArray p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_write_calibration_data(JNIEnv*, jobject obj, jstring p0, jstring p1)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_write_calibrator_cmd(JNIEnv*, jobject obj, jstring p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_write_cfgparam(JNIEnv*, jint p0, jobject obj, jintArray p1)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_write_earpod_adjust_cmd(JNIEnv*, jobject obj, jstring p0)
{
    ALOGE("Function '%s' is not implemented\n", __func__);
    return -1;
}

static jint android_server_DeviceControlService_write_gp2ap(JNIEnv*, jobject obj, jint p0)
{
    jint res = -1;
    if (p0 < 1000) {
        int fd = open("/sys/class/meizu/ps/ps_offset", O_RDWR);
        if (fd < 0) {
            ALOGE("%s: cannot open %s, error %d (%s)\n", __func__, errno, strerror(errno));
        } else {
            char buf[8];
            int len = sprintf(buf, "%d", p0);
            res = write(fd, buf, len);
            close(fd);
            ALOGE("%s: write %d, result %d\n", __func__, p0, res);
            if (res != -1) res = 0;
        }
    } else {
        ALOGE("warning!!! write gp2ap value %d\n", p0);
    }
    return res;
}

static JNINativeMethod sMethods[] = {
     /* name, signature, funcPtr */
    {"native_adjust_gravity_sensor", "([I)I", (void*)android_server_DeviceControlService_adjust_gravity_sensor},
    {"native_calibrate_acceleration_sensor", "()I", (void*)android_server_DeviceControlService_calibrate_acceleration_sensor},
    {"native_calibrate_gp2ap", "()I", (void*)android_server_DeviceControlService_calibrate_gp2ap},
    {"native_calibrate_gravity_sensor", "()I", (void*)android_server_DeviceControlService_calibrate_gravity_sensor},
    {"native_enable_touch_adjust", "()I", (void*)android_server_DeviceControlService_enable_touch_adjust},
    {"native_read_acceleration_sensor_value", "([I)I", (void*)android_server_DeviceControlService_read_acceleration_sensor_value},
    {"native_read_auto_cabc", "()I", (void*)android_server_DeviceControlService_read_auto_cabc},
    {"native_read_calibration_data", "(Ljava/lang/String;)Ljava/lang/String;", (void*)android_server_DeviceControlService_read_calibration_data},
    {"native_read_cfgparam", "(I[I)I", (void*)android_server_DeviceControlService_read_cfgparam},
    {"native_read_cpu_value", "()I", (void*)android_server_DeviceControlService_read_cpu_value},
    {"native_read_earpod_adjust_state", "()I", (void*)android_server_DeviceControlService_read_earpod_adjust_state},
    {"native_read_gp2ap", "()I", (void*)android_server_DeviceControlService_read_gp2ap},
    {"native_read_gravity_value", "([I)I", (void*)android_server_DeviceControlService_read_gravity_value},
    {"native_reset_calibration", "()I", (void*)android_server_DeviceControlService_reset_calibration},
    {"native_save_cpu_value", "(I)I", (void*)android_server_DeviceControlService_save_cpu_value},
    {"native_set_auto_cabc", "(I)I", (void*)android_server_DeviceControlService_set_auto_cabc},
    {"native_set_hdmi_cable_status", "(I)I", (void*)android_server_DeviceControlService_set_hdmi_cable_status},
    {"native_set_key_wakeup_type", "(I)I", (void*)android_server_DeviceControlService_set_key_wakeup_type},
    {"native_switch_usb_fast_charger", "(I)I", (void*)android_server_DeviceControlService_switch_usb_fast_charger},
    {"native_update_led_brightness", "(I)I", (void*)android_server_DeviceControlService_update_led_brightness},
    {"native_write_acceleration_sensor_value", "([I)I", (void*)android_server_DeviceControlService_write_acceleration_sensor_value},
    {"native_write_calibration_data", "(Ljava/lang/String;Ljava/lang/String;)I", (void*)android_server_DeviceControlService_write_calibration_data},
    {"native_write_calibrator_cmd", "(Ljava/lang/String;)I", (void*)android_server_DeviceControlService_write_calibrator_cmd},
    {"native_write_cfgparam", "(I[I)I", (void*)android_server_DeviceControlService_write_cfgparam},
    {"native_write_earpod_adjust_cmd", "(Ljava/lang/String;)I", (void*)android_server_DeviceControlService_write_earpod_adjust_cmd},
    {"native_write_gp2ap", "(I)I", (void*)android_server_DeviceControlService_write_gp2ap},
};

int register_android_server_DeviceControlService(JNIEnv* env)
{
    int res;
    ALOGE("Registering native methods\n");
    res = jniRegisterNativeMethods(env, "com/android/server/DeviceControlService",
                                    sMethods, NELEM(sMethods));
    ALOGE("Registering native methods result %d\n", res);
    return res;
}

} /* namespace android */
