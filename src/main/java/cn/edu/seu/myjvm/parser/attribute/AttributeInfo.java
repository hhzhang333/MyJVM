package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.basictype.u4;
import cn.edu.seu.myjvm.parser.BasicInfo;
import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.parser.ConstantPool;

import cn.edu.seu.myjvm.parser.constantinfo.ConstantUTF8Info;
import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.runtime.heap.Constant;
import com.sun.org.apache.regexp.internal.RE;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public abstract class AttributeInfo extends BasicInfo {

    private static final String CODE = "Code";
    private static final String CONSTANTVALUE = "ConstantValue";
    private static final String DEPRECATED = "Deprecated";
    private static final String EXCEPTIONS = "Exceptions";
    private static final String LINENUMBERTABLE = "LineNumberTable";
    private static final String LOCALVARIABLETABLE = "LocalVariableTable";
    private static final String SOURCEFILE = "SourceFile";
    private static final String SYNTHETIC = "Synthetic";

    public static AttributeInfo[] readAttributes(InputStream inputStream, ConstantPool constantPool) throws Exception {
        int attrCount = u2.init(inputStream).getData();
        AttributeInfo[] attributes = new AttributeInfo[attrCount];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = readAttribute(inputStream, constantPool);
            attributes[i].read(inputStream);
        }
        return attributes;
    }

    public static AttributeInfo readAttribute(InputStream inputStream, ConstantPool constantPool) throws Exception {
        int attrNameIndex = u2.init(inputStream).getData();
        String attrName = ((ConstantUTF8Info) constantPool.getCpInfo()[attrNameIndex]).value;
        int attrLen = (int)u4.init(inputStream).getData();
        return newAttribute(attrName, attrLen, constantPool);
    }

    public AttributeInfo(ConstantPool cp) {
        super(cp);
    }

    public abstract void read(InputStream inputStream) throws Exception;

    public static AttributeInfo newAttribute(String attrName, int attrLen, ConstantPool cp) {
        switch (attrName) {
            case CODE:
                return new CodeAttribute(cp);
            case CONSTANTVALUE:
                return new ConstantValueAttribute(cp);
            case DEPRECATED:
                return new DeprecatedAttribute(cp);
            case EXCEPTIONS:
                return new ExceptionsAttribute(cp, attrLen);
            case LINENUMBERTABLE:
                return new LineNumberTableAttribute(cp);
            case LOCALVARIABLETABLE:
                return new LocalVariableTableAttribute(cp);
            case SOURCEFILE:
                return new SourceFileAttribute(cp);
            default:
                return new UnparsedAttribute(cp, attrName, attrLen);
        }
    }
}
