package cn.edu.seu.myjvm.main.classpath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by a on 2018/3/1.
 */
public class WildcardEntry extends CompositeEntry {

    public static CompositeEntry newWildcardEntry(String path) {
        CompositeEntry compositeEntry = new CompositeEntry();
        String baseDir = path.replace("*", "");
        ArrayList<Entry> entries = new ArrayList<Entry>();
        walkFile(entries, baseDir);
        compositeEntry.setCompositeEntry(entries);
        return compositeEntry;
    }

    private static void walkFile(ArrayList<Entry> entries, String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file: files) {
            if (file.isDirectory())
                walkFile(entries, file.getAbsolutePath());
            if (file.getName().contains(".jar") || file.getName().contains(".JAR")) {
                ZipEntry jarEntry = ZipEntry.newZipEntry(file.getPath());
                entries.add(jarEntry);
            }
        }
    }

    public BasicMatch readClass(String className) throws IOException {
        return null;
    }
}
