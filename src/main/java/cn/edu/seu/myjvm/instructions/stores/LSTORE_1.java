package cn.edu.seu.myjvm.instructions.stores;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/2/26.
 */
public class LSTORE_1 extends LSTORE {
    @Override
    public void execute(Frame frame) throws Exception {
        super.lstore(frame, 1);
    }
}
