package com.vowme.vol.app;

import android.app.Application;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Jibran Tariq on 8/26/2017.
 */

public class VowMeApplication extends Application {

    public static String getFileName() {
        return Environment.getExternalStorageDirectory() + "/com.vowme.vol.app/error_log.txt";
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                handleUncaughtException(t, e);
            }
        });
    }

    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace();
        File file = new File(getFileName());
        FileWriter writer = null;
        try {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            FileWriter writer2 = new FileWriter(file);
        } catch (IOException e4) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e3) {
                }

            }
            System.exit(2);
        }
    }
}
