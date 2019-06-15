package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.ClassFile;
import cn.edu.seu.myjvm.runtime.Slot;

import java.util.HashMap;

/**
 * Created by a on 2018/2/27.
 */
public class Class extends Mobject {
    int accessFlages;
    String name;
    String superClassName;
    String[] interfaceNames;
    ConstantPool constantPool;
    Field[] fields;
    Method[] methods;
    ClassLoader loader;
    Class superClass;
    Class[] interfaces;//uint
    int instanceSlotCount;
    int staticSlotCount;
    boolean initStarted;
    Slot[] staticVars;
    Mobject jClass;

    HashMap<String, String> primitiveType = initPrimitiveType();

    public HashMap<String, String> initPrimitiveType() {
        HashMap<String, String> primitive = new HashMap<>();
        primitive.put("void", "V");
        primitive.put("boolean", "Z");
        primitive.put("byte", "B");
        primitive.put("short", "S");
        primitive.put("int", "I");
        primitive.put("long", "J");
        primitive.put("char", "C");
        primitive.put("float", "F");
        primitive.put("double", "D");
        return primitive;
    }

    public static Class newClass(ClassFile cf) throws Exception {
        Class aClass = new Class();
        aClass.accessFlages = cf.getAccessFlag();
        aClass.name = cf.getClassName();
        aClass.superClassName = cf.getSuperClass();
        aClass.interfaceNames = cf.getInterfaces();
        aClass.constantPool = ConstantPool.newConstantPool(aClass, cf.getConstantPool());
        aClass.fields = Field.newField(aClass, cf.getFields());
        aClass.methods = Method.newMethods(aClass, cf.getMethods());
        return aClass;
    }

    public Class componentClass() throws Exception {
        String componentClassName = getComponentClassName(this.getName());
        return this.getLoader().loadClass(componentClassName);
    }

    public String getComponentClassName(String className) throws Exception {
        if (className.charAt(0) == '[') {
            String componentDescriptor = className.substring(1, className.length());
            return toClassName(componentDescriptor);
        }
        throw new Exception("Not array: " + className);
    }

    public String toClassName(String descriptor) throws Exception {
        if (descriptor.charAt(0) == '[')
            return descriptor;
        if (descriptor.charAt(0) == 'L')
            return descriptor.substring(1, descriptor.length());
        for (String key: primitiveType.keySet()) {
            if (key.equals(descriptor))
                return primitiveType.get(key);
        }
        throw new Exception("Invalid descriptor: " + descriptor);
    }

    public Mobject newObject() {
        return Mobject.newObject(this);
    }

    public Method getClinitMethod() {
        return this.getMethod("<clinit>", "()V", true);
    }

    public Method getStaticMethod(String name, String descriptor) {
        return getMethod(name, descriptor, true);
    }

