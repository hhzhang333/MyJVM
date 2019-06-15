package cn.edu.seu.myjvm.main.classpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by a on 2018/2/28.
 */
public class DirEntry extends Entry {

    private String absDir;

    public BasicMatch readClass(String className) throws FileNotFoundException {
        BasicMatch basicMatch = new BasicMatch();
        basicMatch.setEntry(this);
        basicMatch.setInputStream(new FileInputStream(new File(className)));
        return basicMatch;
    }

    public static DirEntry newDirEntry(String path) {
        DirEntry dirEntry = new DirEntry();
        String absDir = new File("").getAbsolutePath();
        dirEntry.setAbsDir(absDir);
        return dirEntry;
    }

    public String getAbsDir() {
        return absDir;
    }

    public void setAbsDir(String absDir) {
        this.absDir = absDir;
    }
}
