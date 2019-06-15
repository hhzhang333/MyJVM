package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.constantinfo.ConstantFieldRefInfo;

/**
 * Created by a on 2018/2/27.
 */
public class FieldRef extends MemberRef {
    private Field field;

    public static FieldRef newFieldRef(ConstantPool constantPool, ConstantFieldRefInfo refInfo) throws Exception {
        FieldRef ref = new FieldRef();
        ref.setCp(constantPool);
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public Field resolvedField() throws Exception {
        if (this.field == null) {
            this.resolvedFieldRef();
        }
        return this.field;
    }

    public void resolvedFieldRef() throws Exception {
        Class clazz = this.getCp().getClazz();
        Class loadClass = this.resolvedClass();
        Field field = lookpuField(loadClass, this.getName(), this.getDescriptor());
        if (field == null)
            throw new Exception("java.lang.NoSuchFieldError");
        if (!field.isAccessableTo(clazz))
            throw new Exception("java.lang.IllegalAccessError");
        this.field = field;
    }

    public Field lookpuField(Class clazz, String name, String descriptor) {
        for (Field field: clazz.getFields()) {
            if (field.name.equals(name) && field.descriptor.equals(descriptor))
                return field;
        }
        for (Class c: clazz.getInterfaces()) {
            if (lookpuField(c, name, descriptor) != null)
                return lookpuField(c, name, descriptor);
        }
        if (clazz.superClass != null)
            return lookpuField(clazz.superClass, name, descriptor);
        return null;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
