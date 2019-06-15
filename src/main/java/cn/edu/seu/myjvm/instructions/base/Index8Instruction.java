package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by seuzhh on 2018/2/20.
 */
public class Index8Instruction implements Instruction {

    protected u1 index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readU1();
    }

    @Override
    public void execute(Frame frame) throws Exception {

    }

    public u1 getIndex() {
        return index;
    }

    public void setIndex(u1 index) {
        this.index = index;
    }
}
