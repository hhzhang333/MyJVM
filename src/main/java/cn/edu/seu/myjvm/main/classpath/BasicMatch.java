package cn.edu.seu.myjvm.main.classpath;

import java.io.InputStream;

/**
 * Created by a on 2018/3/1.
 */
public class BasicMatch {
    private InputStream inputStream;
    private Entry entry;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }
}
