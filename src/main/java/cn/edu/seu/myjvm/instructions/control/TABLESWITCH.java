package cn.edu.seu.myjvm.instructions.control;

import cn.edu.seu.myjvm.instructions.base.BranchInstruction;
import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class TABLESWITCH extends BranchInstruction{
    private int defaultOffset;
    private int low;
    private int high;
    private int[] jumpOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt();
        this.low = reader.readInt();
        this.high = reader.readInt();
        int jumpOffsetsCount = this.high - this.low + 1;
        this.jumpOffsets = reader.readInts(jumpOffsetsCount);
    }

    @Override
    public void execute(Frame frame) throws Exception {
        int index = frame.getOperandStack().popInt();

        int offset;
        if (index >= this.low && index <= this.high) {
            offset = this.jumpOffsets[index - this.low];
        } else {
            offset = this.defaultOffset;
        }
        super.branch(frame, offset);
    }
}
