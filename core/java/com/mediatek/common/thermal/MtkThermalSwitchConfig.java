package com.mediatek.common.thermal;

import java.util.*;

public class MtkThermalSwitchConfig {
    public static Map<String, Integer> appConfig;

    static {
        Map<String, Integer> tmp = new HashMap<String, Integer>();

        // Add benchmark app: (package name, timeout value (unit: second))
        // Sample: tmp.put("com.android.xxx", 180);
        // Meizu:
        tmp.put("com.antutu.ABenchMark5", 210);
        tmp.put("com.andromeda.androbench2", 180);
        tmp.put("com.antutu.ABenchMark", 410);
        tmp.put("com.antutu.ABenchMark.GL2", 180);
        tmp.put("com.appems.hawkeye", 180);
        tmp.put("com.aurorasoftworks.quadrant.ui.standard", 180);
        tmp.put("com.dianxinos.toolbox.benchmark", 180);
        tmp.put("com.drolez.nbench", 200);
        tmp.put("com.eembc.coremark", 100);
        tmp.put("com.futuremark.dmandroid.application", 320);
        tmp.put("com.ludashi.benchmark", 350);
        tmp.put("com.primatelabs.geekbench", 180);
        tmp.put("com.primatelabs.geekbench3", 180);
        tmp.put("eu.chainfire.cfbench", 180);
        tmp.put("org.zeroxlab.zeroxbenchmark", 300);
        tmp.put("se.nena.nenamark1", 180);
        tmp.put("com.greenecomputing.linpack", 300);
        tmp.put("com.quicinc.vellamo", 560);
        tmp.put("com.rightware.BasemarkX_Free", 300);
        tmp.put("com.aurorasoftworks.quadrant.ui.advanced", 180);
        tmp.put("com.futuremark.pcmark.android.benchmark", 300);
        tmp.put("com.antutu.ABenchMark.GL2", 300);
        tmp.put("com.antutu.ABenchMark.GL3", 300);
        appConfig = Collections.unmodifiableMap(tmp);
    }
}