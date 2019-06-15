package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.constantinfo.ConstantClassInfo;

/**
 * Created by a on 2018/2/27.
 */
public class ClassRef extends SymRef {

    public static ClassRef newClassRef(ConstantPool constantPool, ConstantClassInfo classInfo) throws Exception {
        ClassRef classRef = new ClassRef();
        classRef.setCp(constantPool);
        classRef.setClassName(classInfo.getName());
        return classRef;
    }

}
