package cn.edu.seu.myjvm.instructions.stores;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by hhzhang on 2018/3/23.
 */
public class ISTORE_1 extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        istore(frame, 1);
    }

    public  void istore(Frame frame, int index) throws Exception {
        int val = frame.getOperandStack().popInt();
        frame.getLocalVars().setInt(index, val);
    }
}
