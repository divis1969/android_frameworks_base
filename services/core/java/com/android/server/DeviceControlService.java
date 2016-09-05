//
// Reworked decompiled by Procyon v0.5.30
//

package com.android.server;

import android.util.Slog;
import java.util.Iterator;
import java.io.File;
import android.util.Log;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.SystemProperties;

import java.util.ArrayList;
import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.Sensor;
//import android.annotation.FlymeHook$FlymeRomType;
//import android.annotation.FlymeHook$FlymeHookType;
//import android.annotation.FlymeHook;
import meizu.os.IDeviceControlService;

//@FlymeHook(level = FlymeHook$FlymeHookType.NEW_CLASS, note = "Porting DeviceControlService interface", property = FlymeHook$FlymeRomType.MTK)
public class DeviceControlService extends IDeviceControlService.Stub
{
    private static final String ACCELERATION_FILE_PATH = "/data/inv_cal_data.bin";
    private static boolean DEBUG = true;
    public static final int DEVCFG_IR_CALIBRATE = 0;
    public static final int DEVCFG_TOUCH_SCREEN = 1;
    public static final int DEVCFG_BUTTON_LIGHT = 2;
    public static final int DEVCFG_LIGHT_SENSOR = 3;
    public static final int DEVCFG_GRAVITY_SENSOR = 4;
    public static final int DEVCFG_ACTIVATE_STATS = 5;
    public static final int DEVCFG_MAINTAIN_STATE = 6;
    public static final int DEVCFG_MAX_INT_FIELDS = 7;
    public static final int DEVCFG_SHOPDEMO_TOOL = 8;
    public static final int DEVCFG_ACCELERATION_SENSOR = 9;
    public static final int DEVCFG_GYROSCOPE_SENSOR = 10;
    public static final String EARPOD_ADJUST_CMD_UP_KEY_CALIBRATE = "up_key_calibrate";
    public static final String EARPOD_ADJUST_STATE_COMPLETE = "COMPLETE";
    public static final String EARPOD_ADJUST_STATE_ERROR = "ERROR";
    public static final String EARPOD_ADJUST_STATE_PENDING = "PENDING";
    private static final long CALIBRATE_TIME_OUT = 6000L;
    private static final long REGISTER_LISTENER_TIME_OUT = 1000L;
    private static final long SAMPLE_TIME_OUT = 60L;
    private static final int SAMPLE_COUNT = 50;
    private static String TAG = "DeviceControlService";
    private static final float X_MAX_VALUE = 0.1f;
    private static final float Y_MAX_VALUE = 0.1f;
    private static final float Z_MAX_VALUE = 10.0f;
    private static final float Z_MIN_VALUE = 9.6f;
    private Sensor mAccelerometer;
    private final SensorEventListener mAccelerometerListener;
    private boolean mAccelerometerSensorEnabled;
    public Context mContext;
    private ArrayList<Sample> mSamples;
    private SensorManager mSensorManager;
    private int[] mValues;
    private float mXValue;
    private float mYValue;
    private float mZValue;

