package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u2;
import cn.edu.seu.myjvm.parser.ConstantPool;
import cn.edu.seu.myjvm.runtime.heap.Constant;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantMemberRefInfo extends ConstantInfo {
    public ConstantPool cp;
    public int classIndex;
    public int nameAndTypeIndex;

    public ConstantMemberRefInfo(ConstantPool cp, int classIndex, int nameAndTypeIndex) {
        this.cp = cp;
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public void read(InputStream inputStream) {
        classIndex = u2.init(inputStream).getData();
        nameAndTypeIndex = u2.init(inputStream).getData();
    }

    public String getClassName() throws Exception {
        return cp.getClassName(this.classIndex);
    }

    public String[] getNameAndDescriptor() throws Exception {
        return cp.getNameAndType(this.nameAndTypeIndex);
    }
}
