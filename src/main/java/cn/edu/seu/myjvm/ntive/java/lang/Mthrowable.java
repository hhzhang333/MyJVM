package cn.edu.seu.myjvm.ntive.java.lang;

import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/3/16.
 */
public class Mthrowable {

    public void init() throws NoSuchMethodException {
        Registry.register("java/lang/Throwable", "fillInStackTrace", "(I)Ljava/lang/Throwable;", Mthrowable.class.getMethod("fillInStackTrace", Frame.class));
    }

    public void fillInStackTrace(Frame frame) {

    }
}
