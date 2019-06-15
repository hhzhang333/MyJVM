package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.basictype.u2;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantMethodHandleInfo extends ConstantInfo {
    public short referenceKind;
    public int referenceIndex;

    @Override
    public void read(InputStream inputStream) {
        referenceKind = u1.init(inputStream).getData();
        referenceIndex = u2.init(inputStream).getData();
    }
}
