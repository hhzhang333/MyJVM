package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.instructions.base.MethodInvokeLogic;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Method;
import cn.edu.seu.myjvm.runtime.heap.MethodRef;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/6.
 */
public class INVOKEINTERFACE implements Instruction {
    private int index;
    private int count;
    private int zero;

    @Override
    public void fetchOperands(BytecodeReader reader) throws Exception {
        this.index = reader.readU2().getData();
        this.count = reader.readU1().getData();
        this.zero = reader.readU1().getData();
    }

    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index).getValue();
        Method resolvedMethod = methodRef.resolvedInterfaceMethod();
        if (resolvedMethod.isStatic() || resolvedMethod.isPrivate())
            throw new Exception("java.lang.IncompatibleClassChangeError");

        Mobject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null)
            throw new Exception("java.lang.NullPointerException");
        if (!ref.getClazz().isImplements(methodRef.resolvedClass()))
            throw new Exception("java.lang.IncompatibleClassChangeError");

        Method method = MethodRef.lookupMethodInClass(ref.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        if (method == null || method.isAbstract())
            throw new Exception("java.lang.IllegalAccessError");
        MethodInvokeLogic.invokeMethod(frame, method);
    }
}
