//
// Reworked decompiled by Procyon v0.5.30
//

package meizu.os;

import android.os.RemoteException;
import android.content.Context;
//import android.annotation.FlymeHook;

//@FlymeHook(level = FlymeHook.FlymeHookType.NEW_CLASS, note = "Porting DeviceControlManager interface", property = FlymeHook.FlymeRomType.MTK)
public class DeviceControlManager
{
    public static final int DEVCFG_IR_CALIBRATE = 0;
    public static final int DEVCFG_TOUCH_SCREEN = 1;
    public static final int DEVCFG_BUTTON_LIGHT = 2;
    public static final int DEVCFG_LIGHT_SENSOR = 3;
    public static final int DEVCFG_GRAVITY_SENSOR = 4;
    public static final int DEVCFG_ACTIVATE_STATS = 5;
    public static final int DEVCFG_MAINTAIN_STATE = 6;
    public static final int DEVCFG_SHOPDEMO_TOOL = 8;
    public static final int DEVCFG_ACCELERATION_SENSOR = 9;
    public static final int DEVCFG_GYROSCOPE_SENSOR = 10;

    public static final String EARPOD_ADJUST_CMD_UP_KEY_CALIBRATE = "up_key_calibrate";
    public static final String EARPOD_ADJUST_STATE_COMPLETE = "COMPLETE";
    public static final String EARPOD_ADJUST_STATE_ERROR = "ERROR";
    public static final String EARPOD_ADJUST_STATE_PENDING = "PENDING";
    public Context mContext;
    public IDeviceControlService mService;

    private static boolean DEBUG = true;
    private static String TAG = "DeviceControlManager";

    public DeviceControlManager(Context context, IDeviceControlService service) {
        mContext = context;
        mService = service;
    }

    public int adjustGravitySensor(int[] array) {
        try {
            return mService.adjustGravitySensor(array);
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int calibrateAcceAndGyroSensor() {
        try {
            return mService.calibrateAcceAndGyroSensor();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int calibrateAccelerationSensor() {
        try {
            return mService.calibrateAccelerationSensor();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int calibrateGp2ap() {
        try {
            return mService.calibrateGp2ap();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int calibrateGravitySensor() {
        try {
            return mService.calibrateGravitySensor();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int enableTouchAdjust() {
        try {
            return mService.enableTouchAdjust();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int readAccelerationSensorCalibValue(int[] array) {
        try {
            return mService.readAccelerationSensorCalibValue(array);
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int readAutoCabc() {
        try {
            return mService.readAutoCabc();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int readCPUfreq() {
        try {
            return mService.readCPUfreq();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int readCfgParam(int cfgParam, int[] array) {
        try {
            cfgParam = mService.readCfgParam(cfgParam, array);
            return cfgParam;
        }
        catch (RemoteException ex) {
            cfgParam = -1;
            return cfgParam;
        }
    }

    public String readEarpodAdjustState() {
        try {
            return mService.readEarpodAdjustState();
        }
        catch (RemoteException ex) {
            return "ERROR";
        }
    }

    public int readGp2apValue() {
        try {
            return mService.readGp2apValue();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int readGravityValue(int[] array) {
        try {
            return mService.readGravityValue(array);
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int resetCalibration() {
        try {
            return mService.resetCalibration();
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int saveCPUfreq(int saveCPUfreq) {
        try {
            saveCPUfreq = mService.saveCPUfreq(saveCPUfreq);
            return saveCPUfreq;
        }
        catch (RemoteException ex) {
            saveCPUfreq = -1;
            return saveCPUfreq;
        }
    }

    public int saveKeyWakeupType(int saveKeyWakeupType) {
        try {
            saveKeyWakeupType = mService.saveKeyWakeupType(saveKeyWakeupType);
            return saveKeyWakeupType;
        }
        catch (RemoteException ex) {
            saveKeyWakeupType = -1;
            return saveKeyWakeupType;
        }
    }

    public int setAutoCabc(int setAutoCabc) {
        try {
            setAutoCabc = mService.setAutoCabc(setAutoCabc);
            return setAutoCabc;
        }
        catch (RemoteException ex) {
            setAutoCabc = -1;
            return setAutoCabc;
        }
    }

    public int set_hdmi_cable_status(int set_hdmi_cable_status) {
        try {
            set_hdmi_cable_status = mService.set_hdmi_cable_status(set_hdmi_cable_status);
            return set_hdmi_cable_status;
        }
        catch (RemoteException ex) {
            set_hdmi_cable_status = -1;
            return set_hdmi_cable_status;
        }
    }

    public int switchUsbFastCharger(int switchUsbFastCharger) {
        try {
            switchUsbFastCharger = mService.switchUsbFastCharger(switchUsbFastCharger);
            return switchUsbFastCharger;
        }
        catch (RemoteException ex) {
            switchUsbFastCharger = -1;
            return switchUsbFastCharger;
        }
    }

    public int updateLedBrightness(int updateLedBrightness) {
        try {
            updateLedBrightness = mService.updateLedBrightness(updateLedBrightness);
            return updateLedBrightness;
        }
        catch (RemoteException ex) {
            updateLedBrightness = -1;
            return updateLedBrightness;
        }
    }

    public int writeAccelerationSensorCalibValue(int[] array) {
        try {
            return mService.writeAccelerationSensorCalibValue(array);
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int writeCalibratorCmd(String s) {
        try {
            return mService.writeCalibratorCmd(s);
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int writeCfgParam(int writeCfgParam, int[] array) {
        try {
            writeCfgParam = mService.writeCfgParam(writeCfgParam, array);
            return writeCfgParam;
        }
        catch (RemoteException ex) {
            writeCfgParam = -1;
            return writeCfgParam;
        }
    }

    public int writeEarpodAdjustCmd(String s) {
        try {
            return mService.writeEarpodAdjustCmd(s);
        }
        catch (RemoteException ex) {
            return -1;
        }
    }

    public int writeGp2apValue(int writeGp2apValue) {
        try {
            writeGp2apValue = mService.writeGp2apValue(writeGp2apValue);
            return writeGp2apValue;
        }
        catch (RemoteException ex) {
            writeGp2apValue = -1;
            return writeGp2apValue;
        }
    }
}
