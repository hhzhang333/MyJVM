package cn.edu.seu.myjvm.ntive.java.lang;

import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/3/13.
 */
public class MFloat {
    public void init() throws NoSuchMethodException {
        Registry.register("java/lang/Float", "floatToRawIntBits", "(F)I", MFloat.class.getMethod("floatToRawIntBits", Frame.class));
    }

    public void floatToRawIntBits(Frame frame) throws Exception {
        float value = frame.getLocalVars().getFloat(0);
        frame.getOperandStack().pushInt((int) value);
    }
}
