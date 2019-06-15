package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u4;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantFloatInfo extends ConstantInfo {

    public long value;

    @Override
    public void read(InputStream inputStream) {
        value = u4.init(inputStream).getData();
    }
}
