package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by a on 2018/2/27.
 */
public class IF_ICMPGE extends BranchInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int val2 = stack.popInt();
        int val1 = stack.popInt();
        if (val1 >= val2)
            super.branch(frame, super.getOffset());
    }
}
