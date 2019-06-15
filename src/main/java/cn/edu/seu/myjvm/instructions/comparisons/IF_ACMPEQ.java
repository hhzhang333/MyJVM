package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by a on 2018/2/27.
 */
public class IF_ACMPEQ extends BranchInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        Object ref2 = stack.popRef();
        Object ref1 = stack.popRef();
        if (ref1 == ref2)
            super.branch(frame, super.getOffset());
    }
}
