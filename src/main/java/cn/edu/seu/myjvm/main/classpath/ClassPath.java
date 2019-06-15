package cn.edu.seu.myjvm.main.classpath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by a on 2018/3/1.
 */
public class ClassPath {
    private Entry bootClassPath;
    private Entry extClassPath;
    private Entry userClassPath;

    public static ClassPath parse(String jreOption, String cpOption) throws Exception {
        ClassPath classPath = new ClassPath();
        classPath.parseBootAndExtClassPath(jreOption);
        classPath.parseUserClassPath(cpOption);
        return classPath;
    }

    public BasicMatch readClass(String className) throws IOException {
        String clazzName = className + ".class";
        BasicMatch basicMatch = null;
        basicMatch = bootClassPath.readClass(clazzName);
        if (basicMatch != null)
            return basicMatch;
        basicMatch = extClassPath.readClass(clazzName);
        if (basicMatch != null)
            return basicMatch;
        return userClassPath.readClass(clazzName);
    }

    private void parseBootAndExtClassPath(String jreOption) throws Exception {
        String jreDir = getJreDir(jreOption);
        String jreLibPath = jreDir + File.separator + "*";
        this.bootClassPath = WildcardEntry.newWildcardEntry(jreLibPath);

        String jreExtPath = jreDir + File.separator + "lib" + File.separator + "ext" + File.separator + "*";
        this.extClassPath = WildcardEntry.newWildcardEntry(jreExtPath);
    }

    private String getJreDir(String jreOption) throws Exception {
        if (jreOption.length() != 0 && new File(jreOption).exists())
            return jreOption;
        if (jreOption.equals("./jre"))
            return "./jre";
        Map<String, String> envmap = System.getenv();
        if (envmap.get("JAVA_HOME").length() != 0) {
            return envmap.get("JAVA_HOME") + File.separator + "jre";
        } else
            throw  new Exception("can not find jre folder!");
    }

    private void parseUserClassPath(String cpOption) {
        if (cpOption.isEmpty())
            cpOption = ".";
        this.userClassPath = Entry.newEntry(cpOption);
    }
}
