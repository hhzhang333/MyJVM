package cn.edu.seu.myjvm.instructions.math;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by a on 2018/2/27.
 */
public class LREM extends NoOperandsInstruction{
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        long v2 = stack.popLong();
        long v1 = stack.popLong();
        if (v2 == 0) {
            throw new ArithmeticException("/ by zero");
        }
        long result = v1 % v2;
        stack.pushLong(result);
    }
}
