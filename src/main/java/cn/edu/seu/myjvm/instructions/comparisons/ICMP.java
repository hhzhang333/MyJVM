package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by a on 2018/2/27.
 */
public class ICMP extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        if (v1 > v2)
            stack.pushInt(1);
        if (v1 == v2)
            stack.pushInt(0);
        if (v1 < v2)
            stack.pushInt(-1);
    }
}
