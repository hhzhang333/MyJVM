package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.parser.constantinfo.*;

/**
 * Created by a on 2018/2/27.
 */
public class ConstantPool {
    private Class clazz;
    private Constant[] constants;

    public static ConstantPool newConstantPool(Class clazz, cn.edu.seu.myjvm.parser.ConstantPool cfCp) throws Exception {
        int length = cfCp.getConstant_pool_count();
        Constant[] consts = new Constant[length];

        for (int i = 0; i < length; i++)
            consts[i] = new Constant();

        ConstantPool constantPool = new ConstantPool();
        constantPool.setClazz(clazz);
        constantPool.setConstants(consts);

        ConstantInfo[] cpInfo = cfCp.getCpInfo();

        for (int i = 1; i < length; i++) {
            ConstantInfo tmp = cpInfo[i];
            switch (tmp.getClass().getSimpleName()) {
                case "ConstantIntegerInfo":
                    ConstantIntegerInfo constantInfo = (ConstantIntegerInfo)cpInfo[i];
                    consts[i].setValue(constantInfo.value);
                    break;
                case "ConstantFloatInfo":
                    ConstantFloatInfo constantFloatInfo = (ConstantFloatInfo)cpInfo[i];
                    consts[i].setValue(constantFloatInfo.value);
                    break;
                case "ConstantLongInfo":
                    ConstantLongInfo constantLongInfo = (ConstantLongInfo) cpInfo[i];
                    consts[i].setValue(constantLongInfo.getValue());
                    i++;
                    break;
                case "ConstantDoubleInfo":
                    ConstantDoubleInfo constantDoubleInfo = (ConstantDoubleInfo) cpInfo[i];
                    consts[i].setValue(constantDoubleInfo.getValue());
                    i++;
                    break;
                case "ConstantStringInfo":
                    ConstantStringInfo constantStringInfo = (ConstantStringInfo) cpInfo[i];
                    consts[i].setValue(constantStringInfo.getString());
                    break;
                case "ConstantClassInfo":
                    ConstantClassInfo constantClassInfo = (ConstantClassInfo) cpInfo[i];
                    consts[i].setValue(ClassRef.newClassRef(constantPool, constantClassInfo));
                    break;
                case "ConstantFieldRefInfo":
                    ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) cpInfo[i];
                    consts[i].setValue(FieldRef.newFieldRef(constantPool, constantFieldRefInfo));
                    break;
                case "ConstantMethodRefInfo":
                    ConstantMethodRefInfo methodRefInfo = (ConstantMethodRefInfo) cpInfo[i];
                    consts[i].setValue(MethodRef.newMethodRef(constantPool, methodRefInfo));
                    break;
                case "ConstantInterfaceMethodRefInfo":
                    ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo = (ConstantInterfaceMethodRefInfo) cpInfo[i];
                    consts[i].setValue(InterfaceMethodRef.newInterfaceMethodRef(constantPool, constantInterfaceMethodRefInfo));
                    break;
                default:
            }
        }

        return constantPool;
    }

    public Constant getConstant(int index) throws Exception {
        Constant constant = constants[index];
        if (constant != null)
            return constant;
        throw new Exception("No constants at index " + index);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Constant[] getConstants() {
        return constants;
    }

    public void setConstants(Constant[] constants) {
        this.constants = constants;
    }
}
