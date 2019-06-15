package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.MemberInfo;
import cn.edu.seu.myjvm.parser.attribute.ConstantValueAttribute;

/**
 * Created by a on 2018/2/27.
 */
public class Field extends ClassMember {

    private int slotId;
    private int constValueIndex;

    public static Field[] newField(Class clazz, MemberInfo[] cfFields) throws Exception {
        Field[] fields = new Field[cfFields.length];

        for (int i = 0; i < cfFields.length; i++)
            fields[i] = new Field();

        for (int i = 0; i < cfFields.length; i++) {
            fields[i].clazz = clazz;
            fields[i].copyMemberInfo(cfFields[i]);
            fields[i].copyAttributes(cfFields[i]);
        }
        return fields;
    }

    public void copyAttributes(MemberInfo cfField) {
        if (cfField.getConstantValueAttribute() != null)
            this.constValueIndex = cfField.getConstantValueAttribute().getConstantValueIndex();
    }

    public boolean isLongOrDouble() {
        return this.descriptor.equals("J") || this.descriptor.equals("D");
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }

    public void setConstValueIndex(int constValueIndex) {
        this.constValueIndex = constValueIndex;
    }
}
