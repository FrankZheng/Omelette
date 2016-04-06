package com.frankzheng.app.omelette.log;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zhengxiaoqiang on 16/4/6.
 */
public class LogArchiver {
    private static final String TAG = LogArchiver.class.getSimpleName();

    private static LogArchiver instance;

    public static class ArchiveResult {
        public int result; //0: success
        public long size;
        public long compressedSize;
        public String path;

        @Override
        public String toString() {
            return String.format("{%d, %d, %d, %s}",
                    result,
                    size,
                    compressedSize,
                    path);
        }
    }

    public static LogArchiver getInstance() {
        if (instance == null) {
            synchronized (LogArchiver.class) {
                if (instance == null) {
                    instance = new LogArchiver();
                }
            }
        }
        return instance;
    }

    public ArchiveResult archive() {
        SQLiteHelper dbHelper = SQLiteHelper.getInstance();
        List<LogRecord> records = dbHelper.getAllRecords();
        if (records != null && records.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (LogRecord record : records) {
                sb.append(record.toString());
                sb.append("\n");
            }

            long seed = System.currentTimeMillis();
            String filename = String.format("log_%d.zip", seed);
            File zipFile = new File(Environment.getExternalStorageDirectory(), filename);
            try {
                if (zipFile.createNewFile()) {
                    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
                    ZipEntry entry = new ZipEntry("log.txt");
                    out.putNextEntry(entry);
                    byte[] data = sb.toString().getBytes();
                    out.write(data, 0, data.length);
                    out.closeEntry();
                    out.close();

                    ArchiveResult r = new ArchiveResult();
                    r.result = 0;
                    r.size = entry.getSize();
                    r.path = zipFile.getAbsolutePath();
                    r.compressedSize = entry.getCompressedSize();
                    return r;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
