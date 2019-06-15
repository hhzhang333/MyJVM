package cn.edu.seu.myjvm.runtime.heap;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;

/**
 * Created by a on 2018/3/6.
 */
public class MethodDescriptor {
    private ArrayList<String> parameterTypes;
    private String returnType;

    public MethodDescriptor() {
        parameterTypes = new ArrayList<>();
    }

    public void addParameterType(String paramType) {
        this.parameterTypes.add(paramType);
    }

    public ArrayList<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(ArrayList<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
