package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.instructions.Interpreter;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.Slot;
import cn.edu.seu.myjvm.runtime.Thread;
import cn.edu.seu.myjvm.runtime.heap.Method;

/**
 * Created by a on 2018/3/6.
 */
public class MethodInvokeLogic {
    public static void invokeMethod(Frame invokeFrame, Method method) throws Exception {
        Thread thread = invokeFrame.getThread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);

        int argSlot = method.getArgSlotCount();
        if (argSlot > 0) {
            for (int i = argSlot - 1; i >= 0; i--) {
                Slot slot = invokeFrame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }
//        Interpreter.loop(thread, false);
//        Interpreter.interpreter(newFrame.getMethod(), false, invokeFrame.getLocalVars());
    }
}
