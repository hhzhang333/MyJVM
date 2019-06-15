package cn.edu.seu.myjvm.parser;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public abstract class BasicInfo {
    public ConstantPool constantPool;

    public BasicInfo(ConstantPool init) {
        this.constantPool = init;
    }

    public abstract void read(InputStream inputStream) throws Exception;
}
