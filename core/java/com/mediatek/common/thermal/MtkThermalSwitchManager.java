// 
// Decompiled by Procyon v0.5.30
// Note: the file was modified to match CyanogenMod PerformanceManager design
// 


package com.mediatek.common.thermal;

import java.io.File;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;

import com.mediatek.common.thermal.MtkThermalSwitchConfig;

import meizu.os.IDeviceControlService;

public class MtkThermalSwitchManager {
    private static final String TAG = "ThermalSwitchManager";
    private HandlerThread mHandlerThread;
    private ThermalThreadHandler mHandler;
    private Context mContext;
    private int mState;
    // Meizu
    private static final int POWER_MODE_HIGHPERFOR = 0;
    private static final int POWER_MODE_BALANCE = 1;
    private static final int POWER_MODE_SAVING = 2;
    private String SettingsCPU_L;
    private String mPrevious_SettingsCPU_L;
    private static boolean mPowerModeBenchmark = false;
    private Method mSaveCPUfreq;
    private Method mSystemPropertiesGet;
    private String mAppPackage;
    private Map<String, Integer> processRecord;
    Object mDeviceControlManager;
    BroadcastReceiver mBroadcastReceiver;
    private AlertDialog mThermalSwitchDialog;

    public enum AppState {
        Paused,
        Resumed,
        Destroyed,
        Dead
    }

    class ThermalSwitchState {
        public static final int Init = 0;
        public static final int Ready = 1;      // May be TP or DTM
        public static final int Disabling = 2;  // Switching to TP
        public static final int Disabled= 3;    // Switched to TP 
        public static final int Enabling = 4;   // Switching to DTM
    }

