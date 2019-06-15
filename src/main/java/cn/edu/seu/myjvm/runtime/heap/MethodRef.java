package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.constantinfo.ConstantMethodRefInfo;

/**
 * Created by a on 2018/2/27.
 */
public class MethodRef extends MemberRef {
    private Method method;

    public static MethodRef newMethodRef(ConstantPool constantPool, ConstantMethodRefInfo refInfo) throws Exception {
        MethodRef ref = new MethodRef();
        ref.setCp(constantPool);
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public Method resolvedMethod() throws Exception {
        if (this.method == null) {
            this.resolveMethodRef();
        }
        return this.method;
    }

    public void resolveMethodRef() throws Exception {
        Class d = this.getCp().getClazz();
        Class c = this.resolvedClass();
        if (c.isInterface())
            throw new Exception("java.lang.IncompatibleClassChangeError");
        Method method = lookupMethod(c, this.getName(), this.getDescriptor());
        if (method == null)
            throw new Exception("java.lang.NoSuchMethodError");
        if (!method.isAccessableTo(d)) {
            throw new Exception("java.lang.IllegalAccessError");
        }
        this.method = method;
    }

    public static Method lookupMethod(Class clazz, String name, String descriptor) {
        Method method = lookupMethodInClass(clazz, name, descriptor);
        if (method == null)
            method = lookupMethodInInterface(clazz.getInterfaces(), name, descriptor);
        return method;
    }

    public static Method lookupMethodInClass(Class clazz, String name, String descriptor) {
        for (Class c = clazz; c != null; c = c.getSuperClass()) {
            for (Method method: c.getMethods())
                if (method.getName().equals(name) && method.descriptor.equals(descriptor))
                    return method;
        }
        return null;
    }

    public static Method lookupMethodInInterface(Class[] ifaces, String name, String descriptor) {
        for (Class iface: ifaces) {
            for (Method method: iface.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor))
                    return method;
            }
            Method method = lookupMethodInInterface(iface.getInterfaces(), name, descriptor);
            if (method != null)
                return method;
        }
        return null;
    }

    public Method resolvedInterfaceMethod() throws Exception {
        if (this.method == null)
            resolveInterfaceMethodRef();
        return this.method;
    }

    public void resolveInterfaceMethodRef() throws Exception {
        Class d = this.getCp().getClazz();
        Class c = this.resolvedClass();
        if (!c.isInterface())
            throw new Exception("java.lang.IncompatibleClassChangeError");
        Method method = lookupInterfaceMethod(c, this.getName(), this.getDescriptor());
        if (method == null)
            throw new Exception("java.lang.NoSuchMethodError");
        if (!method.isAccessableTo(d))
            throw new Exception("java.lang.IllegalAccessError");
        this.method = method;
    }

    public Method lookupInterfaceMethod(Class iface, String name, String descriptor) {
        for (Method method: iface.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor))
                return method;
        }
        return lookupMethodInInterface(iface.getInterfaces(), name, descriptor);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
