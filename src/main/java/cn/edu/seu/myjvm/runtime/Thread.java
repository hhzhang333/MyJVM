package cn.edu.seu.myjvm.runtime;

import cn.edu.seu.myjvm.runtime.heap.Method;

import java.util.LinkedList;

/**
 * Created by seuzhh on 2018/2/16.
 */
public class Thread {
    private int pc;
    private Stack stack;

    private final static int defaultStackSize = 1024;

    public static Thread newThread() {
        Thread thread = new Thread();
        thread.setStack(new Stack().newStack(defaultStackSize));
        return thread;
    }

    public Frame newFrame(Method method) {
        return Frame.newFrame(this, method);
    }

    public void pushFrame(Frame frame) throws Exception {
        stack.push(frame);
    }
    public Frame popFrame() throws Exception {
        return stack.pop();
    }

    public Frame currentFrame() {
        return stack.top();
    }

    public boolean isStackEmpty() {
        return this.getStack().isEmpty();
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }
}
