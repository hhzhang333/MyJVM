package cn.edu.seu.myjvm.runtime.heap;

import java.util.Arrays;

/**
 * Created by a on 2018/3/7.
 */
public class ArrayObject extends Mobject {

    public byte[] readBytes() {
        return (byte[]) super.getData();
    }
    public short[] readShorts() {
        return (short[]) super.getData();
    }
    public int[] readInts() {
        return (int[]) super.getData();
    }
    public long[] readLongs() {
        return (long[]) super.getData();
    }
    public char[] readChars() {
        return (char[]) super.getData();
    }
    public float[] readFloats() {
        return (float[]) super.getData();
    }
    public double[] readDoubles() {
        return (double[]) super.getData();
    }

    public Mobject[] readRefs() {
        return (Mobject[]) super.getData();
    }

    public static void arrayCopy(Object src, Object dst, int srcPos, int dstPos, int length) {
        Mobject msrc = (Mobject) src;
        arrayCopy(src, dst, srcPos, dstPos, length);
    }

    public int arrayLength() throws Exception {
        switch (super.getData().getClass().getSimpleName()) {
            case "byte[]":
                return readBytes().length;
            case "short[]":
                return readShorts().length;
            case "int[]":
                return readInts().length;
            case "long[]":
                return readLongs().length;
            case "char[]":
                return readChars().length;
            case "float[]":
                return readFloats().length;
            case "double[]":
                return readDoubles().length;
            case "Mobject[]":
                return readRefs().length;
            default:
                throw new Exception("not array");
        }
    }
}
