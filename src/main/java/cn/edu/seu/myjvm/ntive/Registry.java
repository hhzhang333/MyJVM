package cn.edu.seu.myjvm.ntive;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by a on 2018/3/12.
 */
public class Registry {
    public static Map<String, Object> registry = new HashMap<String, Object>();

    public static void register(String className, String methodName, String methodDescriptor, Method method) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key, method);
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        if (registry.containsKey(key))
            return (NativeMethod) registry.get(key);

        if (methodDescriptor.equals("()V") && methodName.equals("registerNatives"))
            return NativeMethod.emptyNativeMethod();
        return null;
    }
}
