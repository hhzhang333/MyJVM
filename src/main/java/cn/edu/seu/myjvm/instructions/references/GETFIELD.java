package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.Slot;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Field;
import cn.edu.seu.myjvm.runtime.heap.FieldRef;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/5.
 */
public class GETFIELD extends Index16Instruction{
    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(this.getIndex().getData()).getValue();
        Field field = fieldRef.resolvedField();

        if (field.isStatic())
            throw new Exception("java.lang.IncompatibleClassChangeError");

        OperandStack stack = frame.getOperandStack();
        Mobject ref = (Mobject) stack.popRef();
        if (ref == null)
            throw new Exception("java.lang.NullPointerException");
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slot[] slots = (Slot[]) ref.getData();

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
        }
    }
}
