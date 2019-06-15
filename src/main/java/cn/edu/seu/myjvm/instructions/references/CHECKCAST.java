package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassRef;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/5.
 */
public class CHECKCAST extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        Mobject ref = (Mobject) stack.popRef();
        stack.pushRef(ref);
        if (ref == null)
            return;
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.getIndex().getData()).getValue();
        Class clazz = classRef.resolvedClass();
        if (!ref.isInstanceOf(clazz)) {
            throw new Exception("java.lang.ClassCastException");
        }
    }
}
