package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.ClassInitLogic;
import cn.edu.seu.myjvm.instructions.base.Index16Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassRef;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;

/**
 * Created by a on 2018/3/5.
 */
public class NEW extends Index16Instruction {
    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) cp.getConstant(this.getIndex().getData()).getValue();
        Class clazz = classRef.resolvedClass();

        if (!clazz.initStarted()) {
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }


        if (clazz.isInterface() || clazz.isAbstract())
            throw new Exception("java lang instantiationError");

        Object ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }
}
