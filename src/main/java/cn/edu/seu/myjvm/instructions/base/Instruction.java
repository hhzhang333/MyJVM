package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by seuzhh on 2018/2/20.
 */
public interface Instruction {
    void fetchOperands(BytecodeReader reader) throws Exception;
    void execute(Frame frame) throws Exception;
}
