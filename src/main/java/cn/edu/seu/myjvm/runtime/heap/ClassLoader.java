package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.instructions.references.NEW;
import cn.edu.seu.myjvm.main.classpath.BasicMatch;
import cn.edu.seu.myjvm.main.classpath.ClassPath;
import cn.edu.seu.myjvm.main.classpath.Entry;
import cn.edu.seu.myjvm.parser.ClassFile;
import cn.edu.seu.myjvm.runtime.Slot;

import java.io.IOException;
import java.io.InputStream;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a on 2018/2/27.
 */
public class ClassLoader {
    private ClassPath classPath;
    private Map<String, Class> classMap;
    private boolean verboseFlag;

    public static ClassLoader newClassLoader(ClassPath classPath, boolean verboseFlag) throws Exception {
        ClassLoader classLoader = new ClassLoader();
        classLoader.setClassPath(classPath);
        classLoader.setVerboseFlag(verboseFlag);
        classLoader.setClassMap(new HashMap<String, Class>());
        classLoader.loadBasicClasses();
        classLoader.loadPrimitiveClasses();
        return classLoader;
    }

    public void loadPrimitiveClasses() {
        for (String primitiveType: new Class().getPrimitiveType().keySet())
            this.loadPrimitiveClass(primitiveType);
    }

    public void loadPrimitiveClass(String className) {
        Class clazz = new Class();
        clazz.setAccessFlages(AccessFlags.ACC_PUBLIC);
        clazz.setName(className);
        clazz.setLoader(this);
        clazz.setInitStarted(true);

        clazz.setjClass(this.classMap.get("java/lang/Class").newObject());
        clazz.getjClass().setExtra(clazz);
        this.classMap.put(className, clazz);
    }

    public void loadBasicClasses() throws Exception {
        Class jClassClass = this.loadClass("java/lang/Class");
        for (String key: classMap.keySet()) {
            Class clazz = classMap.get(key);
            if (clazz.getjClass() == null) {
                clazz.setjClass(jClassClass.newObject());
                clazz.getjClass().setExtra(clazz);
            }
        }
    }

    public Class loadClass(String name) throws Exception {
        if (classMap.containsKey(name))
            return classMap.get(name);
        Class clazz = null;
        if (name.charAt(0) == '[')
            clazz = this.loadArrayClass(name);
        else
            clazz = loadNonArrayClass(name);

        if (this.classMap.containsKey("java/lang/Class")) {
            clazz.jClass = this.classMap.get("java/lang/Class").newObject();
            clazz.jClass.setExtra(clazz);
        }
        return clazz;
    }

    private Class loadArrayClass(String name) throws Exception {
        Class clazz = new Class();
        clazz.setAccessFlages(AccessFlags.ACC_PUBLIC);
        clazz.setName(name);
        clazz.setLoader(this);
        clazz.setInitStarted(true);
        clazz.setSuperClass(this.loadClass("java/lang/Object"));
        clazz.setInterfaces(new Class[]{this.loadClass("java/lang/Cloneable"), this.loadClass("java/io/Serializable")});
        this.classMap.put(name, clazz);
        return clazz;
    }

    private Class loadNonArrayClass(String name) throws Exception {
        InputStream data = readClass(name);
        Class clazz = defineClass(data);
        link(clazz);
        return clazz;
    }

    private InputStream readClass(String name) throws IOException {
        BasicMatch basicMatch = classPath.readClass(name);
        return basicMatch.getInputStream();
    }

    private Class defineClass(InputStream inputStream) throws Exception {
        Class clazz = parseClass(inputStream);
        clazz.loader = this;
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        this.classMap.put(clazz.name, clazz);
        return clazz;
    }

    private Class parseClass(InputStream inputStream) throws Exception {
        ClassFile classFile = ClassFile.parse(inputStream);
        return Class.newClass(classFile);
    }

    public void resolveSuperClass(Class clazz) throws Exception {
        if (!clazz.name.equals("java/lang/Object")) {
            clazz.superClass = clazz.loader.loadClass(clazz.superClassName);
        }
    }

    private void resolveInterfaces(Class clazz) throws Exception {
        int interfaceCount = clazz.interfaceNames.length;
        if (interfaceCount > 0) {
            clazz.interfaces = new Class[interfaceCount];
            for (int i = 0; i < interfaceCount; i++)
                clazz.interfaces[i] = new Class();

            for (int i = 0; i < interfaceCount; i++) {
                clazz.interfaces[i] = clazz.loader.loadClass(clazz.interfaceNames[i]);
            }
        }
    }

    private void link(Class clazz) throws Exception {
        verify(clazz);
        prepare(clazz);
    }

    private void verify(Class clazz) {}
    private void prepare(Class clazz) throws Exception {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    private void calcInstanceFieldSlotIds(Class clazz) {
        int slotId = 0;
        if (clazz.superClass != null) {
            slotId = clazz.superClass.instanceSlotCount;
        }
        for (Field field: clazz.fields) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
            }
            if (field.isLongOrDouble())
                slotId++;
        }
        clazz.instanceSlotCount = slotId;
    }

    private void calcStaticFieldSlotIds(Class clazz) {
        int slotId = 0;
        for (Field field: clazz.fields) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.staticSlotCount = slotId;
    }

    private void allocAndInitStaticVars(Class clazz) throws Exception {
        clazz.staticVars = new Slot[clazz.staticSlotCount];
        for (int i = 0; i < clazz.staticSlotCount; i++)
            clazz.staticVars[i] = new Slot();

        for (Field field: clazz.fields) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }

    private void initStaticFinalVar(Class clazz, Field field) throws Exception {
        Slot[] vars = clazz.staticVars;
        ConstantPool constantPool = clazz.constantPool;
        int cpIndex = field.getConstValueIndex();
        int slotId = field.getSlotId();

        if (cpIndex > 0) {
            Object val = constantPool.getConstant(cpIndex).getValue();
            setValue(vars, slotId, val);
        }
        else
            System.out.println("hello");
//            throw new Exception("cpIndex is less than 0");
    }

    private void setValue(Slot[] slots, int index, java.lang.Object val) {
        Slot slot = new Slot();
        slot.setNumOrRef(val);
        slots[index] = slot;
    }

    public ClassPath getClassPath() {
        return classPath;
    }

    private void setClassPath(ClassPath classPath) {
        this.classPath = classPath;
    }

    public Map<String, Class> getClassMap() {
        return classMap;
    }

    public void setClassMap(Map<String, Class> classMap) {
        this.classMap = classMap;
    }

    public boolean isVerboseFlag() {
        return verboseFlag;
    }

    public void setVerboseFlag(boolean verboseFlag) {
        this.verboseFlag = verboseFlag;
    }
}
