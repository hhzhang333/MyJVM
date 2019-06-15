package cn.edu.seu.myjvm.runtime.heap;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by a on 2018/3/7.
 */
public class StringPool {
    public static Map<String, Mobject> internedStrings = new HashMap<>();

    public static Mobject jString(ClassLoader loader, String goStr) throws Exception {
        if (internedStrings.get(goStr) != null)
            return internedStrings.get(goStr);
        char[] chars = stringToUtf16(goStr);
        Mobject jChars = new Mobject(loader.loadClass("[C"), chars);
        Mobject jStr = loader.loadClass("java/lang/String").newObject();

        jStr.setRefVar("value", "[C", jChars);
        internedStrings.put(goStr, jStr);
        return jStr;
    }

    public static char[] stringToUtf16(String s) {
        return s.toCharArray();
    }

    public static String utf16ToString(char[] s) {
        return Arrays.toString(s);
    }

    public static String goString(Mobject jStr) {
        char[] mobject = (char[]) jStr.getRefVar("value", "[C");
        return utf16ToString(mobject);
    }

    public static Mobject internString(Mobject jStr) {
        String goString = goString(jStr);
        if (internedStrings.containsKey(goString))
            return internedStrings.get(goString);

        internedStrings.put(goString, jStr);
        return jStr;
    }
}
