package cn.edu.seu.myjvm.instructions;

import cn.edu.seu.myjvm.instructions.base.BytecodeReader;
import cn.edu.seu.myjvm.instructions.base.Instruction;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.Thread;
import cn.edu.seu.myjvm.runtime.heap.*;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassLoader;

/**
 * Created by a on 2018/2/27.
 */
public class Interpreter {

    private static final String outFormat = "%s.%s() %d %s %s";

    public static void interpreter(Method method, boolean logInst, String[] args) throws Exception {
        Thread thread = Thread.newThread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);

//        Mobject jArgs = createArgsArray(method.getClazz().getLoader(), args);
//        frame.getLocalVars().setRef(0, jArgs);

        loop(thread, logInst);
    }

    public static Mobject createArgsArray(ClassLoader loader, String[] args) throws Exception {
        Class stringClass = loader.loadClass("java/lang/String");
        ArrayObject argsArr = (ArrayObject) stringClass.arrayClass().newArray(args.length);
        Mobject[] jArgs = argsArr.readRefs();
        for (int i = 0; i < args.length; i++)
            jArgs[i] = StringPool.jString(loader, args[i]);
        return argsArr;
    }

    public static void loop(Thread thread, boolean logInst) throws Exception {
        Frame frame = thread.currentFrame();
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            int pc = frame.getNextPC();
            if (pc == frame.getMethod().getCode().length)
                return;
            thread.setPc(pc);
            //error
            reader.reset(shortToByte(frame.getMethod().getCode()), pc);
            byte opcode = reader.readByte();
            Instruction inst = Factory.newInstruction(opcode & 0xff);
            inst.fetchOperands(reader);
            frame.setNextPC(reader.getPc());

            if (logInst)
                logInstruction(frame, inst);
            inst.execute(frame);
            if (thread.isStackEmpty()) {
                break;
            }
        }
    }

    public static void logInstruction(Frame frame, Instruction inst) {
        Method method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        int pc = frame.getThread().getPc();
        System.out.println(String.format(outFormat, className, methodName, pc, inst, inst));
    }

    private static byte[] shortToByte(short[] code) {
        byte[] results = new byte[code.length];
        for (int i = 0; i < code.length; i++) {
            byte[] t_result = new byte[2];
            t_result[0] = (byte) (code[i] >> 8);
            results[i] = (byte) (code[i]);
        }
        return results;
    }
}
