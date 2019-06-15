package cn.edu.seu.myjvm.instructions.stores;

import cn.edu.seu.myjvm.instructions.base.Index8Instruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by hhzhang on 2018/3/23.
 */
public class ISTORE extends Index8Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        istore(frame, this.index.getData());
    }

    public  void istore(Frame frame, int index) throws Exception {
        int val = frame.getOperandStack().popInt();
        frame.getLocalVars().setInt(index, val);
    }
}
