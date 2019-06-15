package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.instructions.Interpreter;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.Thread;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.Method;

/**
 * Created by a on 2018/3/6.
 */
public class ClassInitLogic {
    public static void initClass(Thread thread, Class clazz) throws Exception {
        clazz.startInit();
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }

    public static void scheduleClinit(Thread thread, Class clazz) throws Exception {
        Method clinit = clazz.getClinitMethod();
        if (clinit != null) {
            Frame frame = thread.newFrame(clinit);
            thread.pushFrame(frame);
//            Interpreter.loop(thread, false);
        }
    }

    public static void initSuperClass(Thread thread, Class clazz) throws Exception {
        if (!clazz.isInterface()) {
            Class superClass = clazz.getSuperClass();
            if (superClass != null && !superClass.initStarted())
                initClass(thread, superClass);
        }
    }
}
