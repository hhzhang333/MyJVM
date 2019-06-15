package cn.edu.seu.myjvm.instructions.constants;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by seuzhh on 2018/2/20.
 */
public class DCONST_1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        frame.getOperandStack().pushDouble(1.0);
    }
}
