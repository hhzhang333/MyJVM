package cn.edu.seu.myjvm.instructions.control;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/3/6.
 */
public class RETURN extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        frame.getThread().popFrame();
    }
}
