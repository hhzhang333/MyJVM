package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u2;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantInvokeDynamicInfo extends ConstantInfo {
    public int bootstrapMethodAttrIndex;
    public int nameAndTypeIndex;

    @Override
    public void read(InputStream inputStream) {
        bootstrapMethodAttrIndex = u2.init(inputStream).getData();
        nameAndTypeIndex = u2.init(inputStream).getData();
    }
}
