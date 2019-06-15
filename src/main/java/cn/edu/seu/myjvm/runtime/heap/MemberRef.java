package cn.edu.seu.myjvm.runtime.heap;

import cn.edu.seu.myjvm.parser.constantinfo.ConstantMemberRefInfo;

/**
 * Created by a on 2018/2/27.
 */
public class MemberRef extends SymRef {
    private String name;
    private String descriptor;

    public void copyMemberRefInfo(ConstantMemberRefInfo refInfo) throws Exception {
        this.setClassName(refInfo.getClassName());
        String[] result = refInfo.getNameAndDescriptor();
        this.name = result[0];
        this.descriptor = result[1];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
