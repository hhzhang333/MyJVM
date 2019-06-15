package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantClassInfo extends ConstantInfo {

    public int nameIndex;
    public ConstantPool constantPool;

    public ConstantClassInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void read(InputStream inputStream) {
        nameIndex = u2.init(inputStream).getData();
    }

    public String getName() throws Exception {
        return this.constantPool.getUtf8(this.nameIndex);
    }
}
