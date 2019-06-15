package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by a on 2018/3/5.
 */
public class ExceptionsAttribute extends AttributeInfo {
    private short[] exceptionIndexTable;

    public ExceptionsAttribute(ConstantPool cp, int length) {
        super(cp);
        exceptionIndexTable = new short[length];
    }

    @Override
    public void read(InputStream inputStream) {
        for (int i = 0; i < exceptionIndexTable.length; i++)
            exceptionIndexTable[i] = u1.init(inputStream).getData();
    }
}
