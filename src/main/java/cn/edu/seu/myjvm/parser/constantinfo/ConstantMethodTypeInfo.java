package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u2;

import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantMethodTypeInfo extends ConstantInfo {
    public int descType;

    @Override
    public void read(InputStream inputStream) {
        descType = u2.init(inputStream).getData();
    }
}
