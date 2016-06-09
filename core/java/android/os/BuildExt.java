//
// Created from decompiled by Procyon v0.5.30
//

package android.os;

import android.content.ContentResolver;
import android.provider.Settings;
import android.content.Context;

public class BuildExt
{
    public static final Boolean CHINAMOBILE_TEST = getString("ro.chinamobile.test").equals("true");
    private static final String COLOR_TYPE_PATH = "/proc/lk_info/colortype";
    public static final String CTA = getString("ro.build.cta");
    public static final Boolean CUSTOMIZE_CHINAMOBILE = isChinaMobile();
    public static final Boolean CUSTOMIZE_CHINATELECOM = getString("ro.customize.isp").equals("chinatelecom");
    public static final Boolean CUSTOMIZE_CHINAUNICOM = isChinaUnicom();
    private static final String CUSTOMIZE_ISP_VALUE = getString("ro.customize.isp");
    public static final Boolean HAS_DRIVE_MODE = true;
    public static final Boolean HAS_PERMANENTKEY;
    public static final String HAS_SMARTBAR = getString("ro.meizu.has_smartbar");
    public static final Boolean HIDE_INFO = getString("ro.flyme.hideinfo").equals("true");
    public static final String IS_FLYMEROM = getString("ro.meizu.rom.config");
    public static final Boolean IS_M1 = (getString("ro.product.model").equals("m79") || getString("ro.product.model").equals("m1") || getString("ro.product.model").equals("k32v2"));
    public static final Boolean IS_M1_NOTE = (getString("ro.product.model").equals("m71") || getString("ro.product.model").equals("m1 note") || getString("ro.product.model").equals("k52v2"));
    public static final Boolean IS_M2 = (getString("ro.product.model").equals("mt6735") || getString("ro.product.model").equals("m2") || getString("ro.product.model").equals("M2 Mini"));
    public static final Boolean IS_M2C = (getString("ro.product.model").equals("M578C") || getString("ro.product.model").equals("m88c") || getString("ro.product.model").equals("M578CA") || getString("ro.product.model").equals("M578CE"));
    public static final Boolean IS_M2_NOTE = (getString("ro.product.model").equals("mt6753") || getString("ro.product.model").equals("m2 note"));;
    public static final Boolean IS_M2_NOTEC = (getString("ro.product.model").equals("M571C") || getString("ro.product.model").equals("m81c"));
    public static final Boolean IS_M71C = getString("ro.build.device.name").equals("m71c");
    public static final Boolean IS_MA01 = getString("ro.product.model").equals("MA01");
    public static final Boolean IS_MA01C = (getString("ro.product.model").equals("MA01C") || getString("ro.product.model").equals("m1c metal") || getString("ro.product.model").equals("M57AC"));
    public static final Boolean IS_MOBILE_PUBLIC = getString("ro.customize.isp").equals("mobilepublic");
    public static final Boolean IS_MX2 = getString("ro.product.device").equals("mx2");
    public static final Boolean IS_MX3 = getString("ro.product.device").equals("mx3");
    public static final Boolean IS_MX4 = (getString("ro.product.model").equals("m75") || getString("ro.product.model").equals("k95v2") || getString("ro.product.model").equals("M460A") || getString("ro.product.device").equals("mx4"));
    public static final Boolean IS_MX4_Pro = (getString("ro.product.model").equals("m76") || getString("ro.product.model").equals("espresso5430") || (getString("ro.board.platform").equals("exynos5") && getString("ro.arch").equals("exynos5430")));
    public static final Boolean IS_MX5 = (getString("ro.product.model").equals("MX5") || getString("ro.product.model").equals("mt6595"));
    public static final Boolean IS_MX5_PRO = (getString("ro.product.model").equals("M86") || getString("ro.product.model").equals("NIUX") || getString("ro.product.model").equals("MX5 Pro"));
    public static final boolean IS_PRODUCT = !getString("ro.error.receiver.default").equals("com.howell.logsnapshot");
    public static final Boolean IS_SHOPDEMO = getString("ro.meizu.customize.demo").equals("true");
    public static final Boolean IS_TD_PLATFORM = getString("ro.meizu.hardware.modem").equalsIgnoreCase("td-scdma");
    public static final Boolean IS_WCDMA_PLATFORM = getString("ro.meizu.hardware.modem").equalsIgnoreCase("wcdma");
    public static final String MANUFACTURER = getString("ro.product.manufacturer");
    public static final String MZ_MODEL = getString("ro.meizu.product.model");
    public static final String PERMANENTKEY = getString("ro.meizu.permanentkey");
    private static final String TAG = "BuildExt";
    private static String mDeviceTpColor = null;

