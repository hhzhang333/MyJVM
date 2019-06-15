package cn.edu.seu.myjvm.parser.attribute;


import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ExceptionTable {
    public int startPC;
    public int endPC;
    public int handlerPC;
    public int catchType;

    public void read(InputStream inputStream) {
        startPC = u2.init(inputStream).getData();
        endPC = u2.init(inputStream).getData();
        handlerPC = u2.init(inputStream).getData();
        catchType = u2.init(inputStream).getData();
    }
}
