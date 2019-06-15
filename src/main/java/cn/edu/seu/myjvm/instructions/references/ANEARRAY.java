package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassRef;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/7.
 */
public class ANEARRAY extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.getIndex().getData()).getValue();
        Class componentClass = classRef.resolvedClass();

        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();

        if (count < 0)
            throw new Exception("java.lang.NegativeArraySizeException");

        Mobject arrClass = componentClass.newArray(count);
        stack.pushRef(arrClass);
    }
}
