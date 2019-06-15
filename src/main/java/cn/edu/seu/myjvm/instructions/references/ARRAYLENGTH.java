package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.ArrayObject;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/7.
 */
public class ARRAYLENGTH extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack operandStack = frame.getOperandStack();
        ArrayObject ref = (ArrayObject) operandStack.popRef();

        if (ref == null)
            throw new Exception("java.lang.NullPointerException");

        int arrLen = ref.arrayLength();
        operandStack.pushInt(arrLen);
    }
}
