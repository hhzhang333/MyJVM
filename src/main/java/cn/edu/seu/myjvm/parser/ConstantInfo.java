package cn.edu.seu.myjvm.parser;

import cn.edu.seu.myjvm.parser.constantinfo.*;
import cn.edu.seu.myjvm.runtime.heap.*;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public abstract class ConstantInfo {
    public static final short CONSTANT_Class = 7;
    public static final short CONSTANT_Fieldref = 9;
    public static final short CONSTANT_Methodref = 10;
    public static final short CONSTANT_InterfaceMethodref = 11;
    public static final short CONSTANT_String = 8;
    public static final short CONSTANT_Integer = 3;
    public static final short CONSTANT_Float = 4;
    public static final short CONSTANT_Long = 5;
    public static final short CONSTANT_Double = 6;
    public static final short CONSTANT_NameAndType = 12;
    public static final short CONSTANT_Utf8 = 1;
    public static final short CONSTANT_MethodHandle = 15;
    public static final short CONSTANT_MethodType = 16;
    public static final short CONSTANT_InvokeDynamic = 18;

    public short tag;

    public abstract void read(InputStream inputStream);

    public static ConstantInfo getConstantInfo(short tag,  ConstantPool constantPool) {
        switch (tag) {
            case CONSTANT_Class:
                return new ConstantClassInfo(constantPool);
            case CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(constantPool, 0, 0);
            case CONSTANT_Methodref:
                return new ConstantMethodRefInfo(constantPool, 0, 0);
            case CONSTANT_InterfaceMethodref:
                return new ConstantInterfaceMethodRefInfo(constantPool, 0, 0);
            case CONSTANT_Long:
                return new ConstantLongInfo();
            case CONSTANT_Double:
                return new ConstantDoubleInfo();
            case CONSTANT_String:
                return new ConstantStringInfo(constantPool);
            case CONSTANT_Integer:
                return new ConstantIntegerInfo();
            case CONSTANT_Float:
                return new ConstantFloatInfo();
            case CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo();
            case CONSTANT_Utf8:
                return new ConstantUTF8Info();
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandleInfo();
            case CONSTANT_MethodType:
                return new ConstantMethodTypeInfo();
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamicInfo();
        }
        return null;
    }
}
