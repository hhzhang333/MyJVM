package cn.edu.seu.myjvm.ntive.java.lang;

import cn.edu.seu.myjvm.ntive.Registry;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.LocalVars;
import cn.edu.seu.myjvm.runtime.heap.*;
import cn.edu.seu.myjvm.runtime.heap.Class;

/**
 * Created by a on 2018/3/13.
 */
public class MSystem {

    public void init() throws NoSuchMethodException {
        Registry.register("java/lang/System", "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", MSystem.class.getMethod("arrayCopy", Frame.class));
    }

    public void arrayCopy(Frame frame) throws Exception {
        LocalVars localVars = frame.getLocalVars();
        ArrayObject src = (ArrayObject) localVars.getRef(0);
        int srcPos = localVars.getInt(1);
        ArrayObject dest = (ArrayObject) localVars.getRef(2);
        int destPos = localVars.getInt(3);
        int length = localVars.getInt(4);

        if (src == null || dest == null)
            throw new Exception("java.lang.NullPointerException");

        if (!checkArrayCopy(src, dest))
            throw new Exception("java.lang.ArrayStoreException");

        if (srcPos < 0 || destPos < 0 || length < 0 || srcPos + length > src.arrayLength() ||
                destPos + length > dest.arrayLength())
            throw new Exception("java.lang.IndexOutOfBoundsException");
        ArrayObject.arrayCopy(src, dest, srcPos, destPos, length);
    }

    public boolean checkArrayCopy(Mobject src, Mobject dest) throws Exception {
        cn.edu.seu.myjvm.runtime.heap.Class srcClazz = src.getClazz();
        Class destClazz = dest.getClazz();
        if (!srcClazz.isArray() || !destClazz.isArray())
            return false;
        if (srcClazz.componentClass().isPrimitive() || destClazz.componentClass().isPrimitive())
            return srcClazz == destClazz;
        return true;
    }
}
