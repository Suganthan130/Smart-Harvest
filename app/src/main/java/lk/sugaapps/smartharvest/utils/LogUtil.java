package lk.sugaapps.smartharvest.utils;

import lk.sugaapps.smartharvest.BuildConfig;

public class LogUtil {

    private static final boolean DEBUG_MODE = BuildConfig.DEBUG;

    public static void logSuccess(String message) {
        if (DEBUG_MODE) {
            System.out.println("🚀 " + message);
        }
    }

    public static void logInfo(String message) {
        if (DEBUG_MODE) {
            System.out.println("✅ " + message);
        }
    }

    public static void logError(String message) {
        if (DEBUG_MODE) {
            System.err.println("❗" + message);
        }
    }
}
