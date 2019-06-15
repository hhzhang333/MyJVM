package cn.edu.seu.myjvm.runtime;

import java.util.LinkedList;

/**
 * Created by seuzhh on 2018/2/16.
 */
public class Stack {
    private int maxSize;
    private LinkedList<Frame> top;

    public Stack newStack(int size) {
        Stack stack = new Stack();
        stack.setMaxSize(size);
        stack.setTop(new LinkedList<Frame>());
        return stack;
    }

    public void push(Frame frame) throws Exception {
        if (top.size() > this.getMaxSize())
            throw new Exception("stackoverflow");
        top.add(frame);
    }

    public Frame pop() throws Exception {
        if (top.size() == 0)
            throw new Exception("empty");
        return top.removeLast();
    }

    public boolean isEmpty() {
        return this.top.isEmpty();
    }

    public Frame top(){
        return top.getLast();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public LinkedList<Frame> getTop() {
        return top;
    }

    public void setTop(LinkedList<Frame> top) {
        this.top = top;
    }
}
