package cn.edu.seu.myjvm.instructions.constants;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Index8Instruction;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.Constant;
import cn.edu.seu.myjvm.runtime.heap.ConstantPool;

/**
 * Created by a on 2018/3/5.
 */
public class LDC extends Index8Instruction {

    @Override
    public void execute(Frame frame) throws Exception {
        initIDC(frame, this.getIndex().getData());
    }

    public void initIDC(Frame frame, int index) throws Exception {
        OperandStack stack = frame.getOperandStack();
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        Constant constant = constantPool.getConstant(index);
        stack.pushValue(constant);
//        stack.pushInt();
    }
}
