package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.instructions.base.MethodInvokeLogic;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.*;
import cn.edu.seu.myjvm.runtime.heap.Class;

/**
 * Created by a on 2018/3/6.
 */
public class INVOKESPECIAL extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        Class currentClass = frame.getMethod().getClazz();
        ConstantPool constantPool = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.getIndex().getData()).getValue();

        Class resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();

        if (resolvedMethod.getName().equals("<init>") && resolvedMethod.getClazz() != resolvedClass)
            throw new Exception("java.lang.NoSuchMethodError");
        if (resolvedMethod.isStatic())
            throw new Exception("java.lang.IncompatibleClassChangeError");

        Mobject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount());
        if (ref == null)
            throw new Exception("java.lang.NullPointerException");

        if (resolvedMethod.isProtected() && resolvedMethod.getClazz().isSuperClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPacketName().equals(currentClass.getPacketName()) &&
                ref.getClazz() != currentClass && !ref.getClazz().isSubclassOf(currentClass)) {
            throw new Exception("java.lang.IllegalAccessError");
        }

        Method methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper() && resolvedClass.isSuperClassOf(currentClass) && !resolvedMethod.getName().equals("<init>")) {
            methodToBeInvoked = MethodRef.lookupMethodInClass(currentClass.getSuperClass(), methodRef.getName(), methodRef.getDescriptor());
        }
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract())
            throw new Exception("java.lang.AbstractMethodError");
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
