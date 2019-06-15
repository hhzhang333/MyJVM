package cn.edu.seu.myjvm.ntive.java.lang;

import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.LocalVars;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassLoader;
import cn.edu.seu.myjvm.runtime.heap.Mobject;
import cn.edu.seu.myjvm.runtime.heap.StringPool;

/**
 * Created by a on 2018/3/13.
 */
public class MClass {

    public void init() throws NoSuchMethodException {
        Registry.register("java/lang/Class", "getPrimitiveClass", "(Ljava/lang/String)Ljava/lang/Class;", MClass.class.getMethod("getPrimitiveClass", Frame.class));
        Registry.register("java/lang/Class", "getName0", "()Ljava/lang/String;", MClass.class.getMethod("getName0", Frame.class));;
        Registry.register("java/lang/Class", "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", MClass.class.getMethod("desiredAssertionStatus0", Frame.class));
    }

    public void getPrimitiveClass(Frame frame) throws Exception {
        Mobject nameObj = (Mobject) frame.getLocalVars().getRef(0);
        String name = StringPool.goString(nameObj);

        ClassLoader loader = frame.getMethod().getClazz().getLoader();
        Mobject clazz = loader.loadClass(name).getjClass();
        frame.getOperandStack().pushRef(clazz);
    }

    public void getName0(Frame frame) throws Exception {
        Mobject mobject = (Mobject) frame.getLocalVars().getThis();
        cn.edu.seu.myjvm.runtime.heap.Class clazz = (cn.edu.seu.myjvm.runtime.heap.Class) mobject.getExtra();
        java.lang.String name = clazz.javaName();
        Mobject nameObj = StringPool.jString(clazz.getLoader(), name);

        frame.getOperandStack().pushRef(nameObj);
    }

    public void  desiredAssertionStatus0(Frame frame) throws Exception {
        frame.getOperandStack().pushBoolean(false);
    }

    public void isInterface(Frame frame) throws Exception {
        LocalVars vars = frame.getLocalVars();
        Mobject mobject = vars.getThis();
        Class clazz = (Class) mobject.getExtra();
        OperandStack stack = frame.getOperandStack();
        stack.pushBoolean(clazz.isInterface());
    }

    public void isPrimitive(Frame frame) throws Exception {
        LocalVars vars = frame.getLocalVars();
        Mobject mobject = vars.getThis();
        Class clazz = (Class) mobject.getExtra();
        OperandStack stack = frame.getOperandStack();
        stack.pushBoolean(clazz.isPrimitive());
    }
}
