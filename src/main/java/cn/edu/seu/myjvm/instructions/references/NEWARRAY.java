package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassLoader;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/7.
 */
public class NEWARRAY implements Instruction {
    private int atype;

    @Override
    public void fetchOperands(BytecodeReader reader) throws Exception {
        this.atype = reader.readU1().getData();
    }

    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0)
            throw new Exception("java.lang.NegativeArraySizeException");

        ClassLoader classLoader = frame.getMethod().getClazz().getLoader();
        Class arrClass = this.getPrimitiveArrayClass(classLoader, this.atype);
        Mobject arrayClass = arrClass.newArray(count);
        stack.pushRef(arrayClass);
    }

    public Class getPrimitiveArrayClass(ClassLoader loader, int atype) throws Exception {
        switch (atype) {
            case ArrayType.AT_BOOLEAN:
                return loader.loadClass("[Z");
            case ArrayType.AT_BYTE:
                return loader.loadClass("[B");
            case ArrayType.AT_CHAR:
                return loader.loadClass("[C");
            case ArrayType.AT_SHORT:
                return loader.loadClass("[S");
            case ArrayType.AT_INT:
                return loader.loadClass("[I");
            case  ArrayType.AT_LONG:
                return loader.loadClass("[J");
            case ArrayType.AT_FLOAT:
                return loader.loadClass("[F");
            case ArrayType.AT_DOUBLE:
                return loader.loadClass("[D");
            default:
                throw new Exception("Invalid atype");
        }
    }
}
