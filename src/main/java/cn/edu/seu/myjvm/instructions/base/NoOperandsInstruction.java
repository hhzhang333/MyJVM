package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by seuzhh on 2018/2/20.
 */
public class NoOperandsInstruction implements Instruction {
    @Override
    public void fetchOperands(BytecodeReader reader){

    }

    @Override
    public void execute(Frame frame) throws Exception {

    }
}
