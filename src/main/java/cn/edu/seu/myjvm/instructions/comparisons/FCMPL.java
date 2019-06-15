package cn.edu.seu.myjvm.instructions.comparisons;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/27.
 */
public class FCMPL extends FCMPG {
    @Override
    public void execute(Frame frame) throws Exception {
        fcmp(frame, false);
    }
}
