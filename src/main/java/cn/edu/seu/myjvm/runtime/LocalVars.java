package cn.edu.seu.myjvm.runtime;

import cn.edu.seu.myjvm.runtime.heap.Mobject;

import java.util.ArrayList;

/**
 * Created by seuzhh on 2018/2/16.
 */
public class LocalVars {
    private ArrayList<Slot> localVars;
    private int maxLocals;

    public LocalVars newLocalVars(int maxLocals) {
        LocalVars localVar = new LocalVars();
        if (maxLocals > 0) {
            localVar.setMaxLocals(maxLocals);
            ArrayList<Slot> slots = new ArrayList<>();
            for (int i = 0; i < maxLocals; i++)
                slots.add(null);
            localVar.setLocalVars(slots);
            return localVar;
        }
        return null;
    }

    public void setSlot(int index, Slot slot) {
        localVars.set(index, slot);
    }


    public void setInt(int index, int val) {
        setValue(index, val);
    }

    public int getInt(int index) {
        return (int)getValue(index);
    }

    public void setFloat(int index, float val){
        setValue(index, val);
    }
    public float getFloat(int index){
        return (float)getValue(index);
    }
    public void setLong(int index, long val){
        setValue(index, val);
    }
    public long getLong(int index){
        return (long)getValue(index);
    }
    public void setDouble(int index, double val){
        setValue(index, val);
    }
    public double getDouble(int index){
        return (double)getValue(index);
    }

    public void setRef(int index, Object ref) {
        setValue(index, ref);
    }

    public Object getRef(int index) {
        return getValue(index);
    }

    private void setValue(int index, Object val) {
        Slot slot = new Slot();
        slot.setNumOrRef(val);
        localVars.set(index, slot);
    }

    private Object getValue(int index) {
        return localVars.get(index).getNumOrRef();
    }

    public ArrayList<Slot> getLocalVars() {
        return localVars;
    }

    public void setLocalVars(ArrayList<Slot> localVars) {
        this.localVars = localVars;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public Mobject getThis() {
        return (Mobject) getRef(0);
    }
}