    static {
        HAS_PERMANENTKEY = hasPermanentKey();
    }

    public static int getBrightnessMaxLevel() {
        int result;
        if (IS_MX3 || IS_MX4 || IS_MX4_Pro) {
            result = 2040;
        }
        else if (IS_M1_NOTE || IS_M1) {
            result = 2048;
        }
        else {
            result = 255;
        }
        return result;
    }

    public static String getColorType() {
        //
        // This method could not be decompiled.
        //
        // Original Bytecode:
        //
        //     0: new             Ljava/io/File;
        //     3: dup
        //     4: ldc             "/proc/lk_info/colortype"
        //     6: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //     9: astore_2
        //    10: new             Ljava/io/BufferedReader;
        //    13: astore_0
        //    14: new             Ljava/io/FileReader;
        //    17: astore_1
        //    18: aload_1
        //    19: aload_2
        //    20: invokespecial   java/io/FileReader.<init>:(Ljava/io/File;)V
        //    23: aload_0
        //    24: aload_1
        //    25: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;)V
        //    28: aload_0
        //    29: invokevirtual   java/io/BufferedReader.readLine:()Ljava/lang/String;
        //    32: astore_1
        //    33: aload_1
        //    34: ifnull          44
        //    37: aload_1
        //    38: invokevirtual   java/lang/String.trim:()Ljava/lang/String;
        //    41: astore_0
        //    42: aload_0
        //    43: areturn
        //    44: aload_0
        //    45: invokevirtual   java/io/BufferedReader.close:()V
        //    48: ldc_w           "unknown"
        //    51: astore_0
        //    52: goto            42
        //    55: astore_0
        //    56: aload_0
        //    57: invokevirtual   java/lang/Exception.printStackTrace:()V
        //    60: goto            48
        //    63: astore_0
        //    64: goto            56
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ---------------------
        //  10     28     55     56     Ljava/lang/Exception;
        //  28     33     63     67     Ljava/lang/Exception;
        //  37     42     63     67     Ljava/lang/Exception;
        //  44     48     63     67     Ljava/lang/Exception;
        //
        // The error that occurred was:
        //
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0042:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
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

    private static int getInt(String s) {
        return SystemProperties.getInt(s, -1);
    }

    private static String getString(String s) {
        return SystemProperties.get(s, "unknown");
    }

    public static boolean hasEseSmartMX(Context context) {
        boolean b = true;
        if (Settings.Secure.getInt(context.getContentResolver(), "smartmx_ese", -1) != 1) {
            b = false;
        }
        return b;
    }

    public static boolean hasNFC() {
        return getString("ro.meizu.hardware.nfc").equals("true") || IS_MX4_Pro;
    }

    private static Boolean hasPermanentKey() {
        Boolean b;
        if (PERMANENTKEY != null && PERMANENTKEY.equals("true")) {
            b = true;
        }
        else {
            b = false;
        }
        return b;
    }

    public static boolean hasSmartBar() {
        return true;
    }

    public static boolean isBlackDevice() {
        final boolean b = true;
        boolean b2;
        if (IS_M1_NOTE) {
            b2 = b;
        }
        else {
            if (mDeviceTpColor == null) {
                mDeviceTpColor = readFromFb("sys/devices/mx_tsp/appid");
            }
            if (mDeviceTpColor != null) {
                b2 = b;
                if (mDeviceTpColor.contains("B:")) {
                    return b2;
                }
                b2 = b;
                if (mDeviceTpColor.contains("BS:")) {
                    return b2;
                }
            }
            b2 = false;
        }
        return b2;
    }

    public static boolean isBrcm43341() {
        return "brcm43341".equals(getString("ro.nfc.platform"));
    }

    private static Boolean isChinaMobile() {
        Boolean b;
        if (CUSTOMIZE_ISP_VALUE != null && CUSTOMIZE_ISP_VALUE.equals("chinamobile")) {
            b = true;
        }
        else {
            b = false;
        }
        return b;
    }

    private static Boolean isChinaUnicom() {
        Boolean b;
        if (CUSTOMIZE_ISP_VALUE != null && CUSTOMIZE_ISP_VALUE.equals("chinaunicom")) {
            b = true;
        }
        else {
            b = false;
        }
        return b;
    }

    public static boolean isFlymeRom() {
        return IS_FLYMEROM != null && IS_FLYMEROM.equals("true");
    }

    public static boolean isIndiaVersion() {
        return getString("ro.meizu.locale.region").equals("india");
    }

    public static boolean isLtgModem() {
        return "TD".equalsIgnoreCase(getString("sys.baseband"));
    }

    public static boolean isLwgModem() {
        return "UMTS".equalsIgnoreCase(getString("sys.baseband"));
    }

    public static boolean isMzProduct() {
        return IS_MX2 || IS_MX3 || IS_MX4 || IS_MX4_Pro || IS_M1_NOTE || IS_M1;
    }

    public static boolean isNxpPn547() {
        return "nxppn547".equals(getString("ro.nfc.platform"));
    }

    public static boolean isProductInternational() {
        boolean b = false;
        try {
            if (!SystemProperties.get("ro.product.locale.language").equals("zh") || !SystemProperties.get("ro.product.locale.region").equals("CN")) {
                b = true;
            }
            return b;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return b;
        }
    }

    public static boolean isShopDemoVersion() {
        return IS_SHOPDEMO;
    }

    private static String readFromFb(String p0) {
        //
        // This method could not be decompiled.
        //
        // Original Bytecode:
        //
        //     0: aconst_null
        //     1: astore          8
        //     3: aconst_null
        //     4: astore          9
        //     6: aconst_null
        //     7: astore          7
        //     9: sipush          128
        //    12: newarray        B
        //    14: astore          11
        //    16: aconst_null
        //    17: astore          6
        //    19: aconst_null
        //    20: astore          10
        //    22: aconst_null
        //    23: astore          4
        //    25: aconst_null
        //    26: astore          5
        //    28: aload           9
        //    30: astore_2
        //    31: aload           10
        //    33: astore_3
        //    34: new             Ljava/io/FileInputStream;
        //    37: astore_1
        //    38: aload           9
        //    40: astore_2
        //    41: aload           10
        //    43: astore_3
        //    44: new             Ljava/io/File;
        //    47: astore          12
        //    49: aload           9
        //    51: astore_2
        //    52: aload           10
        //    54: astore_3
        //    55: aload           12
        //    57: aload_0
        //    58: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    61: aload           9
        //    63: astore_2
        //    64: aload           10
        //    66: astore_3
        //    67: aload_1
        //    68: aload           12
        //    70: invokespecial   java/io/FileInputStream.<init>:(Ljava/io/File;)V
        //    73: aload_1
        //    74: aload           11
        //    76: invokevirtual   java/io/FileInputStream.read:([B)I
        //    79: pop
        //    80: new             Ljava/lang/String;
        //    83: astore_0
        //    84: aload_0
        //    85: aload           11
        //    87: ldc_w           "UTF-8"
        //    90: invokespecial   java/lang/String.<init>:([BLjava/lang/String;)V
        //    93: new             Ljava/lang/StringBuilder;
        //    96: astore_2
        //    97: aload_2
        //    98: invokespecial   java/lang/StringBuilder.<init>:()V
        //   101: ldc             "BuildExt"
        //   103: aload_2
        //   104: ldc_w           "cat sys/devices/mx_tsp/appid : "
        //   107: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   110: aload_0
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   114: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   117: invokestatic    android/util/Slog.d:(Ljava/lang/String;Ljava/lang/String;)I
        //   120: pop
        //   121: aload_1
        //   122: invokevirtual   java/io/FileInputStream.close:()V
        //   125: aload_1
        //   126: ifnull          140
        //   129: aload_1
        //   130: invokevirtual   java/io/FileInputStream.close:()V
        //   133: aload_0
        //   134: areturn
        //   135: astore_0
        //   136: aload_0
        //   137: invokevirtual   java/io/IOException.printStackTrace:()V
        //   140: aconst_null
        //   141: astore_0
        //   142: goto            133
        //   145: astore          4
        //   147: aload           5
        //   149: astore_1
        //   150: aload           7
        //   152: astore_0
        //   153: aload_0
        //   154: astore_2
        //   155: aload_1
        //   156: astore_3
        //   157: ldc             "BuildExt"
        //   159: ldc_w           "readFromFb error: "
        //   162: aload           4
        //   164: invokestatic    android/util/Slog.e:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   167: pop
        //   168: aload_0
        //   169: ifnull          186
        //   172: aload_0
        //   173: invokevirtual   java/io/FileInputStream.close:()V
        //   176: aload_1
        //   177: astore_0
        //   178: goto            133
        //   181: astore_0
        //   182: aload_0
        //   183: invokevirtual   java/io/IOException.printStackTrace:()V
        //   186: aconst_null
        //   187: astore_0
        //   188: goto            133
        //   191: astore          4
        //   193: aload           6
        //   195: astore_1
        //   196: aload           8
        //   198: astore_0
        //   199: aload_0
        //   200: astore_2
        //   201: aload_1
        //   202: astore_3
        //   203: ldc             "BuildExt"
        //   205: ldc_w           "readFromFb error: "
        //   208: aload           4
        //   210: invokestatic    android/util/Slog.e:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //   213: pop
        //   214: aload_0
        //   215: ifnull          232
        //   218: aload_0
        //   219: invokevirtual   java/io/FileInputStream.close:()V
        //   222: aload_1
        //   223: astore_0
        //   224: goto            133
        //   227: astore_0
        //   228: aload_0
        //   229: invokevirtual   java/io/IOException.printStackTrace:()V
        //   232: aconst_null
        //   233: astore_0
        //   234: goto            133
        //   237: astore_0
        //   238: aload_2
        //   239: ifnull          256
        //   242: aload_2
        //   243: invokevirtual   java/io/FileInputStream.close:()V
        //   246: aload_3
        //   247: astore_0
        //   248: goto            133
        //   251: astore_0
        //   252: aload_0
        //   253: invokevirtual   java/io/IOException.printStackTrace:()V
        //   256: aconst_null
        //   257: astore_0
        //   258: goto            133
        //   261: astore_0
        //   262: aload_1
        //   263: astore_2
        //   264: aload           4
        //   266: astore_3
        //   267: goto            238
        //   270: astore_2
        //   271: aload_1
        //   272: astore_2
        //   273: aload_0
        //   274: astore_3
        //   275: goto            238
        //   278: astore          4
        //   280: aload_1
        //   281: astore_0
        //   282: aload           6
        //   284: astore_1
        //   285: goto            199
        //   288: astore          4
        //   290: aload_0
        //   291: astore_2
        //   292: aload_1
        //   293: astore_0
        //   294: aload_2
        //   295: astore_1
        //   296: goto            199
        //   299: astore          4
        //   301: aload_1
        //   302: astore_0
        //   303: aload           5
        //   305: astore_1
        //   306: goto            153
        //   309: astore          4
        //   311: aload_0
        //   312: astore_2
        //   313: aload_1
        //   314: astore_0
        //   315: aload_2
        //   316: astore_1
        //   317: goto            153
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  -------------------------------
        //  34     38     145    153    Ljava/io/FileNotFoundException;
        //  34     38     191    199    Ljava/io/IOException;
        //  34     38     237    238    Any
        //  44     49     145    153    Ljava/io/FileNotFoundException;
        //  44     49     191    199    Ljava/io/IOException;
        //  44     49     237    238    Any
        //  55     61     145    153    Ljava/io/FileNotFoundException;
        //  55     61     191    199    Ljava/io/IOException;
        //  55     61     237    238    Any
        //  67     73     145    153    Ljava/io/FileNotFoundException;
        //  67     73     191    199    Ljava/io/IOException;
        //  67     73     237    238    Any
        //  73     93     299    309    Ljava/io/FileNotFoundException;
        //  73     93     278    288    Ljava/io/IOException;
        //  73     93     261    270    Any
        //  93     125    309    320    Ljava/io/FileNotFoundException;
        //  93     125    288    299    Ljava/io/IOException;
        //  93     125    270    278    Any
        //  129    133    135    140    Ljava/io/IOException;
        //  157    168    237    238    Any
        //  172    176    181    186    Ljava/io/IOException;
        //  203    214    237    238    Any
        //  218    222    227    232    Ljava/io/IOException;
        //  242    246    251    256    Ljava/io/IOException;
        //
        // The error that occurred was:
        //
        // java.lang.IndexOutOfBoundsException: Index: 188, Size: 188
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

    public static void setEseSmartMX(Context context, boolean b) {
        final ContentResolver contentResolver = context.getContentResolver();
        int n;
        if (b) {
            n = 1;
        }
        else {
            n = 0;
        }
        Settings.Secure.putInt(contentResolver, "smartmx_ese", n);
    }
}
