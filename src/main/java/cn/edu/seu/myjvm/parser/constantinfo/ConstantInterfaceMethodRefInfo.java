package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantPool;

/**
 * Created by a on 2018/2/27.
 */
public class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo {
    public ConstantInterfaceMethodRefInfo(ConstantPool cp, int classIndex, int nameAndTypeIndex) {
        super(cp, classIndex, nameAndTypeIndex);
    }
}
