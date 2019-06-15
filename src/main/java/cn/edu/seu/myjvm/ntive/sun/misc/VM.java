package cn.edu.seu.myjvm.ntive.sun.misc;

import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.Mobject;
import cn.edu.seu.myjvm.runtime.heap.StringPool;

/**
 * Created by a on 2018/3/15.
 */
public class VM {
    public void init() throws NoSuchMethodException {
        Registry.register("sun/misc/VM", "initiablize", "()V", VM.class.getMethod("initialize", Frame.class));
    }

    public void initialize(Frame frame) throws Exception {
        Mobject clazz = frame.getMethod().getClazz();
        Class vmClazz = (Class) clazz;
        Object savedProps = clazz.getRefVar("saveProps", "Ljava/util/Properties;");
        Mobject key = StringPool.jString(vmClazz.getLoader(), "foo");
        Mobject value = StringPool.jString(vmClazz.getLoader(), "bra");

        frame.getOperandStack().pushRef(savedProps);
        frame.getOperandStack().pushRef(key);
        frame.getOperandStack().pushRef(value);

        Class pClazz = vmClazz.getLoader().loadClass("java/lang/Properties");

    }
}
