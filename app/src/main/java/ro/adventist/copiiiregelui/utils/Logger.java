package ro.adventist.copiiiregelui.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import ro.adventist.copiiiregelui.KingsKidsApplication;

public final class Logger {

    private static final String TAG = Logger.class.getSimpleName();
    private static final String LOG_FILE_PATH = KingsKidsApplication.getAppContext().getFilesDir() + "/log/";
    private static final String LOG_FILE_NAME = "log.txt";

    public static void v(@NonNull String tag, @Nullable String message) {
        Log.v(tag, message);
        logToFile(tag, message);
    }

    public static void d(@NonNull String tag, @Nullable String message) {
        Log.d(tag, message);
        logToFile(tag, message);
    }

    public static void i(@NonNull String tag, @Nullable String message) {
        Log.i(tag, message);
        logToFile(tag, message);
    }

    public static void w(@NonNull String tag, @Nullable String message) {
        Log.w(tag, message);
        logToFile(tag, message);
    }

    public static void e(@NonNull String tag, @Nullable String message) {
        Log.e(tag, message);
        logToFile(tag, message);
    }

    public static void e(@NonNull String tag, @Nullable String message, @Nullable Throwable throwable) {
        Log.e(tag, message, throwable);
        logToFile(tag, message + "\n" + Log.getStackTraceString(throwable));
    }

    private static void logToFile(@NonNull String tag, @NonNull String message) {
//        if (AppSettings.isFileLogEnabled()) {
//            Observable.fromCallable(() -> {
//                ensureLogFile();
//                File logFile = new File(LOG_FILE_PATH, LOG_FILE_NAME);
//                String currentTime = CalendarDateUtils.getStringDateFromDate(new Date());
//                try (PrintWriter out = new PrintWriter(new FileWriter(logFile, true))) {
//                    out.append(currentTime).append(": ").append(tag).append(": ").append(message).append("\n");
//                    return true;
//                } catch (IOException e) {
//                    Log.e(TAG, "LogToFile print to file error: " + e.getMessage(), e);
//                }
//                return false;
//            }).compose(RxUtils.applySchedulers())
//                    .subscribe();
//
//
//        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void ensureLogFile() {
        File logFile = new File(LOG_FILE_PATH, LOG_FILE_NAME);
        File logDirectory = new File(LOG_FILE_PATH);

        if (!logFile.exists()) {
            logDirectory.mkdirs();
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "LogToFile create file error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
