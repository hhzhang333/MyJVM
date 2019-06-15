package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.basictype.u4;
import cn.edu.seu.myjvm.parser.ConstantPool;

import java.io.InputStream;

/**
 * Created by a on 2018/3/20.
 */
public class UnparsedAttribute extends AttributeInfo {
    private String name;
    private int length;
    private short[] info;


    public UnparsedAttribute(ConstantPool cp, String name, int length) {
        super(cp);
        this.name = name;
        this.length = length;
        this.info = new short[length];
    }

    @Override
    public void read(InputStream inputStream) throws Exception {
        for (int i = 0; i < length; i++)
            info[i] = u1.init(inputStream).getData();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public short[] getInfo() {
        return info;
    }

    public void setInfo(short[] info) {
        this.info = info;
    }
}
