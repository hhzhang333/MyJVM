package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.instructions.base.MethodInvokeLogic;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.*;
import cn.edu.seu.myjvm.runtime.heap.Class;

/**
 * Created by a on 2018/3/6.
 */
public class INVOKEVIRTUAL extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        Class currentClass = frame.getMethod().getClazz();
        ConstantPool constantPool = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.getIndex().getData()).getValue();
        Method resolvedMethod = methodRef.resolvedMethod();
        if (resolvedMethod.isStatic())
            throw new Exception("java.lang.IncompatibleClassChangeError");
        Mobject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            if (methodRef.getName().equals("println")) {
                println(frame.getOperandStack(), methodRef.getDescriptor());
                return;
            }
            throw new Exception("java.lang.NullPointerException");
        }
        if (resolvedMethod.isProtected() && resolvedMethod.getClazz().isSuperClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPacketName().equals(currentClass.getPacketName()) &&
                ref.getClazz() != currentClass && !ref.getClazz().isSubclassOf(currentClass)) {
            throw new Exception("java.lang.IllegalAccessError");
        }

        Method methodToBeInvoke = MethodRef.lookupMethodInClass(resolvedMethod.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoke == null || methodToBeInvoke.isAbstract())
            throw new Exception("java.lang.AbstractMethodError");
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoke);
    }

    public void println(OperandStack stack, String descriptor) throws Exception {
        switch (descriptor) {
            case "(Z)V":
                System.out.println(stack.popInt() != 0);
                break;
            case "(C)V":
                System.out.println(stack.popInt());
                break;
            case "(I)V": case "(B)V": case "(S)V":
                System.out.println(stack.popInt());
                break;
            case "(F)V":
                System.out.println(stack.popFloat());
                break;
            case "(J)V":
                System.out.println(stack.popLong());
                break;
            case "(D)V":
                System.out.println(stack.popDouble());
                break;
            case "(Ljava/lang/String;)V":
                System.out.println(stack.popRef());
                break;
            default:
                throw new Exception("println: " + descriptor);
        }
    }
}
