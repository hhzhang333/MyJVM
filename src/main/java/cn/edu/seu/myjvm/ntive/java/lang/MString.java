package cn.edu.seu.myjvm.ntive.java.lang;

import cn.edu.seu.myjvm.ntive.NativeMethod;
import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.Mobject;
import cn.edu.seu.myjvm.runtime.heap.StringPool;

/**
 * Created by a on 2018/3/13.
 */
public class MString {

    public void init() throws NoSuchMethodException {
        Registry.register("java/lang/MString", "intern", "()Ljava/lang/MString;", MString.class.getMethod("intern", Frame.class));
    }

    public void intern(Frame frame) throws Exception {
        Mobject mobject = frame.getLocalVars().getThis();
        Mobject interned = StringPool.internString(mobject);
        frame.getOperandStack().pushRef(interned);
    }
}
