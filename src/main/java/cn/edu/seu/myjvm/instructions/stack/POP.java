package cn.edu.seu.myjvm.instructions.stack;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.Stack;

/**
 * Created by a on 2018/2/26.
 */
public class POP extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        stack.popSlot();
    }
}
