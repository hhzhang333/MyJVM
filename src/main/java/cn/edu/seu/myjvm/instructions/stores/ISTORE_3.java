package cn.edu.seu.myjvm.instructions.stores;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by hhzhang on 2018/3/23.
 */
public class ISTORE_3 extends ISTORE_1 {
    @Override
    public void execute(Frame frame) throws Exception {
        istore(frame, 3);
    }
}
