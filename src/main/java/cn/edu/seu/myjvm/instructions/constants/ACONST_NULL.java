package cn.edu.seu.myjvm.instructions.constants;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

public class ACONST_NULL extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        frame.getOperandStack().pushRef(null);
    }
}
