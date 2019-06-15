package cn.edu.seu.myjvm.basictype;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class u1 {

    private short data;

    public static u1 init(InputStream inputStream) {
        u1 result = new u1();
        result.read(inputStream);
        return result;
    }

    public static byte readByte(InputStream inputStream) {
        byte[] bytes = new byte[1];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes[0];
    }

    private void read(InputStream inputStream){
        byte[] bytes = new byte[1];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data = (short) (bytes[0] & 0xff);
    }

    public void setData(byte val) {
        data = (short)(val & 0xff);
    }

    public short getData() {
        return this.data;
    }
}
