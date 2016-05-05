//
// Reworked decompiled by Procyon v0.5.30
//

package meizu.os;

interface IDeviceControlService
{

    int adjustGravitySensor(in int[] p0);
    int calibrateGravitySensor();
    int calibrateAcceAndGyroSensor();

    int calibrateAccelerationSensor();

    int calibrateGp2ap();
    int readGp2apValue();
    int writeGp2apValue(int p0);

    int enableTouchAdjust();

    int readAccelerationSensorCalibValue(out int[] p0);
    int writeAccelerationSensorCalibValue(in int[] p0);

    int readAutoCabc();
    int setAutoCabc(int p0);

    int readCPUfreq();
    int saveCPUfreq(int p0);

    int readCfgParam(int p0, out int[] p1);
    int writeCfgParam(int p0, in int[] p1);

    String readEarpodAdjustState();
    int writeEarpodAdjustCmd(String p0);

    int readGravityValue(out int[] p0);

    int resetCalibration();

    int saveKeyWakeupType(int p0);

    int set_hdmi_cable_status(int p0);

    int switchUsbFastCharger(int p0);

    int updateLedBrightness(int p0);

    int writeCalibratorCmd(String p0);

}

