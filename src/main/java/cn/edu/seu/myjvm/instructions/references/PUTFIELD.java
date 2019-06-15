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
public class PUTFIELD extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        Method currentMethod = frame.getMethod();
        Class currentClass = currentMethod.getClazz();
        ConstantPool constantPool = currentClass.getConstantPool();
        FieldRef fieldRef = (FieldRef)constantPool.getConstant(this.getIndex().getData()).getValue();
        Field field = fieldRef.resolvedField();

        if (field.isStatic())
            throw new Exception("java.lang.IncompatibleClassChangeError");
        if (field.isFinal()) {
            if (currentClass != field.getClazz() || !currentMethod.getName().equals("<init>")) {
                throw new Exception("java.lang.IllegalAccessError");
            }
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        OperandStack operandStack = frame.getOperandStack();

        switch (descriptor.charAt(0)) {
            case 'Z':case 'B':case 'C':case 'S':case 'I':
                int val = operandStack.popInt();
                Mobject ref = (Mobject) operandStack.popRef();
                if (ref == null)
                    throw new Exception("java.lang.NullPointerException");

                Slot[] slotsRef = (Slot[]) ref.getData();
                slotsRef[slotId].setNumOrRef(val);
                break;
            case 'F':
                float fal = operandStack.popFloat();
                Mobject fRef = (Mobject) operandStack.popRef();
                if (fRef == null)
                    throw new Exception("java.lang.NullPointerException");
                Slot[] fSlots = (Slot[]) fRef.getData();
                fSlots[slotId].setNumOrRef(fal);
                break;
            case 'J':
                long lal = operandStack.popInt();
                Mobject lRef = (Mobject) operandStack.popRef();
                if (lRef == null)
                    throw new Exception("java.lang.NullPointerException");
                Slot[] lSlots = (Slot[]) lRef.getData();
                lSlots[slotId].setNumOrRef(lal);
                break;
            case 'D':
                double dal = operandStack.popInt();
                Mobject dRef = (Mobject) operandStack.popRef();
                if (dRef == null)
                    throw new Exception("java.lang.NullPointerException");
                Slot[] dSlots = (Slot[]) dRef.getData();
                dSlots[slotId].setNumOrRef(dal);
                break;
            case 'L':case '[':
                int ral = operandStack.popInt();
                Mobject rRef = (Mobject) operandStack.popRef();
                if (rRef == null)
                    throw new Exception("java.lang.NullPointerException");
                Slot[] rSlots = (Slot[]) rRef.getData();
                rSlots[slotId].setNumOrRef(ral);
                break;
        }
    }
}
