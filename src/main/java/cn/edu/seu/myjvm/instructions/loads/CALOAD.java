package cn.edu.seu.myjvm.instructions.loads;

import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.ArrayObject;

/**
 * Created by a on 2018/3/7.
 */
public class CALOAD extends AALOAD {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int index = stack.popInt();
        ArrayObject arrRef = (ArrayObject) stack.popRef();

        checkNotNull(arrRef);

        char[] chars = arrRef.readChars();
        checkIndex(chars.length, index);
        stack.pushInt(chars[index]);
    }
}
