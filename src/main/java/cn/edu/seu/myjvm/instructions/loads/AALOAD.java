package cn.edu.seu.myjvm.instructions.loads;

import cn.edu.seu.myjvm.instructions.base.NoOperandsInstruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.ArrayObject;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/7.
 */
public class AALOAD extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        ArrayObject arrRef = (ArrayObject) stack.popRef();

        checkNotNull(arrRef);

        Mobject[] refs = arrRef.readRefs();
        checkIndex(refs.length, index);
        stack.pushRef(refs[index]);
    }

    public void checkNotNull(Mobject ref) throws Exception {
        if (ref == null)
            throw new Exception("java.lang.NullPointerException");
    }

    public void checkIndex(int arrLen, int index) throws Exception {
        if (index < 0 || index >= arrLen)
            throw new Exception("ArrayIndexOutOfBoundsException");
    }
}
