package cn.edu.seu.myjvm.instructions.constants;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by seuzhh on 2018/2/20.
 */
public class SIPUSH implements Instruction {
    private int val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        val = (int)reader.readShort();
    }

    @Override
    public void execute(Frame frame) throws Exception {
        int tmp = val;
        frame.getOperandStack().pushInt(tmp);
    }
}