    public DeviceControlService() {
        mAccelerometerSensorEnabled = false;
        mValues = new int[] { 0, 0, 0 };
        mSamples = new ArrayList<Sample>();
        mAccelerometerListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int n) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                if (mAccelerometerSensorEnabled) {
                    mXValue = sensorEvent.values[0];
                    mYValue = sensorEvent.values[1];
                    mZValue = sensorEvent.values[2];
                }
            }
        };
    }

    public DeviceControlService(Context context) {
        mAccelerometerSensorEnabled = false;
        mValues = new int[] { 0, 0, 0 };
        mSamples = new ArrayList<Sample>();
        mAccelerometerListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int n) {
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                if (mAccelerometerSensorEnabled) {
                    mXValue = sensorEvent.values[0];
                    mYValue = sensorEvent.values[1];
                    mZValue = sensorEvent.values[2];
                }
            }
        };
        mContext = context;
        mSensorManager = (SensorManager)mContext.getSystemService("sensor");
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private int calibrateAccelerationAndGyroscopeSensor(final int p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: iconst_m1      
        //     1: istore_3       
        //     2: aload_0        
        //     3: ldc             "/sys/devices/14e30000.i2c/i2c-66/66-003a/input/input0/calibrator_cmd"
        //     5: new             Ljava/lang/StringBuilder;
        //     8: dup            
        //     9: invokespecial   java/lang/StringBuilder.<init>:()V
        //    12: ldc             "7 1 "
        //    14: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    17: iload_1        
        //    18: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    21: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    24: invokespecial   com/android/server/DeviceControlService.writeCalibrationData:(Ljava/lang/String;Ljava/lang/String;)I
        //    27: pop            
        //    28: ldc2_w          2500
        //    31: invokestatic    java/lang/Thread.sleep:(J)V
        //    34: aconst_null    
        //    35: astore          4
        //    37: iconst_0       
        //    38: istore_2       
        //    39: iload_2        
        //    40: bipush          8
        //    42: if_icmpge       129
        //    45: aload_0        
        //    46: ldc             "/sys/devices/14e30000.i2c/i2c-66/66-003a/input/input0/calibrator_cmd"
        //    48: ldc             "6 0 0"
        //    50: invokespecial   com/android/server/DeviceControlService.writeCalibrationData:(Ljava/lang/String;Ljava/lang/String;)I
        //    53: pop            
        //    54: ldc2_w          100
        //    57: invokestatic    java/lang/Thread.sleep:(J)V
        //    60: aload_0        
        //    61: ldc             "/sys/devices/14e30000.i2c/i2c-66/66-003a/input/input0/calibrator_cmd"
        //    63: invokespecial   com/android/server/DeviceControlService.readCalibrationData:(Ljava/lang/String;)Ljava/lang/String;
        //    66: astore          4
        //    68: getstatic       com/android/server/TAG:Ljava/lang/String;
        //    71: new             Ljava/lang/StringBuilder;
        //    74: dup            
        //    75: invokespecial   java/lang/StringBuilder.<init>:()V
        //    78: ldc             "Sensor test "
        //    80: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    83: iload_2        
        //    84: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    87: ldc             " times: "
        //    89: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    92: aload           4
        //    94: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    97: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   100: invokestatic    android/util/Log.i:(Ljava/lang/String;Ljava/lang/String;)I
        //   103: pop            
        //   104: aload           4
        //   106: ifnull          148
        //   109: aload           4
        //   111: ldc             "0"
        //   113: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   116: ifne            148
        //   119: aload           4
        //   121: ldc             "1"
        //   123: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   126: ifne            148
        //   129: aload           4
        //   131: ifnull          160
        //   134: aload           4
        //   136: ldc             "2"
        //   138: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   141: ifne            160
        //   144: iload_3        
        //   145: istore_2       
        //   146: iload_2        
        //   147: ireturn        
        //   148: ldc2_w          400
        //   151: invokestatic    java/lang/Thread.sleep:(J)V
        //   154: iinc            2, 1
        //   157: goto            39
        //   160: aload_0        
        //   161: ldc             "/sys/devices/14e30000.i2c/i2c-66/66-003a/input/input0/calibrator_cmd"
        //   163: new             Ljava/lang/StringBuilder;
        //   166: dup            
        //   167: invokespecial   java/lang/StringBuilder.<init>:()V
        //   170: iload_1        
        //   171: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   174: ldc             " 1 1"
        //   176: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   179: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   182: invokespecial   com/android/server/DeviceControlService.writeCalibrationData:(Ljava/lang/String;Ljava/lang/String;)I
        //   185: pop            
        //   186: aload_0        
        //   187: ldc             "/sys/devices/14e30000.i2c/i2c-66/66-003a/input/input0/calibrator_cmd"
        //   189: invokespecial   com/android/server/DeviceControlService.readCalibrationData:(Ljava/lang/String;)Ljava/lang/String;
        //   192: astore          4
        //   194: iload_3        
        //   195: istore_2       
        //   196: aload_0        
        //   197: aload           4
        //   199: iload_1        
        //   200: invokespecial   com/android/server/DeviceControlService.writeSensorCalibVals:(Ljava/lang/String;I)I
        //   203: iflt            146
        //   206: getstatic       com/android/server/TAG:Ljava/lang/String;
        //   209: new             Ljava/lang/StringBuilder;
        //   212: dup            
        //   213: invokespecial   java/lang/StringBuilder.<init>:()V
        //   216: ldc             "Calibration return is: "
        //   218: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   221: aload           4
        //   223: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   226: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   229: invokestatic    android/util/Log.i:(Ljava/lang/String;Ljava/lang/String;)I
        //   232: pop            
        //   233: aload_0        
        //   234: ldc             "/sys/devices/14e30000.i2c/i2c-66/66-003a/input/input0/calibrator_cmd"
        //   236: aload           4
        //   238: invokespecial   com/android/server/DeviceControlService.writeCalibrationData:(Ljava/lang/String;Ljava/lang/String;)I
        //   241: pop            
        //   242: iconst_0       
        //   243: istore_2       
        //   244: goto            146
        //   247: astore          4
        //   249: goto            34
        //   252: astore          4
        //   254: goto            60
        //   257: astore          5
        //   259: goto            154
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  28     34     247    252    Ljava/lang/Exception;
        //  54     60     252    257    Ljava/lang/Exception;
        //  148    154    257    262    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 121, Size: 121
        //     at java.util.ArrayList.rangeCheck(ArrayList.java:638)
        //     at java.util.ArrayList.get(ArrayList.java:414)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }

    private int calibrateAccelerometerSensor() {
        int ret = calibrateAccelerationSensor();
        if (DEBUG) {
            Log.d(TAG, "startCalibrate......ret = " + ret);
        }
        int n = -1;
        if (ret < 0) {
            setAccelerometerSensorEnabled(false);
        } else {
            threadSleep(CALIBRATE_TIME_OUT);
            ret = readAccelerationSensorCalibValue(mValues);
            if (DEBUG) {
                Log.d(TAG, "readAccelerationSensorCalibValue ret = " + ret);
                Log.d(TAG, "readAccelerationSensorCalibValue x =" + mValues[0] + " y = " + mValues[1] + " z = " + mValues[2]);
            }
            if (ret < 0 || mValues[0] == 0 || mValues[1] == 0 || mValues[2] == 0) {
                setAccelerometerSensorEnabled(false);
                n = -1;
            } else {
                ret = writeAccelerationSensorCalibValue(mValues);
                if (DEBUG) {
                    Log.d(TAG, "writeAccelerationSensorCalibValue ret = " + ret);
                }
                if (ret >= 0) {
                    setAccelerometerSensorEnabled(true);
                    threadSleep(REGISTER_LISTENER_TIME_OUT);
                    mSamples.clear();
                    while (mSamples.size() < SAMPLE_COUNT) {
                        Sample sample = new Sample();
                        sample.x = mXValue;
                        sample.y = mYValue;
                        sample.z = mZValue;
                        mSamples.add(sample);
                        if (DEBUG) {
                            Log.d(TAG, "X = " + sample.x + "Y = " + sample.y + " Z = " + sample.z);
                        }
                        threadSleep(SAMPLE_TIME_OUT);
                    }
                    boolean b = true;
                    Iterator<Sample> iterator = mSamples.iterator();
                    while (iterator.hasNext()) {
                        Sample sample2 = iterator.next();
                        float x = sample2.x;
                        float y = sample2.y;
                        float z = sample2.z;
                        if (Math.abs(x) <= X_MAX_VALUE && Math.abs(y) <= Y_MAX_VALUE && Math.abs(z) >= Z_MIN_VALUE && Math.abs(z) <= Z_MAX_VALUE) {
                            continue;
                        }
                        b = false;
                        break;
                    }
                    if (b) {
                        ret = writeCfgParam(DEVCFG_ACCELERATION_SENSOR, mValues);
                        if (DEBUG) {
                            Log.d(TAG, "writeCfgParam ret = " + ret);
                        }
                        File file = new File(ACCELERATION_FILE_PATH);
                        boolean deleted;
                        if (file.exists()) {
                            deleted = file.delete();
                            if (DEBUG) {
                                Log.d(TAG, "delete file = " + file.toString() + " is " + deleted);
                            }
                         } else {
                             deleted = true;
                             if (DEBUG) {
                                 Log.d(TAG, "file = " + file.toString() + " is not exists!");
                             }
                         }
                         if (ret >= 0) {
                             if (deleted) {
                                 n = 0;
                             }
                         }
                    } else {
                        setAccelerometerSensorEnabled(false);
                        n = -1;
                    }
                } else {
                    n = -1;
                }
            }
        }
        return n;
    }

    private static String expandIntToString(int[] array) {
        String string = "";
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 4; ++j) {
                int n = array[i] >> (3 - j) * 8 & 0xFF;
                int n2;
                if ((n2 = n) < 0) {
                    n2 = 256 - n;
                }
                string = string + " " + n2;
            }
        }
        String s = string.substring(0, string.length() - 1);
        for (int k = 0; k < 6; ++k) {
            if (k == 0) {
                s += 0;
            }
            else {
                s += " 0";
            }
        }
        if (DEBUG) {
            Slog.d(TAG, "result string: " + s);
        }
        return s;
    }

    private static native int native_adjust_gravity_sensor(int[] p0);

    private static native int native_calibrate_acceleration_sensor();

    private static native int native_calibrate_gp2ap();

    private static native int native_calibrate_gravity_sensor();

    private static native int native_enable_touch_adjust();

    private static native int native_read_acceleration_sensor_value(int[] p0);

    private static native int native_read_auto_cabc();

    private static native String native_read_calibration_data(String p0);

    private static native int native_read_cfgparam(int p0, int[] p1);

    private static native int native_read_cpu_value();

    private static native int native_read_earpod_adjust_state();

    private static native int native_read_gp2ap();

    private static native int native_read_gravity_value(int[] p0);

    private static native int native_reset_calibration();

    private static native int native_save_cpu_value(int p0);

    private static native int native_set_auto_cabc(int p0);

    private static native int native_set_hdmi_cable_status(int p0);

    private static native int native_set_key_wakeup_type(int p0);

    private static native int native_switch_usb_fast_charger(int p0);

    private static native int native_update_led_brightness(int p0);

    private static native int native_write_acceleration_sensor_value(int[] p0);

    private static native int native_write_calibration_data(final String p0, String p1);

    private static native int native_write_calibrator_cmd(String p0);

    private static native int native_write_cfgparam(int p0, int[] p1);

    private static native int native_write_earpod_adjust_cmd(String p0);

    private static native int native_write_gp2ap(int p0);

    private String readCalibrationData(String s) {
        String s3;
        String s2 = s3 = native_read_calibration_data(s);
        if (s2 != null) {
            s3 = s2.substring(0, s2.length() - 1);
        }
        if (DEBUG) {
            Log.d(TAG, "readCalibrationData path: " + s + " ret: " + s3);
        }
        return s3;
    }

    private void setAccelerometerSensorEnabled(boolean b) {
        if (DEBUG) {
            Slog.d(TAG, "setAccelerometerSensorEnabled enable:" + b + ",mAccelerometerSensorEnabled:" + mAccelerometerSensorEnabled);
        }
        if (b) {
            if (!mAccelerometerSensorEnabled) {
                mAccelerometerSensorEnabled = true;
                mSensorManager.registerListener(mAccelerometerListener, mAccelerometer, 0);
            }
        } else if (mAccelerometerSensorEnabled) {
            mAccelerometerSensorEnabled = false;
            mXValue = 0.0f;
            mYValue = 0.0f;
            mZValue = 0.0f;
            mSensorManager.unregisterListener(mAccelerometerListener);
        }
    }

    private void threadSleep(long n) {
        try {
            Thread.sleep(n);
        }
        catch (Exception ex) {}
    }

    private int writeCalibrationData(String s, String s2) {
        if (DEBUG) {
            Log.d(TAG, "writeCalibrationData path: " + s + " data: " + s2);
        }
        return native_write_calibration_data(s, s2);
    }

    private int writeSensorCalibVals(String s, int n) {
        int[] array = new int[7];
        for (int i = 0; i < 7; ++i) {
            array[i] = 0;
        }
        int n2 = 0;
        for (int j = 0; j < 27; ++j) {
            int index = s.indexOf(" ", n2);
            int int1 = Integer.parseInt(s.substring(n2, index).trim());
            int n3 = j / 4;
            array[n3] |= (int1 & 0xFF) << (3 - j % 4) * 8;
            n2 = index + 1;
        }
        if (n == 0) {
            n = writeCfgParam(9, array);
        }
        else if (n == 2) {
            n = writeCfgParam(10, array);
        }
        else {
            n = -1;
        }
        return n;
    }

    public int adjustGravitySensor(int[] array) {
        if (DEBUG) {
            Log.d(TAG, "adjustGravitySensor");
        }
        return native_adjust_gravity_sensor(array);
    }

    public int calibrateAcceAndGyroSensor() {
        return calibrateAccelerometerSensor();
    }

    public int calibrateAccelerationSensor() {
        if (DEBUG) {
            Log.d(TAG, "calibrateAccelerationSensor");
        }
        return native_calibrate_acceleration_sensor();
    }

    public int calibrateGp2ap() {
        if (DEBUG) {
            Log.d(TAG, "calibrateGp2ap");
        }
        return native_calibrate_gp2ap();
    }

    public int calibrateGravitySensor() {
        if (DEBUG) {
            Log.d(TAG, "calibrateGravitySensor");
        }
        return native_calibrate_gravity_sensor();
    }

    public int enableTouchAdjust() {
        if (DEBUG) {
            Log.d(TAG, "enableTouchAdjust");
        }
        return native_enable_touch_adjust();
    }

    public int readAccelerationSensorCalibValue(int[] array) {
        if (DEBUG) {
            Log.d(TAG, "readAccelerationSensorCalibValue");
        }
        return native_read_acceleration_sensor_value(array);
    }

    public int readAutoCabc() {
        int native_read_auto_cabc = native_read_auto_cabc();
        if (DEBUG) {
            Log.d(TAG, "readAutoCabc--value:" + native_read_auto_cabc);
        }
        return native_read_auto_cabc;
    }

    public int readCPUfreq() {
        int native_read_cpu_value = native_read_cpu_value();
        if (DEBUG) {
            Log.d(TAG, "readCPUfreq----value:" + native_read_cpu_value);
        }
        return native_read_cpu_value;
    }

    public int readCfgParam(int n, int[] array) {
        if (DEBUG) {
            Log.d(TAG, "readCfgParam dev:" + n);
        }
        int native_read_cfgparam = native_read_cfgparam(n, array);
        if (DEBUG) {
            Log.d(TAG, "readCfgParam dev over:" + n);
        }
        return native_read_cfgparam;
    }

    public String readEarpodAdjustState() {
        String s = null;
        switch (native_read_earpod_adjust_state()) {
            default: {
                s = EARPOD_ADJUST_STATE_ERROR;
                break;
            }
            case -1: {
                s = EARPOD_ADJUST_STATE_ERROR;
                break;
            }
            case 1: {
                s = EARPOD_ADJUST_STATE_PENDING;
                break;
            }
            case 2: {
                s = EARPOD_ADJUST_STATE_COMPLETE;
                break;
            }
        }
        return s;
    }

    public int readGp2apValue() {
        int native_read_gp2ap = native_read_gp2ap();
        if (DEBUG) {
            Log.d(TAG, "readGp2apValue--value:" + native_read_gp2ap);
        }
        return native_read_gp2ap;
    }

    public int readGravityValue(int[] array) {
        if (DEBUG) {
            Log.d(TAG, "readGravityValue");
        }
        return native_read_gravity_value(array);
    }

    public int resetCalibration() {
        int native_reset_calibration = native_reset_calibration();
        if (DEBUG) {
            Log.d(TAG, "resetCalibration----value:" + native_reset_calibration);
        }
        return native_reset_calibration;
    }

    public int saveCPUfreq(int n) {
        if (DEBUG) {
            Log.d(TAG, "saveCPUfreq----value:" + n);
        }
        return native_save_cpu_value(n);
    }

    public int saveKeyWakeupType(int n) {
        if (DEBUG) {
            Log.d(TAG, "saveKeyWakeupType value:" + n);
        }
        return native_set_key_wakeup_type(n);
    }

    public int setAutoCabc(int n) {
        if (DEBUG) {
            Log.d(TAG, "setAutoCabc--value:" + n);
        }
        return native_set_auto_cabc(n);
    }

    public int set_hdmi_cable_status(int native_set_hdmi_cable_status) {
        if (DEBUG) {
            Log.d(TAG, "set_hdmi_cable_status value is " + native_set_hdmi_cable_status);
        }
        native_set_hdmi_cable_status = native_set_hdmi_cable_status(native_set_hdmi_cable_status);
        if (DEBUG) {
            Log.d(TAG, "set_hdmi_cable_status--value overy:" + native_set_hdmi_cable_status);
        }
        return native_set_hdmi_cable_status;
    }

    public int switchUsbFastCharger(int n) {
        if (DEBUG) {
            Log.d(TAG, "switchUsbFastCharger value:" + n);
        }
        return native_switch_usb_fast_charger(n);
    }

    public int updateLedBrightness(int native_update_led_brightness) {
        final long currentTimeMillis = System.currentTimeMillis();
        native_update_led_brightness = native_update_led_brightness(native_update_led_brightness);
        if (DEBUG) {
            Log.d(TAG, "updateLedBrightness--value:" + native_update_led_brightness + "time cost " + (System.currentTimeMillis() - currentTimeMillis));
        }
        return native_update_led_brightness;
    }

    public void writeAccelerationFactoryCalibValue() {
        int[] array = new int[7];
        if (readCfgParam(DEVCFG_ACCELERATION_SENSOR, array) > 0) {
            if (DEBUG) {
                Slog.d(TAG, "writeAccelerationFactoryCalibValue acceleration: " + array[0] + ",data[1]" + array[1] + ",data[2]" + array[2]);
            }
            writeAccelerationSensorCalibValue(new int[] { array[0], array[1], array[2] });
        }
    }

    public int writeAccelerationSensorCalibValue(int[] array) {
        if (DEBUG) {
            Log.d(TAG, "writeAccelerationSensorCalibValue");
        }
        return native_write_acceleration_sensor_value(array);
    }

    public int writeCalibratorCmd(String s) {
        if (DEBUG) {
            Log.d(TAG, "writeCalibratorCmd");
        }
        return native_write_calibrator_cmd(s);
    }

    public int writeCfgParam(int n, int[] array) {
        if (DEBUG) {
            Log.d(TAG, "writeCfgParam dev:" + n);
        }
        return native_write_cfgparam(n, array);
    }

    public int writeEarpodAdjustCmd(String s) {
        if (DEBUG) {
            Log.d(TAG, "write earpod adjust cmd.");
        }
        return native_write_earpod_adjust_cmd(s);
    }

    public int writeGp2apValue(int native_write_gp2ap) {
        if (DEBUG) {
            Log.d(TAG, "writeGp2apValue value is " + native_write_gp2ap);
        }
        native_write_gp2ap = native_write_gp2ap(native_write_gp2ap);
        if (DEBUG) {
            Log.d(TAG, "writeGp2apValue--value overy:" + native_write_gp2ap);
        }
        return native_write_gp2ap;
    }

    public void writeGyroscopeFactoryCalibValue() {
    }

    public void writeProximitySensorFactoryCalibValue() {
        final int[] array = new int[7];
        int override = -1;
        try {
            override = Integer.parseInt(SystemProperties.get("persist.devcontrol.proximity", "undef"));
        }
        catch (NumberFormatException e) {};

        if (override != 0) {
            if (readCfgParam(DEVCFG_IR_CALIBRATE, array) < 0) {
                Slog.d(TAG, "readCfgParam returned error, skipping proximity calibration");
                return;
            }
        } else {
            Slog.d(TAG, "skipping proximity calibration");
            return;
        }

        if (override > 0) {
            Slog.d(TAG, "override proximity calibration: " + array[0] + " => " + override);
            array[0] = override;
        } else {
            Slog.d(TAG, "Use defcfg proximity calibration: " + array[0]);
        }

        Slog.d(TAG, "writeGp2apValue: " + array[0]);
        writeGp2apValue(array[0]);
    }

    static class Sample
    {
        float x;
        float y;
        float z;
    }
}
