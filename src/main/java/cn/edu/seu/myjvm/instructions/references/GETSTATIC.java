package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.ClassInitLogic;
import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.Slot;
import cn.edu.seu.myjvm.runtime.heap.*;
import cn.edu.seu.myjvm.runtime.heap.Class;

/**
 * Created by a on 2018/3/5.
 */
public class GETSTATIC extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(this.getIndex().getData()).getValue();
        Field field = fieldRef.resolvedField();
        Class clazz = field.getClazz();
        if (!clazz.initStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }

        if (!field.isStatic()) {
            throw new Exception("java.lang.IncompatibleClassChangeError");
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slot[] slots = clazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();

        switch (descriptor.charAt(0)) {
            case 'Z':case 'B':case 'C':case 'S':case 'I':
                stack.pushInt((int)slots[slotId].getNumOrRef());
                break;
            case 'F':
                stack.pushFloat((float)slots[slotId].getNumOrRef());
                break;
            case 'J':
                stack.pushLong((long)slots[slotId].getNumOrRef());
                break;
            case 'D':
                stack.pushDouble((double)slots[slotId].getNumOrRef());
                break;
            case 'L':case '[':
                stack.pushRef((Object) slots[slotId].getNumOrRef());
                break;
            default:
        }
    }
}
