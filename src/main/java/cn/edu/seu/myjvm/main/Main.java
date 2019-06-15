package cn.edu.seu.myjvm.main;

import cn.edu.seu.myjvm.instructions.Interpreter;
import cn.edu.seu.myjvm.main.classpath.BasicMatch;
import cn.edu.seu.myjvm.main.classpath.ClassPath;
import cn.edu.seu.myjvm.parser.ClassFile;
import cn.edu.seu.myjvm.parser.ClassReader;
import cn.edu.seu.myjvm.parser.MemberInfo;
import cn.edu.seu.myjvm.runtime.Frame;
import cn.edu.seu.myjvm.runtime.heap.Class;
import cn.edu.seu.myjvm.runtime.heap.ClassLoader;
import cn.edu.seu.myjvm.runtime.heap.Method;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a on 2018/2/28.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Cmd cmd = Cmd.parseCmd(args);
        if (cmd.isVersionFlag())
            System.out.println("version 0.0.1");
        else if (cmd.isHelpFlag() || cmd.getClazz().isEmpty())
            Cmd.printUsage();
        else startJVM(cmd);


    }

    private static void startJVM(Cmd cmd) throws Exception {
        ClassPath classPath = ClassPath.parse(cmd.getXjreOption(), cmd.getCpOption());
        System.out.println("classpath: " + cmd.getCpOption() + " class: " + cmd.getClazz() + " args: " + cmd.getArgs().toString());
        ClassLoader classLoader = ClassLoader.newClassLoader(classPath, cmd.isVerboseClassFlag());

        String className = cmd.getClazz().replace(".", "/");
        Class mainClazz = classLoader.loadClass(className);
        Method mainMethod = mainClazz.getMainMethod();

        if (mainMethod != null) {
            List<String> results = cmd.getArgs();
            String[] args = results.toArray(new String[results.size()]);
            Interpreter.interpreter(mainMethod, cmd.isVerboseInstFlag(), args);
        }
    }

    private static MemberInfo getMainMethod(ClassFile classFile) throws Exception {
        for (MemberInfo memberInfo: classFile.getMethods()) {
            if (memberInfo.name().equals("main") && memberInfo.descriptor().equals("([Ljava/lang/String;)V"))
                return memberInfo;
        }
        return null;
    }

    public static ClassFile loadClass(String className, ClassPath classPath) throws Exception {
        BasicMatch basicMatch = classPath.readClass(className);
        return ClassReader.read(basicMatch.getInputStream());
    }
}
