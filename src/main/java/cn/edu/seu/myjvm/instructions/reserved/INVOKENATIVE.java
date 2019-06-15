package cn.edu.seu.myjvm.instructions.reserved;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.ntive.NativeMethod;
import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.Method;

/**
 * Created by a on 2018/3/13.
 */
public class INVOKENATIVE extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        Method method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();

        NativeMethod natMethod = Registry.findNativeMethod(className, methodName, methodDescriptor);
        if (natMethod == null) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new Exception("java.lang.UnsatisfiedLinkError: " + methodInfo);
        }
        NativeMethod.nativeMethod(frame);
    }
}
