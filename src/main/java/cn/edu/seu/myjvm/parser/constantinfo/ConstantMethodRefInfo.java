package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.runtime.heap.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Method;
import cn.edu.seu.myjvm.runtime.heap.MethodRef;

/**
 * Created by a on 2018/2/27.
 */
public class ConstantMethodRefInfo extends ConstantMemberRefInfo {
    public Method method;

    public ConstantMethodRefInfo(cn.edu.seu.myjvm.parser.ConstantPool cp, int classIndex, int nameAndTypeIndex) {
        super(cp, classIndex, nameAndTypeIndex);
    }

    public MethodRef newMethodRef(ConstantPool cp, ConstantMethodRefInfo refInfo) throws Exception {
        MethodRef ref = new MethodRef();
        ref.setCp(cp);
        ref.copyMemberRefInfo(refInfo);
        return ref;
    }
}
