package cn.edu.seu.myjvm.instructions.base;

import cn.edu.seu.myjvm.basictype.u1;
import cn.edu.seu.myjvm.basictype.u2;

/**
 * Created by seuzhh on 2018/2/20.
 */
public class BytecodeReader {
    private byte[] code;
    private int pc;

    public void reset(byte[] code, int pc) {
        this.code = code;
        this.pc = pc;
    }

    public u1 readU1() {
        u1 result = new u1();
        byte value = readByte();
        result.setData(value);
        return result;
    }

    public u2 readU2() {
        u2 result = new u2();
        byte[] bytes = new byte[2];
        bytes[0] = readByte();
        bytes[1] = readByte();
        result.setData(bytes);
        return result;
    }

    public u1 readU2toU1() {
        u1 result = new u1();
        byte[] bytes = new byte[2];
        bytes[0] = readByte();
        bytes[1] = readByte();
        result.setData(bytes[0]);
        return result;
    }

    public Object readShort() {
        byte[] bytes = new byte[2];
        bytes[0] = readByte();
        bytes[1] = readByte();
        return mergeBytesToShort(bytes);
    }

    public int readInt() {
        byte[] bytes = new byte[4];
        bytes[0] = readByte();
        bytes[1] = readByte();
        bytes[2] = readByte();
        bytes[3] = readByte();
        return (int)mergeBytesToInt(bytes);
    }

    public byte readByte() {
        byte result = code[pc];
        pc++;
        return result;
    }

    public void skipPadding() {
        while (this.pc %4 != 0)
            this.readU1();
    }

    public int[] readInts(int count) {
        int[] result = new int[count];
        for (int i = 0; i < count; i++)
            result[i] = this.readInt();
        return result;
    }

    private Object mergeBytesToShort(byte[] bytes) {
        int result = 0;
        for (byte byteEle: bytes) {
            result <<= 8;
            result |= (byteEle & 0xff);
        }
        return result;
    }

    private Object mergeBytesToInt(byte[] bytes) {
        long result = 0;
        for (byte byteEle: bytes) {
            result <<= 8;
            result |= (byteEle & 0xff);
        }
        return castLongToInt(result);
    }

    private int castLongToInt(long value) {
        int[] t_result = new int[2];
        t_result[0] = (int) (value >> 32);
        t_result[1] = (int) value;
        return t_result[1];
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
