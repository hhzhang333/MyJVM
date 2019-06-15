package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class IFGT extends BranchInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        int val = frame.getOperandStack().popInt();
        if (val > 0)
            super.branch(frame, super.getOffset());
    }
}
