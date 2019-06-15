package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;

/**
 * Created by hhzhang on 2018/3/23.
 */
public class DCMPG extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) throws Exception {
        dcmp(frame, true);
    }

    protected void dcmp(Frame frame, boolean gFlag) throws Exception {
        OperandStack stack = frame.getOperandStack();
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();
        if (v1 > v2)
            stack.pushInt(1);
        if (v1 == v2)
            stack.pushInt(0);
        if (v1 < v2)
            stack.pushInt(-1);
        if (gFlag)
            stack.pushInt(1);
        else
            stack.pushInt(-1);
    }
}
