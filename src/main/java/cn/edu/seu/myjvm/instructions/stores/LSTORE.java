package cn.edu.seu.myjvm.instructions.stores;

import cn.edu.seu.myjvm.instructions.base.Index8Instruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/26.
 */
public class LSTORE extends Index8Instruction {
    public void lstore(Frame frame, int index) throws Exception {
        long result = frame.getOperandStack().popLong();
        frame.getLocalVars().setLong(index, result);
    }

    @Override
    public void execute(Frame frame) throws Exception {
        lstore(frame, index.getData());
    }
}
