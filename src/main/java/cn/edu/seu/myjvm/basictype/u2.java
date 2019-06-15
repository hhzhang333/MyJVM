package cn.edu.seu.myjvm.basictype;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class u2 {

    private int data;

    public static u2 init(InputStream inputStream) {
        u2 result = new u2();
        result.read(inputStream);
        return result;
    }

    private void read(InputStream inputStream) {
        byte[] bytes = new byte[2];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //按照字节重新恢复原来数值
        setData(bytes);
    }

    public void setData(byte[] bytes) {
        int result = 0;
        for (byte byteEle: bytes) {
            result <<= 8;
            result |= (byteEle & 0xff);
        }
        this.data = result;
    }

    public int getData() {
        return data;
    }

}
