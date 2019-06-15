package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.ClassInitLogic;
import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.instructions.base.MethodInvokeLogic;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Method;
import cn.edu.seu.myjvm.runtime.heap.MethodRef;

/**
 * Created by a on 2018/3/6.
 */
public class INVOKESTATIC extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef)constantPool.getConstant(this.getIndex().getData()).getValue();
        Method method = methodRef.resolvedMethod();
        if (!method.getClazz().initStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), method.getClazz());
            return;
        }

        if (!method.isStatic())
            throw new Exception("java.lang.IncompatibleClassChangeError");
        MethodInvokeLogic.invokeMethod(frame, method);
    }
}
