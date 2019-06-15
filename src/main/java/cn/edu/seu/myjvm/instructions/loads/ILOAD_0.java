package cn.edu.seu.myjvm.instructions.loads;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/26.
 */
public class ILOAD_0 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        iload(frame, 0);
    }

    public void iload(Frame frame, int index) throws Exception {
        int result = frame.getLocalVars().getInt(index);
        frame.getOperandStack().pushInt(result);
    }

}
