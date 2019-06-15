package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by seuzhh on 2018/2/20.
 */
public class BranchInstruction extends BranchLogic implements Instruction {
    private int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) throws Exception {
        this.offset = reader.readByte();
    }

    @Override
    public void execute(Frame frame) throws Exception {

    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