    public boolean isAssignableFrom(Class other) throws Exception {
        if (other == this)
            return true;
        if (!other.isArray()) {
            if (!other.isInterface()) {
                if (!this.isInterface()) {
                    return other.isSubclassOf(this);
                } else
                    return other.isImplements(this);
            } else {
                if (!this.isInterface())
                    return this.isJlObject();
                else
                    return this.isSuperInterfaceOf(other);
            }
        } else {
            if (!this.isArray()) {
                if (!this.isInterface())
                    return this.isJlObject();
                else
                    return this.isJlObject() || this.isJioSerializable();
            } else {
                Class sc = other.componentClass();
                Class tc = this.componentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    public boolean isJlObject() {
        return this.getName().equals("java/lang/Object");
    }

    public boolean isJioSerializable() {
        return this.getName().equals("java/io/Serializable");
    }

    public boolean isSuperInterfaceOf(Class iface) {
        return iface.isSubclassOf(this);
    }

    public boolean isSubclassOf(Class other) {
        for (Class clazz = this.superClass; clazz != null; clazz = clazz.superClass) {
            if (clazz == other)
                return true;
        }
        return false;
    }

    public String javaName() {
        return this.name.replaceAll("/", ".");
    }

    public boolean isSuperClassOf(Class other) {
        for (Class clazz = other.superClass; clazz != null; clazz = clazz.superClass)
            if (clazz == this)
                return true;
        return false;
    }

    public boolean isImplements(Class iface) {
        for (Class clazz = this; clazz != null; clazz = clazz.superClass) {
            for (Class iclass: clazz.getInterfaces()) {
                if (iclass == iface || iclass.isSubInterfaceOf(iface))
                    return true;
            }
        }
        return false;
    }

    public boolean isSubInterfaceOf(Class iface) {
        for (Class superInterface: this.getInterfaces()) {
            if (superInterface == iface || superInterface.isSubInterfaceOf(iface))
                return true;
        }
        return false;
    }

    public boolean isPrimitive() {
        if (primitiveType.containsKey(this.name))
            return true;
        else return false;
    }

    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Class clazz = this; clazz != null; clazz = clazz.getSuperClass()) {
            for (Field field: clazz.getFields()) {
                if (field.isStatic() == isStatic && field.getName().equals(name) && field.getDescriptor().equals(descriptor))
                    return field;
            }
        }
        return null;
    }

    public boolean isAccessableTo(Class other) {
        return this.isPublic() || (this.getPacketName().equals(other.getPacketName()));
    }

    public boolean isInterface() {
        return 0 != (this.accessFlages & AccessFlags.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0!= (this.accessFlages & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlages & AccessFlags.ACC_SUPER);
    }

    public String getPacketName() {
        if (this.name.lastIndexOf("/") > 0)
            return this.name.substring(0, this.name.lastIndexOf("/"));
        return "";
    }

    public Mobject newArray(int count) throws Exception {
        if (!this.isArray()) {
            throw new Exception("not array class: " + this.getName());
        } else
            return newObject(this, count);
    }

    public Method getInstanceMethod(String name, String descriptor) {
        return this.getMethod(name, descriptor, false);
    }

    public Method getMethod(String name, String descriptor, boolean isStatic) {
        for (Class c = this; c != null; c = c.superClass) {
            for (Method method: c.getMethods()) {
                if (method.isStatic() == isStatic && method.getName().equals(name) && method.getDescriptor().equals(descriptor))
                    return method;
            }
        }
        return null;
    }

    private static Mobject newObject(Class clazz, int count) {
        Mobject mobject = new Mobject();
        mobject.setClazz(clazz);
        mobject.setData(new Object[count]);
        return mobject;
    }

    public Class arrayClass() throws Exception {
        String arrayClassName = this.getArrayClassName(this.getName());
        return this.getLoader().loadClass(arrayClassName);
    }

    public String getArrayClassName(String className) {
        return "[" + toDescriptor(className);
    }

    public String toDescriptor(String className) {
        if (className.charAt(0) == '[')
            return className;
        if (!primitiveType.get(className).isEmpty())
            return primitiveType.get(className);
        return "L" + className + ";";
    }

    public Method getMainMethod() {
        return this.getMethod("main", "([Ljava/lang/String;)V", true);
    }

    public boolean isArray() {
        return this.getName().charAt(0) == '[';
    }

    public boolean initStarted() {
        return this.initStarted;
    }

    public void startInit() {
        this.initStarted = true;
    }

    public boolean isPublic() {
        return 0 != (this.accessFlages & AccessFlags.ACC_PUBLIC);
    }

    public int getAccessFlages() {
        return accessFlages;
    }

    public void setAccessFlages(int accessFlages) {
        this.accessFlages = accessFlages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public void setInterfaceNames(String[] interfaceNames) {
        this.interfaceNames = interfaceNames;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    public void setLoader(ClassLoader loader) {
        this.loader = loader;
    }

    public Class getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Class superClass) {
        this.superClass = superClass;
    }

    public Class[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class[] interfaces) {
        this.interfaces = interfaces;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public Slot[] getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(Slot[] staticVars) {
        this.staticVars = staticVars;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public void setInitStarted(boolean initStarted) {
        this.initStarted = initStarted;
    }

    public Mobject getjClass() {
        return jClass;
    }

    public void setjClass(Mobject jClass) {
        this.jClass = jClass;
    }

    public HashMap<String, String> getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(HashMap<String, String> primitiveType) {
        this.primitiveType = primitiveType;
    }
}
