package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by a on 2018/2/27.
 */
public class FCMPG extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        fcmp(frame, true);
    }

    protected void fcmp(Frame frame, boolean flag) throws Exception {
        OperandStack stack = frame.getOperandStack();
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();
        if (v1 > v2)
            stack.pushInt(1);
        if (v1 == v2)
            stack.pushInt(0);
        if (v1 < v2)
            stack.pushInt(-1);
        if (flag)
            stack.pushInt(1);
        else
            stack.pushInt(-1);
    }
}
