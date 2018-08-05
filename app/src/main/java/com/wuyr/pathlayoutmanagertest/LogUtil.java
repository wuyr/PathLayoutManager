package com.wuyr.pathlayoutmanagertest;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wuyr on 6/8/16 6:54 PM.
 */
@SuppressWarnings({"unused", "WeakerAccess", "SameParameterValue"})
public class LogUtil {

    public static final int VERBOSE = 1, DEBUG = 2, INFO = 3, WARN = 4, ERROR = 5;
    private static boolean isDebugOn, isShowClassName;
    private static int debugLevel = DEBUG;

    public static void setDebugOn(boolean isDebugOn) {
        LogUtil.isDebugOn = isDebugOn;
        LogUtil.isShowClassName = true;
    }

    public static void setIsShowClassName(boolean isShowClassName) {
        LogUtil.isShowClassName = isShowClassName;
    }

    public static void setDebugLevel(@DebugLevel int l) {
        debugLevel = l;
        setDebugOn(true);
    }

    public static void print(Object s) {
        print(null, s);
    }

    public static void printf(String format, Object... args) {
        print(format, args);
    }

    private static void print(String format, Object... args) {
        if (isDebugOn) {
            if (args == null || args.length == 0) {
                return;
            }
            StackTraceElement element = Thread.currentThread().getStackTrace()[4];
            String tag = isShowClassName ? String.format("%s-->%s", element.getClassName(), element.getMethodName()) : element.getMethodName();
            String msg = format != null ? String.format(format, args) : String.valueOf(args[0]);
            switch (debugLevel) {
                case VERBOSE:
                    Log.v(tag, msg);
                    break;
                case DEBUG:
                    Log.d(tag, msg);
                    break;
                case INFO:
                    Log.i(tag, msg);
                    break;
                case WARN:
                    Log.w(tag, msg);
                    break;
                case ERROR:
                    Log.e(tag, msg);
                    break;
                default:
                    break;
            }
        }
    }

    @IntDef({VERBOSE, DEBUG, INFO, WARN, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    private @interface DebugLevel {
    }
}