package cn.edu.seu.myjvm.instructions.conversions;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by a on 2018/2/27.
 */
public class I2L extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int i = stack.popInt();
        long l = (long)i;
        stack.pushLong(l);
    }
}
