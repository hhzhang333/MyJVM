package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/26.
 */
public class Index16Instruction implements Instruction {

    private u2 index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readU2();
    }

    @Override
    public void execute(Frame frame) throws Exception {

    }

    public u2 getIndex() {
        return index;
    }

    public void setIndex(u2 index) {
        this.index = index;
    }
}
