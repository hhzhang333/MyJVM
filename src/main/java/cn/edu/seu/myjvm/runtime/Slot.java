package cn.edu.seu.myjvm.runtime;

/**
 * Created by seuzhh on 2018/2/18.
 */
public class Slot {
    private Object num;

    public Object getNumOrRef() {
        return num;
    }

    public void setNumOrRef(Object num) {
        this.num = num;
    }
}
