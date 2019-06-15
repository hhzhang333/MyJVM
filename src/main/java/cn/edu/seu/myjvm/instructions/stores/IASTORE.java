package cn.edu.seu.myjvm.instructions.stores;

import cn.edu.seu.myjvm.instructions.loads.AALOAD;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.ArrayObject;
import cn.edu.seu.myjvm.runtime.heap.Mobject;

/**
 * Created by a on 2018/3/7.
 */
public class IASTORE extends AALOAD {
    @Override
    public void execute(Frame frame) throws Exception {
        OperandStack stack = frame.getOperandStack();
        int ref = stack.popInt();
        int index = stack.popInt();
        ArrayObject arrRef = (ArrayObject) stack.popRef();

        checkNotNull(arrRef);

        int[] refs = arrRef.readInts();
        checkIndex(refs.length, index);
        refs[index] = ref;
    }
}
