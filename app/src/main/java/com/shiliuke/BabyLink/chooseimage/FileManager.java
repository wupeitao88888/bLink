package com.shiliuke.BabyLink.chooseimage;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public final class FileManager {

    /**
     * 缓存基本目录
     */

    public static final String APP_CACHE_DIR = "/data/data/com.shiliuke/babyLink";

    private static final String BASE_PATH = "babyLink";

    private static final String IMAGE = "image";
    private static final String TEMP_FILE = "temp_file";
    private static final String LOG = "log";
    private static final String DEBUG = "debug";

    public static File getImagePath() {
        return getPath(IMAGE);
    }

    public static File getTempFilePath() {
        return getPath(TEMP_FILE);
    }


    public static File getLogPath() {
        return getPath(LOG);
    }

    public static File getDebugPath() {
        return getPath(DEBUG);
    }

    private static File getPath(String fileName) {
        File file = new File(getBasepath(), fileName);
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private static File getBasepath() {
        File appCacheDir = null;
        if (isSDCardAvailable()) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), BASE_PATH);
        }
        if (appCacheDir == null) {

            appCacheDir = new File(APP_CACHE_DIR);
        }
        if (!appCacheDir.exists())
            appCacheDir.mkdirs();
        return appCacheDir;
    }


    /**
     * copy file
     *
     * @param src
     * @param dst
     * @throws IOException
     */
    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }

}
