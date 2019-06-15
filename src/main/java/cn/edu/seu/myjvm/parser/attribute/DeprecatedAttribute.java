package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.basictype.u4;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/16.
 */
public class DeprecatedAttribute extends AttributeInfo {

    public DeprecatedAttribute(ConstantPool cp) {
        super(cp);
    }

    @Override
    public void read(InputStream inputStream) {
        u4 tmp = new u4();
        tmp.setData(0);
    }
}
