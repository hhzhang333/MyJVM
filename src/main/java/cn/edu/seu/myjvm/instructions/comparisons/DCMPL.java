package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by hhzhang on 2018/3/23.
 */
public class DCMPL extends DCMPG {

    @Override
    public void execute(Frame frame) throws Exception {
        dcmp(frame, false);
    }
}
