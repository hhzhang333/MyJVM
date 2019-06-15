package cn.edu.seu.myjvm.runtime.heap;

import java.lang.*;

/**
 * Created by hhzhang on 2018/2/27.
 */
public class Constant {
    private java.lang.Object value = new Object();

    public java.lang.Object getValue() {
        return value;
    }

    public void setValue(java.lang.Object value) {
        this.value = value;
    }
}
