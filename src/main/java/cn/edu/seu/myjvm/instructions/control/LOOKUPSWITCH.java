package cn.edu.seu.myjvm.instructions.control;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class LOOKUPSWITCH extends BranchInstruction {
    private int defaultOffset;
    private int npairs;
    private int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt();
        this.npairs = reader.readInt();
        this.matchOffsets = reader.readInts(this.npairs * 2);
    }

    @Override
    public void execute(Frame frame) throws Exception {
        int key = frame.getOperandStack().popInt();
        for (int i = 0; i < this.npairs * 2; i += 2) {
            if (this.matchOffsets[i] == key) {
                int offset = this.matchOffsets[i+1];
                super.branch(frame, offset);
            }
        }
        super.branch(frame, this.defaultOffset);
    }
}
