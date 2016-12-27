package com.mlk.smsautopass.util;

import android.util.Log;


/**
 * The type Log util.
 */
public class LogUtil {

    /**
     * The Tag.
     */
    static String TAG = "MLK";
    /**
     * The Output log.
     */
    static boolean outputLog = true;

    /**
     * D.
     *
     * @param log the log
     */
    public static void d(String log) {
        if (outputLog) {
            String logMsg = debugInfo() + log;
            Log.d(TAG, logMsg);
        }
    }

    /**
     * .
     *
     * @param log the log
     */
    public static void i(String log) {
        if (outputLog) {
            String logMsg = debugInfo() + log;
            Log.i(TAG, logMsg);
        }
    }

    /**
     * E.
     *
     * @param log the log
     */
    public static void e(String log) {
        if (outputLog) {
            String logMsg = debugInfo() + log;
            Log.e(TAG, logMsg);
        }
    }

    /**
     * W.
     *
     * @param log the log
     */
    public static void w(String log) {
        if (outputLog) {
            String logMsg = debugInfo() + log;
            Log.w(TAG, logMsg);
        }
    }

    /**
     * E.
     *
     * @param e the e
     */
    public static void e(Exception e) {
        if (outputLog) {
            String logMsg = debugInfo() + e.toString();
            Log.e(TAG, logMsg);
        }
    }

    /**
     * W.
     *
     * @param e the e
     */
    public static void w(Exception e) {
        if (outputLog) {
            String logMsg = debugInfo() + e.toString();
            Log.w(TAG, logMsg);
        }
    }

    private static String debugInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[4].getClassName();
        int lineNumber = stackTrace[4].getLineNumber();
        Thread t = Thread.currentThread();
        return "[" + t.getName() + "]" + "[" + className + ":" + lineNumber + "]";
    }
}
