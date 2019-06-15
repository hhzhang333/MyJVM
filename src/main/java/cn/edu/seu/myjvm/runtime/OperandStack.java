package cn.edu.seu.myjvm.runtime;


import cn.edu.seu.myjvm.runtime.heap.Mobject;

import java.util.LinkedList;

/**
 * Created by seuzhh on 2018/2/16.
 */
public class OperandStack {
    private int size;
    private LinkedList<Slot> slots;

    public static OperandStack newOperandStack(int maxStack) {
        if (maxStack > 0) {
            OperandStack operandStack = new OperandStack();
            operandStack.setSize(maxStack);
            operandStack.setSlots(new LinkedList<Slot>());
            return operandStack;
        }
        return null;
    }

    public void pushSlot(Slot slot) {
        slots.add(slot);
    }

    public Slot popSlot() {
        return slots.removeLast();
    }

    public void pushInt(int val) throws Exception {
        pushValue(val);
    }
    public int popInt() throws Exception {
        return (int)popValue();
    }

    public void pushFloat(float val) throws Exception {
        pushValue(val);
    }
    public float popFloat() throws Exception {
        return (float)popValue();
    }
    public void pushLong(long val) throws Exception {
        pushValue(val);
    }
    public long popLong() throws Exception {
        return (long)popValue();
    }
    public void pushDouble(double val) throws Exception {
        pushValue(val);
    }
    public double popDouble() throws Exception {
        return (double)popValue();
    }

    public void pushBoolean(boolean flag) throws Exception {
        pushValue(flag);
    }

    public boolean popBoolean() throws Exception {
        return (boolean) popValue();
    }

    public void pushRef(Object ref) throws Exception {
        pushValue(ref);
    }

    public Object popRef() throws Exception {
        return popValue();
    }

    public void pushValue(Object val) throws Exception {
        if (slots.size() == size)
            throw new Exception("full");
        Slot slot = new Slot();
        slot.setNumOrRef(val);
        slots.add(slot);
    }

    public Object popValue() throws Exception {
        if (slots.size() == 0)
            throw new Exception("empty");
        Slot slot = slots.removeLast();
        return slot.getNumOrRef();
    }

    public Mobject getRefFromTop(int n) {
        return (Mobject) this.slots.get(this.slots.size() - n - 1).getNumOrRef();
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LinkedList<Slot> getSlots() {
        return slots;
    }

    public void setSlots(LinkedList<Slot> slots) {
        this.slots = slots;
    }
}
