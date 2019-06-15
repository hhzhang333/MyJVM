package cn.edu.seu.myjvm.ntive;

import cn.edu.seu.myjvm.runtime.Frame;

/**
 * Created by a on 2018/3/12.
 */
public class NativeMethod {

    public static NativeMethod emptyNativeMethod() {
        return new NativeMethod();
    }

    public static void nativeMethod(Frame frame) {}
}
