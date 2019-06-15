package cn.edu.seu.myjvm.parser;

import cn.edu.seu.myjvm.parser.attribute.AttributeInfo;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.attribute.CodeAttribute;
import cn.edu.seu.myjvm.parser.attribute.ConstantValueAttribute;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class MemberInfo extends BasicInfo {
    public int accessFlags;
    public int nameIndex;
    public int descriptorIndex;
    public int attributesCount;
    public AttributeInfo[] attributes;

    public MemberInfo(ConstantPool cp) {
        super(cp);
    }

    @Override
    public void read(InputStream inputStream) throws Exception {
        accessFlags = u2.init(inputStream).getData();
        nameIndex = u2.init(inputStream).getData();
        descriptorIndex = u2.init(inputStream).getData();
        attributesCount = u2.init(inputStream).getData();
        attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            AttributeInfo attributeInfo = AttributeInfo.readAttribute(inputStream, constantPool);
            attributeInfo.read(inputStream);
            attributes[i] = attributeInfo;
        }
    }

    public ConstantValueAttribute getConstantValueAttribute() {
        for (AttributeInfo attributeInfo: attributes) {
            if (attributeInfo.getClass().getSimpleName().equals("ConstantValueAttribute")) {
                return (ConstantValueAttribute)attributeInfo;
            }
        }
        return null;
    }

    //public u2 accessFlags() {}

    public String name() throws Exception {
        return this.constantPool.getUtf8(this.nameIndex);
    }

    public String descriptor() throws Exception {
        return this.constantPool.getUtf8(this.descriptorIndex);
    }

    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attributeInfo: attributes) {
            switch (attributeInfo.getClass().getSimpleName()) {
                case "CodeAttribute":
                    return (CodeAttribute)attributeInfo;
            }
        }
        return null;
    }
}
