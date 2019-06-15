package cn.edu.seu.myjvm.instructions.math;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.LocalVars;

/**
 * Created by a on 2018/2/27.
 */
public class IINC implements Instruction {

    private int increment;
    private u1 index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readU1();
        this.increment = reader.readInt();
    }

    @Override
    public void execute(Frame frame) throws Exception {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(this.index.getData());
        val += this.increment;
        localVars.setInt(this.index.getData(), val);
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public u1 getIndex() {
        return index;
    }

    public void setIndex(u1 index) {
        this.index = index;
    }
}
