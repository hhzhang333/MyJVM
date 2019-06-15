package cn.edu.seu.myjvm.runtime;

import cn.edu.seu.myjvm.runtime.heap.Method;

import java.util.LinkedList;

/**
 * Created by seuzhh on 2018/2/16.
 */
public class Frame {
    private LocalVars localVars;
    private OperandStack operandStack;
    private Thread thread;
    private Method method;
    private int nextPC;

    public static Frame newFrame(Thread thread, Method method){
        Frame frame = new Frame();
        frame.setThread(thread);
        frame.setMethod(method);
        frame.setLocalVars(new LocalVars().newLocalVars(method.getMaxLocals()));
        frame.setOperandStack(new OperandStack().newOperandStack(method.getMaxStack()));
        return frame;
    }

    public void revertNextPC() {
        this.nextPC = this.getThread().getPc();
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public void setLocalVars(LocalVars localVars) {
        this.localVars = localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public void setOperandStack(OperandStack operandStack) {
        this.operandStack = operandStack;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getNextPC() {
        return nextPC;
    }

    public void setNextPC(int nextPC) {
        this.nextPC = nextPC;
    }
}
