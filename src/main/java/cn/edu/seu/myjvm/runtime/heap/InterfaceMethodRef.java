package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.constantinfo.ConstantInterfaceMethodRefInfo;

/**
 * Created by a on 2018/2/27.
 */
public class InterfaceMethodRef extends MemberRef {
    private Method method;

    public static InterfaceMethodRef newInterfaceMethodRef(ConstantPool constantPool, ConstantInterfaceMethodRefInfo refInfo) throws Exception {
        InterfaceMethodRef ref = new InterfaceMethodRef();
        ref.setCp(constantPool);
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
