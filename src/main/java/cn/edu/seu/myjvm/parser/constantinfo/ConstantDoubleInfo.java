package cn.edu.seu.myjvm.parser.constantinfo;

import cn.edu.seu.myjvm.parser.ConstantInfo;
import cn.edu.seu.myjvm.basictype.u4;


import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class ConstantDoubleInfo extends ConstantInfo {

    public long highValue;
    public long lowValue;

    @Override
    public void read(InputStream inputStream) {
        highValue = u4.init(inputStream).getData();
        lowValue = u4.init(inputStream).getData();
    }

    public double getValue() {
        return 0.0;
    }
}
