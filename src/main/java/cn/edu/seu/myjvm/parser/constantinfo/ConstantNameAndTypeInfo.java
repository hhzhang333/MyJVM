package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u2;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantNameAndTypeInfo extends ConstantInfo {
    public int nameIndex;
    public int descriptorIndex;

    @Override
    public void read(InputStream inputStream) {
        nameIndex = u2.init(inputStream).getData();
        descriptorIndex = u2.init(inputStream).getData();
    }
}
