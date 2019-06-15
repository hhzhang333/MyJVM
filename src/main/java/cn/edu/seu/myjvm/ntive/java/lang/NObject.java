package cn.edu.seu.myjvm.ntive.java.lang;

import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/13.
 */
public class NObject {

    public void init() throws NoSuchMethodException {
        Registry.register("java/lang/Object", "getClass", "()Ljava/lang/Class", NObject.class.getMethod("getClass", Frame.class));
        Registry.register("java/lang/Object", "hashCode", "()I", NObject.class.getMethod("hashCode", Frame.class));
    }

    public void getClass(Frame frame) throws Exception {
        Mobject mobject = frame.getLocalVars().getThis();
        Mobject clazz = mobject.getClazz().getjClass();
        frame.getOperandStack().pushRef(clazz);
    }

    public void hashCode(Frame frame) throws Exception {
        Mobject mobject = frame.getLocalVars().getThis();
        int hash = mobject.hashCode();
        frame.getOperandStack().pushInt(hash);
    }

    public void clone(Frame frame) throws Exception {
        Mobject mobject = frame.getLocalVars().getThis();
        Class cloneAble = mobject.getClazz().getLoader().loadClass("java/lang/Cloneable");
        if (!mobject.getClazz().isImplements(cloneAble)) {
            throw new Exception("java.lang.cloneNotSupportedException");
        }

        frame.getOperandStack().pushRef(mobject.clone());
    }
}
