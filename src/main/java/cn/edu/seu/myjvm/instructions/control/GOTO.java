package cn.edu.seu.myjvm.instructions.control;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class GOTO extends BranchInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        super.branch(frame, super.getOffset());
    }
}