    public MtkThermalSwitchManager(Context context) {
        super();
        mContext = context;
        mState = ThermalSwitchState.Init;
        mHandlerThread = new HandlerThread(TAG, Process.THREAD_PRIORITY_FOREGROUND);
        mHandlerThread.start();
        mHandler = new ThermalThreadHandler(mHandlerThread.getLooper());
        // Meizu
        //SettingsCPU_L = "cpu_l";
        SettingsCPU_L = Settings.Secure.PERFORMANCE_PROFILE;
        mSaveCPUfreq = null;
        mSystemPropertiesGet = null;
        mPrevious_SettingsCPU_L = "cpu_l_p";
        mBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "received intent " + action);
                if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(ThermalThreadHandler.MESSAGE_ACTION_BOOT_COMPLETED), 0L);
                    Log.d(TAG, "received intent " + action + ",Done!");
                }
            }
        };
        mPowerModeBenchmark = false;
        // end Meizu
        Log.i(TAG, "Created and started thermal switch thread");
    }

    public void systemReady() {
        Log.i(TAG, "systemReady, register ACTION_BOOT_COMPLETED");
        mContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_BOOT_COMPLETED), null, mHandler);
        // Meizu
        int powerSavingMode = Settings.System.getInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, 1);
        if (powerSavingMode < POWER_MODE_HIGHPERFOR || powerSavingMode > POWER_MODE_SAVING) {
            powerSavingMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
            if (powerSavingMode < POWER_MODE_HIGHPERFOR || powerSavingMode > POWER_MODE_SAVING) {
                powerSavingMode = POWER_MODE_BALANCE;
            }
        }
        Settings.Secure.putInt(mContext.getContentResolver(), SettingsCPU_L, powerSavingMode);
        Settings.System.putInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, powerSavingMode);
        Log.d(TAG, "systemReady, set power mode: " + powerSavingMode);
        setPowerSavingMode(powerSavingMode);
    }
 
    public void notifyAppState(String appPackage, AppState appState) {
        //Log.i(TAG, "notify app state, app: " + appPackage + ", state: " + appState + ", thermal state: " + mState);

        // Check whether appPackage is a benchmark app and whether appState is "Resume"
//        if (MtkThermalSwitchConfig.appConfig.containsKey(appPackage) &&
//            appState == AppState.Resumed &&
//            mState != ThermalSwitchState.Disabling &&
//            mState != ThermalSwitchState.Enabling) {
//            Message msg = mHandler.obtainMessage();
//            msg.what = ThermalThreadHandler.MESSAGE_APP_RESUMED;
//            msg.arg1 = MtkThermalSwitchConfig.appConfig.get(appPackage);
//            msg.sendToTarget();
//        }
        // Meizu
        if (MtkThermalSwitchConfig.appConfig.containsKey(appPackage) &&
                appState == AppState.Resumed &&
                mState != ThermalSwitchState.Disabling &&
                mState != ThermalSwitchState.Enabling) {
            mAppPackage = appPackage;
            Log.i(TAG, "it is a benchmark app And state is Resumed: " + mAppPackage);
            sendPowerModeSwitchDialogMsg(mAppPackage);
        }
        else if (MtkThermalSwitchConfig.appConfig.containsKey(appPackage) &&
                (appState == AppState.Dead || appState == AppState.Paused || appState == AppState.Destroyed) &&
                mState != ThermalSwitchState.Disabling &&
                mState != ThermalSwitchState.Enabling) {
            MtkThermalSwitchManager.mPowerModeBenchmark = false;
            int powerSavingMode = Settings.System.getInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, 1);
            if (powerSavingMode < POWER_MODE_HIGHPERFOR || powerSavingMode > POWER_MODE_SAVING) {
                powerSavingMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_SAVING);
                if (powerSavingMode < POWER_MODE_HIGHPERFOR || powerSavingMode > POWER_MODE_SAVING) {
                    powerSavingMode = POWER_MODE_SAVING;
                }
            }
            if (powerSavingMode >= POWER_MODE_HIGHPERFOR && powerSavingMode <= POWER_MODE_SAVING) {
                Log.i(TAG, "it is a benchmark app And state is Dead: " + appState + ",appPackage:" + appPackage + ", setPowerSavingMode previous state: " + powerSavingMode);
                Settings.Secure.putInt(mContext.getContentResolver(), SettingsCPU_L, powerSavingMode);
                Settings.System.putInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, powerSavingMode);
                setPowerSavingMode(powerSavingMode);
            }
            processRecord.put(mAppPackage, 0);
        }
    }

    private void execShellCommand(String shellCommand) {
        java.lang.Process proc = null;
        OutputStreamWriter osw = null;

        try {
            proc = Runtime.getRuntime().exec(shellCommand);
            osw = new OutputStreamWriter(proc.getOutputStream());
            if (null != osw) {
                osw.write(shellCommand);
                osw.write("\n");
                osw.write("exit\n");
                osw.flush();
                osw.close();
            }
        }
        catch (IOException ex) {
            Log.e(TAG, "execCommandLine() IO Exception");
            return;
        }
        finally {
            if (null != osw) {
                try {
                    osw.close();
                }
                catch (IOException e) {}
            }
        }

        try {
            proc.waitFor();
        }
        catch (InterruptedException e) {}

        if (proc.exitValue() != 0) {
            Log.e(TAG, "execCommandLine() Err exit code: " + proc.exitValue());
        }
    }

    private void changeToThermalProtection()
    {
        execShellCommand("/system/bin/thermal_manager /etc/.tp/thermal.off.conf");
        // Meizu
        int powerMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
        if (powerMode < POWER_MODE_HIGHPERFOR || powerMode > POWER_MODE_SAVING) {
            powerMode = POWER_MODE_BALANCE;
        }
        Message message = mHandler.obtainMessage(ThermalThreadHandler.MESSAGE_BENCHMARK_PREVIOUS);
        message.arg1 = powerMode;
        mHandler.sendMessageDelayed(message, 1000L);
        if (powerMode != POWER_MODE_HIGHPERFOR) {
            Settings.Secure.putInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_HIGHPERFOR);
        }
        mPowerModeBenchmark = true;
        Log.i(TAG, "begin antutu. set previous power mode is " + powerMode);
    }

    private void changeToDynamicThermalManagement()
    {
        // Meizu
        execShellCommand(MTKToMeizuForDTM("/system/bin/thermal_manager /etc/.tp/thermal.conf"));
        mPowerModeBenchmark = false;
        int powerSavingMode = Settings.System.getInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, POWER_MODE_BALANCE);
        if (powerSavingMode < POWER_MODE_HIGHPERFOR || powerSavingMode > POWER_MODE_SAVING) {
            powerSavingMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_SAVING);
            if (powerSavingMode < POWER_MODE_HIGHPERFOR || powerSavingMode > POWER_MODE_SAVING) {
                powerSavingMode = POWER_MODE_BALANCE;
            }
        }
        Log.i(TAG, "antutu time out set to powerMode is " + powerSavingMode);
        if (powerSavingMode >= POWER_MODE_HIGHPERFOR && powerSavingMode <= POWER_MODE_SAVING) {
            Settings.Secure.putInt(mContext.getContentResolver(), SettingsCPU_L, powerSavingMode);
            Settings.System.putInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, powerSavingMode);
            setPowerSavingMode(powerSavingMode);
        }
    }

    private class ThermalThreadHandler extends Handler {
        private static final int MESSAGE_APP_RESUMED = 0;
        private static final int MESSAGE_TIMER = 1;
        // Meizu
        private static final int MESSAGE_SHOW_DIALOG = 2;
        private static final int MESSAGE_SETTING_POWERMODE = 3;
        private static final int MESSAGE_ACTION_BOOT_COMPLETED = 4;
        private static final int MESSAGE_BENCHMARK_PREVIOUS = 5;

        public ThermalThreadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case MESSAGE_APP_RESUMED:
                    {
                        // Get current thermal policy. Switch to TP only when current policy is DTM.
                        boolean dtm = checkIsDTM();
                        Log.d(TAG, "handleMessage " + msg.what + ", thermal state: " + mState + ", dtm: " + dtm);

                        if (mState == ThermalSwitchState.Ready) {
                            if (true == dtm) {
                                // DTM: Change state to Disabling -> Switch to TP -> Start timer -> Change state to Disabled
                                mState = ThermalSwitchState.Disabling;
                                changeToThermalProtection();
                                stopTimer();
                                startTimer(msg.arg1);
                                mState = ThermalSwitchState.Disabled;
                            }
                        }
                        else if (mState == ThermalSwitchState.Disabled) {
                            if (true == dtm) {
                                // DTM: Change state to Disabling -> Stop timer -> Switch to TP -> Start timer -> Change state to Disabled
                                mState = ThermalSwitchState.Disabling;
                                stopTimer();
                                changeToThermalProtection();
                                startTimer(msg.arg1);
                                mState = ThermalSwitchState.Disabled;
                            }
                            else {
                                // TP: reset timer
                                stopTimer();
                                startTimer(msg.arg1);
                            }
                        }
                        break;
                    }

                    case MESSAGE_TIMER:
                    {
                        // Get current thermal policy. Switch to TP only when current policy is DTM.
                        boolean dtm = checkIsDTM();
                        Log.d(TAG, "handleMessage " + msg.what + ", thermal state: " + mState + ", dtm: " + dtm);

                        // Handle timeout message only when state is Disabled
                        if (mState == ThermalSwitchState.Disabled) {
                            if (true == dtm) {
                                // DTM: Change state to Ready
                                mState = ThermalSwitchState.Ready;
                            }
                            else {
                                // TP: Change state to Enabling -> Switch to DTM -> Change state to Ready
                                mState = ThermalSwitchState.Enabling;
                                changeToDynamicThermalManagement();
                                mState = ThermalSwitchState.Ready;
                            }
                        }
                        break;
                    }

                    // Meizu
                    case MESSAGE_SHOW_DIALOG: {
                        showPowerModeSwitchDialog();
                        break;
                    }

                    case MESSAGE_SETTING_POWERMODE: {
                        setPowerSavingMode(msg.arg1);
                        break;
                    }

                    case MESSAGE_ACTION_BOOT_COMPLETED: {
                        HandleOnReceiveBootCompleted();
                        break;
                    }

                    case MESSAGE_BENCHMARK_PREVIOUS: {
                        Settings.System.putInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, msg.arg1);
                        break;
                    }

                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in ThermalThreadHandler.handleMessage: ", e);
            }
        }

        private void startTimer(int delay) {
            Message msg = obtainMessage(MESSAGE_TIMER);
            sendMessageDelayed(msg, delay * 1000);
        }

        private void stopTimer() {
            removeMessages(MESSAGE_TIMER);
        }

        private boolean checkIsDTM() {
            boolean ret = false;
            File f = new File("/data/.tp.settings");

            if(f.exists() == false) {
                // File not found -> apply one thermal policy and thermal will create the file
                execShellCommand(MTKToMeizuForDTM("/system/bin/thermal_manager /etc/.tp/thermal.conf"));
            }

            f = new File("/data/.tp.settings");
            if (f.exists()) {
                try {
                    // Open the file for reading
                    InputStream instream = new FileInputStream(f);

                    // If file is available for reading
                    if (instream != null) {
                        // Prepare the file for reading
                        InputStreamReader inputReader = new InputStreamReader(instream);
                        BufferedReader buffReader = new BufferedReader(inputReader);
                        String line;

                        if ((line = buffReader.readLine()) != null) {
                            if (line.equals("/etc/.tp/thermal.conf") ||
                                line.equals("/etc/.tp/thermal.high.conf") ||
                                line.equals("/etc/.tp/thermal.mid.conf") ||
                                line.equals("/etc/.tp/thermal.low.conf")) {
                                ret = true;
                            }
                        }
                    }

                    // Close the file
                    instream.close();
                } catch (IOException e) {}
            }

            return ret;
        }
        
        // TODO: rework this if need to be enabled (ThermalSwitchDialogBuilder to be ported)
        private void showPowerModeSwitchDialog() {
//            final boolean checkIsDTM = checkIsDTM();
//            if (mThermalSwitchDialog == null && checkIsDTM) {
//                ThermalSwitchDialogBuilder thermalSwitchDialogBuilder = new ThermalSwitchDialogBuilder(mContext);
//                mThermalSwitchDialog = thermalSwitchDialogBuilder.create();
//                thermalSwitchDialogBuilder.setOnThermalSwitchListener(new ThermalSwitchDialogBuilder.OnThermalSwitchClickListener() {
//                    @Override
//                    public void onThermalSwitchClick(boolean b) {
//                        if (b) {
//                            handlePowerModeSwitch(0);
//                        }
//                        else {
//                            handlePowerModeSwitch(1);
//                        }
//                        mThermalSwitchDialog.dismiss();
//                    }
//                });
//                thermalSwitchDialogBuilder.mDialogBuilderHomeWatcher.setOnHomePressedListener(new ThermalSwitchDialogBuilder.OnHomePressedListener() {
//                    @Override
//                    public void onHomeLongPressed() {
//                        Log.d(TAG, "onHomeLongPressed");
//                        mThermalSwitchDialog.dismiss();
//                        mThermalSwitchDialog = null;
//                        if (mAppPackage != null && processRecord != null && processRecord.containsKey(mAppPackage)) {
//                            processRecord.put(mAppPackage, 0);
//                        }
//                    }
//                    
//                    @Override
//                    public void onHomePressed() {
//                        Log.d(TAG, "onHomePressed");
//                        mThermalSwitchDialog.dismiss();
//                        mThermalSwitchDialog = null;
//                        if (mAppPackage != null && processRecord != null && processRecord.containsKey(mAppPackage)) {
//                            processRecord.put(mAppPackage, 0);
//                        }
//                    }
//                });
//                mThermalSwitchDialog.getWindow().setType(2003);
//                mThermalSwitchDialog.setCanceledOnTouchOutside(false);
//                mThermalSwitchDialog.show();
//                Log.d(TAG, "it is Create mThermalSwitchDialogBuilder*************");
//            }
//            else {
//                if (!mThermalSwitchDialog.isShowing()) {
//                    mThermalSwitchDialog.show();
//                }
//                Log.d(TAG, "The Dialog is not dismiss!");
//            }
        }

    }


