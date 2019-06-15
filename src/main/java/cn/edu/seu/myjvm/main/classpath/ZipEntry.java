package cn.edu.seu.myjvm.main.classpath;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by a on 2018/2/28.
 */
public class ZipEntry extends Entry {

    private String absPath;

    public BasicMatch readClass(String className) throws IOException {
        BasicMatch basicMatch = new BasicMatch();
        basicMatch.setEntry(this);
        ZipFile zipFile = new ZipFile(this.absPath);
        Enumeration<? extends java.util.zip.ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            java.util.zip.ZipEntry zipEntry = entries.nextElement();
            if (zipEntry.getName().equals(className)) {
                basicMatch.setInputStream(zipFile.getInputStream(zipEntry));
                return basicMatch;
            }
        }
        zipFile.close();
        return null;
    }

    public static ZipEntry newZipEntry(String path) {
        ZipEntry zipEntry = new ZipEntry();
        zipEntry.setAbsPath(path);
        return zipEntry;
    }

    public String getAbsPath() {
        return absPath;
    }

    public void setAbsPath(String absPath) {
        this.absPath = absPath;
    }
}
