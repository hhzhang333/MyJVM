package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;
import cn.edu.seu.myjvm.parser.MemberInfo;

import java.io.InputStream;

/**
 * Created by a on 2018/3/5.
 */
public class ConstantValueAttribute extends AttributeInfo {
    private int constantValueIndex;

    public ConstantValueAttribute(ConstantPool cp) {
        super(cp);
    }

    @Override
    public void read(InputStream inputStream) {
        this.constantValueIndex = u2.init(inputStream).getData();
    }

    public int getConstantValueIndex() {
        return constantValueIndex;
    }

    public void setConstantValueIndex(int constantValueIndex) {
        this.constantValueIndex = constantValueIndex;
    }
}