// Meizu replaced the broadcast receiver with few functions
//    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            Log.d(TAG, "received intent " + action);
//
//            // Check if app timeout is smaller than thermal reset time
//            if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
//                int appTimeout = Integer.MAX_VALUE;
//                int resetTime = 60; // Default thermal reset time is 60s if can not get the value
//                File f = new File("/data/.tp.settings");
//
//                // Get the smallest app timeout                
//                for (Object o : MtkThermalSwitchConfig.appConfig.keySet()) {
//                    if (MtkThermalSwitchConfig.appConfig.get(o) < appTimeout) {
//                        appTimeout = MtkThermalSwitchConfig.appConfig.get(o);
//                    }
//                }
//                Log.d(TAG, "smallest app timeout: " + appTimeout + " seconds");
//
//                // Get thermal reset time
//                if(f.exists() == false) {
//                    // File not found -> apply one thermal policy and thermal will create the file
//                    execShellCommand("/system/bin/thermal_manager /etc/.tp/thermal.conf");
//                }
//
//                f = new File("/data/.tp.settings");
//                if (f.exists()) {
//                    try {
//                        // Open the file for reading
//                        InputStream instream = new FileInputStream(f);
//
//                        // If file is available for reading
//                        if (instream != null) {
//                            // Prepare the file for reading
//                            InputStreamReader inputReader = new InputStreamReader(instream);
//                            BufferedReader buffReader = new BufferedReader(inputReader);
//                            String line;
//
//                            if (buffReader.readLine() != null) {
//                                if ((line = buffReader.readLine()) != null) {
//                                    resetTime = Integer.parseInt(line.trim());
//                                }
//                            }
//                        }
//
//                        // Close the file
//                        instream.close();
//                    } catch (IOException e) {}
//                }
//  
//                Log.d(TAG, "thermal reset time: " + resetTime);
//
//                // Compare app timeout with thermal reset time
//                assertFalse(appTimeout < resetTime);
//
//                mState = ThermalSwitchState.Ready;
//            }
//        }
//    };

    private String systemPropertiesGet(String property, String defValue) {
        String result = null;
        if (mSystemPropertiesGet == null) {
            return null;
        }
        try {
            result = (String)mSystemPropertiesGet.invoke(null, property, defValue);
            Log.d(TAG, "systemPropertiesGet: systemProperties:" + property + ",result:" + result);
            return result;
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (IllegalArgumentException ex2) {
            ex2.printStackTrace();
            return null;
        }
        catch (InvocationTargetException ex3) {
            ex3.printStackTrace();
            return null;
        }
    }

    private boolean isBuildCTAMode() {
        String ctaPropValue = systemPropertiesGet("ro.build.cta", "unknown");
        boolean result = false;
        if (ctaPropValue != null && ctaPropValue.length() > 0 && ctaPropValue.equals("cta")) {
            Log.d(TAG, "set Build CTA true");
            result = true;
        }
        return result;
    }

    private boolean isWifiTestMode() {
        boolean result = false;
        String wifTestPropValue = systemPropertiesGet("sys.wifiTestMode", "unknown");
        if (wifTestPropValue != null && wifTestPropValue.length() > 0 && 
                wifTestPropValue.charAt(0) == '1') {
            Log.d(TAG, "set enableAdb true for wifitest");
            result = true;
        }
        return result;
    }

    private void setPowerSavingMode(int n) {
        Log.d(TAG, "setPowerSavingMode powerMode:" + n);
        if (mSaveCPUfreq == null || mDeviceControlManager == null) {
            Log.d(TAG, "setPowerSavingMode mSaveCPUfreq == " + mSaveCPUfreq + " || mDeviceControlManager == " + mDeviceControlManager);
        } else {
            try {
                mSaveCPUfreq.invoke(mDeviceControlManager, n);
                if (!MtkThermalSwitchManager.mPowerModeBenchmark) {
                    String mtkToMeizuForDTM = MTKToMeizuForDTM("/system/bin/thermal_manager /etc/.tp/thermal.conf");
                    Log.d(TAG, "setPowerSavingMode command:" + mtkToMeizuForDTM + ",powerMode:" + n);
                    execShellCommand(mtkToMeizuForDTM);
                }
            }
            catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
            catch (IllegalArgumentException ex2) {
                ex2.printStackTrace();
            }
            catch (InvocationTargetException ex3) {
                ex3.printStackTrace();
            }
        }
    }

    public boolean setPowerMode(int powerMode) {
        Message msg = mHandler.obtainMessage(ThermalThreadHandler.MESSAGE_SETTING_POWERMODE);
        msg.arg1 = powerMode;
        mHandler.sendMessageDelayed(msg, 0L);
        return true;
    }

    private void initProcessStartRecord() {
        processRecord = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : MtkThermalSwitchConfig.appConfig.entrySet()) {
            processRecord.put(entry.getKey().toString(), 0);
            Log.d(TAG, "ProcessStartRecord info.getkey()=" + entry.getKey().toString());
        }
    }

    private void initPowerSavingMode() {
        try {
            
            IBinder checkService = ServiceManager.checkService("device_control");
            if (checkService != null) {
                mDeviceControlManager = IDeviceControlService.Stub.asInterface(checkService);
            }
            //Class.forName("meizu.os.DeviceControlManager");
            //mDeviceControlManager = mContext.getSystemService("device_control");
            if (mDeviceControlManager != null) {
                Log.d(TAG, "initPowerSavingMode  mDeviceControlManager successfull!");
                mSaveCPUfreq = mDeviceControlManager.getClass().getMethod("saveCPUfreq", Integer.TYPE);
                Log.d(TAG, "initPowerSavingMode  mDeviceControlManager mSaveCPUfreq:" + mSaveCPUfreq);
                mSystemPropertiesGet = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class, String.class);
                boolean nonCTA = true;
                if (isWifiTestMode()) {
                    return;
                }
                int initialPowerMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
                if (isBuildCTAMode()) {
                    initialPowerMode = POWER_MODE_BALANCE;
                    nonCTA = false;
                }
                setPowerMode(initialPowerMode);
                Log.d(TAG, "initPowerSavingMode updateMode:" + nonCTA + ",powerMode:" + initialPowerMode);
                if (nonCTA) {
                    mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor(SettingsCPU_L), false, (ContentObserver)new ContentObserver(null) {
                        public void onChange(boolean b) {
                            int powerMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
                            if (powerMode <  POWER_MODE_HIGHPERFOR || powerMode > POWER_MODE_SAVING) {
                                powerMode = POWER_MODE_BALANCE;
                            }
                            Settings.System.putInt(mContext.getContentResolver(), mPrevious_SettingsCPU_L, powerMode);
                            Log.d(TAG, "initPowerSavingMode  onChange powerMode=" + powerMode);
                            if (powerMode == POWER_MODE_BALANCE || powerMode == POWER_MODE_SAVING) {
                                mPowerModeBenchmark = false;
                            }
                            // Disabled by divis1969 to allow high performance profile
                            //if (powerMode != POWER_MODE_HIGHPERFOR) {
                            //    setPowerMode(powerMode);
                            //}
                        }
                    });
                }
            }
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (NoSuchMethodException ex2) {
            ex2.printStackTrace();
        }
    }

    private String MTKToMeizuForDTM(String s) {
        String string = s;
        if (s.endsWith("/etc/.tp/thermal.conf")) {
            int powerMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
            if (powerMode == POWER_MODE_HIGHPERFOR) {
                s = "/etc/.tp/thermal.high.conf";
            } else if (powerMode == POWER_MODE_SAVING) {
                s = "/etc/.tp/thermal.low.conf";
            } else {
                s = "/etc/.tp/thermal.mid.conf";
            }
            string = "/system/bin/thermal_manager " + s;
        }
        return string;
    }

    private void HandleOnReceiveBootCompleted() {
        if (!new File("/data/.tp.settings").exists()) {
            execShellCommand(MTKToMeizuForDTM("/system/bin/thermal_manager /etc/.tp/thermal.conf"));
        }
        mState = ThermalSwitchState.Ready;
        initProcessStartRecord();
        initPowerSavingMode();
    }

    private void handlePowerModeSwitch(int n) {
        String appPackage = mAppPackage;
        if (appPackage == null) {
            Log.d(TAG, "handlePowerModeSwitch mAppPackage == null!!!");
        }
        else if (n == POWER_MODE_BALANCE) {
            Log.i(TAG, "handlePowerModeSwitch POWER_MODE_BALANCE");
            Settings.Secure.putInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
        }
        else if (n == POWER_MODE_HIGHPERFOR) {
            Message obtainMessage = mHandler.obtainMessage(ThermalThreadHandler.MESSAGE_APP_RESUMED);
            obtainMessage.arg1 = MtkThermalSwitchConfig.appConfig.get(appPackage);
            mHandler.sendMessageDelayed(obtainMessage, 0L);
            Log.i(TAG, "handlePowerModeSwitch powerMode:" + n + ",appPackage:" + appPackage + "/" + obtainMessage.arg1);
        }
    }

    private void sendPowerModeSwitchDialogMsg(String app) {
        int powerMode = Settings.Secure.getInt(mContext.getContentResolver(), SettingsCPU_L, POWER_MODE_BALANCE);
        if (powerMode == 0) {
            Log.i(TAG, "Don't Show Dialog! powerMode:" + powerMode);
            handlePowerModeSwitch(POWER_MODE_HIGHPERFOR);
        } else if (processRecord.get(app) == 0) {
            Log.i(TAG, "Show Dialog! powerMode:" + powerMode);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(ThermalThreadHandler.MESSAGE_SHOW_DIALOG), 0L);
            processRecord.put(app, 1);
        }
    }
}
