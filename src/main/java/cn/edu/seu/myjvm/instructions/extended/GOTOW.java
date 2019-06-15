package cn.edu.seu.myjvm.instructions.extended;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class GOTOW extends BranchInstruction{
    private int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) throws Exception {
        this.offset = reader.readInt();
    }

    @Override
    public void execute(Frame frame) throws Exception {
        super.branch(frame, this.offset);
    }
}
