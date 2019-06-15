package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by a on 2018/3/5.
 */
public class SourceFileAttribute extends AttributeInfo{
    private int sourcefileIndex;

    public SourceFileAttribute(ConstantPool cp) {
        super(cp);
    }

    public void read(InputStream inputStream) {
        this.sourcefileIndex = u2.init(inputStream).getData();
    }

    public String getFileName() throws Exception {
        return constantPool.getUtf8(this.sourcefileIndex);
    }
}
