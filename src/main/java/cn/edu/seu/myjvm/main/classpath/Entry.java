package cn.edu.seu.myjvm.main.classpath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by a on 2018/2/28.
 */
public abstract class Entry {
    public abstract BasicMatch readClass(String className) throws IOException;

    public static Entry newEntry(String path) {
        if (path.contains(File.pathSeparator))
            return CompositeEntry.newCompositeEntry(path);
        if (path.contains("*"))
            return WildcardEntry.newWildcardEntry(path);
        if (path.contains(".jar") || path.contains(".zip") || path.contains(".JAR") || path.contains(".ZIP"))
            return ZipEntry.newZipEntry(path);
        else
            return DirEntry.newDirEntry(path);
    }
}
