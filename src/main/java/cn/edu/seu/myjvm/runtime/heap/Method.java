package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.MemberInfo;
import cn.edu.seu.myjvm.parser.attribute.CodeAttribute;
import cn.edu.seu.myjvm.parser.attribute.ExceptionTable;

import java.util.List;

/**
 * Created by a on 2018/2/27.
 */
public class Method extends ClassMember {
    private int maxStack;
    private int maxLocals;
    private short[] code;
    private int argSlotCount;
    private ExceptionTable exceptionTable;

    public static Method[] newMethods(Class clazz, MemberInfo[] cfMethod) throws Exception {
        Method[] methods = new Method[cfMethod.length];
        for (int i = 0; i < cfMethod.length; i++) {
            methods[i] = newMethod(clazz, cfMethod[i]);
        }
        return methods;
    }

    public static Method newMethod(Class clazz, MemberInfo cfMethod) throws Exception {
        Method method = new Method();
        method.setClazz(clazz);
        method.copyMemberInfo(cfMethod);
        method.copyAttributes(cfMethod);
        MethodDescriptor methodDescriptor = MethodDescriptorParser.parseMethodDescriptor(method.getDescriptor());
        method.calArgSlotCount(methodDescriptor.getParameterTypes());
        if (method.isNative()) {
            method.injectCodeAttribute(methodDescriptor.getReturnType());
        }
        return method;
    }

    public void injectCodeAttribute(String returnType) {
        this.maxStack = 4;
        this.maxLocals = this.argSlotCount;
        switch (returnType.charAt(0)) {
            case 'V':
                this.code = new short[]{(short) 0xfe, (short) 0xb1};
                break;
            case 'D':
                this.code = new short[]{(short) 0xfe, (short) 0xaf};
                break;
            case 'F':
                this.code = new short[]{(short) 0xfe, (short) 0xae};
                break;
            case 'J':
                this.code = new short[]{(short) 0xfe, (short) 0xad};
                break;
            case 'L':case '[':
                this.code = new short[]{(short) 0xfe, (short) 0xb0};
                break;
            default:
                this.code = new short[]{(short) 0xfe, (short) 0xac};
                break;
        }
    }

    public void calArgSlotCount(List<String> paramTypes) throws Exception {
        if (paramTypes == null)
            return;
        for (String paramType: paramTypes) {
            this.argSlotCount++;
            if (paramType.equals("J") || paramType.equals("D"))
                this.argSlotCount++;
        }
        if (!this.isStatic())
            this.argSlotCount++;
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STATIC);
    }

    public void copyAttributes(MemberInfo cfMethod) {
        CodeAttribute codeAttribute = cfMethod.getCodeAttribute();
        if (codeAttribute != null) {
            this.maxStack = codeAttribute.getMaxStack();
            this.maxLocals = codeAttribute.getMaxLocals();
            this.code = codeAttribute.getCode();
        }
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isNative() {
        return 0 != (this.accessFlags & AccessFlags.ACC_NATIVE);
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public short[] getCode() {
        return code;
    }

    public void setCode(short[] code) {
        this.code = code;
    }

    public int getArgSlotCount() {
        return argSlotCount;
    }

    public void setArgSlotCount(int argSlotCount) {
        this.argSlotCount = argSlotCount;
    }

    public ExceptionTable getExceptionTable() {
        return exceptionTable;
    }

    public void setExceptionTable(ExceptionTable exceptionTable) {
        this.exceptionTable = exceptionTable;
    }
}
