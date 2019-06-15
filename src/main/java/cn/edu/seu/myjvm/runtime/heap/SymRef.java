package cn.edu.seu.myjvm.runtime.heap;

/**
 * Created by a on 2018/2/27.
 */
public class SymRef {
    private ConstantPool cp;
    private String className;
    private Class clazz;

    public ConstantPool getCp() {
        return cp;
    }

    public Class resolvedClass() throws Exception {
        if (this.clazz == null)
            this.resolveClassRef();
        return this.clazz;
    }

    public void resolveClassRef() throws Exception {
        Class clazz = this.cp.getClazz();
        Class loadClass = clazz.loader.loadClass(this.className);
        if (!loadClass.isAccessableTo(clazz)) {
            throw new Exception("java.lang.IllegalAccessError");
        }
        this.clazz = loadClass;
    }

    public void setCp(ConstantPool cp) {
        this.cp = cp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
