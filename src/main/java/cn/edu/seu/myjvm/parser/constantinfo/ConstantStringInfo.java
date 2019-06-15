package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantStringInfo extends ConstantInfo {

    public int stringIndex;
    public ConstantPool constantPool;

    public ConstantStringInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void read(InputStream inputStream) {
        stringIndex = u2.init(inputStream).getData();
    }

    public String getString() throws Exception {
        return constantPool.getUtf8(stringIndex);
    }
}
