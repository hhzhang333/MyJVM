package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class BranchLogic {
    public void branch(Frame frame, int offset) {
        int PC = frame.getThread().getPc();
        int nextPC = PC + offset;
        frame.setNextPC(nextPC);
    }
}
