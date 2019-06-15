package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.runtime.Slot;

/**
 * Created by a on 2018/3/5.
 */
public class Mobject {
    private Class clazz;
    private Object data;
    private Object extra;

    public static Mobject newObject(Class clazz) {
        Mobject object = new Mobject();
        object.setClazz(clazz);
        object.setData(new Slot[clazz.getInstanceSlotCount()]);
        return object;
    }

    public Mobject() {}

    public Mobject(Class clazz, Object data) {
        this.clazz = clazz;
        this.data = data;
    }

    public void setRefVar(String name, String descriptor, Mobject ref) {
        Field field = this.getClazz().getField(name, descriptor, false);
        Slot[] slots = (Slot[]) this.getData();
        Slot slot = new Slot();
        slot.setNumOrRef(ref);
        slots[field.getSlotId()] = slot;
    }

    public Object getRefVar(String name, String descriptor) {
        Field field = this.clazz.getField(name, descriptor, false);
        Slot[] slots = (Slot[]) this.data;
        return slots[field.getSlotId()].getNumOrRef();
    }

    public Mobject clone() {
        Mobject mobject = new Mobject();
        mobject.setClazz(this.clazz);
        mobject.setData(this.cloneData());
        return mobject;
    }

    public Object cloneData() {
        Object[] elements = (Object[]) this.getData();
        Object[] elements2 = new Object[elements.length];
        ArrayObject.arrayCopy(elements, elements2, 0, 0, elements.length);
        return elements2;
    }

    public boolean isInstanceOf(Class clazz) throws Exception {
        return clazz.isAssignableFrom(this.clazz);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
