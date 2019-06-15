package cn.edu.seu.myjvm.parser.attribute;

import cn.edu.seu.myjvm.parser.ConstantPool;
import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.basictype.u4;


import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class CodeAttribute extends AttributeInfo {
    public int maxStack;
    public int maxLocals;
    public int codeLength;
    public short[] code;
    public int excepetionTableLength;
    public ExceptionTable[] exceptionTable;
    public int attributes_count;
    public AttributeInfo[] attributes;


    public CodeAttribute(ConstantPool cp) {
        super(cp);
    }

    @Override
    public void read(InputStream inputStream) throws Exception {

        //这里可以增加一个输出maxStack、maxLocal的功能
        maxStack = u2.init(inputStream).getData();
        maxLocals = u2.init(inputStream).getData();
        codeLength = (int) u4.init(inputStream).getData();
        code = new short[codeLength];
        for (int i = 0; i < codeLength; i++) {
            code[i] = u1.init(inputStream).getData();
        }
        excepetionTableLength = u2.init(inputStream).getData();
        exceptionTable = new ExceptionTable[excepetionTableLength];
        for (int i = 0; i < excepetionTableLength; i++) {
            ExceptionTable exceTable = new ExceptionTable();
            exceTable.read(inputStream);
            exceptionTable[i] = exceTable;
        }
        attributes = super.readAttributes(inputStream, constantPool);

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

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public short[] getCode() {
        return code;
    }

    public void setCode(short[] code) {
        this.code = code;
    }

    public int getExcepetionTableLength() {
        return excepetionTableLength;
    }

    public void setExcepetionTableLength(int excepetionTableLength) {
        this.excepetionTableLength = excepetionTableLength;
    }

    public ExceptionTable[] getExceptionTable() {
        return exceptionTable;
    }

    public void setExceptionTable(ExceptionTable[] exceptionTable) {
        this.exceptionTable = exceptionTable;
    }

    public int getAttributes_count() {
        return attributes_count;
    }

    public void setAttributes_count(int attributes_count) {
        this.attributes_count = attributes_count;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }
}
