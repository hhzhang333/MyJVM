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
public class PUTSTATIC extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        Method currentMethod = frame.getMethod();
        Class currentClass = currentMethod.getClazz();
        ConstantPool constantPool = currentClass.getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(this.getIndex().getData()).getValue();
        Field field = fieldRef.resolvedField();
        Class clazz = field.getClazz();

        if (!clazz.initStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }


        if (!field.isStatic())
            throw new Exception("java.lang.IncompatibleClassChangeError");
        if (field.isFinal()) {
            if (currentClass != clazz || !currentMethod.getName().equals("<clinit>")) {
                throw new Exception("java.lang.IllegalAccessError");
            }
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slot[] slots = clazz.getStaticVars();
        OperandStack operandStack = frame.getOperandStack();

        switch (descriptor.charAt(0)) {
            case 'Z':case 'B':case 'C':case 'S':case 'I':
                slots[slotId].setNumOrRef(operandStack.popInt());
                break;
            case 'F':
                slots[slotId].setNumOrRef(operandStack.popFloat());
                break;
            case 'J':
                slots[slotId].setNumOrRef(operandStack.popLong());
                break;
            case 'D':
                slots[slotId].setNumOrRef(operandStack.popDouble());
                break;
            case 'L':case '[':
                slots[slotId].setNumOrRef(operandStack.popRef());
        }
    }
}
