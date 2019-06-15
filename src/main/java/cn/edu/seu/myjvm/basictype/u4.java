package cn.edu.seu.myjvm.basictype;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by seuzhh on 2018/2/14.
 */
public class u4 {

    public long data;

    public static u4 init(InputStream inputStream) {
        u4 result = new u4();
        result.read(inputStream);
        return result;
    }

    private void read(InputStream inputStream) {
        byte[] bytes = new byte[4];
        try {
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long result = 0;
        for (byte byteEle: bytes) {
            result <<= 8;
            result |= (byteEle & 0xff);
        }
        this.data = result;
    }

    public int castToSigned() {
        return (int)data;
    }

    public long getData() {
        return data;
    }
    public void setData(long data) {
        this.data = data;
    }
}
