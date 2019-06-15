package cn.edu.seu.myjvm.instructions.loads;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/26.
 */
public class ILOAD_1 extends ILOAD_0 {
    @Override
    public void execute(Frame frame) throws Exception {
        super.iload(frame, 1);
    }
}
