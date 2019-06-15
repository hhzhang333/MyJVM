package cn.edu.seu.myjvm.instructions.references;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.OperandStack;
import cn.edu.seu.myjvm.runtime.heap.*;
import cn.edu.seu.myjvm.runtime.heap.Class;

/**
 * Created by a on 2018/3/7.
 */
public class MultiANewArray implements Instruction {
    private int index;
    private int dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) throws Exception {
        this.index = reader.readU2().getData();
        this.dimensions = reader.readU1().getData();
    }

    @Override
    public void execute(Frame frame) throws Exception {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index).getValue();

        Class arrClass = classRef.resolvedClass();

        OperandStack stack = frame.getOperandStack();
        int[] counts = popAndCheckCounts(stack, this.dimensions);
        Mobject arr = newMultiDimensionalArray(counts, arrClass);
        stack.pushRef(arr);
    }

    public int[] popAndCheckCounts(OperandStack stack, int dimensions) throws Exception {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = stack.popInt();
            if (counts[i] < 0)
                throw new Exception("java.lang.NegativeArraySizeException");
        }
        return counts;
    }

    public Mobject newMultiDimensionalArray(int[] counts, Class arrClass) throws Exception {
        int count = counts[0];
        ArrayObject arr = (ArrayObject) arrClass.newArray(count);

        if (counts.length > 1) {
            Mobject[] refs = arr.readRefs();
            for (int i = 0; i < refs.length; i++)
                refs[i] = newMultiDimensionalArray(purgeArray(counts, 1, counts.length), arrClass.componentClass());
        }
        return arr;
    }

    public int[] purgeArray(int[] arr, int start, int end) {
        int count = end - start + 1;
        int[] result = new int[count];
        for (int i = 0; i < count; i++)
            result[i] = arr[start + count];
        return result;
    }
}
